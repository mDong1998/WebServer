package com.webserver.core;

import java.util.HashMap;
import java.util.Map;

/**
 * ��������������Ϣ
 * @author adminitartor
 *
 */
public class ServerContext {
	/**
	 * Servletӳ��
	 * key:����·��
	 * value:Servlet����ȫ�޶���
	 */
	private static Map<String,String> servletMapping = new HashMap<String,String>();
	static{
		initServletMapping();
	}
	/**
	 * ��ʼ��Servletӳ��
	 */
	private static void initServletMapping(){
		
		servletMapping.put("/myweb/reg", "com.webserver.servlet.RegServlet");
		servletMapping.put("/myweb/login", "com.webserver.servlet.LoginServlet");
	}
	/**
	 * ��������·����ȡ��Ӧ��Servlet����
	 * @param url
	 * @return
	 */
	public static String getServlet(String url){
		return servletMapping.get(url);
	}
	
	public static void main(String[] args) {
		/*
		 * ���������ȡ����������Servlet������
		 */
		String name = getServlet("/myweb/reg");
		System.out.println(name);
	}
}







