package com.ruan.laboratorio.service;

import com.ruan.laboratorio.entity.users.UserDTO;
import com.ruan.laboratorio.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    public UserDTO getUsersById(Integer id) {
        return userRepository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteUser(Integer id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("Usuario não encontrada" + id);
        }
            userRepository.deleteById(id);
    }
}
