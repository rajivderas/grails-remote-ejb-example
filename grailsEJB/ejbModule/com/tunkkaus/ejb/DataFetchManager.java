package com.tunkkaus.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.tunkkaus.data.ExampleData;



public interface DataFetchManager {

	List<ExampleData> searchData(String name);
	
}
