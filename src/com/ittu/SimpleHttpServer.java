package com.ittu;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/*
 * ���Է�����
 * a simple static http server
 * @author www.zuidaima.com
 */
public class SimpleHttpServer {
	
	public static long callnum=0;
	public static String serverName="Server";
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("�������� ServerName port");
		
		HttpServer server = null;
		if(args[0]==null ||args[1]==null){
			System.out.println("��������ȱʧ");
		}else{
			System.out.println("���ʵ�ַ��http://127.0.0.1:"+args[1]+"/req");
			serverName = args[0];
			server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[1])), 0);
		}		
		
		server.createContext("/req", new MyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	static class MyHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			callnum = callnum+1;
			String response = serverName + " request   "+callnum;
			System.out.println(response);
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

}