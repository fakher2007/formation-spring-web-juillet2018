package com.acme.ex1.service.impl;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;

public class MovieServiceImpl implements MovieService {

	// je veux disposer de FoxMovieDaoImpl : NON !
	// je veux disposer de WarnerMovieDaoImpl : NON !
	// je veux disposer d'une MovieDao : OUI
	
	private final MovieDao dao;

	public MovieServiceImpl(MovieDao dao) {
		super();
		this.dao = dao;
	}


	@Override
	public Stream<Movie> find(String title) {
		return dao.retrieve(title);
	}

}
