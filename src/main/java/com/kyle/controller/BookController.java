package com.kyle.controller;

import com.kyle.dao.BookRespository;
import com.kyle.dao.ChapterResporitory;
import com.kyle.domain.Book;
import com.kyle.domain.Chapter;
import com.kyle.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRespository bookRespository;
    @Autowired
    private ChapterResporitory chapterResporitory;
        @Autowired
        private BookService bookService;
        @RequestMapping("/findBookOwn/{aid}")
        public List<Book> findBookown(@PathVariable Integer aid){
            List<Book> bookown = bookService.findBookown(aid);
            return bookown;
        }
        @RequestMapping("/saveBook")
    public String saveBook(@RequestBody Book book){
            bookRespository.save(book);
            return "seccess";
        }
        @RequestMapping("/saveChapter")
    public String saveChapter(@RequestBody Chapter chapter){
            chapterResporitory.save(chapter);
            return "/seccess";
        }
    @RequestMapping("/selectChapter")
    public Chapter selectChapter(@RequestBody Chapter chapter){
        Optional<Chapter> byId = chapterResporitory.findById(chapter.getChid());
        Chapter chapter1 = byId.get();
        return chapter1;
    }
    @RequestMapping("/deleteChapter")
    public String deleteChapter(@RequestBody Chapter chapter){
            chapterResporitory.deleteById(chapter.getChid());
            return "seccess";
    }
}
