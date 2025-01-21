package org.example.borrowingservice.controller;

import org.example.borrowingservice.entity.Borrowing;
import org.example.borrowingservice.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @GetMapping
    public List<Borrowing> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @GetMapping("/{id}")
    public Borrowing getBorrowingById(@PathVariable Long id) {
        return borrowingService.getBorrowingById(id);
    }

    @PostMapping
    public Borrowing saveBorrowing(@RequestBody Borrowing borrowing) {
        return borrowingService.saveBorrowing(borrowing);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrowingById(@PathVariable Long id) {
        borrowingService.deleteBorrowingById(id);
    }
}
