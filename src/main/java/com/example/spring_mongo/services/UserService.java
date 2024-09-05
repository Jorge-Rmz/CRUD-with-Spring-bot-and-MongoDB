package com.example.spring_mongo.services;

import com.example.spring_mongo.models.User;
import com.example.spring_mongo.repositories.UserRepository;
import com.mongodb.MongoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
        //return ResponseEntity.ok(users);

    }


    public ResponseEntity<?> createUser(User user) {
        try {
            // Intentar guardar el usuario
            User userCreated = userRepository.save(user);

            // Si se guarda correctamente, devolver 201 Created con el usuario creado
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (DataIntegrityViolationException ex) {
            // Manejar violaciones de integridad de datos, como campos únicos duplicados
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Violación de restricción de datos. " + ex.getMessage());
        } catch (MongoException ex) {
            // Manejar errores específicos de MongoDB
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Problema con la base de datos. " + ex.getMessage());
        } catch (Exception ex) {
            // Manejar cualquier otro tipo de excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Ocurrió un error inesperado. " + ex.getMessage());
        }
    }

    public ResponseEntity<User> updateUser(String id, User user) {
        Optional<User> userFind = userRepository.findById(id);

        if(userFind.isPresent()){
            User existingUser = userFind.get();

            existingUser.setUserName(user.getUserName());
            existingUser.setLastName(user.getLastName());
            existingUser.setAge(user.getAge());
            existingUser.setUserName(user.getUserName());

            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND, null);
    }
}
