package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
			
	
	// 생성자 -> Map과 properties 등을 초기화 하는 역할
	private RequestMapping(String resources) {
		//Map<String, Action>을 하위 클래스인 HashMap으로 생성 
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			// 클래스 로더의 모든 경로에서 파일을 읽기
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties();
			
			// 파라미터로 넘겨준 InputStream으로 부터 자동으로 Properties 목록을 만들어준다.		
			// Properties는 HashTables의 하위 클래스로 key, value구조를 갖으며 String 만 넣을수 있다.
			// properties 안에 key, value 형태로 저장한다.
			properties.load(in);
			
			
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ 
					in.close(); 
				} catch(Exception ex){}
			}
		}
	}
	
	//싱글톤 으로 생성 => 세션 구조도 동일하다, 있으면 있는것을 쓰고, 없으면 새로만든다.
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	//path와 같은 이름의 액션 클래스를 받아오고 패스에 맞는 액션 클래스를 인스턴스함
	//path는 => login.do 같은 형태로 저장됨
	public Action getAction(String path){
		
		//전달받은 path에 해당하는 Action 객체가 들어있는지 확 인
		Action action = map.get(path);
		
		// Action 객체가 만들어 지지 않았다면
		if(action == null){
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			
			//혹시 빈칸이생겼을까봐 제거해준다.
			className = className.trim();
			try{
				//path의 이름을 갖는 클래스를 만든다./동적로딩: 이름으로 어떤 클래스를 메모리에 올림
				Class c = Class.forName(className);
				// 동적 인스턴스를 생성한다./ 런타임중 객체가 결정될때 사용한다.
				Object obj = c.newInstance();
				if(obj instanceof Action){
					
					//다음번 생성 요청이 왔을때 불러올수 있도록 map에 저장한다.
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}