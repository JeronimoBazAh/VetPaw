package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements com.VetPaw.Veterinaria.service.Service<Usuario> {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) userRepository.findAll();
    }

    @Transactional
    @Override
    public Usuario save(Usuario x) {
        return userRepository.save(x);
    }

    @Transactional
    @Override
    public Optional<Usuario> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    public Optional<Usuario> findByDocumento(String documento){
        return userRepository.findByDocumento(documento);
    }



}
