package com.sr.taller3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CreateRatingController {

    @RequestMapping("/t3_add_user_rating")
    public ModelAndView t3UsuarioRating(@RequestParam Map<String, String> params) {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("taller3UsuarioRating", model);
    }

}
