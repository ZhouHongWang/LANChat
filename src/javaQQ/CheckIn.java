package javaQQ;

import java.io.*; 
import java.net.*;  
//ʵ�ֶ��һ��ͨ�ţ�������ͻ�����һ����������ͨ�ţ���Ⱥ���е�һ���û�������Ϣʱ�������˱�����һ���߳̽�����Ϣ
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
	//�Ի�Ӧ��Ϣ�Ķ˿ڵļ�����һ���յ���Ӧ��Ϣ����һ�����̶߳��������Ϣ�Ľ���  
	public void run(){   
		try {     
			Check=new ServerSocket(CHECK_PORT);  //CHECK_PORT��ͻ����еĶ˿���ͬ
			processMsg("���ڼ���");
		} 
		catch (IOException e) {  //��CHECK_PORT�˿ڱ�ռ��ʱ���ͷ������쳣
			processMsg("��Ⱥ�Ĺ����Կ�ʹ��");
			e.printStackTrace();    
		}   
		try {    
			while(true){ 
				//����accept����������ʼ���������ܵ����׼��ֵ����� ��accept������������һ������ ��
				//����accept�����������ͻ����׽��ֺͷ��������׽���������������ͬʱ����һ���Ϳͻ���Socket���������ӵ�Socket����
				client=Check.accept(); 
				//�������ӵ��߳��࣬�������˽��ܿͻ��˵���������
				//ͬʱ����һ���̴߳���������ӣ��̲߳�ͣ�Ķ�ȡ�ͻ������룬�ɴ�ʵ��ʵʱ��Ϣ����
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

	
