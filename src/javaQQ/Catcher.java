package javaQQ;

import java.io.*; 
import java.net.*;   
import sun.audio.*;  
//�����Ե�¼��Ϣ���������������͹��������ݱ������µ�¼�������û����û�����ʾ��ɾ�������ѵ�¼�û������ߺ����б��У�
public class Catcher extends Thread {  
	//MulticastSocket�������ݱ��Թ㲥��ʽ���͵��������ȵĶ���ͻ���
	MulticastSocket msocket;  
	Socket CheckSocket;  
	StateFrame sf;  
	InetAddress Cgroup;  
	DatagramPacket packet;
	//DatagramSocketֻ�������ݱ����͸�ָ����Ŀ���ַ
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
			//��¼��Ϣ��Ĺ㲥��ַ
			Cgroup=InetAddress.getByName("239.0.0.0");    
		} 
		catch (UnknownHostException e) {    
			e.printStackTrace();    
		}   
		start();   
	}     
	//ʵ�ֶ��鲥�˿ڵļ�������������ӳ  
	public void run(){   
		try {    
			Dsocket=new DatagramSocket();    
			while(true){ 
				//��������������ݵ��ֽ�����
				byte[] Rdata=new byte[256];
				//��ָ���ֽ����鴴��׼���������ݵ�DatagramPacket����
				packet=new DatagramPacket(Rdata,Rdata.length);     
				//�������ݱ��ĵ�packet�У�receive��������һ������������  
				msocket.receive(packet);   
				//count��¼�����ݱ������ݲ��ֵ����һλ����Ҳ���Ǽ�¼���û����ĳ��ȡ�     
				String count=new String(packet.getData()).substring(packet.getLength()-1,packet.getLength());     
				int num=Integer.parseInt(count);    
				//checkΪ���ݱ������ݲ��ְ�����IP��ַ����ԴIP��ַ        
				String check=new  String(packet.getData()).substring(1+num,packet.getLength()-1);     
				//������ݱ����ݲ��ְ������ж�λ
				String judge=new String(packet.getData()).substring(0,1);         
				//�����������¼��Ϣ����û����û���
				String usertag=new String (packet.getData()).substring(1,num+1);
				//judgeΪ��C��ʱΪ��������¼��Ϣ������ݱ��������û�����ʾ���ѵ�¼�û������ߺ����б���     
				if(judge.equals("C")){
					//��ȡ�б��е�����������i
					i=sf.friList.getItemCount();      
					processMsg(usertag);       
					userinfo[i]=new UserInfo(usertag,check);      
					ring();       
					Connect(check);      
					} 
				//judgeΪ��D��ʱΪ�û����ߵ����ݱ��������û������ѵ�¼�û������ߺ����б���ɾ��
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
	//Ĭ�������ߵ��û������Լ��ĺ��ѣ�����Socket����������Է��������ӵ��׽��ֶ���checkΪ�Է�������������IP
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
	

