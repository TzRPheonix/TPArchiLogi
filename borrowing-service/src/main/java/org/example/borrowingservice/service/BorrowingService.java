package org.example.borrowingservice.service;

import org.example.borrowingservice.entity.Borrowing;

import java.util.List;

public interface BorrowingService {
    List<Borrowing> getAllBorrowings();

    Borrowing getBorrowingById(Long id);

    Borrowing saveBorrowing(Borrowing book);

    void deleteBorrowingById(Long id);
}