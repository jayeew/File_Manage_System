package com.chiae.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.chiae.springmvc.dao.File_Dao;
import com.chiae.springmvc.entities.File_;
import com.chiae.springmvc.entities.PageBean;
import com.chiae.springmvc.entities.User;


@SessionAttributes(value = { "user" }, types = { String.class })
@Controller
public class SpringMVCTest {

	// 驱动程序名
	String driver = "com.mysql.jdbc.Driver";
	// URL指向要访问的数据库名hello
	String url = "jdbc:mysql://118.89.225.128:3306/springmvc";
	// MySQL配置
	String sqluser = "root";
	String sqlpassword = "123";

	String host_url = "http://localhost:8080/springmvc-/springmvc/";
	String savePath;
	
	String password_;
	String email_;
	int id_;
	String file_name_;
	String file_url_;
	String file_data_;
	boolean n = false;
	Collection<File_> cache = null;
	
	@Autowired
	private File_Dao file_dao;
	

	@RequestMapping("/reg")
	public String testStart() {
		return "redirect:/Register.jsp";
	} 
	@RequestMapping("/log")
	public String testlog() {
		return "redirect:/index.jsp";
	} 
	@RequestMapping("/login")
	public String testReg(User user) {
		UserDB(user.getEmail(), user.getPassword());
		int user_id = selectsql();
		UserDB(user.getEmail(), user.getPassword(), user_id);
		boolean Reg_Y = addsql();
		if (Reg_Y) {
			System.out.println("Reg suc");
			return "redirect:/index.jsp";
			}
		else
			return "redirect:/Register.jsp";
	}

	@RequestMapping("logout")
	public String testLogout(Map<String, Object> map, HttpSession session) {
		UserDB(null,null);
		session.setAttribute("id", 0);
//		file_dao.selectsql_file();
		List<User> user_clo = new ArrayList<>();
		user_clo.add(new User("1","1","0"));
		map.put("user",new User("1","1","0"));
		map.put("files", cache);
		return "main";
	}

	/*
	 * main
	 */
	@RequestMapping(value="/main")
	public String testLogin(User user, Map<String, Object> map, HttpSession session) {
		UserDB(user.getEmail(), user.getPassword(), 999);
		int user_id = selectsql();
		if(user_id != 0) {
			UserDB(user.getEmail(), user.getPassword(), user_id);
			map.remove("files");
			System.out.println("刷新了！！！！！");
//			file_dao.selectsql_file();
			map.put("user", new User(user.getEmail(), user.getPassword(), user_id+""));
			map.put("files", cache);
			System.out.println(map);
			session.setAttribute("id", user_id);
			return "main";
		}
		else
			return "main";
	}
	/*
	 * 
	 */
	@RequestMapping("/FindAllWithPage")
	public String testPage(HttpServletRequest request, Map<String, Object> map) {
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = 5;
		PageBean<File_> pb = findAllFileWithPage(pageNum, pageSize);
		map.put("pageBean", pb);
		List<File_> x = pb.getList();
		for(File_ xx : x)
			System.out.println(xx.getFile_name());
		map.put("files", pb.getList());
		return "main";
	}
	/*
	 * Search
	 */
	@RequestMapping("/search")
	public String goSearch(Map<String, Object> map, @RequestParam("keys") String keys) {
//		file_dao.selectsql_file();
		Iterator<File_> iterator =  cache.iterator();
		Collection<File_> file_clo = new ArrayList<>();
		while(iterator.hasNext()) {
			File_ file = iterator.next();
			String str = new String(file.getFile_name());
			if(str.contains(keys)) {
				file_clo.add(file);
			}
		}
		map.put("files", file_clo);
		return "main";
	}
	
	/*
	 * Sort
	 */
	@RequestMapping("/sblmd")
	public String SortbyDate(Map<String, Object> map, HttpSession session) {
//			file_dao.selectsql_file();
			Iterator<File_> iterator =  cache.iterator();
			List<File_> file_clo = new ArrayList<>();
			while(iterator.hasNext()) {
				File_ file = iterator.next();
				file_clo.add(file);
			}
			Time_compare file_compare = new Time_compare();
			Collections.sort(file_clo, file_compare);
			map.put("files", file_clo);
			return "main";
	}
	@RequestMapping("/sbldt")
	public String SortbyDownloadTimes(Map<String, Object> map, HttpSession session) {
//		file_dao.selectsql_file();
		Iterator<File_> iterator =  cache.iterator();
		List<File_> file_clo = new ArrayList<>();
		while(iterator.hasNext()) {
			File_ file = iterator.next();
			file_clo.add(file);
		}
		Download_compare file_compare = new Download_compare();
		Collections.sort(file_clo, file_compare);
		map.put("files", file_clo);
		return "main";
	}
	/*
	 * File Type Display
	 */
	@RequestMapping(value="/txt")
	public String dspTxt(Map<String, Object> map) {
//		file_dao.selectsql_file();
		Iterator<File_> iterator =  cache.iterator();
		Collection<File_> file_clo = new ArrayList<>();
		while(iterator.hasNext()) {
			File_ file = iterator.next();
			String str = new String(file.getFile_name());
			int point = str.lastIndexOf(".");
			String file_type =str.substring(point+1, str.length());
			if(file_type.equals("txt")) {
				file_clo.add(file);
			}
		}
		map.put("files", file_clo);
		return "main";
	}
	@RequestMapping(value="/png")
	public String dspPng(Map<String, Object> map) {
//		file_dao.selectsql_file();
		Iterator<File_> iterator =  cache.iterator();
		Collection<File_> file_clo = new ArrayList<>();
		while(iterator.hasNext()) {
			File_ file = iterator.next();
			String str = new String(file.getFile_name());
			int point = str.lastIndexOf(".");
			String file_type =str.substring(point+1, str.length());
			if(file_type.equals("png")) {
				file_clo.add(file);
			}
		}
		map.put("files", file_clo);
		return "main";
	}
	@RequestMapping(value="/zip")
	public String dspZip(Map<String, Object> map) {
//		file_dao.selectsql_file();
		Iterator<File_> iterator =  cache.iterator();
		Collection<File_> file_clo = new ArrayList<>();
		while(iterator.hasNext()) {
			File_ file = iterator.next();
			String str = new String(file.getFile_name());
			int point = str.lastIndexOf(".");
			String file_type =str.substring(point+1, str.length());
			if(file_type.equals("zip")) {
				file_clo.add(file);
			}
		}
		map.put("files", file_clo);
		return "main";
	}
	@RequestMapping(value="/jpg")
	public String dspJpg(Map<String, Object> map) {
//		file_dao.selectsql_file();
		Iterator<File_> iterator =  cache.iterator();
		Collection<File_> file_clo = new ArrayList<>();
		while(iterator.hasNext()) {
			File_ file = iterator.next();
			String str = new String(file.getFile_name());
			int point = str.lastIndexOf(".");
			String file_type =str.substring(point+1, str.length());
			if(file_type.equals("jpg")) {
				file_clo.add(file);
			}
		}
		map.put("files", file_clo);
		
		return "main";
	}
	/*
	 * post 转 delete请求
	 */

	@RequestMapping(value="/emp/{file_name:[a-zA-Z0-9\\\\.]+}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("file_name") String file_name, Map<String, Object> map, HttpSession session) {
		String str = session.getAttribute("id").toString();
		if(str!=null && Integer.parseInt(str) != 0 &&Integer.parseInt(str) == id_) {
			System.out.println("this is delete method");
			file_dao.delete(file_name);
			file_dao.deletesql_file(file_name);
			file_dao.selectsql_file();
			cache = file_dao.getAll();
			File file = new File(savePath + "/" + file_name);
			if(file.isFile())
				file.delete();
			return "redirect:/main";
		}
		return "redirect:/log";
	}
	
	/*
	 *  Data Back
	 */
	@RequestMapping(value="emp/test")
	public String test(Map<String, Object> map) {
		file_dao.selectsql_file();
		Collection<File_> File_pool = file_dao.getAll();
		Iterator<File_> it = File_pool.iterator();
		int i=0;
		while(it.hasNext()) {
			System.out.println(i++);
			File_ file = it.next();
			System.out.println(file.getFile_data());
		}
		return "redirect:/main";
	}
	
	/*
	 * download
	 */
	
	@RequestMapping(value="/emp/{file_name:[a-zA-Z0-9\\\\.]+}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> DownLoad(HttpSession session, @PathVariable("file_name") String file_name)throws IOException {
			file_dao.add_downloadTimes(file_name);
			file_dao.selectsql_file();
			cache = file_dao.getAll();
			byte []body = null;
			ServletContext servletContext = session.getServletContext();
			InputStream file_down_in = new FileInputStream(servletContext.getRealPath("/WEB-INF/files/"+file_name).toString());
			body = new byte[file_down_in.available()];
			file_down_in.read(body);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment;filename="+file_name);
			
			HttpStatus statusCode = HttpStatus.OK;
			ResponseEntity<byte[]> response = new ResponseEntity<>(body, headers, statusCode);
			
			file_down_in.close();
			return response;
	}
	
	/*
	 * upload 
	 */
	@RequestMapping(value="/upload")
	public String testUpload(HttpSession session,
			@RequestParam("file") MultipartFile file_up, @RequestParam("desc") String desc) throws IOException{
		System.out.println(file_up.getOriginalFilename() + " " + file_up.getSize());
		System.out.println(file_up.getInputStream());
		String file_up_name = file_up.getOriginalFilename();
		file_up_name = file_up_name.substring(file_up_name.lastIndexOf("\\")+1);
		InputStream file_up_in = file_up.getInputStream();
		savePath = session.getServletContext().getRealPath("/WEB-INF/files");
		System.out.println(savePath);
		File file_host = new File(savePath);
		if(!file_host.exists() && !file_host.isDirectory()) {
			System.out.println(savePath + "目录不存在");
			file_host.mkdir();
		}
		
		List<File> list = getFiles(savePath, new ArrayList<File>());
		if(list != null && list.size() > 0) {
			int list_size = list.size();
			for(int i=0; i<list_size; i++) {
				String file_host_name = new String(list.get(i).getName());
				if(file_host_name.equals(file_up_name)){
					System.out.println("文件存在, 重命名");
					file_up_name = "(copy)" + file_up_name;
					break;
				}
			}
		}
		FileOutputStream file_up_out = new FileOutputStream(savePath + "\\" + file_up_name);
		
		byte buffer[] = new byte[1024];
		int bufRead_len = 0;
		int file_up_done = 0;
		while((bufRead_len = file_up_in.read(buffer)) > 0) {
			file_up_out.write(buffer, 0, bufRead_len);
			file_up_done += bufRead_len;
		}
		String file_up_date = new Date().toString();
		String file_up_curenttime = (System.currentTimeMillis()/1000)+"";
		System.out.println("file_up_curenttime : " + file_up_curenttime);
		if(file_up_done == file_up.getSize()) {
			File_ file_db = new File_(file_up_name, host_url+file_up_name, file_up_date, 0,file_up_curenttime, file_up_done/1000, email_, desc);
			boolean file_up_sql = addsql_file(file_db);
			if(file_up_sql)
				System.out.println("文件上传成功");
		}
		file_dao.selectsql_file();
		cache = file_dao.getAll();
		file_up_in.close();
		file_up_out.close();
		
		return "redirect:/main";
	}
	
	/*
	 *  //已知数据
    	private int pageNum;    //当前页,从请求那边传过来。
    	private int pageSize;    //每页显示的数据条数。
    	private int totalRecord;    //总的记录条数。查询数据库得到的数据
    
    	//需要计算得来
    	private int totalPage;    //总页数，通过totalRecord和pageSize计算可以得来
    	//开始索引，也就是我们在数据库中要从第几行数据开始拿，有了startIndex和pageSize，
    	//就知道了limit语句的两个数据，就能获得每页需要显示的数据了
    	private int startIndex;    
	 */
	
	public PageBean<File_> findAllFileWithPage(int pageNum, int pageSize){
		int totalRecord=0;
		if(cache == null);
		else totalRecord = cache.size();
		PageBean<File_> pb = new PageBean<>(pageNum, pageSize, totalRecord);
		int startIndex = pb.getStartIndex();
		file_dao.selectsql_limit(startIndex, pageSize);
		Iterator<File_> iterator =  file_dao.getAll().iterator();
		List<File_> file_clo = new ArrayList<>();
		while(iterator.hasNext()) {
			File_ file = iterator.next();
			file_clo.add(file);
		}
		pb.setList(file_clo);
		return pb;
	}
	
	public void UserDB(String name, String pwd, int... id) {
		email_ = name;
		password_ = pwd;
		if(id.length == 0)
			id_ = 0;
		else {
			id_ = id[0];
		}
	}

	/*
	 * 文件相关
	 */
	
	 //获取所有目录文件
	 public static List<File> getFiles(String realpath, List<File> files) {
	        File realFile = new File(realpath);
	        if (realFile.isDirectory()) {
	            File[] subfiles = realFile.listFiles();
	            for (File file : subfiles) {
	                if (file.isDirectory()) {
	                    getFiles(file.getAbsolutePath(), files);
	                } else {
	                    files.add(file);
	                }
	            }
	        }
	        return files;
	    }
	
	/*
	 * 数据库相关
	 */

	public Boolean addsql_file(File_ file) {
		n = false;
		try {
			// 加载驱动
			Class.forName(driver);
			// 连接数据库
			Connection conn = DriverManager.getConnection(url, sqluser,
					sqlpassword);
			if (!conn.isClosed())
				System.out.println("连接数据库成功!");

			String sql = "insert into File_T (file_name, file_url, file_data, file_download, "
					+ "file_currenttime, file_size, file_Uper, file_desc) values (?,?,?,?,?,?,?,?);";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, file.getFile_name());
			ps.setString(2, file.getFile_url());
			ps.setString(3, file.getFile_data());
			ps.setString(4, file.getFile_download()+"");
			ps.setString(5, file.getFile_currenttime());
			ps.setString(6, file.getFile_size()+"");
			ps.setString(7, file.getFile_Uper());
			ps.setString(8, file.getFile_desc());
			ps.executeUpdate();
			n= true;
			ps.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySQL驱动失败!");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}
	
	public int selectsql() {
		int user_id = 0;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, sqluser,
					sqlpassword);
			if (!conn.isClosed())
				System.out.println("连接数据库成功!");
			Statement statement = conn.createStatement();
			String sql = "select password from User_db where email=" + "'" + email_ + "';";
			ResultSet rs = statement.executeQuery(sql);
			String readpwd = null;
			while (rs.next()) {
				readpwd = rs.getString("password");
				readpwd = new String(readpwd.getBytes("ISO-8859-1"), "GB2312");
				if (readpwd.equals(password_)) {
					Statement statement_ = conn.createStatement();
					String sql_ = "select id from User_db where email=" + "'" + email_ + "';";
					ResultSet rs_ = statement_.executeQuery(sql_);
					while(rs_.next()) 
						user_id = Integer.parseInt(rs_.getString(1).toString());
				}
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
		return user_id;
	}

	public boolean addsql() {
		n = false;
		try {
			// 加载驱动
			Class.forName(driver);
			// 连接数据库
			Connection conn = DriverManager.getConnection(url, sqluser,
					sqlpassword);
			if (!conn.isClosed())
				System.out.println("连接数据库成功!");

			String sql = "insert into User_db (email, password) values (?,?);";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email_);
			ps.setString(2, password_);
			ps.executeUpdate();
			if (this.selectsql() != 0)
				{	n = true;
					System.out.println("***");
				}
			else {
				System.out.println("注册失败");
			}
			ps.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("加载MySQL驱动失败!");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}
}
class Time_compare implements Comparator<File_>{
	@Override 
	public int compare(File_ file_a, File_ file_b) {
		int l1 = Integer.parseInt(new String(file_a.getFile_currenttime()));
		int l2 = Integer.parseInt(new String(file_b.getFile_currenttime()));
		return l1-l2;
	}
}
class Download_compare implements Comparator<File_>{
	@Override 
	public int compare(File_ file_a, File_ file_b) {
		int l1 = file_a.getFile_download();
		int l2 = file_b.getFile_download();
		return l1-l2;
	}
}
