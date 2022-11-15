package com.singer.application.dto.sr;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sr.SR03Entity;
import java.util.ArrayList;
import java.util.List;

public class SR03Composer {

    public static SR03Entity requestToEntity(SR03Request request, String userid) {
        SR03Entity sr03Entity = new SR03Entity();
        sr03Entity.setSeq01(request.getSeq01());
        sr03Entity.setParents(request.getParents());
        sr03Entity.setNowPage(request.getNowPage());
        sr03Entity.setText(request.getText());
        sr03Entity.setUserid(userid);
        sr03Entity.setRegdate(DateUtil.getTodayTime());
        return sr03Entity;
    }

    public static SR03Response entityToResponse(SR03Entity entity) {

        return new SR03Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(),
            entity.getGood(), entity.getReply(), entity.getRegdate(), entity.isDeleteYn());
    }

    public static SR03Entity selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SR03Entity sr03Entity = new SR03Entity();
        sr03Entity.setSeq01(seq01);
        sr03Entity.setParents(parents);
        sr03Entity.setNowPage(nowPage);
        return sr03Entity;
    }

    public static SR03Entity deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SR03Entity sr03Entity = new SR03Entity();
        sr03Entity.setSeq(seq);
        sr03Entity.setSeq01(seq01);
        sr03Entity.setParents(parents);
        return sr03Entity;
    }

    public static SR03ListResponse entityListToResponse(List<SR03Entity> list, int parents, int nowPage, int totCnt) {

        List<SR03Response> responseList = new ArrayList<>();

        for (SR03Entity entity : list) {
            responseList.add(new SR03Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(), entity.getGood(),
                entity.getReply(), entity.getRegdate(), entity.isDeleteYn()));
        }

        return new SR03ListResponse(responseList, parents, nowPage, totCnt);
    }
}
