package com.singer.application.service.sr;

import com.singer.application.dto.sr.SR02Composer;
import com.singer.application.dto.sr.SR02Request;
import com.singer.application.dto.sr.SR02Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.singer.domain.dao.sr.SR02Dao;
import com.singer.domain.entity.sr.SR01Vo;
import com.singer.domain.entity.sr.SR02Vo;

@Service
public class SR02Service {

    @Autowired
    private SR02Dao sr02Dao;

    public SR02Response insertSR02Vo(SR02Request request, String sessionid) throws Exception {

        SR02Vo sr02Vo = SR02Composer.requestToEntitu(request, sessionid);
        sr02Dao.insertSR02Vo(sr02Vo);

        SR01Vo sr01Vo = new SR01Vo();
        sr01Vo.setSeq(sr02Vo.getSeq());
        sr02Dao.selectGradeSR02Vo(sr01Vo);
        return SR02Composer.entityToResponse(sr01Vo);
    }

    public SR02Response selectOneSR02Vo(int seq, String userid) throws Exception {
        SR01Vo sr01Vo = new SR01Vo();
        sr01Vo.setSeq(seq);
        sr01Vo.setUserid(userid);
        SR01Vo vo = sr02Dao.selectOneSR02Vo(sr01Vo);

        return SR02Composer.entityToResponse(vo);
    }


    public int deleteSR02Vo(int seq, String userid) throws Exception {
        SR01Vo sr01Vo = new SR01Vo();
        sr01Vo.setSeq(seq);
        sr01Vo.setSessionid(userid);
        return sr02Dao.deleteSR02Vo(sr01Vo);
    }

}
