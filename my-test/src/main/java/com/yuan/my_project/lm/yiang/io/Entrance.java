package com.yuan.my_project.lm.yiang.io;

import java.io.IOException;

public class Entrance {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						EchoIOServer ioServer = new EchoIOServer();
						ioServer.serve(9060);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					EchoNIOServer nioServer = new EchoNIOServer();
					nioServer.serve(9061);
				}
			}).start();
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					EchoNIO2Server nio2Server = new EchoNIO2Server();
					nio2Server.serve(9062);
				}
			}).start();
	}

}
