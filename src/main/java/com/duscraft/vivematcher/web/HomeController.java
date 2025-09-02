package com.duscraft.vivematcher.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Forward to the static index.html located in src/main/resources/static
        return "forward:/index.html";
    }
}
