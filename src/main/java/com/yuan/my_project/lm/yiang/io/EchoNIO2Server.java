package com.yuan.my_project.lm.yiang.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * @author yuanjuntao
 *
 */
public class EchoNIO2Server {

	public void serve(int port){
		try {
			System.out.println("NIO2Server Listening for connection on port:"+port);
			final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
			InetSocketAddress address = new InetSocketAddress(port);
			//Bind Server to port
			serverChannel.bind(address);
			final CountDownLatch latch = new CountDownLatch(1);
			
			//Start to accept new client connection. Once one is accepted the CompletionHandler will get called
			serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

				@Override
				public void completed(AsynchronousSocketChannel channel, Object attachment) {
					// TODO Auto-generated method stub
					serverChannel.accept(null, this);
					ByteBuffer buffer = ByteBuffer.allocate(100);
					// Trigger a read operation on the Channel, 
					//the given CompletionHandler will be notified once something was read
					channel.read(buffer, buffer, new EchoCompletionHandler(channel));
				}

				@Override
				public void failed(Throwable exc, Object attachment) {
					// TODO Auto-generated method stub
					try {
						serverChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally{
						latch.countDown();
					}
				}
			});
			
			try {
				latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final class EchoCompletionHandler implements CompletionHandler<Integer, ByteBuffer>{
		
		private final AsynchronousSocketChannel channel;
		EchoCompletionHandler(AsynchronousSocketChannel channel) {
			// TODO Auto-generated constructor stub
			this.channel = channel;
		}

		@Override
		public void completed(Integer result, ByteBuffer attachment) {
			// TODO Auto-generated method stub
			attachment.flip();
			
			channel.write(attachment, attachment, new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					// TODO Auto-generated method stub
					if(attachment.hasRemaining()){
						//Trigger again a write operation if something is left in the ByteBuffer
						channel.write(attachment, attachment, this);
					}else{
						attachment.compact();
						//Trigger a read operation on the channel, 
						//the given CompletionHandler will be notified once something was read 
						channel.read(attachment, attachment, EchoCompletionHandler.this);
					}
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					// TODO Auto-generated method stub
					try {
						channel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			// TODO Auto-generated method stub
			try {
				channel.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
