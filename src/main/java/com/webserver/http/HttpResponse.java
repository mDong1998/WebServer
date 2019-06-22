package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * ��Ӧ����
 * �����ÿһ��ʵ�����ڱ�ʾҪ���ͻ��˷��͵�ʵ����Ӧ
 * ���ݡ�
 * @author adminitartor
 *
 */
public class HttpResponse {
	/*
	 * ״̬�������Ϣ����
	 */
	//״̬����
	private int statusCode = 200;
	
	
	/*
	 * ��Ӧͷ�����Ϣ����
	 */
	//key:��Ӧͷ����     value:��Ӧͷ��ֵ
	private Map<String,String> headers = new HashMap<String,String>();
	
	
	
	/*
	 * ��Ӧ���������Ϣ����
	 */
	//��Ӧ��ʵ���ļ�
	private File entity;
	
	//�Ϳͻ���������ص�����
	private Socket socket;
	private OutputStream out;
	
	
	public HttpResponse(Socket socket){
		try {
			this.socket = socket;
			this.out = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����ǰ��Ӧ�����������һ��HTTP��׼��Ӧ��ʽ
	 * ���͸��ͻ���
	 */
	public void flush(){
		/*
		 * 1:����״̬��
		 * 2:������Ӧͷ
		 * 3:������Ӧ����
		 */
		System.out.println("HttpResponse:��ʼ������Ӧ...");
		sendStatusLine();
		sendHeaders();
		sendContent();
		System.out.println("HttpResponse:������Ӧ���!");
	}
	/**
	 * ����״̬��
	 */
	private void sendStatusLine(){
		try {
			System.out.println("��ʼ����״̬��...");
			//����״̬�� 
			String line = "HTTP/1.1"+" "+statusCode+" "+HttpContext.getStatusReason(statusCode);
			out.write(line.getBytes("ISO8859-1"));
			out.write(13);//written CR
			out.write(10);//written LF			
			System.out.println("״̬�з������!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ������Ӧͷ
	 */
	private void sendHeaders(){
		try {
			System.out.println("��ʼ������Ӧͷ...");
			//������Ӧͷ
			Set<Entry<String,String>> entrySet 
									= headers.entrySet();
			for(Entry<String,String> header : entrySet){
				String line = 
				  header.getKey()+": "+header.getValue();
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
			}			
			//��������CRLF��ʾ��Ӧͷ���ַ������
			out.write(13);//written CR
			out.write(10);//written LF
			System.out.println("��Ӧͷ�������!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ������Ӧ����
	 */
	private void sendContent(){
		try (
			FileInputStream fis = new FileInputStream(entity);	
		){
			System.out.println("��ʼ������Ӧ����...");
			//������Ӧ����
			int len = -1;
			byte[] data = new byte[1024*10];
			while((len = fis.read(data))!=-1){
				out.write(data, 0, len);
			}
			System.out.println("������Ӧ�������!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public File getEntity() {
		return entity;
	}
	/**
	 * ������Ӧ���ĵ�ʵ���ļ��������õ�ͬʱ���Զ�
	 * ��Ӷ�Ӧ���ĵ�������Ӧͷ:
	 * Content-Type��Content-Length
	 * @param entity
	 */
	public void setEntity(File entity) {
		this.entity = entity;
		//���ݸ�ʵ���ļ�������Ӧͷ
		//����ʵ���ļ��ĺ�׺��ȡ��Ӧ�Ľ�������
		String fileName = entity.getName();
		int index = fileName.lastIndexOf(".");
		String ext = fileName.substring(index+1);
		String type = HttpContext.getMimeType(ext);
		this.headers.put("Content-Type", type);
		this.headers.put("Content-Length", String.valueOf(entity.length()));
		
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * ������Ӧͷ
	 * @param name  ��Ӧͷ������
	 * @param value ��Ӧͷ��Ӧ��ֵ
	 */
	public void putHeader(String name,String value){
		headers.put(name, value);
	}
	
	public String getHeader(String name){
		return headers.get(name);
	}
	
	
	
	
}








