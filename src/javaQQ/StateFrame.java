package javaQQ;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*; 
import java.net.*;  

//������ʾ�û����������˵Ĵ��ڣ����Դ����и�������м����������˳�����ɸ�������ҳ��
public class StateFrame extends JFrame  {  
	String name="����"; 
	JPanel northPanel=new JPanel(new GridLayout(1,2));  
	JPanel centerPanel=new JPanel(new GridLayout(1,1));  
	JPanel southPanel=new JPanel();   
	JPanel labelPanel=new JPanel(new GridLayout(2,1));  
	//Icon icon=new ImageIcon("\\image\\pic.jpg");  
	JLabel pic=new JLabel(new ImageIcon("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\2016-05-07 14-10-59.jpg") );  
	JLabel nameLabel=new JLabel();  
	JLabel stateLabel=new JLabel("\u597D\u53CB\u5217\u8868");  
	List friList=new List();   
	JButton allBtn=new JButton("Ⱥ��");     
	GroupChatFrame groupchat;     
	UserInfo[] userinfo=new UserInfo[50];     
    ServerSocket listen_socket;//���������׽���

    public final static int DEFAULT_PORT=8322; //Ĭ�϶˿� 
    public final static int CATCH_PORT=7322;  
    public final static int TCP_PORT=6322;   //Ĭ��TCP���Ӷ˿�  
    //DatagramSocketֻ�������ݱ����͵�ָ��Ŀ���ַ
    DatagramSocket dsocket=null;  
    InetAddress group;  
    InetAddress groupC;  
    DatagramPacket packet; 
    //DatagramSocketֻ�������ݱ����͵�ָ��Ŀ���ַ
    DatagramSocket s=null;  
    //MulticastSocket�������ݱ����͵����������Ķ���ͻ���
    MulticastSocket socketr;  
    MulticastSocket socketC;  
    Socket Csocket;     
    Listener listener;  
    private final JButton btnNewButton = new JButton("\u79C1\u804A");
  
    
    public StateFrame(String s)  {   
    	this.name=s; 
    	groupchat=new GroupChatFrame(this);   
    	init();   
    	JoinGroup();    
    	enableEvents(AWTEvent.WINDOW_EVENT_MASK);  
    	groupListener();  
    	TCPListener();      
    } 
    //�Դ��ڹر��¼��Ĵ���
    protected void processWindowEvent(WindowEvent e){     
    	super.processWindowEvent(e);
    	if(e.getID()==WindowEvent.WINDOW_CLOSING){     
    		try {
    			//���ݱ������ݰ�����Ϣ���ж�λ+�û���+IP�س�+�û�������  ���ж�λΪDʱ��ʾ�����û�������Ϣ
    			byte[]notice=new String( "D"+name+InetAddress.getLocalHost().getHostAddress()+name.length()).getBytes();      
    			//���ݱ��Ľṹ�����ݲ��֡����ĳ��ȡ�Ŀ��IP��Ŀ�Ķ˿ں�
    			packet=new DatagramPacket(notice,notice.length,groupC,CATCH_PORT);       
    			//�������ݱ�packet��Ŀ�ĵأ�Ŀ�ĵ�Ϊ�㲥��ַproupC+CATCH_PORT��
    			dsocket.send(packet);      
    		} 
    		catch (Exception e1) {      
    			e1.printStackTrace();      
    			}      
    		dsocket.close();      
    		System.exit(0);    
    		}   
    	}   
    //��ʼ��Ӧ�ó��򴰿�  �����Դ����еĸ�������м���
    public void init(){   
    	//setIconImage((new ImageIcon("\\image\\icon.gif")).getImage()); 
    	setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\sunset.jpg"));
    	getContentPane().setBackground(new Color(255, 204, 255)); 
    	getContentPane().setLayout(new BorderLayout(5,5));   
    	northPanel.setBackground(new Color(0, 153, 255)); 
    	northPanel.add(pic);   
    	nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	nameLabel.setBackground(new Color(0, 153, 255));
    	nameLabel.setText(name);   
    	labelPanel.setBackground(new Color(0, 153, 255));
    	labelPanel.add(nameLabel);   
    	stateLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	labelPanel.add(stateLabel);       
    	northPanel.add(labelPanel);    
    	getContentPane().add(northPanel,BorderLayout.NORTH);
    	
    	//�����ߺ����б����ʱ�����
    	friList.addActionListener(new ActionListener(){
    		//��ActionListener�ӿ�����ͼ�ν����еĸ���������м���
    		public void actionPerformed(ActionEvent e) {     
    			//˫����¼���б������ʱ�������¼�Ϊ��ͨ�б���е�IP,����TCPͨ��     
    			Socket socket; 
    			//��ȡ�б���ѡ���������
    			int s=friList.getSelectedIndex();     
    			String address=userinfo[s].getIP();  //��ȡ�Է�IP 
    			try {
    				//�����������ӵ����������׽��ֶ���addressΪ��������IP��ַ
    				socket=new Socket(address,TCP_PORT);//��������
    				//����һ���̣߳�����˽�Ľ���
    				//MainChatFrame mf=new MainChatFrame(socket,userinfo[s].getName());  
    				MainChatFrame mf=new MainChatFrame(socket,userinfo[s].getName());
    			}  
    			catch (Exception e1) {      
    				e1.printStackTrace();      
    			}     
    		}        
    	});    
    	centerPanel.add(friList);    
    	getContentPane().add(centerPanel,BorderLayout.CENTER);
    	
    	southPanel.add(btnNewButton);
    	southPanel.add(allBtn);    
    	groupchat=new GroupChatFrame(this); 
    	
    	//��Ⱥ�İ�ť����¼�����
    	allBtn.addActionListener(new ActionListener(){    
    		public void actionPerformed(ActionEvent e) {     
    			groupchat.setVisible(true);
    			groupchat.friList.add("����Ⱥ��Ա");
    			//����ϵͳ�����ߵ������û� 
    			for(int i=0;i<friList.getItemCount();i++){ 
    				//�������û�����ӵ�Ⱥ�Ĵ��ڵ�List�б�
    				groupchat.friList.add(new String(friList.getItem(i)));      
    			}     
    		}        
    	});    
    	getContentPane().add(southPanel,BorderLayout.SOUTH);
    	this.setBounds(820, 45, 150, 450);       
    	this.setVisible(true);    
    	this.setResizable(false);//�����Ƿ���Ե������ڴ�С  
    	//this.setDefaultCloseOperation(3);        	
    }  
    //��ʼ��UDPЭ��˿���Ϣ   
    //�����������ߵĳ����ͱ�����IP�Լ�������Ӧ������Ӧ���鲥�˽��м���  �����������ಥ�飬�����նಥ��Ϣ
    public void JoinGroup(){   
    	try { 
    		//��ʼ���㲥������Ϣ��
    		group=InetAddress.getByName("238.0.0.0"); 
    		//��ʼ����¼��Ϣ�� 
    		groupC=InetAddress.getByName("239.0.0.0");   
    		dsocket=new DatagramSocket(); 
    		//�����ֽ�����
    		byte notify[]=new byte[100];    
    		byte nametag[]=new byte[20];    
    		nametag=name.getBytes();     
    		//����һ����¼��Ϣ���ѵ�¼���û���֪ͨ���Ǳ��������ߡ�    
    		//���ݱ������ݰ�����Ϣ���ж�λ+�û���+IP�س�+�û�������  ���ж�λΪCʱ��ʾ������������鲥�鱨��
    		notify=new String("C"+name+InetAddress.getLocalHost().getHostAddress()+name.length()).getBytes();    
    		//���ݱ��Ľṹ�����ݲ��֡����ĳ��ȡ�Ŀ��IP��Ŀ�Ķ˿ں�
    		packet=new DatagramPacket(notify,notify.length,groupC,CATCH_PORT);    
    		//���ͱ���packet��Ŀ�ĵأ�Ŀ�ĵ�Ϊ�㲥��ַproupC+CATCH_PORT��
    		dsocket.send(packet);  
    		//ʹ�ñ���Ĭ�ϵ�ַ��ָ���˿�������MulticastSocket���󣻶��㲥�׽���socketC����CATCH_PORT�˿ڹ㲥
    		socketC=new MulticastSocket(CATCH_PORT);
    		//��MulticastSocket����socketC���뵽ָ���Ĺ㲥��ַ,���������뵽��¼��Ϣ�� 
    		socketC.joinGroup(groupC); 
    		//����һ���߳��࣬�����Ե�¼��Ϣ���������������͹��������ݱ������µ�¼�������û����û�����ʾ��ɾ�������ѵ�¼�û������ߺ����б��У�
    		Catcher catcher=new Catcher(socketC,this,name,userinfo);
    		//����һ���߳��࣬����Ⱥ����Ϣ
    		CheckIn login=new CheckIn(this,userinfo); 
    		for(int j=0;j<login.sf.friList.getItemCount()-1;j++)
    		{
    			this.friList.add(login.sf.userinfo[j].getName());
    		}
    		  
    	} 
    	catch (Exception e) {    
    			e.printStackTrace();    
        }   
}    //�µ�¼�û�����Ⱥ���鲥�� ��
    public void groupListener(){   
    	try { 
    		//ʹ�ñ���Ĭ�ϵ�ַ��ָ���˿�������MulicastSocket����
    		socketr=new MulticastSocket(DEFAULT_PORT);  
    		//��MulticastSocket����socketr���뵽ָ���Ķ��㲥��ַ�������Լ����뵽�ಥ��
    		socketr.joinGroup(group);   
    	} 
    	catch (IOException e) {    
    		e.printStackTrace();    
    	} 
    	//����һ���߳�����ʵ�ֶ�Ⱥ��Ϣ�ļ���������
    	listener=new Listener(socketr,this);   
    }
    //
    public void TCPListener(){ 
    	//����һ���߳��࣬�������õ��Է���IP��Ϣ����һ��TCP���ӣ�����TCPͨ��
    	//TCPConnect tcp=new TCPConnect(TCP_PORT,name,this);  
    	TCPConnect tcp=new TCPConnect(TCP_PORT,name,this); 
    }   
    public void processMsg(String str){   
    	this.groupchat.output.add(str); 
    	//ѡ�й����б���ָ������������
    	this.groupchat.output.select(this.groupchat.output.getItemCount()-1);   
    }     
    public void processFriend(String str){   
    	this.friList.add(str);   
    }  
}
    
    
    	
   

