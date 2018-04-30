package com.yuan.my_project.mytest.protobuf.deserialize;

import java.io.Serializable;

/**
 * 用于protobuf协议的序列化和反序列化
 * @author yuanjuntao
 * @date 2017-07-07
 */
public class PersonVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String email;
	private String phoneNum;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}
