package javaQQ;
//��ʼ���û���Ϣ�������Լ�IP��ַ��������ͨ����¼�������û�����ģ�IP��ַ�ǳ���ֱ���ɻ�����ȡ��
public class UserInfo {  
	private String name;  
	private String IP;   
	public UserInfo(String name, String ip) {   
		this.name = name;   
		this.IP = ip;   
	}   
	public String getIP() {   
		return IP;  
	}   
	public void setIP(String ip) {   
		IP = ip;  
	}   
	public String getName() {   
		return name;  
	}   
	public void setName(String name) {   
		this.name = name;  
	} 
}

