package com.example.TestApi.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResponse {
    private List<StudentDto> studentDtos;
    private  int pageNo;
    private long totalItems;
    private int totalPages;
    private String size;
    private String sortField;
    private String sortDir;
}

