package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.user.vo.UserVO;

import sun.nio.ch.SelChImpl;


public class UserDAO {
	
	public UserDAO(){
	}

	public void insertUser(UserVO userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into USERS values (?,?,?,'user',?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserId());
		stmt.setString(2, userVO.getUserName());
		stmt.setString(3, userVO.getPassword());
		stmt.setString(4, userVO.getSsn());
		stmt.setString(5, userVO.getPhone());
		stmt.setString(6, userVO.getAddr());
		stmt.setString(7, userVO.getEmail());
		stmt.executeUpdate();
		
		con.close();
	}

	public UserVO findUser(String userId) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from USERS where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userId);

		ResultSet rs = stmt.executeQuery();

		UserVO userVO = null;
		while (rs.next()) {
			userVO = new UserVO();
			userVO.setUserId(rs.getString("USER_ID"));
			userVO.setUserName(rs.getString("USER_NAME"));
			userVO.setPassword(rs.getString("PASSWORD"));
			userVO.setRole(rs.getString("ROLE"));
			userVO.setSsn(rs.getString("SSN"));
			userVO.setPhone(rs.getString("CELL_PHONE"));
			userVO.setAddr(rs.getString("ADDR"));
			userVO.setEmail(rs.getString("EMAIL"));
			userVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();

		return userVO;
	}

	public HashMap<String,Object> getUserList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();		
		
		String sql = "select * from USERS ";
		
		
		//searchVO�� �뵵 => ���͸� �����Ͽ� Ž���ϰ� �Ѵ�. 0�϶� ID, 1�϶� �̸�
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where USER_ID like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where USER_NAME like '%" + searchVO.getSearchKeyword() + "%'";
			}
		}
		sql += " order by USER_ID";

		PreparedStatement stmt = 
		con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		
		int total = rs.getRow();
		System.out.println("�ο��� ��:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		
		//ResultSet.absolute() ������ ��ġ�� Ŀ���� �̵��Ѵ�.
		//���� ������ �������� ǥ���ϱ� ���� ResultSet�� Ŀ���� ǥ���� ��ġ�� �̵���Ų��??
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		System.out.println("searchVO.getSearchKeyword():" + searchVO.getSearchKeyword());
		
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		
		//������ �˻� �������� ã�� ���� �ִٸ� 
		if (total > 0) {
			//1�������� ǥ���ϴ� ������ŭ ǥ���Ѵ�.
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				UserVO vo = new UserVO();
				vo.setUserId(rs.getString("USER_ID"));
				vo.setUserName(rs.getString("USER_NAME"));
				vo.setPassword(rs.getString("PASSWORD"));
				vo.setRole(rs.getString("ROLE"));
				vo.setSsn(rs.getString("SSN"));
				vo.setPhone(rs.getString("CELL_PHONE"));
				vo.setAddr(rs.getString("ADDR"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setRegDate(rs.getDate("REG_DATE"));							

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	
	
	public HashMap<String,Object> getUserList(SearchVO searchVO, int page) throws Exception {
		
		Connection con = DBUtil.getConnection();		
		
		String Countsql = "select count(user_ID) from users";
		PreparedStatement stmt = con.prepareStatement(Countsql);
		ResultSet rs = stmt.executeQuery();
		
		int total = 0;
		if(rs.next()) {
			total = rs.getInt("count(user_ID)");			
		}		
		
		//*********************************************************************//
		
		System.out.println("�ο��� ��:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY USER_ID) num, USERS.* FROM USERS";
		
		
		//searchVO�� �뵵 => ���͸� �����Ͽ� Ž���ϰ� �Ѵ�. 0�϶� ID, 1�϶� �̸�
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where USER_ID like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where USER_NAME like '%" + searchVO.getSearchKeyword() + "%'";
			}
		}		
		sql += ") WHERE num BETWEEN ? AND ? ";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, 1+ (page-1)*searchVO.getPageUnit());
		stmt.setInt(2, page * searchVO.getPageUnit());		

		rs = stmt.executeQuery();
		
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		System.out.println("searchVO.getSearchKeyword():" + searchVO.getSearchKeyword());
		
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		
		//������ �˻� �������� ã�� ���� �ִٸ� 
		if (total > 0) {
			while(rs.next()) {
				
				UserVO vo = new UserVO();
				vo.setUserId(rs.getString("USER_ID"));
				vo.setUserName(rs.getString("USER_NAME"));
				vo.setPassword(rs.getString("PASSWORD"));
				vo.setRole(rs.getString("ROLE"));
				vo.setSsn(rs.getString("SSN"));
				vo.setPhone(rs.getString("CELL_PHONE"));
				vo.setAddr(rs.getString("ADDR"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setRegDate(rs.getDate("REG_DATE"));							

				list.add(vo);
			}			
			
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	
	
	

	public void updateUser(UserVO userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update USERS set USER_NAME=?,CELL_PHONE=?,ADDR=?, EMAIL=? where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserName());
		stmt.setString(2, userVO.getPhone());
		stmt.setString(3, userVO.getAddr());
		stmt.setString(4, userVO.getEmail());
		stmt.setString(5, userVO.getUserId());
		stmt.executeUpdate();
		
		con.close();
	}
}