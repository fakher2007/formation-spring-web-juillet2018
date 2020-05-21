package com.acme.ex4.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.acme.ex3.model.component.Comment;
import com.acme.ex3.model.entity.Book;

public class BookDto extends AbstractDto {

	public static class CommentDto{
		public String author;
		public String text;
		public Instant date;
		public CommentDto(Comment entity) {
			super();	
			this.author=entity.getAuthor().getAccount().getUsername();
			this.text = entity.getText();
			this.date = entity.getDate();
		}	
	}
	
	public Integer id;
	public String title,author,category;
	public List<CommentDto> comments;
	
	public BookDto(Book entity, boolean withComments) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.author = entity.getAuthor().getFullname();
		this.category = entity.getCategory().getName();
		
		if(withComments) {
			this.comments = entity.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
		}
		this.links.add(new Link("/authors/"+entity.getAuthor().getId(), "author"));
		this.links.add(new Link("/books/"+entity.getId()+"/comments", "comments"));
		this.links.add(new Link("/books/"+entity.getId(), "self"));		
	}
}