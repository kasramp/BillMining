package com.outlet.objects;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface Bean<T> {
	
	public T getObject(Integer obj);
	public T getObject(String obj);
	public List<T> getObjects(Map<String,Object> conditions);
	public List<T> getObjects();
	public Integer setObject(T t) throws Exception;
	
}
