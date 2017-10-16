package javaQQ;

import java.awt.*; 
import java.awt.event.*; 
import java.util.Vector;
import javax.swing.*;
import java.net.*;  
//��ʾȺ�Ĵ��ڣ���Ⱥ�Ĵ����еĸ�������м���
public class GroupChatFrame extends JFrame implements Runnable {  
	String name;   
	JPanel textPanel=new JPanel(new BorderLayout(10,10));  
	JPanel inputPanel=new JPanel();  
	List output=new List(15);  
	JTextField input=new JTextField(15);  
	JButton sendBtn=new JButton("����");   
	JPanel eastPanel=new JPanel(new GridLayout(2,1)); 
	Icon icon=new ImageIcon("\\image\\notice.jpg");  
	JLabel notice=new JLabel(icon);  
	//Vector<String> v=new Vector<String>(); 
	List friList=new List();  
	StateFrame sf;    
	
	public GroupChatFrame(StateFrame sf){  
		this.sf=sf;     
		this.name =new String(sf.name);  
		//this.friList=new List(sf.friList);     
		init();     
		}    
	public void init(){
		setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\psbCACQS3SR.jpg"));   
		getContentPane().setLayout(new BorderLayout(5,5));   
		output.setBackground(new Color(255, 204, 255));
		textPanel.add(output,BorderLayout.CENTER);   
		inputPanel.add(input);   
		inputPanel.add(sendBtn);    
		textPanel.add(inputPanel,BorderLayout.SOUTH);  
		getContentPane().add(textPanel,BorderLayout.CENTER);  
		getContentPane().add(eastPanel,BorderLayout.EAST);  
		textPanel.setBackground(Color.blue);   
		eastPanel.setBackground(new Color(119, 136, 153));
		friList.setMaximumSize(new Dimension(32767, 30000));
		friList.setBackground(new Color(204, 204, 153));
		friList.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		eastPanel.add(friList);
		notice.setFont(new Font("���Ĳ���", Font.BOLD, 16));
		notice.setVerticalAlignment(SwingConstants.TOP);
		notice.setText("\u516C\u544A\u680F");
		notice.setBackground(new Color(248, 248, 255));
		eastPanel.add(notice);
		inputPanel.setBackground(Color.blue);    
		((JPanel)this.getContentPane()).setBackground(new Color(112, 128, 144));  
		this.setBounds(180, 80, 420, 420);    
		this.setTitle(name);  		
		eventHandle();   
	}    
	public  void eventHandle(){ 
		//�������ı�����м���
		input.addKeyListener(new KeyListener(){   
			public void keyPressed(KeyEvent e) {   
				if(e.getKeyChar()=='\n')   //�س���Ҳ�ܷ�����Ϣ   
					try{      
						byte[] dataS=(name+":"+input.getText()).getBytes();   
						sf.packet=new  DatagramPacket(dataS,dataS.length,sf.group,sf.DEFAULT_PORT);      
						//�������ݱ�
						sf.dsocket.send(sf.packet);       
						input.setText("");     
						}catch(Exception e2){    
							e2.printStackTrace();
						}  
           }     
			public void keyReleased(KeyEvent e) { 
				// TODO Auto-generated method stub      
				}     
			public void keyTyped(KeyEvent e) {   
				// TODO Auto-generated method stub          
			}       
	});   
		//�Է��Ͱ�ť���м��� 
		sendBtn.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e) {     
				try{      
					byte[] dataS=(name+":"+input.getText()).getBytes();   
					sf.packet=new  DatagramPacket(dataS,dataS.length,sf.group,sf.DEFAULT_PORT);    
					sf.dsocket.send(sf.packet);      
					input.setText("");    
					}catch(Exception e2){    
						e2.printStackTrace();   
						}   
				}        
			});      
		}  
	public void run() {   
		
	}
	public void processMsg(String str){  
		this.output.add(str);   
		this.output.select(output.getItemCount()-1);  
	}   
}
			

