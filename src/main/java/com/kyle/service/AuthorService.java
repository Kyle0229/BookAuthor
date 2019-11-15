package com.kyle.service;

import com.kyle.domain.Author;
import com.kyle.response.ResponseAuthor;

public interface AuthorService {
    ResponseAuthor findAll(Integer page, Integer size);
    Author save(Author author);
    Author selectOne(Integer aid);
    void deleteOne(Integer aid);
    Author selectbyTel(String aphone);
}
