package com.acme.ex4.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDto {


	public static class Link{
		public String href, rel;
		
		public Link(String href, String rel) {
			this.href = href;this.rel = rel;
		}
	}
	
	public List<Link> links = new ArrayList<AbstractDto.Link>();

}
