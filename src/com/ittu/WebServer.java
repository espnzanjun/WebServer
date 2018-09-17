package com.ittu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    /** 默认使用的服务器Socket端口号 */
    public static final int HTTP_PORT = 8811;
    private ServerSocket serverSocket;
    
    public static long T1 = 0;

    public static String ServerName = "";
    
    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Web Server startup on  " + port);
            while (true) {
            	WebServer.T1=WebServer.T1+1;
                Socket socket = serverSocket.accept();
                // 通过线程的方式来处理客户的请求
                new Processor(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WebServer类的启动方法，可以通过命令行参数指定当前Web服务器所使用的端口号。
     */
    public static void main(String[] argv) throws Exception {
    	System.out.println("请输入服务器名称和端口号，如 Server1 8080");
        WebServer server = new WebServer();
        if (argv.length == 1) {
            server.startServer(Integer.parseInt(argv[0]));
        }if (argv.length == 2) {
        	ServerName  = argv[0];
        	server.startServer(Integer.parseInt(argv[1]));
        }
        else {
            server.startServer(WebServer.HTTP_PORT);
        }
    }
}