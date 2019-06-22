package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * �����¼ҵ��
 * @author adminitartor
 *
 */
public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("LoginServlet:��ʼ�����¼");
		//��ȡ�û���¼��Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","r");
		){
			//���أ�Ĭ�ϱ�ʾ��¼ʧ��
			boolean check = false;
			for(int i=0;i<raf.length()/100;i++){
				//�Ƚ�ָ���ƶ���������¼�û�����λ��
				raf.seek(i*100);
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data).trim();
				if(name.equals(username)){
					//�ҵ����û�
					raf.read(data);
					String pwd = new String(data).trim();
					if(pwd.equals(password)){
						//��¼�ɹ�
						check = true;
					}
					break;
				}
			}//ѭ������			
			//����check���ж��Ƿ��¼�ɹ�
			if(check){
				forward("myweb/login_success.html", request, response);
			}else{
				forward("myweb/login_fail.html", request, response);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("LoginServlet:�����¼���");
	}
}




