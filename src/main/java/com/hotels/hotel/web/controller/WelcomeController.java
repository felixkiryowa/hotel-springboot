package com.hotels.hotel.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.micrometer.common.util.StringUtils;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getWelcomeBody(@RequestParam(value = "name", required = false)String name) {

        String greetings = "Hello ";

        if(StringUtils.isNotBlank(name)) {
           greetings = greetings + name;

        }else {
            greetings = greetings + "World";
        }

        return "<h1>" + greetings + "</h1>";
        
    }

}
