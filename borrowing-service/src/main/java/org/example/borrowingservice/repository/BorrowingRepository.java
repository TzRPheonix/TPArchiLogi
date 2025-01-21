package org.example.borrowingservice.repository;

import org.example.borrowingservice.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
}


