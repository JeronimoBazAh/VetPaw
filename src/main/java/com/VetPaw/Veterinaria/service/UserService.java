package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.User;
import com.VetPaw.Veterinaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements com.VetPaw.Veterinaria.service.Service<User> {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User x) {
        return userRepository.save(x);
    }

    @Transactional
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    public Optional<User> findByDocumento(String documento){
        return userRepository.findByDocumento(documento);
    }



}
