package com.outlet.objects;

import java.util.List;

public interface Bean<T> {
	
	public T getObject(Integer obj);
	public T getObject(String obj);
	public List<T> getObjects(String conditions);
	public Integer setObject(T t) throws Exception;
	
}
