package com.webserver.servlet;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 将user.dat文件中的用户信息读取出来并输出
 * @author adminitartor
 *
 */
public class ShowAllUserDemo {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf
			= new RandomAccessFile("user.dat","r");
		
		//读取若干个100字节(每个用户信息)
		for(int i=0;i<raf.length()/100;i++){
			//读取用户名
			//1先读取32字节
			byte[] data = new byte[32];
			raf.read(data);
			//2将该字节内容还原为字符串
			String username = new String(data,"utf-8").trim();
			
			//读密码
			raf.read(data);
			String password = new String(data,"utf-8").trim();
			
			//读昵称
			raf.read(data);
			String nickname = new String(data,"utf-8").trim();
			
			//读年龄
			int age = raf.readInt();
			
			System.out.println(username+","+password+","+nickname+","+age);
			System.out.println("pos:"+raf.getFilePointer());
		}
		
	}
}








