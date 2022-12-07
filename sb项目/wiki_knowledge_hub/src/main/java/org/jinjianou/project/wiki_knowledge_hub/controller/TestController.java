package org.jinjianou.project.wiki_knowledge_hub.controller;

import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
@RequestMapping("/test")
//@CrossOrigin
public class TestController {
    @GetMapping("get")
    public String get(){
        return "Hello World!";
    }

    @GetMapping("getMsg")
    public String getMsg(){
        return "Msg From Backend";
    }

    @PostMapping("post")
    public String post(@RequestBody Map<String,String> params){
        return "Hello World! POST"+params.get("name");
    }

}
