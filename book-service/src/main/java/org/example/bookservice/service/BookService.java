package org.example.bookservice.service;

import org.example.bookservice.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book saveBook(Book book);

    void deleteBookById(Long id);

    boolean isBookAvailable(Long id);

    void setUnavailable(Long id);

    void setAvailable(Long id);
}