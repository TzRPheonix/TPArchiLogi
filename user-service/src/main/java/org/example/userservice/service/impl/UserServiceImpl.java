package org.example.userservice.service.impl;

import org.example.userservice.entity.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User saveUser(User user) {
        if (user.getNombreMaxEmprunt() == null) {
            int defaultLimit = switch (user.getMembershipType()) {
                case PREMIUM -> 7;
                case REGULAR -> 5;
                default -> 5; // Valeur par défaut pour les cas non spécifiés
            };
            user.setNombreMaxEmprunt(defaultLimit);
        }

        // Sauvegarder l'utilisateur
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void lockUser(Long id) {
        User user = getUserById(id);
        user.setLocked(true);
        userRepository.save(user);
    }

    @Override
    public void unlockUser(Long id) {
        User user = getUserById(id);
        user.setLocked(false);
        userRepository.save(user);
    }
}
