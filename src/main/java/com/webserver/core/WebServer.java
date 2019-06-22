package com.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer����
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
			 * ��ʱ���ṩ�ͻ��˵Ķ�����ӡ��Ƚ�һ��
			 * �����������Ϻ���֧�ָù��ܡ�
			 */
			while(true){
				System.out.println("�ȴ��ͻ�������...");
				Socket socket = server.accept();
				System.out.println("һ���ͻ���������!");
				//�����̴߳���ͻ�������
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
















