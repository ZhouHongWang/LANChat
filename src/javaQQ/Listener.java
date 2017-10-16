package javaQQ;
 
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.net.*;
import sun.audio.AudioPlayer; 
import sun.audio.AudioStream;  
//����ʵ�ֶ�Ⱥ��Ϣ�ļ���������
public class Listener extends Thread { 
	MulticastSocket MSocket;  
	DatagramPacket packet;  
	StateFrame SF;   
	public Listener(MulticastSocket socket, StateFrame sf) {   
		MSocket = socket;   
		SF = sf;   
		this.start();   
		}   
	public void run(){   
		try {    
			while(true){     
				byte[] Rdata=new byte[256]; 
				//��ָ���ֽ����鴴��׼���������ݵ�DatagramPacket����
				packet=new DatagramPacket(Rdata,Rdata.length);
				//��ȡMSocket�е����ݴ�ŵ�packet�У�receive������������һ������
				MSocket.receive(packet); 
				//���ܵ���Ϣ��Ⱥ�Ĵ��ڵĹ����б���ʾ
				SF.groupchat.processMsg(new String(packet.getData())); 
				//����Ⱥ�Ĵ��ڿɼ�
				SF.groupchat.setVisible(true);     
				ring();      
				}    
			} 
		catch (IOException e) {    
			e.printStackTrace();    
			}   
		}   
	public void ring(){   
		FileInputStream file;   
		try {    
			file=new FileInputStream("\\sound\\reMsg.wav");    
			AudioStream as=new AudioStream(file);     
			AudioPlayer.player.start(as);   
			} 
		catch (FileNotFoundException e) {     
			e.printStackTrace();      
		} 
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
}
