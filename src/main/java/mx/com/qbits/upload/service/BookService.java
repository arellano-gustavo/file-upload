package mx.com.qbits.upload.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import mx.com.qbits.upload.model.Book;

@Service
public class BookService {
    private final ConcurrentMap<String, Book> books;

    public BookService() {
        this.books = new ConcurrentHashMap<>();
    }

    public Collection<Book> getAllBooks() {
        Collection<Book> allBooks = books.values();
        return allBooks.isEmpty() ? Collections.emptyList() : new ArrayList<>(allBooks);
    }
    
    public Book getBook(String oid) {
        return new Book(oid, "Nombre libro", "Asimov", "Ficcion");
    }
    
    public void addBook(Book book) {
        System.out.println("Libro Agregado");
    }
    
    public Book updateBook(String oid, Book book) {
        System.out.println("Libro Actualizado");
        return book;
    }
    
    public void deleteBook(String oid) {
        System.out.println("Libro Borrado");
    }
}
