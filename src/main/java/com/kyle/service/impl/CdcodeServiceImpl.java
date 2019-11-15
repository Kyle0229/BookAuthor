package com.kyle.service.impl;

import com.kyle.dao.CdcodeRespository;
import com.kyle.domain.Cdcode;
import com.kyle.mapper.CdcodeMapper;
import com.kyle.service.CdcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CdcodeServiceImpl implements CdcodeService {
    @Autowired
    private CdcodeRespository cdcodeRespository;
    @Autowired
    private CdcodeMapper cdcodeMapper;
    @Override
    public Cdcode savecode(Cdcode cdcode) {
        return cdcodeRespository.save(cdcode);
    }

    @Override
    public Cdcode selectcdTel(String aphone) {
        return cdcodeMapper.selectcdTel(aphone);
    }
}
