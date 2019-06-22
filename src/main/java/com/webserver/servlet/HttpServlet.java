package com.webserver.servlet;

import java.io.File;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * Servlet�ĳ���
 * ��������Servlet�����еĹ���
 * @author adminitartor
 *
 */
public abstract class HttpServlet {
	/**
	 * ��������ķ���
	 * @param request  �������
	 * @param response ��Ӧ
	 */
	public abstract void service(HttpRequest request,HttpResponse response);
	
	/**
	 * ��ת��ָ��ҳ��
	 * �÷�����Tomcat����ת�����еķ�����ͨ��
	 * request���Ի�ȡ��ת�����������ڲ���תʹ�á�
	 * ����ֻ����������Ӧ��Ӧ·����ʾ��ҳ����ͻ��ˡ�
	 * @param url
	 * @param request
	 * @param response
	 */
	public void forward(String url,HttpRequest request,HttpResponse response){
		File file = new File("webapps/"+url);
		response.setEntity(file);
	}
}






