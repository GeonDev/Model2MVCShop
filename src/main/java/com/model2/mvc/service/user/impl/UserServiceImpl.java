package com.model2.mvc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.UserDao;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.domain.User;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	///Field
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;
	
	///Constructor
	public UserServiceImpl() {		
		System.out.println(this.getClass());
		System.out.println("UserDao : "+  userDao);
	}

	///Method
	public void addUser(User user) throws Exception {
		userDao.addUser(user);
	}

	public User loginUser(User user) throws Exception {
			
			User dbUser = userDao.getUser(user.getUserId());					
			
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
		return userDao.getUser(userId);
	}

	public Map<String , Object > getUserList(Search search) throws Exception {
		List<User> list= userDao.getUserList(search);
		int totalCount = userDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDao.getUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
}