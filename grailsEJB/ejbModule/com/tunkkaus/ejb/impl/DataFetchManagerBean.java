package com.tunkkaus.ejb.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.tunkkaus.data.ExampleData;
import com.tunkkaus.ejb.DataFetchManager;


@Stateless(name = "DataFetchManager") 
@Remote(DataFetchManager.class)
public class DataFetchManagerBean implements DataFetchManager {
	
	@Override
	public List<ExampleData> searchData(String name) {
		List<ExampleData> ret = new ArrayList<ExampleData>();
		ret.add(new ExampleData(4, name + "!"));
		ret.add(new ExampleData(14, name + "?"));
		ret.add(new ExampleData(32, name + "~"));
		return ret;
	}

}
