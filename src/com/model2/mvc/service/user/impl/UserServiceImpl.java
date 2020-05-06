package com.model2.mvc.service.user.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDao;
import com.model2.mvc.service.domain.User;


public class UserServiceImpl implements UserService{
	
	///Field
	private UserDao userDao;
	
	///Constructor
	public UserServiceImpl() {
		userDao=new UserDao();
	}

	///Method
	public void addUser(User user) throws Exception {
		userDao.insertUser(user);
	}

	public User loginUser(User user) throws Exception {
			User dbUser = null;
			dbUser = userDao.findUser(user.getUserId());					
			
			if(dbUser !=null) {
				if(! dbUser.getPassword().equals(user.getPassword())){
					//LoginState 1 = 패스워드 오류
					dbUser.setLoginState(1);
				}else {
					//LoginState 0 = 정상 로그인
					System.out.println("정상 로그인");
					dbUser.setLoginState(0);
				}
			}
			
			return dbUser;
	}

	public User getUser(String userId) throws Exception {
		return userDao.findUser(userId);
	}

	public Map<String,Object> getUserList(Search search) throws Exception {
		return userDao.getUserList(search);
	}

	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDao.findUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
}