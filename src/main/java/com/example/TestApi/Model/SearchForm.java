package com.example.TestApi.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {
    private String keyword;
    private int pageNo;
}
