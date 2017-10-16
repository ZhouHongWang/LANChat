package javaQQ;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*; 
import java.net.*;  

//用来显示用户名及在线人的窗口，并对窗口中各组件进行监听，让其可顺利生成各聊天主页面
public class StateFrame extends JFrame  {  
	String name="匿名"; 
	JPanel northPanel=new JPanel(new GridLayout(1,2));  
	JPanel centerPanel=new JPanel(new GridLayout(1,1));  
	JPanel southPanel=new JPanel();   
	JPanel labelPanel=new JPanel(new GridLayout(2,1));  
	//Icon icon=new ImageIcon("\\image\\pic.jpg");  
	JLabel pic=new JLabel(new ImageIcon("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\2016-05-07 14-10-59.jpg") );  
	JLabel nameLabel=new JLabel();  
	JLabel stateLabel=new JLabel("\u597D\u53CB\u5217\u8868");  
	List friList=new List();   
	JButton allBtn=new JButton("群聊");     
	GroupChatFrame groupchat;     
	UserInfo[] userinfo=new UserInfo[50];     
    ServerSocket listen_socket;//服务器端套接字

    public final static int DEFAULT_PORT=8322; //默认端口 
    public final static int CATCH_PORT=7322;  
    public final static int TCP_PORT=6322;   //默认TCP连接端口  
    //DatagramSocket只允许将数据报发送到指定目标地址
    DatagramSocket dsocket=null;  
    InetAddress group;  
    InetAddress groupC;  
    DatagramPacket packet; 
    //DatagramSocket只允许将数据报发送到指定目标地址
    DatagramSocket s=null;  
    //MulticastSocket允许将数据报发送到数量不定的多个客户端
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
    //对窗口关闭事件的处理
    protected void processWindowEvent(WindowEvent e){     
    	super.processWindowEvent(e);
    	if(e.getID()==WindowEvent.WINDOW_CLOSING){     
    		try {
    			//数据报中数据包含信息：判断位+用户名+IP地扯+用户名长度  ；判断位为D时表示的是用户下线信息
    			byte[]notice=new String( "D"+name+InetAddress.getLocalHost().getHostAddress()+name.length()).getBytes();      
    			//数据报的结构：数据部分、报文长度、目的IP、目的端口号
    			packet=new DatagramPacket(notice,notice.length,groupC,CATCH_PORT);       
    			//发送数据报packet到目的地（目的地为广播地址proupC+CATCH_PORT）
    			dsocket.send(packet);      
    		} 
    		catch (Exception e1) {      
    			e1.printStackTrace();      
    			}      
    		dsocket.close();      
    		System.exit(0);    
    		}   
    	}   
    //初始化应用程序窗口  ，并对窗口中的各组件进行监听
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
    	
    	//对在线好友列表添加时间监听
    	friList.addActionListener(new ActionListener(){
    		//用ActionListener接口来对图形界面中的各个组件进行监听
    		public void actionPerformed(ActionEvent e) {     
    			//双击登录到列表框内容时触发的事件为连通列表框中的IP,建立TCP通信     
    			Socket socket; 
    			//获取列表中选中项的索引
    			int s=friList.getSelectedIndex();     
    			String address=userinfo[s].getIP();  //获取对方IP 
    			try {
    				//建立负责连接到服务器的套接字对象，address为服务器端IP地址
    				socket=new Socket(address,TCP_PORT);//建立连接
    				//启动一个线程，加载私聊界面
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
    	
    	//对群聊按钮添加事件监听
    	allBtn.addActionListener(new ActionListener(){    
    		public void actionPerformed(ActionEvent e) {     
    			groupchat.setVisible(true);
    			groupchat.friList.add("在线群成员");
    			//遍历系统中在线的所有用户 
    			for(int i=0;i<friList.getItemCount();i++){ 
    				//将在线用户名添加到群聊窗口的List列表
    				groupchat.friList.add(new String(friList.getItem(i)));      
    			}     
    		}        
    	});    
    	getContentPane().add(southPanel,BorderLayout.SOUTH);
    	this.setBounds(820, 45, 150, 450);       
    	this.setVisible(true);    
    	this.setResizable(false);//设置是否可以调整窗口大小  
    	//this.setDefaultCloseOperation(3);        	
    }  
    //初始化UDP协议端口信息   
    //首先向已在线的程序发送本机的IP以及请求响应并对相应的组播端进行监听  ，即请求加入多播组，并接收多播信息
    public void JoinGroup(){   
    	try { 
    		//初始化广播聊天信息组
    		group=InetAddress.getByName("238.0.0.0"); 
    		//初始化登录信息组 
    		groupC=InetAddress.getByName("239.0.0.0");   
    		dsocket=new DatagramSocket(); 
    		//定义字节数组
    		byte notify[]=new byte[100];    
    		byte nametag[]=new byte[20];    
    		nametag=name.getBytes();     
    		//发送一个登录信息给已登录的用户，通知他们本程序上线。    
    		//数据报中数据包含信息：判断位+用户名+IP地扯+用户名长度  ；判断位为C时表示的是请求加入组播组报文
    		notify=new String("C"+name+InetAddress.getLocalHost().getHostAddress()+name.length()).getBytes();    
    		//数据报的结构：数据部分、报文长度、目的IP、目的端口号
    		packet=new DatagramPacket(notify,notify.length,groupC,CATCH_PORT);    
    		//发送报文packet到目的地（目的地为广播地址proupC+CATCH_PORT）
    		dsocket.send(packet);  
    		//使用本机默认地址、指定端口来创建MulticastSocket对象；多点广播套接字socketC将在CATCH_PORT端口广播
    		socketC=new MulticastSocket(CATCH_PORT);
    		//将MulticastSocket对象socketC加入到指定的广播地址,即本机加入到登录信息组 
    		socketC.joinGroup(groupC); 
    		//启动一个线程类，用来对登录信息监听，并解析传送过来的数据报，将新登录或下线用户的用户名显示或删除（从已登录用户的在线好友列表中）
    		Catcher catcher=new Catcher(socketC,this,name,userinfo);
    		//启动一个线程类，接收群聊信息
    		CheckIn login=new CheckIn(this,userinfo); 
    		for(int j=0;j<login.sf.friList.getItemCount()-1;j++)
    		{
    			this.friList.add(login.sf.userinfo[j].getName());
    		}
    		  
    	} 
    	catch (Exception e) {    
    			e.printStackTrace();    
        }   
}    //新登录用户加入群聊组播组 ，
    public void groupListener(){   
    	try { 
    		//使用本机默认地址、指定端口来创建MulicastSocket对象
    		socketr=new MulticastSocket(DEFAULT_PORT);  
    		//将MulticastSocket对象socketr加入到指定的多点广播地址，即将自己加入到多播组
    		socketr.joinGroup(group);   
    	} 
    	catch (IOException e) {    
    		e.printStackTrace();    
    	} 
    	//启动一个线程用来实现对群消息的监听及处理
    	listener=new Listener(socketr,this);   
    }
    //
    public void TCPListener(){ 
    	//启动一个线程类，根据所得到对方的IP信息建立一个TCP连接，进行TCP通信
    	//TCPConnect tcp=new TCPConnect(TCP_PORT,name,this);  
    	TCPConnect tcp=new TCPConnect(TCP_PORT,name,this); 
    }   
    public void processMsg(String str){   
    	this.groupchat.output.add(str); 
    	//选中滚动列表中指定索引处的项
    	this.groupchat.output.select(this.groupchat.output.getItemCount()-1);   
    }     
    public void processFriend(String str){   
    	this.friList.add(str);   
    }  
}
    
    
    	
   

