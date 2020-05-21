package com.acme.common.persistence;

import java.io.Serializable;
import java.util.List;

public class Results<T> implements Serializable {
	
	private static final long serialVersionUID = 7401657765966455168L;
	
	private List<T> items;
	private long count;

	private Results() {}
	
	public Results(List<T> items, long count) {
		this();
		this.items = items;
		this.count = count;
	}

	public List<T> getItems() {
		return items;
	}

	@SuppressWarnings("unused")
	private void setItems(List<T> items) {
		this.items = items;
	}

	public long getCount() {
		return count;
	}
	
	@SuppressWarnings("unused")
	private void setCount(long count) {
		this.count = count;
	}
}
