package com.example.spring_mongo.services;

import com.example.spring_mongo.models.User;
import com.example.spring_mongo.repositories.UserRepository;
import com.example.spring_mongo.tdo.GeneralResponse;
import com.mongodb.MongoException;
import org.apache.coyote.Response;
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

    public ResponseEntity<GeneralResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        GeneralResponse response = new GeneralResponse(HttpStatus.OK, "todos los usuarios", users);
        return ResponseEntity.ok().body(response);
        //return ResponseEntity.ok(users);

    }


    public ResponseEntity<GeneralResponse> createUser(User user) {
        try {
            // Intentar guardar el usuario
            User userCreated = userRepository.save(user);
            GeneralResponse response = new GeneralResponse(HttpStatus.CREATED,"Usuario creado correctamente",userCreated);
            // Si se guarda correctamente, devolver 201 Created con el usuario creado
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException ex) {
            GeneralResponse response = new GeneralResponse(HttpStatus.BAD_REQUEST,"Error: Violación de restricción de datos",null);
            // Manejar violaciones de integridad de datos, como campos únicos duplicados
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (MongoException ex) {
            GeneralResponse response = new GeneralResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Error: Problema con la base de datos",null);

            // Manejar errores específicos de MongoDB
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        } catch (Exception ex) {
            GeneralResponse response = new GeneralResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Error: Ocurrió un error inesperado." , null);
            // Manejar cualquier otro tipo de excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    public ResponseEntity<GeneralResponse> updateUser(String id, User user) {
        Optional<User> userFind = userRepository.findById(id);

        if(userFind.isPresent()){
            User existingUser = userFind.get();

            existingUser.setName(user.getName());
            existingUser.setLastName(user.getLastName());
            existingUser.setAge(user.getAge());
            existingUser.setUserName(user.getUserName());

            userRepository.save(existingUser);
            GeneralResponse response = new GeneralResponse(HttpStatus.OK,"Usuario editado correctamente" , existingUser);

            return ResponseEntity.ok().body(response);
        }
        GeneralResponse response = new GeneralResponse(HttpStatus.NOT_FOUND,"Usuario no encontrado en la base de datos" , null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public ResponseEntity<GeneralResponse> getById(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            GeneralResponse response = new GeneralResponse(HttpStatus.OK, "Usuario encontrado", user);
            return ResponseEntity.ok().body(response);
        }
        GeneralResponse response = new GeneralResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado", user);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public ResponseEntity<GeneralResponse> deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            GeneralResponse response = new GeneralResponse(HttpStatus.OK, "Usuario eliminado correctamente", user);
            return ResponseEntity.ok().body(response);
        }
        GeneralResponse response = new GeneralResponse(HttpStatus.OK, "Usuario eliminado correctamente", user);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
