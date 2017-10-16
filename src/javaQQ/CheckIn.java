package javaQQ;

import java.io.*; 
import java.net.*;  
//实现多对一的通信，即多个客户端与一个服务器端通信，在群聊中当一个用户发送信息时服务器端便启动一个线程接收信息
public class CheckIn extends Thread{  
	ServerSocket Check;  
	Socket client;  
	UserInfo[] userinfo;  
	StateFrame sf;     
	public final static int CHECK_PORT=5001;     
	public CheckIn(StateFrame sf,UserInfo[]userinfo){   
		this.sf=sf;    
		this.userinfo=userinfo;   
		this.start();   
	}   
	//对回应信息的端口的监听，一旦收到回应信息则开启一个新线程对其进行信息的接收  
	public void run(){   
		try {     
			Check=new ServerSocket(CHECK_PORT);  //CHECK_PORT与客户呼叫的端口相同
			processMsg("正在监听");
		} 
		catch (IOException e) {  //当CHECK_PORT端口被占用时，就发生此异常
			processMsg("此群聊功能仍可使用");
			e.printStackTrace();    
		}   
		try {    
			while(true){ 
				//调用accept（）方法开始监听并接受到此套集字的连接 ，accept（）方法产生一个阻塞 ；
				//调用accept（）方法将客户端套接字和服务器端套接字里连接起来，同时返回一个和客户端Socket对象相连接的Socket对象
				client=Check.accept(); 
				//处理连接的线程类，服务器端接受客户端的连接请求
				//同时启动一个线程处理这个连接，线程不停的读取客户端输入，由此实现实时消息传递
				Logon ll=new Logon(sf,client,userinfo); 
				ll.start();     
			}    
		} 
		catch (IOException e) {    
			e.printStackTrace();    
		}   
	}
	
	public void processMsg(String str){   
		sf.groupchat.output.add(str);
	}
}

	
