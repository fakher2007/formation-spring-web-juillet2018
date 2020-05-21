package com.acme.common.persistence.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acme.common.persistence.ObjectStore;
import com.acme.common.persistence.Results;
import com.acme.ex3.ApplicationConfig;
import com.acme.ex3.model.entity.Author;
import com.acme.ex3.model.entity.Book;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class)
public class JpaObjectStoreImplTest {

	@Autowired
	private ObjectStore objectStore;
	
	@Test @Transactional(propagation=Propagation.REQUIRED)
    public void testSaveNew(){
        Author author = this.objectStore.getById(Author.class, 1).get(); /*equivalent de entityManager.find(Author.class, 1))*/;
        Book b = new Book();
        b.setTitle("new book");
        b.setAuthor(author);
        this.objectStore.saveOrUpdate(b);
        assertNotNull(b.getId());
    }

	@Test @Transactional(propagation=Propagation.REQUIRED)
    public void testUpdateExisting(){
        Optional<Book> _book = this.objectStore.getById(Book.class, 1) /*equivalent de Optional.ofNullable(entityManager.find(Book.class, 1))*/;
        if(_book.isPresent()){
            Book book = _book.get();
            book.setTitle("new title");
        }
    }

	@Test @Transactional(propagation=Propagation.REQUIRED)
    public void testDelete(){

    	this.objectStore.delete(Book.class, 1);
        assertFalse(this.objectStore.getById(Book.class, 1).isPresent());
    }

	@Test @Transactional(propagation=Propagation.REQUIRED)
    public void testGetById(){
        Optional<Book> _book1 = this.objectStore.getById(Book.class, 1) /*equivalent de Optional.ofNullable(entityManager.find(Book.class, 1))*/;
        System.out.println("after first call to getById");
        Book book1 = _book1.get();

        Optional<Book> _book1Again = this.objectStore.getById(Book.class, 1) /*equivalent de Optional.ofNullable(entityManager.find(Book.class, 1))*/;
        System.out.println("after second call to getById");
        Book book1Again = _book1Again.get();

        assertEquals(book1, book1Again);
    }

	@Test @Transactional(propagation=Propagation.REQUIRED)
    public void testGetByIdWithLazyLoading(){
        Optional<Book> _book = this.objectStore.getById(Book.class, 1) /*equivalent de Optional.ofNullable(entityManager.find(Book.class, 1))*/;
        System.out.println("after call to getById");
        _book.ifPresent(b->{
            System.out.println(b.getTitle());
            System.out.println(b.getAuthor().getFullname());
            System.out.println(b.getComments().size());
        });
    }

	@Test @Transactional(propagation=Propagation.REQUIRED)
    public void testGetByIdWithControlledLoading(){
        Optional<Book> _book = this.objectStore.getById(Book.class, 1, b -> b.getComments());
        System.out.println("after call to getById");
        _book.ifPresent(b -> {
            System.out.println(b.getTitle());
            System.out.println(b.getAuthor().getFullname());
            System.out.println(b.getComments().size());
            System.out.println(b.getReservations().size());
        });
    }


	@Test @Transactional(propagation=Propagation.REQUIRED)// ne pas mettre de @Transactional si ce n'est pas Mandatory dans JpaObjectStoreImpl
    public void testFind(){
    	String query1 = "select b from Book b join fetch b.author where b.title like :title";
    	String query2 = "select b from Book b where b.title like :title";
        Results<Book> a = this.objectStore.findMany(Book.class,  query1,  Map.of("title", "%%"));
        for (Book book : a.getItems()) {
            System.out.println(String.format("%-40s %-40s %-40s", book.getTitle(), book.getAuthor().getFullname(), book.getCategory().getName()));
        }
    }

}
