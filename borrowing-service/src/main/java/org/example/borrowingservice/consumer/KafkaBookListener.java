package org.example.borrowingservice.consumer;

import org.example.borrowingservice.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaBookListener {

    //@Autowired
    //private BorrowingRepository borrowingRepository;

    //@KafkaListener(topics = "book-deleted", groupId = "borrowing-service-group")
    //public void consumeBookDeletedEvent(String bookId) {
    //    System.out.println("Consommateur activé pour le livre ID : " + bookId);

        // Supprimer les emprunts associés à ce livre
    //    borrowingRepository.deleteByBookId(Long.parseLong(bookId));
    //}
}

