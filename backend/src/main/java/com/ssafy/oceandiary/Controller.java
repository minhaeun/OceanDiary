package com.ssafy.oceandiary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api/test")
    public String getTest() {
        return "정상적으로 수행되었습니다. 2";
    }
}
