package com.model2.mvc.common.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class DBUtil {
	
	///Field
	private final static String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final static String JDBC_URL = "jdbc:oracle:thin:scott/tiger@localhost:1521:xe";
	
	///Constructor
	private DBUtil(){
	}
	
	///Method
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static SqlSession getSqlSession() throws IOException {
		//==> 1. xml metadata �д� Stream ����
		Reader reader = Resources.getResourceAsReader("com/model2/mvc/resources/sql/mybatis-config.xml");
		
		//==> 2. Reader ��ü�� �̿� xml MetaData �� ������ ���� ������ ����, ��밡���� 
		//==>     SqlSession�� �����ϴ� SqlSessionFactory  instance ����
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		//==>3. SqlSessionFactory �� ���� autoCommit true �� SqlSession instance ����
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		return sqlSession;
	}
	
	
}