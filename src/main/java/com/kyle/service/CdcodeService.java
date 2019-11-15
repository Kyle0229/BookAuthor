package com.kyle.service;

import com.kyle.domain.Cdcode;

public interface CdcodeService {
    Cdcode savecode(Cdcode cdcode);
    Cdcode selectcdTel(String aphone);
}
