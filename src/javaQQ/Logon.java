package javaQQ;

import java.io.*; 
import java.net.*; 
//��TCP���ӵ���Ϣ�Ľ��� ���Լ����û���Ϣ�ı���
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
			//sf.processMsg("���ӳɹ�");      
		} 
		catch (Exception e) {    
			e.printStackTrace();    
		}
		//start();
	}      
	//run()����ʵ�ֶԻ�Ӧ��TCP���ӵ���Ϣ�Ľ��գ�������ɶ�UserInfo��Ϣ�ı���
	public void run(){   
		String msg=null;   
		try{    
			//��ȡ�Է�������    
			msg=receiveMsg();    
			name=msg; 
			//��ȡ�Է���ip
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
	//��������   
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

			
				
	
	
