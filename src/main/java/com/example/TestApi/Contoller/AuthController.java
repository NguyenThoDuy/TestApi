package com.example.TestApi.Contoller;

import com.example.TestApi.Model.ForgotPass;
import com.example.TestApi.Model.LoginRequest;
import com.example.TestApi.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("auth/login");
        mav.addObject("request", new LoginRequest());
        mav.addObject("error",true);
        return mav;
    }

    @PostMapping("/api/auth/login")
    public String add(@ModelAttribute LoginRequest loginRequest, Model model , HttpServletRequest request, HttpServletResponse respons) throws InterruptedException {
       boolean check = authService.login( request, loginRequest, respons);
        if(check){
            return "redirect:/student";
        }
            model.addAttribute("request", new LoginRequest());
            model.addAttribute("error", check);
            return "/auth/login.html";
    }

    @GetMapping("/api/auth/logout")
    public String add(HttpServletRequest request) throws InterruptedException {

        ModelAndView mav = new ModelAndView();
        boolean check = authService.logout( request);
        if(check){
            return "redirect:/";
        }else {
            mav.addObject("error", check);
        }
        return null;
    }

    @GetMapping("/api/auth/forgotPassword")
    public ModelAndView forgotPass(){
        ModelAndView mav = new ModelAndView("auth/forgotPass");
        mav.addObject("forgotPass", new ForgotPass());
        return mav;
    }

    @PostMapping("/api/auth/forgotPassword")
    public String addUser(@Valid @ModelAttribute ForgotPass forgotPass, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/forgotPass";
        }
        Boolean check = authService.forgotPassword(forgotPass);
            model.addAttribute("check", check);
            return "auth/forgotResult";
    }
}
