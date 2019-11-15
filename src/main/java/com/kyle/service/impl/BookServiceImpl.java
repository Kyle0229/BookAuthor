package com.kyle.service.impl;

import com.kyle.dao.BookRespository;
import com.kyle.domain.Book;

import com.kyle.mapper.BookMapper;
import com.kyle.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRespository bookRespository;
    @Autowired
    private BookMapper bookMapper;
    @Override
    public List<Book> findBookown(Integer aid) {
        List<Book> byAid = bookMapper.selectBookAid(aid);
        return byAid;
    }
}
