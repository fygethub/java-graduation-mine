package sy.fsdoor.testjdb;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

 public class TestJdbc {
	private  String URL="jdbc:mysql://127.0.0.1:3306/sypro";
	private  String PASSWORD="123456";
	private  String USER="root";
	private  Properties properties;
	private  Connection connection;
	private static TestJdbc testJdbc=new TestJdbc();
	
	private  TestJdbc() {
		// TODO Auto-generated constructor stub
		properties=new Properties();
	
		InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties");
		try {
			properties.load(inputStream);
			URL=properties.getProperty("jdbc.url");
			PASSWORD=properties.getProperty("jdbc.password");
			USER=properties.getProperty("jdbc.username");
			
			Class.forName("com.mysql.jdbc.Driver");
			connection= DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  Connection getConnection(){
		return connection;
	}
	
	public static TestJdbc getTestJdbc(){
		return testJdbc;
	}
}