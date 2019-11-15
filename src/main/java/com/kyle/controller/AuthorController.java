package com.kyle.controller;

import com.kyle.dao.AuthorRespository;
import com.kyle.domain.Author;
import com.kyle.response.ResponseAuthor;
import com.kyle.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRespository authorRespository;


    @RequestMapping("/authorfindall/{page}/{size}")
    public ResponseAuthor findall(@PathVariable Integer page, @PathVariable Integer size) {
        return authorService.findAll(page, size);
    }

    @RequestMapping("/authorsave")
    public String save(@RequestBody Author author) {
        authorService.save(author);
        return "success";
    }

    @RequestMapping("/selectOne/{aid}")
    public Author selectOne(@PathVariable Integer aid) {
        return authorService.selectOne(aid);
    }

    @RequestMapping("/deleteOne")
    public String deleteOne(@RequestBody Author author) {
        authorService.deleteOne(author.getAid());
        return "success";
    }
}