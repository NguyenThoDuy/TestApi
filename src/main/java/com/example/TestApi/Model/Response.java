package com.example.TestApi.Model;

import lombok.Data;

@Data
public class Response <T> {
    private int code;
    private String message;
}
