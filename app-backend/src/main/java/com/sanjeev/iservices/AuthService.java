package com.sanjeev.iservices;

import com.sanjeev.dto.DtoUser;
import com.sanjeev.dto.LoginDto;

public interface AuthService {
    public DtoUser register(DtoUser userObject);
    String login(LoginDto loginDto);
}