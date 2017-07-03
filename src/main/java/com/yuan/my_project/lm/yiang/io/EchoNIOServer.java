package com.yuan.my_project.lm.yiang.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.yuan.my_project.lm.yiang.io.service.NIOService;


/**
 * 
 * @author yuanjuntao
 *
 */
public class EchoNIOServer {

	public void serve(int port){
		try {
			System.out.println("NIOServer Listening for connection on port:"+port);
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket ss = ssc.socket();
			
			InetSocketAddress address = new InetSocketAddress(port);
			//Bind server to port
			ss.bind(address);
			
			Selector selector = Selector.open();
			//Register the channel with the selector to be interested in new client connection that get accepted
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			
			for(;;){
				try{
					//Blocked until something is selected
					selector.select();
				}catch(IOException e1){
					e1.printStackTrace();
					break;
				}
				
				Set<SelectionKey> keys = selector.keys();
				Iterator<SelectionKey> it = keys.iterator();
				while(it.hasNext()){
					SelectionKey key = it.next();
					//after get then remove the selectionKey from iterator
					it.remove();
					try{
						if(key.isAcceptable()){
							ServerSocketChannel server = (ServerSocketChannel)key.channel();
							//Accept client connection
							SocketChannel client = server.accept();
							System.out.println("Accept connection from client:"+client);
							client.configureBlocking(false);
							//Register connection to selector and set ByteBuffer
							client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
						}
						//check for SelectionKey for read
						if(key.isReadable()){
							SocketChannel client = (SocketChannel)key.channel();
							ByteBuffer output = (ByteBuffer)key.attachment();
							//read data to bytebuffer
							client.read(output);
							
							NIOService nioService = new NIOService();
							nioService.helloClient(output.toString());
						}
						//check for SelectionKey for writable
						if(key.isWritable()){
							SocketChannel client = (SocketChannel)key.channel();
							ByteBuffer output = (ByteBuffer)key.attachment();
							output.flip();
							//write data from bytebuffer to channel
							client.write(output);
							output.compact();
						}
					}catch (IOException e) {
						// TODO: handle exception
						key.cancel();
						key.channel().close();
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
