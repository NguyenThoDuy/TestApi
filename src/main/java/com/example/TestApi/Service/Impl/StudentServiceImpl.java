package com.example.TestApi.Service.Impl;

import com.example.TestApi.Model.*;
import com.example.TestApi.Service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private RestTemplate restTemplate;

    private final String GETALL = "http://localhost:8080/student";
    private final String FINDBYID = "http://localhost:8080/student/findById/";
    private final String PAGE = "http://localhost:8080/student/page=";
    private final String DELETE = "http://localhost:8080/student/delete/";
    private final String ADD = "http://localhost:8080/student/add";
    private final String UPDATE = "http://localhost:8080/student/update";
    private final String SEARCH = "http://localhost:8080/student/search/keyword=";

    @Override
    public PageResponse getAll(HttpServletRequest request) {
        PageResponse studentDtos = null;
        try {
                HttpEntity<String> jwtEntity = new HttpEntity<String>(CookieUtilToken.jwtGetToken(request));
//                ResponseEntity<List<PageResponse>> getAllStudent = restTemplate.exchange(GETALL, HttpMethod.GET, jwtEntity,  new ParameterizedTypeReference<List<PageResponse>>() {});
                ResponseEntity<PageResponse> getAllStudent = restTemplate.exchange(GETALL, HttpMethod.GET, jwtEntity, PageResponse.class);
                if (getAllStudent.getStatusCode().equals(HttpStatus.OK)) {
                    studentDtos = (PageResponse) getAllStudent.getBody();
                }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return studentDtos;
    }

    @Override
    public Optional<StudentDto> showById(HttpServletRequest request, String id) {
        Optional<StudentDto> studentDto = null;
        try {
            HttpHeaders headers = CookieUtilToken.jwtGetToken(request);
                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                ResponseEntity<StudentDto> studennById = restTemplate.exchange(FINDBYID + id, HttpMethod.GET, jwtEntity, StudentDto.class);
                if (studennById.getStatusCode().equals(HttpStatus.OK)) {
                    studentDto = Optional.ofNullable((StudentDto) studennById.getBody());
                }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return studentDto;
    }

    @Override
    public PageResponse page(HttpServletRequest request, int pageNo, String size, String sortField, String sortDir){
//        /student/page={currentPage}&size=${size}andsortField={sortField}&sortDir={sortDir}
        PageResponse pageResponse = null;
        try {
//            if(size == null){size = String.valueOf(10);}
//            if(sortField == null){sortField = "asc";}
//            if(sortDir == null){sortField = "id";}
                HttpEntity<String> jwtEntity = new HttpEntity<String>(CookieUtilToken.jwtGetToken(request));
                String curpage = String.valueOf(pageNo);
                String link = curpage + "&size=" + size + "andsortField=" + sortField + "&sortDir=" + sortDir;
//                ResponseEntity<List<PageResponse>> getAllStudent = restTemplate.exchange(GETALL, HttpMethod.GET, jwtEntity,  new ParameterizedTypeReference<List<PageResponse>>() {});
                ResponseEntity<PageResponse> getAllStudent = restTemplate.exchange(PAGE + link, HttpMethod.GET, jwtEntity, PageResponse.class);
                if (getAllStudent.getStatusCode().equals(HttpStatus.OK)) {
                    pageResponse = (PageResponse) getAllStudent.getBody();
                }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return pageResponse;
    }

    @Override
    public Boolean delete(String id, HttpServletRequest request) {
        Boolean check = false;
        HttpEntity<String> jwtEntity = new HttpEntity<String>(CookieUtilToken.jwtGetToken(request));
        ResponseEntity<?> checkDelete = restTemplate.exchange(DELETE + id, HttpMethod.DELETE, jwtEntity, Response.class);
        if (checkDelete.getStatusCode().equals(HttpStatus.OK)) {
            check = true;
        }
        return check;
    }

    @Override
    public Boolean create(StudentDto studentDto, HttpServletRequest request) throws URISyntaxException {
        Boolean check = null;
        HttpHeaders headers = CookieUtilToken.jwtGetToken(request);
            HttpEntity<String> requestUrl = new HttpEntity<String>(studentDto.toString(), headers);
//            URI uri = new URI(ADD);
            HttpEntity<StudentDto> requests = new HttpEntity<>(studentDto, headers);
            ResponseEntity<StudentDto> result = restTemplate.postForEntity(ADD, requests, StudentDto.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                check = true;
            }
        return check;
    }

    @Override
    public void update(HttpServletRequest request, StudentDto studentDto) {
        Boolean check = null;
        HttpHeaders headers = CookieUtilToken.jwtGetToken(request);
        HttpEntity<StudentDto> requests = new HttpEntity<>(studentDto, headers);
        ResponseEntity<StudentDto> result = restTemplate.postForEntity(UPDATE, requests, StudentDto.class);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            check = true;
        }
    }

    @Override
    public Optional<PageResponse> search(HttpServletRequest request, String keyword, String pageNo, String size, String sortField, String sortDir) {
        Optional<PageResponse> studentDtos = null;
//        SearchForm searchForm = new SearchForm();
//        searchForm.setKeyword(keyword);
//        searchForm.setPageNo(pageNo);
//
//        HttpHeaders headers = CookieUtilToken.jwtGetToken(request);
//        HttpEntity<SearchForm> requests = new HttpEntity<>(searchForm, headers);
//        "search/keyword={keyword}andpage={pageNo}&size={size}andsortField={sortField}&sortDir={sortDir}"
        HttpEntity<String> jwtEntity = new HttpEntity<String>(CookieUtilToken.jwtGetToken(request));
        String key = keyword + "andpage=" + pageNo + "&size=" + size + "andsortField=" + sortField + "&sortDir=" + sortDir;
        ResponseEntity<PageResponse> resultSearch = restTemplate.exchange(SEARCH+key,HttpMethod.GET, jwtEntity, PageResponse.class);
        if (resultSearch.getStatusCode().equals(HttpStatus.OK)) {
            studentDtos = Optional.ofNullable((PageResponse) resultSearch.getBody());
        }else {
            studentDtos = null;
        }
        return studentDtos;
    }

}
