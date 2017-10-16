package javaQQ;

import java.io.*; 
import java.net.*;   
import sun.audio.*;  
//用来对登录信息监听，并解析传送过来的数据报，将新登录或下线用户的用户名显示或删除（从已登录用户的在线好友列表中）
public class Catcher extends Thread {  
	//MulticastSocket允许数据报以广播方式发送到数量不等的多个客户端
	MulticastSocket msocket;  
	Socket CheckSocket;  
	StateFrame sf;  
	InetAddress Cgroup;  
	DatagramPacket packet;
	//DatagramSocket只允许数据报发送给指定的目标地址
	DatagramSocket Dsocket;  
	String tag;  
	int i=0,k;     
	BufferedReader in; 
	PrintWriter out;     
	UserInfo[] userinfo;      
	public final static int CATCH_PORT=7322;
	public final static int CHECK_PORT=5001;     
	public Catcher(MulticastSocket csocket, StateFrame sf, String tag, UserInfo[] userinfo)  {   
		msocket = csocket;   
		this.sf = sf;   
		this.tag = tag;    
		this.userinfo = userinfo;   
		try { 
			//登录信息组的广播地址
			Cgroup=InetAddress.getByName("239.0.0.0");    
		} 
		catch (UnknownHostException e) {    
			e.printStackTrace();    
		}   
		start();   
	}     
	//实现对组播端口的监听，并作出反映  
	public void run(){   
		try {    
			Dsocket=new DatagramSocket();    
			while(true){ 
				//定义接受网络数据的字节数组
				byte[] Rdata=new byte[256];
				//以指定字节数组创建准备接受数据的DatagramPacket对象
				packet=new DatagramPacket(Rdata,Rdata.length);     
				//接收数据报文到packet中，receive方法产生一个“阻塞”。  
				msocket.receive(packet);   
				//count记录了数据报中数据部分的最后一位数，也就是记录了用户名的长度。     
				String count=new String(packet.getData()).substring(packet.getLength()-1,packet.getLength());     
				int num=Integer.parseInt(count);    
				//check为数据报中数据部分包含的IP地址，即源IP地址        
				String check=new  String(packet.getData()).substring(1+num,packet.getLength()-1);     
				//获得数据报数据部分包含的判断位
				String judge=new String(packet.getData()).substring(0,1);         
				//获得请求加入登录信息组的用户的用户名
				String usertag=new String (packet.getData()).substring(1,num+1);
				//judge为“C”时为请求加入登录信息组的数据报，将该用户名显示在已登录用户的在线好友列表中     
				if(judge.equals("C")){
					//获取列表中的项数并赋给i
					i=sf.friList.getItemCount();      
					processMsg(usertag);       
					userinfo[i]=new UserInfo(usertag,check);      
					ring();       
					Connect(check);      
					} 
				//judge为“D”时为用户下线的数据报，将该用户名从已登录用户的在线好友列表中删除
				if(judge.equals("D")){      
					while(userinfo[i]!=null){       
						String l=userinfo[i].getName();       
						if(l.equals(usertag)){        
							k=i;         
						    while(userinfo[k]!=null){         
						    	userinfo[k]=userinfo[k+1];         
						    	k++;         
						    }        
						    break;        
						 }       
				     }      
					try {        
						removeMsg(usertag);       
						} 
					catch (Exception n) {       
						try {         
							removeMsg(usertag);        
						} 
						catch (Exception n2) {
							
						}       
					}      
				  }     
				}         
			} 
		catch (Exception e) {    
			e.printStackTrace();    
			}    
		}
	//默认新上线的用户都是自己的好友，调用Socket建立负责与对方进行连接的套接字对象，check为对方（服务器）的IP
	public void Connect(String check){   
		try {    
			CheckSocket=new Socket(check,CHECK_PORT);    
			in=new BufferedReader(new InputStreamReader(CheckSocket.getInputStream()));    
			out=new PrintWriter(CheckSocket.getOutputStream());    
			out.println("--------------------------");    
			out.flush();         
			out.println(tag);    
			out.flush();         
			out.println(InetAddress.getLocalHost().getHostAddress());    
			out.flush();        
			out.println("---------------------------");     
			out.flush();    
			}  
		catch (Exception e) {    
			try {    
				in.close();     
				out.close();       
				CheckSocket.close();     
				} 
			catch (Exception e1) {     
				e1.printStackTrace();     
				}      
			e.printStackTrace();    
			} 
		finally{    
			try {     
				in.close();         
				out.close();	
				CheckSocket.close();     
				} 
			catch (Exception e1) {     
				e1.printStackTrace();     
				}    
			}   
		}   
	public void ring(){   
		FileInputStream file;   
		try {    
			file=new FileInputStream("\\sound\\bell.wav");    
			AudioStream as=new AudioStream(file);     
			AudioPlayer.player.start(as);    
		} catch (Exception e) {    
				e.printStackTrace();    
			}   
	}     
	public void processMsg(String str){   
		sf.friList.add(str);  
	}   
	public void removeMsg(String str){   
		sf.friList.remove(str);  
	}    
}
	

