package com.chiae.springmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chiae.springmvc.entities.File_;

@Repository
public class File_Dao {

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://118.89.225.128:3306/springmvc";
	String sqluser = "root";
	String sqlpassword = "123";

	String host_url = "http://localhost:8080/springmvc-/springmvc/";

	private static Map<String, File_> files = new HashMap<>();

	public Collection<File_> getAll() {
		return files.values();
	}
	
	
	public File_ get(String name) {
		return files.get(name);
	}

	public void delete(String name) {
		files.remove(name);
	}
	
	public Boolean selectsql_limit(int pageNum, int pageSize) {
		boolean n = false;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, sqluser, sqlpassword);
			if (!conn.isClosed())
				System.out.println("连接数据库成功!");
			Statement statement = conn.createStatement();
			String sql = "select * from File_T LIMIT "+pageNum+","+pageSize+";";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("数据库查询信息");
			files.clear();
			while (rs.next()) {
					files.put(rs.getString(2).toString(),
							new File_(rs.getString(2).toString(), rs.getString(3).toString(), rs.getString(4).toString(),
									Integer.parseInt(rs.getString(5)), rs.getString(6).toString(),
									Integer.parseInt(rs.getString(7).toString()), rs.getString(8).toString(),
									rs.getString(9).toString()));
			}
			rs.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySQL驱动失败!");
		} catch (SQLException e1) {
			System.out.println("1.hellosql:" + e1.getMessage());
		} catch (Exception e2) {
			System.out.println("2.hellosql:" + e2.getMessage());
		}
		return n;
	}
	
	public Boolean add_downloadTimes(String name) {
		boolean n = false;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, sqluser, sqlpassword);
			if (!conn.isClosed())
				System.out.println("连接数据库成功!");
			Statement statement = conn.createStatement();
			String sql = "select file_download from File_T where file_name='" + name +"';";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("数据库查询信息");
			int downloadTimes=0;
			while (rs.next()) {
					downloadTimes = Integer.parseInt(rs.getString("file_download"));
			}
			Statement statement_ = conn.createStatement();
			String sql_add = "update File_T set file_download="+(downloadTimes+1)+" where file_name='" +name+"';";
			int rs_add = statement_.executeUpdate(sql_add);
			System.out.println(downloadTimes + "   " + rs_add);
			rs.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySQL驱动失败!");
		} catch (SQLException e1) {
			System.out.println("1.hellosql:" + e1.getMessage());
		} catch (Exception e2) {
			System.out.println("2.hellosql:" + e2.getMessage());
		}
		return n;
	}

	public Boolean selectsql_file() {
		boolean n = false;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, sqluser, sqlpassword);
			if (!conn.isClosed())
				System.out.println("连接数据库成功!");
			Statement statement = conn.createStatement();
			String sql = "select * from File_T;";
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("数据库查询信息");
			while (rs.next()) {
					files.put(rs.getString(2).toString(),
							new File_(rs.getString(2).toString(), rs.getString(3).toString(), rs.getString(4).toString(),
									Integer.parseInt(rs.getString(5)), rs.getString(6).toString(),
									Integer.parseInt(rs.getString(7).toString()), rs.getString(8).toString(),
									rs.getString(9).toString()));
			}
			rs.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySQL驱动失败!");
		} catch (SQLException e1) {
			System.out.println("1.hellosql:" + e1.getMessage());
		} catch (Exception e2) {
			System.out.println("2.hellosql:" + e2.getMessage());
		}
		return n;
	}

	public Boolean deletesql_file(String file_name) {
		boolean n = false;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, sqluser, sqlpassword);
			if (!conn.isClosed())
				System.out.println("连接数据库成功!");
			Statement statement = conn.createStatement();
			String sql = "delete from File_T where file_name=" + "'" + file_name + "';";
			statement.executeUpdate(sql);
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySQL驱动失败!");
		} catch (SQLException e1) {
			System.out.println("1.hellosql:" + e1.getMessage());
		} catch (Exception e2) {
			System.out.println("2.hellosql:" + e2.getMessage());
		}
		return n;
	}
}
