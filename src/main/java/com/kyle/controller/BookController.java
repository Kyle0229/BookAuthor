package com.kyle.controller;

import com.alibaba.fastjson.JSONObject;
import com.kyle.dao.BookRespository;
import com.kyle.dao.ChapterResporitory;
import com.kyle.domain.Author;
import com.kyle.domain.Book;
import com.kyle.domain.Chapter;
import com.kyle.service.BookService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
        @RequestMapping("/findBookOwn")
        public List<Book> findBookown(HttpSession session){
            Author author = (Author)session.getAttribute("author");
            Integer aid = author.getAid();
            List<Book> bookown = bookService.findBookown(aid);
                System.out.println(bookown);
            return bookown;
        }
        @RequestMapping("/deleteBook")
        public void deleteBook(Integer bid){
            bookRespository.deleteById(bid);
            return;
        }

        @RequestMapping("/saveBook")
    public String saveBook(@RequestBody Book book,HttpSession session){
            Author author = (Author) session.getAttribute("author");
            Integer aid = author.getAid();
            book.setAid(aid);
            book.setBstatus(0);
            bookRespository.save(book);
            return "success";
        }
        @RequestMapping("/saveChapter")
    public String saveChapter(@RequestBody Chapter chapter){
            chapterResporitory.save(chapter);
            return "success";
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


    @RequestMapping("/bookUpload")
    public void upload(HttpServletRequest request, HttpServletResponse response) {



    }
}
