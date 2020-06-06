package com.sr.taller1.controller;

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

    /**
     * Método encargado de direccionar a la vista del Taller1.
     *
     * @return Modelo
     */

    @RequestMapping("/taller1")
    public ModelAndView taller1() {
        Map<String, Object> model = new HashMap<>();
        model.put("tipoRecomendador","");
        model.put("algoritmo","");
        model.put("user","");
        model.put("tipo_algoritmo","");
        model.put("resultados","");
        return new ModelAndView("taller1", model);
    }

    @RequestMapping("/taller2")
    public ModelAndView taller2() {
        Map<String, Object> model = new HashMap<>();
        model.put("tipoRecomendador","");
        model.put("algoritmo","");
        model.put("user","");
        model.put("tipo_algoritmo","");
        model.put("resultados","");
        return new ModelAndView("taller2", model);
    }

    @RequestMapping("/taller3")
    public ModelAndView taller3() {
        Map<String, Object> model = new HashMap<>();
        model.put("tipoRecomendador","");
        model.put("algoritmo","");
        model.put("user","");
        model.put("tipo_algoritmo","");
        model.put("resultados","");
        return new ModelAndView("taller3", model);
    }


}