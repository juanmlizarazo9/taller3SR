package com.sr.taller3.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Clase controladora, encargada de redireccionar a la visa de cada uno de los talleres.
 *
 */
@Controller
public class SRController {

    @RequestMapping("/")
    public String index(Model model) {

        return "index";
    }

   @RequestMapping("/taller3")
    public ModelAndView taller3() {
        Map<String, Object> model = new HashMap<>();
        model.put("user","");
        model.put("tipo_algoritmo","");
        model.put("resultados","");
        return new ModelAndView("taller3", model);
    }


}