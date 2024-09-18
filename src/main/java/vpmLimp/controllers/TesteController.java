package vpmLimp.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teste")

public class TesteController {


    @GetMapping
    public String teste(){
        return "Hello World!";
    }
}

