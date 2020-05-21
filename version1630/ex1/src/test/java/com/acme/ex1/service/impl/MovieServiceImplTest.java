package com.acme.ex1.service.impl;

import com.acme.ex1.ApplicationConfig;
import com.acme.ex1.service.MovieService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MovieServiceImplTest {

    private MovieService service;

    public MovieServiceImplTest() {
        // TODO : chargement du contexte et valorisation du champ service
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        this.service = ctx.getBean("service1", MovieServiceImpl.class);
    }

    @Test
    public void testFindMoviesWithResults() {
        service.find("a").forEach(m -> {
            System.out.println(m.getTitle());
            assertTrue(m.getTitle().contains("a"));
        });
    }

    @Test
    public void testFindMoviesWithNoResult() {
        assertNotNull(service.find("_*_*_*_*_*_*"));
    }

}
