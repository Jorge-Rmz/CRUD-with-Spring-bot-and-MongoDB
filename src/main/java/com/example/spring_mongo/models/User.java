package com.example.spring_mongo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document("Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
    private String name;

    @Field("last_name")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 30, message = "El apellido no puede tener más de 30 caracteres")
    private String lastName;

    @NotNull(message = "La edad no puede ser nula")
    @Size(min = 0, max = 90, message = "La edad debe estar entre 0 y 90 años")
    private Long age;

    @Field("user_name")
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 15, message = "El nombre de usuario no puede tener más de 15 caracteres")
    private String userName;

}
