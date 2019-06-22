package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * ����ע��ҵ��
 * @author adminitartor
 *
 */
public class RegServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("RegServlet:��ʼ����ע��ҵ��...");
		//1��ȡ�û���ע����Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.parseInt(request.getParameter("age"));
		System.out.println("username:"+username);
		System.out.println("password:"+password);
		System.out.println("nickname:"+nickname);
		System.out.println("age:"+age);
		/*
		 * 2:���û���Ϣд���ļ�user.dat��
		 * ÿ���û�ռ��100�ֽڣ������û��������룬
		 * �ǳ�Ϊ�ַ�������ռ32�ֽڣ�����Ϊintֵռ
		 * �̶���4�ֽڡ�
		 */
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw");
		){			
			//�Ƚ�ָ���ƶ����ļ�ĩβ
			raf.seek(raf.length());
			//д�û���
			byte[] data = username.getBytes("utf-8");
			//���������ݵ�32
			data = Arrays.copyOf(data, 32);
			//һ����д��32�ֽ�
			raf.write(data);			
			//д����
			data = password.getBytes("utf-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			//д�ǳ�
			data = nickname.getBytes("utf-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			//д����
			raf.writeInt(age);
			//ע�����!
			//3���û���Ӧע��ɹ�ҳ��
			forward("myweb/reg_success.html", request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println("RegServlet:����ע��ҵ�����!");
	}
}







