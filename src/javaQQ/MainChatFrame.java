package javaQQ;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.net.*; 
import java.io.*;   
import sun.audio.*;  
//显示点对点聊天窗口，建立对私聊信息接收和发送的监听并进行处理
public class MainChatFrame extends JFrame implements Runnable {  
	public final static int TCP_PORT=6321;  
	String name;  
	Socket client;  
	Thread th;    
	List output=new List();  
	JPanel southPanel=new JPanel();  
	JPanel eastPanel=new JPanel();  
	JPanel westPanel=new JPanel();  
	JTextField input=new JTextField(25);  
	JButton send=new JButton("发送");  
	Color color=new Color(62,160,255);  
	protected BufferedReader in;  
	protected PrintWriter out;     
	public MainChatFrame(Socket client,String name) throws HeadlessException {   
		ring(1);    
		this.name = new String(name);
		this.client = client;   
		init(name);  
		startConnect();    
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}     
	public void init(String name){ //传入参数为自身用户名  
		setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\2016-05-07 14-10-59.jpg"));   
		getContentPane().setLayout(new BorderLayout(10,10));   
		output.setBackground(new Color(255, 204, 255));
		getContentPane().add(output,BorderLayout.CENTER);  
		getContentPane().add(eastPanel,BorderLayout.EAST);   
		getContentPane().add(westPanel,BorderLayout.WEST);   
		southPanel.add(input);
		//对输入文本框添加事件监听  
		input.addActionListener(new ActionListener(){      
			public void actionPerformed(ActionEvent e) {     
				try {     
					sendMsg(input.getText());       
					//processMsg("I:"+input.getText());
					processMsg("我对"+name+"说："+input.getText());
					input.setText("");     
					} catch (IOException e1) {      
						e1.printStackTrace();      
						}     
				}        
			});   
		send.setBackground(color);  
		southPanel.add(send);  
		//对发送按钮添加事件监听
		send.addActionListener(new ActionListener(){   
			public void actionPerformed(ActionEvent e) {    
				try {      
					sendMsg(input.getText());      
					//processMsg("I:"+input.getText()); 
					processMsg("我对"+name+"说："+input.getText());
					input.setText("");     
					} catch (IOException e1) {    
						e1.printStackTrace();      
					}     
				}
		});    
		getContentPane().add(southPanel,BorderLayout.SOUTH);   
		this.setBounds(180, 80, 420, 420);   
		this.setVisible(true);    
		this.setTitle("与"+name+"私聊中");  
		//this.setTitle(name);
		this.setResizable(false);   
		}       
	public void startConnect(){   
		try {    
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));   
			out=new PrintWriter(client.getOutputStream());    
			processMsg("连接成功");     
			out.println("----------------------");     
			out.flush();    
			} catch (IOException e) {    
				processMsg("连接失败");    
				e.printStackTrace();   
				}    
		if(th==null){   
			th=new Thread(this);    
			th.start();    
			}   
		}   
	public void run() {  
		String msg=null;   
		try {    
			msg=receiveMsg();    
			while(!msg.equals("User have been disconnect!")){    
				try {      
					msg=receiveMsg();     
					th.sleep(100L);            
					if(msg!=null&&!msg.equals("User have been disconnect!")){
						//聊天信息在聊天窗口中显示
						processMsg(name+"对我说:"+msg);      
						ring(2);        
						}      
					} catch (Exception e) {      
						in.close();      
						out.close();     
						client.close();     
						msg=new String("User have been disconnect!");     
						}     
				}    
			in.close();   
			out.close();    
			client.close();    
			this.setVisible(false);     
			this.dispose();    
			} catch (IOException e) {   
				try {     
					in.close();     
					out.close();      
					client.close();     
					} catch (IOException e1) {    
						e1.printStackTrace();    
						}   
				} finally{   
					try {     
						in.close();   
						out.close();    
						client.close();    
						} catch (IOException e1) { 
							e1.printStackTrace();    
							}   
					}       
		}
	protected void processWindowEvent(WindowEvent e){   
		super.processWindowEvent(e);    
		if(e.getID()==WindowEvent.WINDOW_CLOSING){        
			try {    
				sendMsg("User have been disconnect!");     
				in.close();     
				out.close();     
				client.close();    
				} catch (IOException e1) {    
					e1.printStackTrace();     
					}    
			}  
		}       
	//播放提示音  
	public void ring(int i){   
		FileInputStream file;    
		try {      
			switch(i){      
			case 1:        
				file=new FileInputStream("\\sound\\Global.wav");        
				break;        
			case 2:       
				file=new FileInputStream("\\sound\\reMsg.wav");     
				break;        
			default:          
				file=new FileInputStream("\\sound\\system.wav");        
			}        
			AudioStream as=new AudioStream(file);          
			AudioPlayer.player.start(as);
		} catch (Exception e) {       
			e.printStackTrace();     
			}       
		}     
	
	public String receiveMsg()throws IOException{   
		String msg=new String();   
		try {    
			ring(2);    
			msg=in.readLine(); //读取文本框中输入的消息     
			}
		catch (IOException e) {    
			e.printStackTrace();   
			in.close();    
			out.checkError();  
			client.close();    
			msg=new String("User have been disconnect!");    
		}    
		return msg;   
	}   
	public void sendMsg(String str)throws IOException{  
		//打印 String，然后终止该行
		out.println(str);   
		out.flush();  
	}   
	public void processMsg(String str){   
		this.output.add(str);   
		this.output.select(this.output.getItemCount()-1); 
	}  
}
					

	

					
						
	



