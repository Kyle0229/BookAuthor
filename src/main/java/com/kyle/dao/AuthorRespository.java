package com.kyle.dao;

import com.kyle.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRespository extends JpaRepository<Author,Integer> {
   Author findByAname(String aname);
   Author findByAphone(String aphone);
}
