package com.example.TestApi.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPass {
    @NotBlank(message = "Email can't is empty")
    @Email( message = "Invalid Email format")
    private String email;
}
