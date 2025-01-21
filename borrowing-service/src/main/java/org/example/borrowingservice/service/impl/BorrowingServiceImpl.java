package org.example.borrowingservice.service.impl;

import org.example.borrowingservice.entity.Borrowing;
import org.example.borrowingservice.repository.BorrowingRepository;
import org.example.borrowingservice.rest.ServiceClient;
import org.example.borrowingservice.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private ServiceClient serviceClient;

    @Override
    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    @Override
    public Borrowing getBorrowingById(Long id) {

        return borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Borrowing not found"));
    }

    @Override
    public Borrowing saveBorrowing(Borrowing borrowing) {
        // Vérification du rang de l'utilisateur
        String userRank = serviceClient.getUserRank(borrowing.getUserId());
        int maxBorrowingLimit = "PREMIUM".equalsIgnoreCase(userRank) ? 7 : 5; // 7 pour PREMIUM, 5 pour REGULAR

        // Récupérer tous les emprunts
        List<Borrowing> allBorrowings = borrowingRepository.findAll();

        // Vérifier si l'utilisateur dépasse la limite fixée par son rang
        long borrowingCount = allBorrowings.stream()
                .filter(b -> b.getUserId().equals(borrowing.getUserId()))
                .count();

        if (borrowingCount >= maxBorrowingLimit) {
            // Verrouiller l'utilisateur si la limite est atteinte
            serviceClient.lockUser(borrowing.getUserId());
            throw new RuntimeException("Erreur : Trop de livres réservés. L'utilisateur a été verrouillé.");
        }

        // Vérifier si l'utilisateur a déjà réservé ce livre
        boolean alreadyBorrowed = allBorrowings.stream()
                .anyMatch(b -> b.getUserId().equals(borrowing.getUserId()) &&
                        b.getBookId().equals(borrowing.getBookId()));

        if (alreadyBorrowed) {
            throw new RuntimeException("Erreur : Vous avez déjà réservé ce livre.");
        }

        // Vérifier si le livre est déjà emprunté par une autre personne
        boolean bookAlreadyBorrowed = allBorrowings.stream()
                .anyMatch(b -> b.getBookId().equals(borrowing.getBookId()));

        if (bookAlreadyBorrowed) {
            throw new RuntimeException("Erreur : Ce livre est déjà emprunté par un autre utilisateur.");
        }

        // Marquer le livre comme indisponible
        serviceClient.setUnavailable(borrowing.getBookId());

        // Si toutes les vérifications sont passées, enregistrer l'emprunt
        return borrowingRepository.save(borrowing);
    }

    @Override
    public void deleteBorrowingById(Long id) {
        // Récupérer l'emprunt à supprimer
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowing not found"));

        // Supprimer l'emprunt
        borrowingRepository.deleteById(id);

        // Marquer le livre comme disponible
        serviceClient.setAvailable(borrowing.getBookId());

        // Vérifier si l'utilisateur doit être déverrouillé après suppression
        List<Borrowing> userBorrowings = borrowingRepository.findAll();
        long borrowingCount = userBorrowings.stream()
                .filter(b -> b.getUserId().equals(borrowing.getUserId()))
                .count();

        // Vérification du rang pour récupérer la limite
        String userRank = serviceClient.getUserRank(borrowing.getUserId());
        int maxBorrowingLimit = "PREMIUM".equalsIgnoreCase(userRank) ? 7 : 5;

        // Déverrouiller l'utilisateur si le nombre d'emprunts est en dessous de la limite
        if (borrowingCount < maxBorrowingLimit) {
            serviceClient.unlockUser(borrowing.getUserId());
            System.out.println("L'utilisateur avec ID " + borrowing.getUserId() + " a été déverrouillé.");
        }
    }

}
