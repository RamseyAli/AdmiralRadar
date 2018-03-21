package net;

import java.io.Serializable;

public class MyPacket<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private T payload;
	private String objectClass;
	
	private MyPacket(T p, String n){
		payload = p;
		objectClass = n;
	}
	
	MyPacket(T p) {
		this(p, p.getClass().getSimpleName());
	}

	public T getObject(){
		
		return payload;
		
	}
	
	public String getObjectClass(){
		
		return objectClass;
	}
	

}
