package com.kyle.controller;

import com.kyle.domain.Book;
import com.kyle.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
        @Autowired
        private BookService bookService;
        @RequestMapping("/findBookOwn/{aid}")
        public List<Book> findBookown(@PathVariable Integer aid){
            List<Book> bookown = bookService.findBookown(aid);
            return bookown;
        }
}
