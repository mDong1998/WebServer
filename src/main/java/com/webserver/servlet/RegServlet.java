package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 处理注册业务
 * @author adminitartor
 *
 */
public class RegServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("RegServlet:开始处理注册业务...");
		//1获取用户的注册信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.parseInt(request.getParameter("age"));
		System.out.println("username:"+username);
		System.out.println("password:"+password);
		System.out.println("nickname:"+nickname);
		System.out.println("age:"+age);
		/*
		 * 2:将用户信息写入文件user.dat中
		 * 每个用户占用100字节，其中用户名，密码，
		 * 昵称为字符串，各占32字节，年龄为int值占
		 * 固定的4字节。
		 */
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw");
		){			
			//先将指针移动到文件末尾
			raf.seek(raf.length());
			//写用户名
			byte[] data = username.getBytes("utf-8");
			//将数组扩容到32
			data = Arrays.copyOf(data, 32);
			//一次性写出32字节
			raf.write(data);			
			//写密码
			data = password.getBytes("utf-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			//写昵称
			data = nickname.getBytes("utf-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			//写年龄
			raf.writeInt(age);
			//注册完毕!
			//3给用户响应注册成功页面
			forward("myweb/reg_success.html", request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println("RegServlet:处理注册业务完毕!");
	}
}







