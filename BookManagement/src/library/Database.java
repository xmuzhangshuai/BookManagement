package library;

import javax.swing.*;
import java.sql.*;

public class Database {

	private String url = "jdbc:mysql://localhost/library";//
	//private String url = "jdbc:mysql://localhost:3306/library";
	public static Connection con;
	public Statement stmt, stmt2;
	//MySQL数据库账户密码
	private String userName = "root";
	private String userPasswd = "e23456";

	public void dbCon() {
		/*try{
		Class.forName("org.gjt.mm.mysql.Driver");
		//Class.forName("com.mysql.jdbc.Driver");
		//Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		}catch(ClassNotFoundException e){
		JOptionPane.showMessageDialog(null,"数据库驱动程序加载失败！","提示！",
		JOptionPane.YES_NO_OPTION);
		}*/
		try {
			con = DriverManager.getConnection(url, userName, userPasswd);
			stmt = con.createStatement();
		} catch (Exception g) {
			JOptionPane.showMessageDialog(null, "数据库连接失败！", "提示！", JOptionPane.YES_NO_OPTION);
			System.out.println("E M: " + g.getMessage());
		}
	}

	public void dbClose() {
		try {
			con.close();
		} catch (SQLException g) {
			JOptionPane.showMessageDialog(null, "数据库断开时出错！", "提示！", JOptionPane.YES_NO_OPTION);
			System.out.println("E M" + g.getMessage());
		}
	}
}
