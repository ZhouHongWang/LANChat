package javaQQ;

import java.io.*; 
import java.net.*; 
//对TCP连接的信息的接收 ，以及对用户信息的保存
public class Logon extends Thread{  
	StateFrame sf;  
	BufferedReader in;  
	PrintWriter out;  
	Socket client;  
	UserInfo[] userinfo;  
	int k=0;   
	String name,ip;     
	public Logon(StateFrame sf, Socket client, UserInfo[] userinfo) {   
		this.sf = sf;    
		this.client = client;   
		this.userinfo = userinfo;   
		try {    
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));    
			out=new PrintWriter(client.getOutputStream());     
			//sf.processMsg("连接成功");      
		} 
		catch (Exception e) {    
			e.printStackTrace();    
		}
		//start();
	}      
	//run()方法实现对回应的TCP连接的信息的接收，并且完成对UserInfo信息的保存
	public void run(){   
		String msg=null;   
		try{    
			//获取对方的名字    
			msg=receiveMsg();    
			name=msg; 
			//获取对方的ip
			msg=receiveMsg();    
			ip=msg;        
			k=sf.friList.getSelectedIndex();
			
			//k=sf.friList.getItemCount();
			
			userinfo[k]=new UserInfo(name,ip);        
			sf.processFriend(userinfo[k].getName());
			sf.processMsg(userinfo[k].getName());
			msg=receiveMsg();       
			in.close();   
			out.close();   
			client.close();         
		} 
		catch (IOException e) {    
			try {     
				in.close();     
				out.close();      
				client.close();     
				} 
			catch (IOException e1) {     
				e1.printStackTrace();     
				}      
			e.printStackTrace();    
			}
		finally{    
			try {     
				in.close();     
				out.close();      
				client.close();        
				} 
			catch (IOException e1) {
				e1.printStackTrace();     
				}    
			}   
		}
	//接受数据   
	public String receiveMsg()throws IOException{   
		String msg=new String();   
		try {     
			msg=in.readLine();    
		} catch (IOException e) {   
			in.close();   
			out.close();    
			client.close();    
			msg=new String("---");        
			e.printStackTrace();   
			}    
		return msg;   
	}       
}

			
				
	
	
