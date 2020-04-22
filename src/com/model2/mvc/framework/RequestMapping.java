package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
			
	
	// ������ -> Map�� properties ���� �ʱ�ȭ �ϴ� ����
	private RequestMapping(String resources) {
		//Map<String, Action>�� ���� Ŭ������ HashMap���� ���� 
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			// �������� ���ҽ� ��θ� Ž���Ͽ� ���ҽ��� Ȯ�� => �̰� �� �𸣰ڴ�!!!!!!!!
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties();
			
			// �Ķ���ͷ� �Ѱ��� InputStream���� ���� �ڵ����� Properties ����� ������ش�.
			// Properties�� HashTables�� ���� Ŭ������ key, value������ ���´�. 
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ 
					in.close(); 
				} catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	//�н��� ���� �̸��� �׼� Ŭ������ �޾ƿ��� �н��� �´� �׼� Ŭ������ �ν��Ͻ���
	public Action getAction(String path){
		Action action = map.get(path);
		if(action == null){
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				//path�� �̸��� ���� Ŭ������ �����./�����ε�: �̸����� � Ŭ������ �޸𸮿� �ø�
				Class c = Class.forName(className);
				// ���� �ν��Ͻ��� �����Ѵ�./ ��Ÿ���� ��ü�� �����ɶ� ����Ѵ�.
				Object obj = c.newInstance();
				if(obj instanceof Action){
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}