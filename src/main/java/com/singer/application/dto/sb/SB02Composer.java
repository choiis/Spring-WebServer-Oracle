package com.singer.application.dto.sb;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sb.SB02Entity;
import java.util.ArrayList;
import java.util.List;

public class SB02Composer {

    public static SB02Entity requestToEntity(SB02Request request, String userid) {
        SB02Entity sb02Entity = new SB02Entity();
        sb02Entity.setParents(request.getParents());
        sb02Entity.setSeq01(request.getSeq01());
        sb02Entity.setParents(request.getParents());
        sb02Entity.setNowPage(request.getNowPage());
        sb02Entity.setText(request.getText());
        sb02Entity.setUserid(userid);
        sb02Entity.setRegdate(DateUtil.getTodayTime());
        return sb02Entity;
    }

    public static SB02Response entityToResponse(SB02Entity entity) {

        return new SB02Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(),
            entity.getGood(), entity.getReply(), entity.getRegdate(), entity.isDeleteYn());
    }

    public static SB02Entity selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SB02Entity sb02Entity = new SB02Entity();
        sb02Entity.setSeq01(seq01);
        sb02Entity.setParents(parents);
        sb02Entity.setNowPage(nowPage);
        return sb02Entity;
    }

    public static SB02Entity deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SB02Entity sb02Entity = new SB02Entity();
        sb02Entity.setSeq(seq);
        sb02Entity.setSeq01(seq01);
        sb02Entity.setParents(parents);
        return sb02Entity;
    }

    public static SB02ListResponse entityListToResponse(List<SB02Entity> list, int parents, int nowPage, int totCnt) {

        List<SB02Response> responseList = new ArrayList<>();

        for (SB02Entity entity : list) {
            responseList.add(new SB02Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(), entity.getGood(),
                entity.getReply(), entity.getRegdate(), entity.isDeleteYn()));
        }

        return new SB02ListResponse(responseList, parents, nowPage, totCnt);
    }
}
