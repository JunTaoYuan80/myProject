package com.yuan.my_project.lm.yiang.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;

import com.yuan.my_project.lm.yiang.io.service.IOService;

/**
 * 
 * @author yuanjuntao
 *
 */
public class EchoIOServer {
	
	
	public void serve(int port) throws IOException{
		//Bind server to port
		final ServerSocket socket = new ServerSocket(port);
		try{
			for(;;){
				System.out.println("IOServer Listening for connection on port:"+port);
				//Blocked until client connection is accepted
				final Socket clientSocket = socket.accept();
				System.out.println("Accepted connection from "+clientSocket);
				
				//create new thread to handle client connection
				new Thread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
							String line = reader.readLine();
							StringBuilder builder = new StringBuilder("client address:");
							builder.append(clientSocket.getInetAddress().getHostAddress());
							builder.append("client content:");
							while(StringUtils.isNotBlank(line)){
								builder.append(line);
							}
							
							IOService service = new IOService();
							String rslt = service.helloClient(builder.toString());
							writer.println(rslt);
							writer.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							try {
								socket.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					
				}).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
