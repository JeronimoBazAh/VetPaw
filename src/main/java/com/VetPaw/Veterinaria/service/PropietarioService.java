package com.VetPaw.Veterinaria.service;


import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioService implements com.VetPaw.Veterinaria.service.Service<Propietario> {


    @Autowired
    private PropietarioRepository propietarioRepository;




    public Optional<Propietario> findByDocumento(String documento){
        return propietarioRepository.findByDocumento(documento);
    }



    @Override
    public List<Propietario> findAll() {
        return List.of();
    }

    @Override
    public Propietario save(Propietario x) {
        return null;
    }

    @Override
    public Optional<Propietario> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
