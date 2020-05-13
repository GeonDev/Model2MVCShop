package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class LoginAction extends Action{

	@Override	
	public String execute(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		UserService userService=new UserServiceImpl();
		User dbUser=userService.getUser(userId);
	
		HttpSession session=request.getSession();						
		
		if(dbUser !=null) {
			session.setAttribute("user", dbUser);	
			
			if((dbUser.getPassword()).equals(password)) {
				// 로그인 성공
				dbUser.setLoginState(0);
				session.setAttribute("user", dbUser);
				return "redirect:/index.jsp";
			}else {
				// 로그인 비밀번호 다름
				dbUser.setLoginState(1);
				session.setAttribute("user", dbUser);
				return "redirect:/user/loginfailView.jsp";			
			}			
			
		}else {
			// 아이디 없음
			dbUser.setLoginState(2);
			session.setAttribute("user", dbUser);
			return "redirect:/user/loginfailView.jsp";
		}					
		
	}//end of execute
}//end of class
