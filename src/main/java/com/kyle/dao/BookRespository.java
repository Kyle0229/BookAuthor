package com.kyle.dao;

import com.kyle.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRespository extends JpaRepository<Book,Integer> {
    Book findByAid(Integer aid);
}
