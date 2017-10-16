package javaQQ;

import java.net.*;
import java.io.*; 
import java.awt.*;  
//根据所得到对方的IP信息建立一个TCP连接，进行TCP通信
public class TCPConnect extends Thread{
	ServerSocket listen;  
	int port;  
	String name;
	String name2;
	StateFrame sf;  

	public TCPConnect(int tcpPort, String name, StateFrame sf1) {
		this.port = tcpPort;   
		this.name = sf1.name;
		this.sf=sf1;  
		this.start(); 
	}

	public void run(){   
		try {
			//创建ServerSocket对象，该对象通过将客户端的套接字对象与服务器端的套接字对象连接起来，达到连接的目的
			listen=new ServerSocket(port); //port和客户端呼叫的端口号相同   			
		} catch (Exception e) {        
				e.printStackTrace();    
		  }         
		try {     
			while(true){
				//调用accept（）方法开始监听，等待连接，accept（）方法会产生一个阻塞；
				//accept（）方法会返回一个和客户端Socket对象相连接的Socket对象
				Socket client = listen.accept();  
				//启动一个私聊主界面
				MainChatFrame mf=new MainChatFrame(client,name); 
				//mf.setVisible(true);       
			}     
		} catch (HeadlessException e) {    
				// TODO Auto-generated catch block     
				e.printStackTrace();     
			} catch (IOException e) {     
					// TODO Auto-generated catch block    
					e.printStackTrace();    
			   }
	}   
	/*public void processMsg(String str){   
		sf.groupchat.output.add(str);
	}*/   
}
	

