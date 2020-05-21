package com.acme.ex3.model.component;

import java.time.Instant;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.acme.ex3.model.entity.Book;
import com.acme.ex3.model.entity.Member;

@Embeddable
public class Comment {

	@ManyToOne
	private Member author;
	
	@ManyToOne
	private Book book;

	private String text;

	private Instant date;

	public Member getAuthor() {
		return author;
	}

	public void setAuthor(Member author) {
		this.author = author;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}
}
