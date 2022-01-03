package com.example.TestApi.Service;

import com.example.TestApi.Model.PageResponse;
import com.example.TestApi.Model.StudentDto;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    PageResponse getAll(HttpServletRequest request);

    Optional<StudentDto> showById(HttpServletRequest request, String id);



    Boolean delete(String id, HttpServletRequest request);

    Boolean create(StudentDto studentDto, HttpServletRequest request) throws URISyntaxException;

    void update(HttpServletRequest request, StudentDto studentDto);

    PageResponse page(HttpServletRequest request, int pageNo, String size, String sortField, String sortDir);

    Optional<PageResponse> search(HttpServletRequest request, String keyword, String pageNo, String size, String sortField, String sortDir);
}
