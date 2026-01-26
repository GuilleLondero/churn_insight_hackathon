package com.churnInsight.churnInsight.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/")
public class BienvenidaController {

    @GetMapping()
    public String getMethodName() {
        return "ChurnInsight Backend! Link de repositorio https://github.com/GuilleLondero/churn_insight_hackathon ";
    }
    
}
