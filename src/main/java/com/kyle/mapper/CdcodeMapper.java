package com.kyle.mapper;

import com.kyle.domain.Author;
import com.kyle.domain.Cdcode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CdcodeMapper {
    Cdcode selectcdTel(String aphone);
}
