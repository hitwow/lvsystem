package lvs.server;

import java.io.*;
import java.net.*;
import java.sql.*;

public class server{
    static int nc = 0;
	static String [][] teamrs = null;
	
	public static void main(String[] args){
		try {
			ServerSocket ss = new ServerSocket(8888);
			while (true){
				Socket socket = ss.accept();
				BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
				String sw;
				sw = din.readLine();
				System.out.println(sw);
				if (sw.equals("@signup"))
					s_signup(din, socket);
				else if (sw.equals("@login"))
					s_login(din, socket);
				else if (sw.equals("@vinfo"))
					s_vinfo(din, socket);
				else if (sw.equals("@update"))
					s_update(din, socket);
				else if (sw.equals("@cteam"))
					s_cteam(din, socket);
				else if (sw.equals("@scteam"))
					s_scteam(din, socket);
				else if (sw.equals("@sresult"))
					s_sres(din, socket);
				else if (sw.equals("@vteam"))
					s_vteam(din, socket);
				else if (sw.equals("@inteam"))
					s_inteam(din, socket);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void s_inteam(BufferedReader din, Socket socket){
		String sql = null;
		String tid = null;
		String uname = null;
		int rs;
		
		try{
			tid = din.readLine();
			uname = din.readLine();
			din.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sql = "insert into member (tid, uname) values ('"+tid+"', '"+uname+"')";
		rs = ins_up(sql);
	}
	
	public static void s_vteam(BufferedReader din, Socket socket){
		String tid;
		String sql;
		String sql2;
		String sql3;
		String [] rs = new String[6];
		String [] rt = new String[7];
		int tmp1, tmp2;
		
		try{
			tid = din.readLine();
			sql = "select * from team where Tid = '"+tid+"'";
			rs = sel3(sql);
			
			rt[0] = rs[1];
			rt[2] = rs[2];
			rt[3] = rs[3];
			rt[4] = rs[4];
			rt[5] = rs[5];
			
			sql2 = "select phone from info where Uname='"+rt[0]+"'";
			rt[1] = sel5(sql2);
			
			sql3 = "select count(*) as rc from member where Tid='"+tid+"'";
			tmp1 = sel4(sql3);
			tmp2 = Integer.valueOf(rs[5]).intValue();
			tmp1 = tmp2 - tmp1;
			rt[6] = Integer.toString(tmp1);
					
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			dout.write(rt[0]+'\n'+rt[1]+'\n'+rt[2]+'\n'+rt[3]+'\n'+rt[4]+'\n'+rt[5]+'\n'+rt[6]+'\n');
			dout.flush();
			dout.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void s_sres(BufferedReader in, Socket socket){
		String tmp;
		
		try{
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			tmp = String.valueOf(nc).toString();
			dout.write(tmp+'\n');
			System.out.println(teamrs[0][0]);
			System.out.println(teamrs[0][1]);
			System.out.println(teamrs[0][2]);
			for (int i=0 ;i<nc; i++){
				dout.write(teamrs[i][0]+'\n'+teamrs[i][1]+'\n'+teamrs[i][2]+'\n');
			}
			dout.flush();
			dout.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void s_scteam(BufferedReader din, Socket socket){
		String [] str = new String[4];
		String sql1 = null;
		String sql2 = null;
		
		try {
			for (int i=0; i<4; i++){
				str[i] = din.readLine();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		sql1 = "select count(*) as rc from team where cname='"+str[0]+"' and stime='"+str[1]+"' and etime='"+str[2]+"' and nmember="+str[3];
		sql2 = "select * from team where cname='"+str[0]+"' and stime='"+str[1]+"' and etime='"+str[2]+"' and nmember="+str[3];
		
		System.out.println(sql1);
		System.out.println(sql2);
		steam(sql1, sql2);
	}
	
	public static void s_cteam(BufferedReader din, Socket socket){
		String [] str = new String[5];
		String sql1 = null;
		String sql2 = null;
		String sql3 = null;
		int rs = 0;
		String tid = null;
		
		try{
			for (int i=0; i<5; i++){
				str[i] = din.readLine();
			}
			din.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sql1 = "insert into team (Uname, cname, stime, etime, nmember) values ('"+str[0]+"' , '"+str[1]+"' , '"+str[2]+"' , '"+str[3]+"' , '"+str[4]+"')";
		sql2 = "select Tid from team where Uname='"+str[0]+"' and cname='"+str[1]+"'";
		
		rs = ins_up(sql1);
		tid = sel2(sql2);
		sql3 = "insert into member (tid, uname) values ('"+tid+"', '"+str[0]+"')";
		rs = ins_up(sql3);
	}
	
	public static void s_signup(BufferedReader din, Socket socket){
		String [] str = new String[7];
		String sql = null;
		int rs = 0;
		
		try{
			for (int i=0; i<7; i++){
				str[i] = din.readLine();
			}
			din.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sql = "insert into info (Uname, Psw, Tname, Age, Work, phone, email) values ('"+str[0]+"' , '"+str[1]+"' , '"+str[2]+"' , "+str[3]+" , '"+str[4]+"' , '"+str[5]+"' , '"+str[6]+"')";
	
		rs = ins_up(sql);
		if (rs!=1)
			System.out.println("I&U Error!\n");
	}
	
	public static void s_login(BufferedReader din, Socket socket){
		String gname = null;
		String sql;
		String [] rs = new String[6];
		
		try{
			gname = din.readLine();
			sql = "select * from info where Uname = '"+gname+"'";
			rs = sel(sql);
			
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
			dout.write(rs[1]+'\n');
			dout.flush();
			dout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(gname);
		System.out.println(rs[1]);

	}
	
	public static void s_vinfo(BufferedReader din, Socket socket){
		String gname;
		String sql;
		String [] rs = new String[7];
		
		try{
			gname = din.readLine();
			sql = "select * from info where Uname = '"+gname+"'";
			rs = sel(sql);
			
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			dout.write(rs[0]+'\n'+rs[2]+'\n'+rs[3]+'\n'+rs[4]+'\n'+rs[5]+'\n'+rs[6]+'\n');
			dout.flush();
			dout.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void s_update(BufferedReader din, Socket socket){
		String [] str = new String[7];
		String sql = null;
		int rs = 0;
		
		try{
			for (int i=0; i<7; i++){
				str[i] = din.readLine();
			}
			din.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sql = "update info set Uname='"+str[0]+"', Psw='"+str[1]+"', Tname='"+str[2]+"', Age="+str[3]+", Work='"+str[4]+"', phone='"+str[5]+"', email='"+str[6]+"' where Uname='"+str[0]+"'";
		
		rs = ins_up(sql);
		if (rs!=1)
			System.out.println("I&U Error!\n");
	}
	
	public static void steam(String sql1, String sql2) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lvs?characterEncoding=GBK";
		String user = "root";
		String password = "mysql";
		ResultSet rs;
		int i = 0;
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if (!conn.isClosed()){
				Statement statement = conn.createStatement();
				System.out.println(sql1);
				rs = statement.executeQuery(sql1);
				while (rs.next())
					nc = rs.getInt("rc");
				System.out.println(nc);
				teamrs = new String[nc][3];
				rs = statement.executeQuery(sql2);
				while (rs.next()) {
					teamrs[i][0] = rs.getString("Tid");
					teamrs[i][1] = rs.getString("Uname");
					teamrs[i][2] = rs.getString("cname");
					i++;
				}
				conn.close();
			}
			else{
				System.out.println("Error in db!\n");
			}
			
		}catch(ClassNotFoundException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int ins_up(String sql){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lvs?characterEncoding=GBK";
		String user = "root";
		String password = "mysql";
		int rs = 0;
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if (!conn.isClosed()){
				Statement statement = conn.createStatement();
				rs = statement.executeUpdate(sql);
				conn.close();
			}
			else{
				System.out.println("Error in db!\n");
			}
			
		}catch(ClassNotFoundException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static String [] sel(String sql){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lvs?characterEncoding=GBK";
		String user = "root";
		String password = "mysql";
		ResultSet tmp;
		String [] rs = new String[7];
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if (!conn.isClosed()){
				Statement statement = conn.createStatement();
				tmp = statement.executeQuery(sql);
				while (tmp.next())
				{
					rs[0] = tmp.getString("Uname");
					rs[1] = tmp.getString("Psw");
					rs[2] = tmp.getString("Tname");
					rs[3] = tmp.getString("Age");
					rs[4] = tmp.getString("Work");
					rs[5] = tmp.getString("phone");
					rs[6] = tmp.getString("email");
				}
				tmp.close();
				conn.close();
			}
			else{
				System.out.println("Error in db!\n");
			}
			
		}catch(ClassNotFoundException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public static String  sel2(String sql){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lvs?characterEncoding=GBK";
		String user = "root";
		String password = "mysql";
		String tid = null;
		ResultSet tmp;
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if (!conn.isClosed()){
				Statement statement = conn.createStatement();
				tmp = statement.executeQuery(sql);
				while (tmp.next()) {
					tid = tmp.getString("Tid");
				}
				tmp.close();
				conn.close();
			}
			else{
				System.out.println("Error in db!\n");
			}
			
		}catch(ClassNotFoundException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return tid;
	}
	
	public static String [] sel3(String sql){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lvs?characterEncoding=GBK";
		String user = "root";
		String password = "mysql";
		ResultSet tmp;
		String [] rs = new String[6];
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if (!conn.isClosed()){
				Statement statement = conn.createStatement();
				tmp = statement.executeQuery(sql);
				while (tmp.next())
				{
					rs[0] = tmp.getString("Tid");
					rs[1] = tmp.getString("Uname");
					rs[2] = tmp.getString("cname");
					rs[3] = tmp.getString("stime");
					rs[4] = tmp.getString("etime");
					rs[5] = tmp.getString("nmember");
				}
				tmp.close();
				conn.close();
			}
			else{
				System.out.println("Error in db!\n");
			}
			
		}catch(ClassNotFoundException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}
	
	public static int sel4(String sql){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lvs?characterEncoding=GBK";
		String user = "root";
		String password = "mysql";
		ResultSet tmp;
		int rs = 0;
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if (!conn.isClosed()){
				Statement statement = conn.createStatement();
				System.out.println(sql);
				tmp = statement.executeQuery(sql);
				while (tmp.next())
					rs = tmp.getInt("rc");
				conn.close();
			}
			else{
				System.out.println("Error in db!\n");
			}
			
		}catch(ClassNotFoundException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static String sel5(String sql){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lvs?characterEncoding=GBK";
		String user = "root";
		String password = "mysql";
		String phone = null;
		ResultSet tmp;
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if (!conn.isClosed()){
				Statement statement = conn.createStatement();
				tmp = statement.executeQuery(sql);
				while (tmp.next()) {
					phone = tmp.getString("phone");
				}
				tmp.close();
				conn.close();
			}
			else{
				System.out.println("Error in db!\n");
			}
			
		}catch(ClassNotFoundException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return phone;
	}
}
	
	