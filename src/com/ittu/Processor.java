package com.ittu;

import java.io.*;
import java.net.Socket;

/**
 * ����һ��HTTP�û�������߳��ࡣ
 */
public class Processor extends Thread {
    private PrintStream out;
    private InputStream input;
    /** Ĭ�ϵķ�������ŷ������ݵ�Ŀ¼ */
    public static final String WEB_ROOT = "/library/webserver/documents";
    public Processor(Socket socket) {
        try {
            input = socket.getInputStream();
            out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
    	testNginx();
    	//wol();
    }
    
    
    public void wol() {
        try {
            String baseUrl = parse(input);
            System.out.println("input: "+baseUrl);
        	UserVO userVo =  new UserVO(); 
            if(null!=baseUrl && !"".equals(baseUrl) && (baseUrl.indexOf("/wol")>-1)){
            	String aa= baseUrl.split("$")[0];
            	if (aa!=null){
            		userVo.setPcIP(aa.split("&")[0].split("=")[1]);
            		userVo.setPcMac(aa.split("&")[1].split("=")[1]);
            	}

            	System.out.println(userVo.getPcIP()+"   "+userVo.getPcMac() );
            	this.exec(userVo);
            	System.out.println("OK");
            }
            //readFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void testNginx() {
        try {        	
        	sendSucess(WebServer.T1);
        	System.out.println(WebServer.T1);
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �����ͻ�������������HTTP��������Ƿ���HTTPЭ�����ݵģ� �ͷ������ͻ�����Ҫ�����ļ������֣����ҷ����ļ�����
     */
    public String parse(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String inputContent = in.readLine();
        if (inputContent == null || inputContent.length() == 0) {
            sendError(400, "Client invoke error");
            return null;
        }
        // �����ͻ������HTTP��Ϣ������������������ĸ��ļ���
        // ��������HTTP����Ӧ���������֡�

        String request[] = inputContent.split(" ");
        if (request.length != 3) {
            sendError(400, "Client invoke error");
            return null;
        }
        // ��һ����������ķ���
        String method = request[0];
        // �ڶ�������������ļ���
        String fileName = request[1];
        // ����������HTTP�汾��
        String httpVersion = request[2];
        System.out.println("Method: " + method + ", file name: " + fileName + ", HTTP version: " + httpVersion);
        return fileName;
    }

    /**
     * �������һ���ļ�������
     */
    public void readFile(String fileName) throws IOException {
        File file = new File(Processor.WEB_ROOT + fileName);
        if (!file.exists()) {
            sendError(404, "File Not Found");
            return;
        }
        // ���ļ������ݶ�ȡ��in�����С�
        InputStream in = new FileInputStream(file);
        byte content[] = new byte[(int) file.length()];
        in.read(content);
        out.println("HTTP/1.0 200 sendFile");
        out.println("Content-length: " + content.length);
        out.println();
        out.write(content);
        out.flush();
        out.close();
        in.close();
    }

    /**
     * ���ʹ������Ϣ
     */
    public void sendError(int errNum, String errMsg) {
        out.println("HTTP/1.0 " + errNum + " " + errMsg);
        out.println("Content-type: text/html");
        out.println();
        out.println("<html>");
        out.println("<head><title>Error " + errNum + "--" + errMsg + "</title></head>");
        out.println("<h1>" + errNum + " " + errMsg + "</h1>");
        out.println("</html>");
        out.println();
        out.flush();
        out.close();
    }

    /**
     * ������Ϣ
     */
    public void sendSucess(long num) {
        out.println("HTTP/1.0 " + WebServer.ServerName +"  "+ "Request " + num);
        out.println("Content-type: text/html");
        out.println();
        out.println("<html>");
        out.println("<head><title>"+WebServer.ServerName +"  "+  "Request " + num+"</title></head>");
        out.println("<h1>" + WebServer.ServerName +"  "+  "Request " + num + "</h1>");
        out.println("</html>");
        out.println();
        out.flush();
        out.close();
    }
    
	public void exec(UserVO uservo) {
		try {
			//cmd="cmd /c dir";
			//cmd="wol 3C:97:0E:AB:D8:2D -i 192.168.21.116";
			String cmd="wol "+ uservo.getPcMac() +" -i " + uservo.getPcIP();
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmd); // cmd /c calc //Process pr =  
												// rt.exec("D:\\xunlei\\project.aspx");
			BufferedReader input = new BufferedReader(new InputStreamReader(pr
					.getInputStream(), "GBK"));
			String line = null;
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			int exitVal = pr.waitFor();
			System.out.println("Exited with error code " + exitVal);
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	} 

}
