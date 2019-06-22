package com.webserver.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务端相关配置信息
 * @author adminitartor
 *
 */
public class ServerContext {
	/**
	 * Servlet映射
	 * key:请求路径
	 * value:Servlet的完全限定名
	 */
	private static Map<String,String> servletMapping = new HashMap<String,String>();
	static{
		initServletMapping();
	}
	/**
	 * 初始化Servlet映射
	 */
	private static void initServletMapping(){
		
		servletMapping.put("/myweb/reg", "com.webserver.servlet.RegServlet");
		servletMapping.put("/myweb/login", "com.webserver.servlet.LoginServlet");
	}
	/**
	 * 根据请求路径获取对应的Servlet名字
	 * @param url
	 * @return
	 */
	public static String getServlet(String url){
		return servletMapping.get(url);
	}
	
	public static void main(String[] args) {
		/*
		 * 根据请求获取处理该请求的Servlet的名字
		 */
		String name = getServlet("/myweb/reg");
		System.out.println(name);
	}
}







