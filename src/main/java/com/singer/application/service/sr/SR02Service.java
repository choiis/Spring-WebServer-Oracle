package com.singer.application.service.sr;

import com.singer.application.dto.sr.SR02Composer;
import com.singer.application.dto.sr.SR02Request;
import com.singer.application.dto.sr.SR02Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.singer.domain.dao.sr.SR02Dao;
import com.singer.domain.entity.sr.SR01Entity;
import com.singer.domain.entity.sr.SR02Entity;

@Service
public class SR02Service {

    @Autowired
    private SR02Dao sr02Dao;

    public SR02Response insertSR02(SR02Request request, String sessionid) throws Exception {

        SR02Entity sr02Entity = SR02Composer.requestToEntitu(request, sessionid);
        sr02Dao.insertSR02(sr02Entity);

        SR01Entity sr01Entity = new SR01Entity();
        sr01Entity.setSeq(sr02Entity.getSeq());
        sr02Dao.selectGradeSR02(sr01Entity);
        return SR02Composer.entityToResponse(sr01Entity);
    }

    public SR02Response selectOneSR02(int seq, String userid) throws Exception {
        SR01Entity sr01Entity = new SR01Entity();
        sr01Entity.setSeq(seq);
        sr01Entity.setUserid(userid);
        SR01Entity entity = sr02Dao.selectOneSR02(sr01Entity);

        return SR02Composer.entityToResponse(entity);
    }


    public int deleteSR02(int seq, String userid) throws Exception {
        SR01Entity sr01Entity = new SR01Entity();
        sr01Entity.setSeq(seq);
        sr01Entity.setSessionid(userid);
        return sr02Dao.deleteSR02(sr01Entity);
    }

}
