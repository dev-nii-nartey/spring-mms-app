package com.sanjeev.utils;

import com.sanjeev.dto.DtoUser;
import com.sanjeev.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppUtils {
    // Helper method to create ResponseEntity
    public static <T> ResponseEntity<T> createResponse(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }

    //Helper method to convert to Dto
    public static DtoUser convertToDto(User user) {
        DtoUser savedUser = new DtoUser();
        savedUser.setEmail(user.getEmail());
        savedUser.setFirstName(user.getFirst_name());
        savedUser.setLastName(user.getLast_name());
        savedUser.setRoles(user.getRole());
        return savedUser;
    }
}
