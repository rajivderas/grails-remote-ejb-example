package com.tunkkaus.data;

import java.io.Serializable;

public class ExampleData implements Serializable {
	

	// !!!! THIS IS VERY IMPORTANT !!!!
	private static final long serialVersionUID = 1L;
	
	private String name;
	private long id;
	
	
	public ExampleData(long id, String name) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


}
