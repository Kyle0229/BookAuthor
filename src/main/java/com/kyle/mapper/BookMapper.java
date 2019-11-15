package com.kyle.mapper;

import com.kyle.domain.Book;
import com.kyle.domain.Cdcode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> selectBookAid(Integer aid);
}
