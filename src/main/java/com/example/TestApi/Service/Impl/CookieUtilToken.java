package com.example.TestApi.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class CookieUtilToken {
    public static void create(String name, String value, Integer maxAge, HttpServletResponse respons) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        respons.addCookie(cookie);
    }

    public static void clear(String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
    }


    public String getCookieValue(HttpServletRequest req, String cookieName) {
        return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }


    //get token add url
    public static HttpHeaders jwtGetToken(HttpServletRequest request) {
        HttpHeaders headers = CookieUtilToken.getHeaders();
        Cookie[] cookies = request.getCookies();
        HttpEntity<String> jwtEntity = null;

        String token = "";
        if (cookies != null) {
            token = "Bearer " + Arrays.stream(cookies)
                    .filter(c -> c.getName().equalsIgnoreCase("JWTTOKEN"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
            headers.set("Authorization", token);
        }
        return headers;
    }

    //get refreshtoken
    //get token add url
    public static String getRefreshtToken(HttpServletRequest request) {
        HttpHeaders headers = CookieUtilToken.getHeaders();
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (cookies != null) {
            token = Arrays.stream(cookies)
                    .filter(c -> c.getName().equalsIgnoreCase("REFRESHTOKEN"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return token;
    }
}


