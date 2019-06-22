package com.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer主类
 * @author adminitartor
 *
 */
public class WebServer {
	private ServerSocket server;
	private ExecutorService threadPool;
	
	public WebServer(){
		try {
			server = new ServerSocket(8088);
			threadPool = Executors.newFixedThreadPool(30);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		try {
			/*
			 * 暂时不提供客户端的多次连接。先将一次
			 * 请求处理测试完毕后再支持该功能。
			 */
			while(true){
				System.out.println("等待客户端连接...");
				Socket socket = server.accept();
				System.out.println("一个客户端连接了!");
				//启动线程处理客户端请求
				ClientHandler handler 
					= new ClientHandler(socket);		
				threadPool.execute(handler);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.start();
	}
}
















