package com.acme.common.persistence.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.acme.common.JpaConfig4Test;
import com.acme.common.persistence.ObjectStore;
import com.acme.common.persistence.Results;
import com.acme.ex3.ApplicationConfig;
import com.acme.ex3.model.entity.Author;
import com.acme.ex3.model.entity.Book;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
@ContextConfiguration(classes= {ApplicationConfig.class, JpaConfig4Test.class})
public class JpaObjectStoreImplTest {
	
	@Autowired
	private ObjectStore objectStore;
	
	@Test @Transactional(propagation=Propagation.REQUIRED) 
    public void testSaveNew(){
		System.out.println(this.objectStore.getClass());
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
            book.setTitle("Walden");
        }
        // code de vérification
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
        assertTrue(book1==book1Again);
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



	
	@Test @Transactional(propagation=Propagation.REQUIRED)
    public void testFind() {
		
    	String query1 = "select b from Book b join fetch b.author where b.title like :title";
    	String query2 = "select b from Book b where b.title like :title";
        Results<Book> a = this.objectStore.findMany(Book.class,  query2,  Map.of("title", "%%"));
        for (Book book : a.getItems()) {
            System.out.println(String.format("%-40s %-40s %-40s", book.getTitle(), book.getAuthor().getFullname(), book.getCategory().getName()));
        }
    }
    

}
