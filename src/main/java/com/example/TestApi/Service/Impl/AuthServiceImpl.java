package com.example.TestApi.Service.Impl;

import com.example.TestApi.Model.*;
import com.example.TestApi.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RestTemplate restTemplate;


    private final String LOGIN = "http://localhost:8080/api/auth/login";
    private final String LOGOUT = "http://localhost:8080/api/auth/logout";
    private final String REFRESHTOKEN= "http://localhost:8080/api/auth/refreshtoken";
    private final String RESETPASS = "http://localhost:8080/api/auth/resetPasswod";

    @Override
    public Boolean login( HttpServletRequest request, LoginRequest loginRequest, HttpServletResponse respons) {
        Boolean check = null;
        try {
            // Register User
            ResponseEntity<JwtResponse> registrationResponse = restTemplate.postForEntity(LOGIN, loginRequest, JwtResponse.class);
            // if the registration is successful
            if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
                JwtResponse jwtResponse = registrationResponse.getBody();
                log.info("login sucess");
                check = true;
                // create a cookie

                CookieUtilToken.create("JWTTOKEN", jwtResponse.getToken(), 30*60, respons);
                CookieUtilToken.create("REFRESHTOKEN", jwtResponse.getRefreshToken(), 60*60, respons);
                String id_user = String.valueOf(jwtResponse.getId());
                CookieUtilToken.create("USERID", id_user, 60*60, respons);
                CookieUtilToken.create("USERNAME", jwtResponse.getUsername(), 60*60, respons);

            }

        } catch (Exception ex) {
            System.out.println(ex);
            check = false;
        }

        return check;
    }

    @Override
    public boolean logout(HttpServletRequest request) {
        boolean check = false;
        try {
            HttpHeaders headers = CookieUtilToken.jwtGetToken(request);
            HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
            ResponseEntity<String> studennById = restTemplate.exchange(LOGOUT, HttpMethod.POST, jwtEntity, String.class);
            if (studennById.getStatusCode().equals(HttpStatus.OK)) {
                check = true;
                CookieUtilToken.clear("JWTTOKEN");
                CookieUtilToken.clear("REFRESHTOKEN");
                CookieUtilToken.clear("USERID");
                CookieUtilToken.clear("USERNAME");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return check;
    }

    @Override
    public Boolean forgotPassword(ForgotPass forgotPass) {
        Boolean check = false;
      try {
          ResponseEntity<String> resetPassword = restTemplate.postForEntity(RESETPASS, forgotPass, String.class);
          if (resetPassword.getStatusCode().equals(HttpStatus.OK)) {
              check = true;
          }
          if(resetPassword.getStatusCode().equals(HttpStatus.NOT_FOUND)){
              check = false;
          }
      }catch (Exception ex){
          ex.printStackTrace();
      }

        return check;
    }




//    public void refreshToken(HttpServletRequest request) {
//        try {
//            HttpHeaders headers = CookieUtilToken.jwtGetToken(request);
//            String refreshToken = CookieUtilToken.getRefreshtToken(request);
//            if(headers == null & !refreshToken.isEmpty()) {
//                TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest();
//                tokenRefreshRequest.setRefreshToken(refreshToken);
//                ResponseEntity<TokenRefreshResponse> RefreshResponse= restTemplate.postForEntity(REFRESHTOKEN, tokenRefreshRequest, TokenRefreshResponse.class);
//            if (RefreshResponse.getStatusCode().equals(HttpStatus.OK)) {
//                CookieUtilToken.create("JWTTOKEN", jwtResponse.getToken(), 30*60, respons);
//            }
//            }
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//    }

}


//    HttpHeaders headers = CookieUtilToken.getHeaders();
//    Cookie[] cookies = request.getCookies();
//
//                if (cookies != null) {
//                        String token = "Bearer " + Arrays.stream(cookies)
//                        .filter(c -> c.getName().equalsIgnoreCase("JWTTOKEN"))
//                        .findFirst()
//                        .map(Cookie::getValue)
//                        .orElse(null);
//
//                        else {
//                        headers.set("Authorization", token);
//                        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
//        ResponseEntity<JwtResponse> helloResponse = restTemplate.exchange(LOGIN, HttpMethod.POST, jwtEntity,
//        JwtResponse.class);
//        if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
//        log.info("login sucess");
//        check = true;
//        }
