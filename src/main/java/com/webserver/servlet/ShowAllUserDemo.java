package com.webserver.servlet;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * ��user.dat�ļ��е��û���Ϣ��ȡ���������
 * @author adminitartor
 *
 */
public class ShowAllUserDemo {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf
			= new RandomAccessFile("user.dat","r");
		
		//��ȡ���ɸ�100�ֽ�(ÿ���û���Ϣ)
		for(int i=0;i<raf.length()/100;i++){
			//��ȡ�û���
			//1�ȶ�ȡ32�ֽ�
			byte[] data = new byte[32];
			raf.read(data);
			//2�����ֽ����ݻ�ԭΪ�ַ���
			String username = new String(data,"utf-8").trim();
			
			//������
			raf.read(data);
			String password = new String(data,"utf-8").trim();
			
			//���ǳ�
			raf.read(data);
			String nickname = new String(data,"utf-8").trim();
			
			//������
			int age = raf.readInt();
			
			System.out.println(username+","+password+","+nickname+","+age);
			System.out.println("pos:"+raf.getFilePointer());
		}
		
	}
}








