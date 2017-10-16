package javaQQ;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.net.*; 
import java.io.*;   
import sun.audio.*;  
//��ʾ��Ե����촰�ڣ�������˽����Ϣ���պͷ��͵ļ��������д���
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
	JButton send=new JButton("����");  
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
	public void init(String name){ //�������Ϊ�����û���  
		setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\2016-05-07 14-10-59.jpg"));   
		getContentPane().setLayout(new BorderLayout(10,10));   
		output.setBackground(new Color(255, 204, 255));
		getContentPane().add(output,BorderLayout.CENTER);  
		getContentPane().add(eastPanel,BorderLayout.EAST);   
		getContentPane().add(westPanel,BorderLayout.WEST);   
		southPanel.add(input);
		//�������ı�������¼�����  
		input.addActionListener(new ActionListener(){      
			public void actionPerformed(ActionEvent e) {     
				try {     
					sendMsg(input.getText());       
					//processMsg("I:"+input.getText());
					processMsg("�Ҷ�"+name+"˵��"+input.getText());
					input.setText("");     
					} catch (IOException e1) {      
						e1.printStackTrace();      
						}     
				}        
			});   
		send.setBackground(color);  
		southPanel.add(send);  
		//�Է��Ͱ�ť����¼�����
		send.addActionListener(new ActionListener(){   
			public void actionPerformed(ActionEvent e) {    
				try {      
					sendMsg(input.getText());      
					//processMsg("I:"+input.getText()); 
					processMsg("�Ҷ�"+name+"˵��"+input.getText());
					input.setText("");     
					} catch (IOException e1) {    
						e1.printStackTrace();      
					}     
				}
		});    
		getContentPane().add(southPanel,BorderLayout.SOUTH);   
		this.setBounds(180, 80, 420, 420);   
		this.setVisible(true);    
		this.setTitle("��"+name+"˽����");  
		//this.setTitle(name);
		this.setResizable(false);   
		}       
	public void startConnect(){   
		try {    
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));   
			out=new PrintWriter(client.getOutputStream());    
			processMsg("���ӳɹ�");     
			out.println("----------------------");     
			out.flush();    
			} catch (IOException e) {    
				processMsg("����ʧ��");    
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
						//������Ϣ�����촰������ʾ
						processMsg(name+"����˵:"+msg);      
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
	//������ʾ��  
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
			msg=in.readLine(); //��ȡ�ı������������Ϣ     
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
		//��ӡ String��Ȼ����ֹ����
		out.println(str);   
		out.flush();  
	}   
	public void processMsg(String str){   
		this.output.add(str);   
		this.output.select(this.output.getItemCount()-1); 
	}  
}
					

	

					
						
	



