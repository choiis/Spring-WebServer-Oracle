package com.singer.application.dto.sv;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sv.SV04Entity;
import java.util.ArrayList;
import java.util.List;

public class SV04Composer {

    public static SV04Entity requestToEntity(SV04Request request, String userid) {
        SV04Entity sv04Entity = new SV04Entity();
        sv04Entity.setSeq01(request.getSeq01());
        sv04Entity.setParents(request.getParents());
        sv04Entity.setNowPage(request.getNowPage());
        sv04Entity.setText(request.getText());
        sv04Entity.setUserid(userid);
        sv04Entity.setRegdate(DateUtil.getTodayTime());
        return sv04Entity;
    }

    public static SV04Response entityToResponse(SV04Entity entity) {

        return new SV04Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(),
            entity.getGood(), entity.getReply(), entity.getRegdate(), entity.isDeleteYn());
    }

    public static SV04Entity selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SV04Entity sv04Entity = new SV04Entity();
        sv04Entity.setSeq01(seq01);
        sv04Entity.setParents(parents);
        sv04Entity.setNowPage(nowPage);
        return sv04Entity;
    }

    public static SV04Entity deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SV04Entity sv04Entity = new SV04Entity();
        sv04Entity.setSeq(seq);
        sv04Entity.setSeq01(seq01);
        sv04Entity.setParents(parents);
        return sv04Entity;
    }

    public static SV04ListResponse entityListToResponse(List<SV04Entity> list, int parents, int nowPage, int totCnt) {

        List<SV04Response> responseList = new ArrayList<>();

        for (SV04Entity entity : list) {
            responseList.add(new SV04Response(entity.getSeq(), entity.getSeq01(), entity.getUserid(), entity.getText(), entity.getGood(),
                entity.getReply(), entity.getRegdate(), entity.isDeleteYn()));
        }

        return new SV04ListResponse(responseList, parents, nowPage, totCnt);
    }
}
