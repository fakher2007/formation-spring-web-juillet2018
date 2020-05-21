package com.acme.ex1.service.impl;

import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;

@Service
public class SuperMovieServiceImpl implements MovieService {
	// je veux disposer de FoxMovieDaoImpl et de WarnerMovieDaoImpl : NON
	// je veux disposer des MovieDao : OUI

	private final Set<MovieDao> daos;
	
	public SuperMovieServiceImpl(Set<MovieDao> daos) {
		super();
		for (MovieDao movieDao : daos) {
			System.out.println("about to work with "+movieDao.getClass());
		}
		this.daos = daos;
	}

	@Override
	public Stream<Movie> find(String title) {
		return daos.parallelStream().flatMap(d -> d.retrieve(title));
	}
}
