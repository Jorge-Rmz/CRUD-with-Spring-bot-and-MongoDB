package com.example.spring_mongo.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {
    private HttpStatus code;
    private String message;
    private Object resultInfo;

}
