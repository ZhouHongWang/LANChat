package javaQQ;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;  
public class LoadingFrame extends JFrame implements ActionListener{  
	public String text=null;   
	private Icon icon=new ImageIcon("G:\\JAVA���\\LANChat\\image\\QQBG01.gif");
	private JLabel label=new JLabel(new ImageIcon("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\2016-05-07 14-10-59.jpg"));  
	private JPanel panel=new JPanel();  
	private JPanel inputPanel=new JPanel();  
	private JLabel nameLabel=new JLabel("�û���"); 
	private JTextField nameText=new JTextField(12); 
	private JPanel choosePanel=new JPanel();   
	private JCheckBox reCheckBox=new JCheckBox("��ס�û���");  
	private JCheckBox autoCheckBox=new JCheckBox("�Զ���½");  
	private JPanel btnPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));  
	private JButton cancelBtn=new JButton("ȡ��");  
	public JButton loadBtn=new JButton("��¼");	
	private final JButton btnNewButton = new JButton("\u6CE8\u518C");
	
	//��ʼ����¼����
	public LoadingFrame(){
		
		setForeground(new Color(0, 153, 204));
		getContentPane().setBackground(new Color(0, 153, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage("G:\\JAVA\u7F16\u7A0B\\LANChat\\image\\sunset.jpg"));   		
		setTitle("��¼");        
		getContentPane().setLayout(new GridLayout(3,1)); 
		JPanel content=(JPanel)this.getContentPane();      
		label.setForeground(new Color(255, 51, 0));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setText("\u6C9F\u901A\u65E0\u8DDD\u79BB");
		label.setFont(new Font("�����п�", Font.BOLD, 22));
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
		//������¼����ť���뵽�¼���������
		loadBtn.addActionListener(this); 
		
		btnPanel.add(btnNewButton);
		cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPanel.add(cancelBtn);   
		content.add(btnPanel);      
		setLocation(387, 234);   
		setSize(350,300);   
		setVisible(true);    
		setDefaultCloseOperation(3);
		//����ȡ������ť���뵽�¼���������
		cancelBtn.addActionListener(this);  
		//�������û������ı�����뵽�¼���������
		nameText.addActionListener(this);   
		this.setResizable(false); 
	}   
	
	public void actionPerformed(ActionEvent e) {   
		//�����¼��س���ʼ��һ��StateFrame���󣬲�����ʾ������ʾ���ͻ��˵�������
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
		else if(e.getSource()==cancelBtn){ //ȡ����¼�˳�����
			dispose();    
			System.exit(0);   
			}     
		}    
	} 
	
	