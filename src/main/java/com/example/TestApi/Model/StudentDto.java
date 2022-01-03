package com.example.TestApi.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;


@NotNull
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    private Long id;
    @NotBlank(message = "fullname is mandatory")
    @Length(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    private String fullname;
    @NotBlank(message = "gender is mandatory")
    private String gender;

    private String birthday;
    @NotBlank(message = "address is mandatory")
    private String address;
    @NotBlank(message = "email is mandatory")
    @Email( message = "Invalid Email format ok ko em")
    private String email;
}