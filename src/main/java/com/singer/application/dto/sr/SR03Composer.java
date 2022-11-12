package com.singer.application.dto.sr;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sr.SR03Entity;
import java.util.ArrayList;
import java.util.List;

public class SR03Composer {

    public static SR03Entity requestToEntity(SR03Request request, String userid) {
        SR03Entity sr03Vo = new SR03Entity();
        sr03Vo.setSeq01(request.getSeq01());
        sr03Vo.setParents(request.getParents());
        sr03Vo.setNowPage(request.getNowPage());
        sr03Vo.setText(request.getText());
        sr03Vo.setUserid(userid);
        sr03Vo.setRegdate(DateUtil.getTodayTime());
        return sr03Vo;
    }

    public static SR03Response entityToResponse(SR03Entity sr03Vo) {

        return new SR03Response(sr03Vo.getSeq(), sr03Vo.getSeq01(), sr03Vo.getUserid(), sr03Vo.getText(),
            sr03Vo.getGood(), sr03Vo.getReply(), sr03Vo.getRegdate(), sr03Vo.isDeleteYn());
    }

    public static SR03Entity selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SR03Entity sr03Vo = new SR03Entity();
        sr03Vo.setSeq01(seq01);
        sr03Vo.setParents(parents);
        sr03Vo.setNowPage(nowPage);
        return sr03Vo;
    }

    public static SR03Entity deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SR03Entity sr03Vo = new SR03Entity();
        sr03Vo.setSeq(seq);
        sr03Vo.setSeq01(seq01);
        sr03Vo.setParents(parents);
        return sr03Vo;
    }

    public static SR03ListResponse entityListToResponse(List<SR03Entity> list, int parents, int nowPage, int totCnt) {

        List<SR03Response> responseList = new ArrayList<>();

        for (SR03Entity vo : list) {
            responseList.add(new SR03Response(vo.getSeq(), vo.getSeq01(), vo.getUserid(), vo.getText(), vo.getGood(),
                vo.getReply(), vo.getRegdate(), vo.isDeleteYn()));
        }

        return new SR03ListResponse(responseList, parents, nowPage, totCnt);
    }
}
