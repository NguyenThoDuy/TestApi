package com.example.TestApi.Contoller;

import com.example.TestApi.Model.PageResponse;
import com.example.TestApi.Model.StudentDto;
import com.example.TestApi.Service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("student")
@Slf4j
public class StudentController {
    private final StudentService studentService;
    private Object id;

    @GetMapping("")
    public ModelAndView getAll(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("demo");
       PageResponse pageResponses = studentService.getAll(request);
        mav.addObject("students", pageResponses.getStudentDtos());
        mav.addObject("currentPage", pageResponses.getPageNo());
        mav.addObject("totalPages", pageResponses.getTotalPages());
        mav.addObject("totalItems", pageResponses.getTotalItems());
        mav.addObject("size", pageResponses.getSize());
        mav.addObject("sortField", pageResponses.getSortField());
        mav.addObject("sortDir", pageResponses.getSortDir());
        return mav;
    }


//    /student/page=5&size=10?sortField=fullname&sortDir=asc
    @GetMapping(value = "/page={pageNo}&size={size}andsortField={sortField}&sortDir={sortDir}")
    public ModelAndView page(HttpServletRequest request,
                               @PathVariable("pageNo") int pageNo,
                               @PathVariable("size") String size,
                               @PathVariable("sortField") String sortField,
                               @PathVariable("sortDir") String sortDir){
        ModelAndView mav = new ModelAndView("demo");
        PageResponse pageResponses = studentService.page(request, pageNo, size, sortField, sortDir);
        mav.addObject("students", pageResponses.getStudentDtos());
        mav.addObject("currentPage", pageResponses.getPageNo());
        mav.addObject("totalPages", pageResponses.getTotalPages());
        mav.addObject("totalItems", pageResponses.getTotalItems());
        mav.addObject("size", pageResponses.getSize());
        mav.addObject("sortField", pageResponses.getSortField());
        mav.addObject("sortDir", pageResponses.getSortDir());
        return mav;
    }

    //delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")  String id, HttpServletRequest request){
        Boolean check = studentService.delete(id, request);
        if(check){
            return "redirect:" + request.getHeader("Referer");
        }

        return "/api/auth/login";
    }


    //add new
    @GetMapping("add")
    public ModelAndView add(){
        ModelAndView mav = new ModelAndView("add");
        mav.addObject("student", new StudentDto());
        return mav;
    }
    @PostMapping("/add")
    public String add(@ModelAttribute StudentDto studentDto, HttpServletRequest request) throws URISyntaxException {
        studentService.create(studentDto,request);
        return "redirect:/student" ;
    }


    @GetMapping("/findById/{id}")
    public ModelAndView update(@PathVariable("id") String id, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("edit");
        Optional<StudentDto> studentDto = studentService.showById(request, id);
        mav.addObject("student", studentDto.get());
        return mav;
    }

    //update
    @PostMapping("update")
    public String update(@ModelAttribute StudentDto studentDto, HttpServletRequest request){
        studentService.update(request, studentDto);
        return "redirect:/student";
    }
    @GetMapping("search")

    public ModelAndView serach(HttpServletRequest request, @RequestParam("keyword") String keyword, @RequestParam("pageNo") String pageNo,
                               @RequestParam("size") String size, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir){
       return serachAndSort(request, keyword, pageNo, size, sortField, sortDir);
    }

    @GetMapping("search/keyword={keyword}andpage={pageNo}&size={size}andsortField={sortField}&sortDir={sortDir}")
    public ModelAndView serachAndSort(HttpServletRequest request, @PathVariable("keyword") String keyword,
                               @PathVariable("pageNo") String pageNo, @PathVariable("size") String size,
                               @PathVariable("sortField") String sortField, @PathVariable("sortDir") String sortDir) {
        ModelAndView mav = new ModelAndView("search");
        Optional<PageResponse> pageResponses = studentService.search(request, keyword, pageNo, size, sortField, sortDir);
        if(pageResponses.isPresent()) {
            mav.addObject("students", pageResponses.get().getStudentDtos());
            mav.addObject("currentPage", pageResponses.get().getPageNo());
            mav.addObject("totalPages", pageResponses.get().getTotalPages());
            mav.addObject("totalItems", pageResponses.get().getTotalItems());
            mav.addObject("keyword", keyword);
            mav.addObject("size", pageResponses.get().getSize());
            mav.addObject("sortField", pageResponses.get().getSortField());
            mav.addObject("sortDir", pageResponses.get().getSortDir());
        }else {
            mav.addObject("students", pageResponses.get().getStudentDtos());
        }
        return mav;
    }
}
