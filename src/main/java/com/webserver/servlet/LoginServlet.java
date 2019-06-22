package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 处理登录业务
 * @author adminitartor
 *
 */
public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("LoginServlet:开始处理登录");
		//获取用户登录信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","r");
		){
			//开关，默认表示登录失败
			boolean check = false;
			for(int i=0;i<raf.length()/100;i++){
				//先将指针移动到该条记录用户名的位置
				raf.seek(i*100);
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data).trim();
				if(name.equals(username)){
					//找到此用户
					raf.read(data);
					String pwd = new String(data).trim();
					if(pwd.equals(password)){
						//登录成功
						check = true;
					}
					break;
				}
			}//循环结束			
			//基于check来判定是否登录成功
			if(check){
				forward("myweb/login_success.html", request, response);
			}else{
				forward("myweb/login_fail.html", request, response);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("LoginServlet:处理登录完毕");
	}
}




