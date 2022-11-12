package com.singer.application.dto.sb;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sb.SB02Entity;
import java.util.ArrayList;
import java.util.List;

public class SB02Composer {

    public static SB02Entity requestToEntity(SB02Request request, String userid) {
        SB02Entity sb02Vo = new SB02Entity();
        sb02Vo.setParents(request.getParents());
        sb02Vo.setSeq01(request.getSeq01());
        sb02Vo.setParents(request.getParents());
        sb02Vo.setNowPage(request.getNowPage());
        sb02Vo.setText(request.getText());
        sb02Vo.setUserid(userid);
        sb02Vo.setRegdate(DateUtil.getTodayTime());
        return sb02Vo;
    }

    public static SB02Response entityToResponse(SB02Entity sb02Vo) {

        return new SB02Response(sb02Vo.getSeq(), sb02Vo.getSeq01(), sb02Vo.getUserid(), sb02Vo.getText(),
            sb02Vo.getGood(), sb02Vo.getReply(), sb02Vo.getRegdate(), sb02Vo.isDeleteYn());
    }

    public static SB02Entity selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SB02Entity sb02Vo = new SB02Entity();
        sb02Vo.setSeq01(seq01);
        sb02Vo.setParents(parents);
        sb02Vo.setNowPage(nowPage);
        return sb02Vo;
    }

    public static SB02Entity deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SB02Entity sb02Vo = new SB02Entity();
        sb02Vo.setSeq(seq);
        sb02Vo.setSeq01(seq01);
        sb02Vo.setParents(parents);
        return sb02Vo;
    }

    public static SB02ListResponse entityListToResponse(List<SB02Entity> list, int parents, int nowPage, int totCnt) {

        List<SB02Response> responseList = new ArrayList<>();

        for (SB02Entity vo : list) {
            responseList.add(new SB02Response(vo.getSeq(), vo.getSeq01(), vo.getUserid(), vo.getText(), vo.getGood(),
                vo.getReply(), vo.getRegdate(), vo.isDeleteYn()));
        }

        return new SB02ListResponse(responseList, parents, nowPage, totCnt);
    }
}
