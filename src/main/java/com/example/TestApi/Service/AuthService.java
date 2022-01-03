package com.example.TestApi.Service;

import com.example.TestApi.Model.ForgotPass;
import com.example.TestApi.Model.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    Boolean login(HttpServletRequest request, LoginRequest loginRequest, HttpServletResponse respons);

    boolean logout(HttpServletRequest request);

    Boolean forgotPassword(ForgotPass forgotPass);
}
