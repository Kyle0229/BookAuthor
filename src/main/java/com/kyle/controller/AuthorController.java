package com.kyle.controller;

import com.kyle.dao.AuthorRespository;
import com.kyle.domain.Author;
import com.kyle.response.ResponseAuthor;
import com.kyle.service.AuthorService;
import com.kyle.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;


@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRespository authorRespository;
    @Resource
    private UploadUtils uploadUtils;

    @RequestMapping("/authorfindall/{page}/{size}")
    public ResponseAuthor findall(@PathVariable Integer page, @PathVariable Integer size) {
        return authorService.findAll(page, size);
    }

    @RequestMapping("/authorsave")
    public String save(@RequestBody Author author) {
        authorService.save(author);
        return "success";
    }


    @RequestMapping("/deleteOne")
    public String deleteOne(@RequestBody Author author) {
        authorService.deleteOne(author.getAid());
        return "success";
    }

    @RequestMapping("/selectOne")
    public Author selectOne(HttpSession session) {
        Author author = (Author)session.getAttribute("author");

        Integer aid = author.getAid();
        Author author1 = authorService.selectOne(aid);
        return author1;
    }
    //将作者钱包的金币乘0.8换成人民币
    @RequestMapping("/selectAuthormoney")
    public BigDecimal selectAuthormoney(HttpSession session) {
        Author author = (Author)session.getAttribute("author");
        BigDecimal awallet = author.getAwallet();
        BigDecimal a =null;
        Integer faultRate = 8;
        a = BigDecimal.valueOf(faultRate.doubleValue()/10);
        System.out.println(a);
        BigDecimal multiply = awallet.multiply(a);
        System.out.println(multiply);
        return multiply;
    }

    @RequestMapping("/updateAuther")
    public String updateAuther(@RequestBody Author author,HttpSession session){
            authorRespository.saveAndFlush(author);
            session.setAttribute("author",author);
            return "修改成功";
    }

    //上传头像
    @RequestMapping(value = "/updatePic",method = RequestMethod.POST)
    public String updatePic(MultipartFile file){
        if (file.isEmpty()){
            return null;
        }
        System.out.println(uploadUtils.upload(file));
        return uploadUtils.upload(file);
    }
}