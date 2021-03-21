import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Chatroom {

	public static void main(String[] args) {
		Connection con = null;
		Statement   sta;
		ResultSet     res;
//创建一个窗体
JFrame f = new JFrame();
//创建一个文本域
JTextArea area = new JTextArea();
//为文本域添加滚动条
JScrollPane scr = new JScrollPane(area);
//界面相关的配置
JLabel l1 = new JLabel("历史记录");
JLabel l2 = new JLabel("用户名");
JLabel l3 = new JLabel("发送的内容");
JTextField t1 = new JTextField();
JTextField t2 = new JTextField();
JButton b1 = new JButton("发送");
JButton b2 = new JButton("刷新");
scr.setBounds(0, 50, 290, 290);
l1.setBounds(110, 0, 55, 50);
l2.setBounds(120, 350, 50, 50);
l3.setBounds(105, 420, 80, 50);
t1.setBounds(0, 400, 300, 20);
t2.setBounds(0, 470, 300, 20);
b1.setBounds(110, 500, 60, 30);
b2.setBounds(110, 550, 60, 30);
f.setBounds(0, 0, 300, 800);
f.setLayout(null);
f.add(scr);
f.add(l1);
f.add(l2);
f.add(l3);
f.add(t1);
f.add(t2);
f.add(b1);
f.add(b2);
try {
//加载mysql数据库驱动
	Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	// TODO 自动生成的 catch 块
	e.printStackTrace();
	}
	try {
//连接到数据库("jdbc:mysql://你的数据库地址:你的数据库端口(默认是3306)","用户名","密码")
	con = DriverManager.getConnection("jdbc:mysql://rm-bp1n95b9x5t0tn1zcdo.mysql.rds.aliyuncs.com:3306", "dong", "Dong231321626");
//输出是否链接成功
	System.out.println(con);
	} catch (SQLException e) {
	// TODO 自动生成的 catch 块
	e.printStackTrace();
	}

	try {
	sta = con.createStatement();
//创建库chatroom 如果你的数据库中没有库chatroom请取消下一行的注释运行一次后再加上注释
//	sta.execute("create database chatroom");
//使用chatroom这个库
	sta.execute("use  chatroom");
//创建表chatroom 如果你的数据库中没有表chatroom请取消下一行的注释运行一次后再加上注释
//	sta.execute("create table chatroom(user varchar(255),text varchar(255))");
//输出chatroom表中的内容
	res = sta.executeQuery("select * from chatroom");
	while(res.next()){
	String user = res.getString("user");
	String text = res.getString("text");
	System.out.println(user+":"+text);
//将chatroom表中的内容添加到文本域中
	area.append(user+":"+text+"\n");
	}
	} catch (SQLException e) {
	// TODO 自动生成的 catch 块
	e.printStackTrace();
	}
//为按钮b1添加监听事件
b1.addActionListener (new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent d) {
		// TODO 自动生成的方法存根
		Connection con = null;
		Statement   sta;
		ResultSet     res;
//设置发信的用户名是第一个文本框内的内容
		String username = t1.getText();
//设置发信的内容是第二个文本框内的内容
		String txt = t2.getText();
		String sql = "insert into chatroom(user,text) values('"+username+"','"+txt+"')";		
		try {
//连接到数据库("jdbc:mysql://你的数据库地址:你的数据库端口(默认是3306)","用户名","密码")
			con = DriverManager.getConnection("jdbc:mysql://rm-bp1n95b9x5t0tn1zcdo.mysql.rds.aliyuncs.com:3306", "dong", "Dong231321626");
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
			try {
				sta = con.createStatement();
//使用chatroom这个库
				sta.execute("use chatroom");
//执行sql语句将用户名和发送的内容写入到数据库中
				sta.execute(sql);
//将发送的信息添加到文本域中
				area.append(username+":"+txt+"\n");
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		
	}
});
//为按钮b2添加监听事件
b2.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
//清除文本域中的内容
		area.setText("");
		Connection con = null;
		Statement   sta;
		ResultSet     res;
		try {
//连接到数据库("jdbc:mysql://你的数据库地址:你的数据库端口(默认是3306)","用户名","密码")
			con = DriverManager.getConnection("jdbc:mysql://rm-bp1n95b9x5t0tn1zcdo.mysql.rds.aliyuncs.com:3306", "dong", "Dong231321626");
			sta = con.createStatement();
//使用chatroom这个库
			sta.execute("use chatroom");
//输出chatroom表中的内容
			res = sta.executeQuery("select * from chatroom");
			while(res.next()){
				String user = res.getString("user");
				String text = res.getString("text");
				System.out.println(user+":"+text);
				area.append(user+":"+text+"\n");
				}
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
});
//设置窗体可见
f.setVisible(true);
//设置窗体关闭的方式
f.setDefaultCloseOperation(3);

	}



}
