package com.sanjeev.iservices;

import com.sanjeev.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}