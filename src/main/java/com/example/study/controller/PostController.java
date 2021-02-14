package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// html 폼 태그
// ajax 검색
// http post body->data
// json, xml, multipart-form, text-plain
@RestController
@RequestMapping("/api") //컨트롤러는 매핑 겹쳐도 상관 없음
public class PostController {
    @PostMapping(value = "/postMethod")
    public SearchParam postMethod(@RequestBody SearchParam searchParam){
        return searchParam;
    }

}
