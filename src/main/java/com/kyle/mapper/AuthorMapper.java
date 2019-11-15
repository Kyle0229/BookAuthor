package com.kyle.mapper;

import com.kyle.domain.Author;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorMapper {
    Author selectByAphone(String aphone);
}
