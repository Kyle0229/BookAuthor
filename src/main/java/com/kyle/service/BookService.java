package com.kyle.service;

import com.kyle.domain.Book;

import java.util.List;


public interface BookService {
    List<Book> findBookown(Integer aid);
}
