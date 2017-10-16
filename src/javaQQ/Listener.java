package javaQQ;
 
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.net.*;
import sun.audio.AudioPlayer; 
import sun.audio.AudioStream;  
//用来实现对群消息的监听及处理
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
				//以指定字节数组创建准备接受数据的DatagramPacket对象
				packet=new DatagramPacket(Rdata,Rdata.length);
				//读取MSocket中的数据存放到packet中，receive（）方法产生一个阻塞
				MSocket.receive(packet); 
				//接受的信息在群聊窗口的滚动列表显示
				SF.groupchat.processMsg(new String(packet.getData())); 
				//设置群聊窗口可见
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
