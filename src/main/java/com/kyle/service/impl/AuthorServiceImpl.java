package com.kyle.service.impl;

import com.kyle.dao.AuthorRespository;
import com.kyle.domain.Author;
import com.kyle.mapper.AuthorMapper;
import com.kyle.response.ResponseAuthor;
import com.kyle.service.AuthorService;
import com.kyle.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRespository authorRespository;
    @Autowired
    private AuthorMapper authorMapper;
    @Override
    public ResponseAuthor findAll(Integer page, Integer size) {
        Pageable pages=PageRequest.of(page-1,size);
        Page<Author> all=authorRespository.findAll(pages);
        ResponseAuthor res=new ResponseAuthor();
        res.setList(all.getContent());
        res.setTotal(all.getTotalElements());
        return res;
    }

    @Override
    public Author save(Author author) {
        String apassword = author.getApassword();
        String aname = author.getAname();
        String password1 =Md5Utils.encryptPassword(apassword,aname);
        author.setApassword(password1);
        return authorRespository.save(author);
    }

    @Override
    public Author selectOne(Integer aid) {
        Optional<Author> byId = authorRespository.findById(aid);
        Author author = byId.get();
        return author;
    }

    @Override
    public void deleteOne(Integer aid) {
        authorRespository.deleteById(aid);
    }

    @Override
    public Author selectbyTel(String aphone) {
        return authorMapper.selectByAphone(aphone);
    }
}
