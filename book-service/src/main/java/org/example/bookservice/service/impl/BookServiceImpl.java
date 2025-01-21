package org.example.bookservice.service.impl;
import org.example.bookservice.entity.Book;
import org.example.bookservice.repository.BookRepository;
import org.example.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }


    @Override
    public boolean isBookAvailable(Long id) {
        Book book = getBookById(id);
        return book.isAvailable();
    }

    @Override
    public void setUnavailable(Long id) {
        Book book = getBookById(id);
        book.setAvailable(false);
        bookRepository.save(book);
    }

    @Override
    public void setAvailable(Long id) {
        Book book = getBookById(id);
        book.setAvailable(true);
        bookRepository.save(book);
    }

}