package javaQQ;
//初始化用户信息，姓名以及IP地址，姓名是通过登录界面由用户键入的，IP地址是程序直接由机器获取的
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

