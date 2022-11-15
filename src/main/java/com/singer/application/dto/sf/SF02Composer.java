package com.singer.application.dto.sf;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sf.SF02Entity;
import java.util.ArrayList;
import java.util.List;

public class SF02Composer {

    public static SF02Entity requestToEntity(SF02Request request, String userid) {
        SF02Entity sf02Entity = new SF02Entity();
        sf02Entity.setSeq01(request.getSeq01());
        sf02Entity.setParents(request.getParents());
        sf02Entity.setNowPage(request.getNowPage());
        sf02Entity.setText(request.getText());
        sf02Entity.setUserid(userid);
        sf02Entity.setRegdate(DateUtil.getTodayTime());
        return sf02Entity;
    }

    public static SF02Response entityToResponse(SF02Entity entity) {

        return new SF02Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(),
            entity.getGood(), entity.getReply(), entity.getRegdate(), entity.isDeleteYn());
    }

    public static SF02Entity selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SF02Entity sf02Entity = new SF02Entity();
        sf02Entity.setSeq01(seq01);
        sf02Entity.setParents(parents);
        sf02Entity.setNowPage(nowPage);
        return sf02Entity;
    }

    public static SF02Entity deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SF02Entity sf02Entity = new SF02Entity();
        sf02Entity.setSeq(seq);
        sf02Entity.setSeq01(seq01);
        sf02Entity.setParents(parents);
        return sf02Entity;
    }

    public static SF02ListResponse entityListToResponse(List<SF02Entity> list, int parents, int nowPage, int totCnt) {

        List<SF02Response> responseList = new ArrayList<>();

        for (SF02Entity entity : list) {
            responseList.add(new SF02Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(), entity.getGood(),
                entity.getReply(), entity.getRegdate(), entity.isDeleteYn()));
        }

        return new SF02ListResponse(responseList, parents, nowPage, totCnt);
    }
}
