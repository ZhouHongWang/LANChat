package javaQQ;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;  
public class LoadingFrame extends JFrame implements ActionListener{  
	public String text=null;   
	private Icon icon=new ImageIcon("G:\\JAVA编程\\LANChat\\image\\QQBG01.gif");
	private JLabel label=new JLabel(new ImageIcon("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\2016-05-07 14-10-59.jpg"));  
	private JPanel panel=new JPanel();  
	private JPanel inputPanel=new JPanel();  
	private JLabel nameLabel=new JLabel("用户名"); 
	private JTextField nameText=new JTextField(12); 
	private JPanel choosePanel=new JPanel();   
	private JCheckBox reCheckBox=new JCheckBox("记住用户名");  
	private JCheckBox autoCheckBox=new JCheckBox("自动登陆");  
	private JPanel btnPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));  
	private JButton cancelBtn=new JButton("取消");  
	public JButton loadBtn=new JButton("登录");	
	private final JButton btnNewButton = new JButton("\u6CE8\u518C");
	
	//初始化登录界面
	public LoadingFrame(){
		
		setForeground(new Color(0, 153, 204));
		getContentPane().setBackground(new Color(0, 153, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\sunset.jpg"));   		
		setTitle("登录");        
		getContentPane().setLayout(new GridLayout(3,1)); 
		JPanel content=(JPanel)this.getContentPane();      
		label.setForeground(new Color(255, 51, 0));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setText("\u6C9F\u901A\u65E0\u8DDD\u79BB");
		label.setFont(new Font("华文行楷", Font.BOLD, 22));
		content.add(label);      
		label.setBounds(0, 0, 350, 250);   
		panel.setBorder(BorderFactory.createTitledBorder(""));   
		inputPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		inputPanel.add(nameLabel);   
		inputPanel.add(nameText);  
		panel.add(inputPanel);
		panel.add(choosePanel);   
		choosePanel.add(reCheckBox);
		reCheckBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		choosePanel.add(autoCheckBox);  
		content.add(panel);   
		loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPanel.add(loadBtn);   
		//将“登录”按钮加入到事件监听器中
		loadBtn.addActionListener(this); 
		
		btnPanel.add(btnNewButton);
		cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPanel.add(cancelBtn);   
		content.add(btnPanel);      
		setLocation(387, 234);   
		setSize(350,300);   
		setVisible(true);    
		setDefaultCloseOperation(3);
		//将“取消”按钮加入到事件监听器中
		cancelBtn.addActionListener(this);  
		//将输入用户名的文本框加入到事件侦听器中
		nameText.addActionListener(this);   
		this.setResizable(false); 
	}   
	
	public void actionPerformed(ActionEvent e) {   
		//点击登录或回车初始化一个StateFrame对象，并在显示器上显示出客户端的主界面
		if(e.getSource()==loadBtn||e.getSource()==nameText){   
			text=nameText.getText();    
			//new StateFrame(text);
			StateFrame sf=new StateFrame(text);
			
			/*userInfo.users[userInfo.j++]=text;
			for(int i=0;i<users.length-1;i++)
			{
				sf.friList.add(users[i]);
			}*/
			
			dispose();      
			}
		else if(e.getSource()==cancelBtn){ //取消登录退出程序
			dispose();    
			System.exit(0);   
			}     
		}    
	} 
	
	