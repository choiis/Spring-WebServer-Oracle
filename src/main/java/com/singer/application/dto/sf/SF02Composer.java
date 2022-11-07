package com.singer.application.dto.sf;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sf.SF02Vo;
import java.util.ArrayList;
import java.util.List;

public class SF02Composer {

    public static SF02Vo requestToEntity(SF02Request request, String userid) {
        SF02Vo sf02Vo = new SF02Vo();
        sf02Vo.setSeq01(request.getSeq01());
        sf02Vo.setParents(request.getParents());
        sf02Vo.setNowPage(request.getNowPage());
        sf02Vo.setText(request.getText());
        sf02Vo.setUserid(userid);
        sf02Vo.setRegdate(DateUtil.getTodayTime());
        return sf02Vo;
    }

    public static SF02Response entityToResponse(SF02Vo sb02Vo) {

        return new SF02Response(sb02Vo.getSeq(), sb02Vo.getSeq01(), sb02Vo.getUserid(), sb02Vo.getText(),
            sb02Vo.getGood(), sb02Vo.getReply(), sb02Vo.getRegdate(), sb02Vo.isDeleteYn());
    }

    public static SF02Vo selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SF02Vo sf02Vo = new SF02Vo();
        sf02Vo.setSeq01(seq01);
        sf02Vo.setParents(parents);
        sf02Vo.setNowPage(nowPage);
        return sf02Vo;
    }

    public static SF02Vo deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SF02Vo sf02Vo = new SF02Vo();
        sf02Vo.setSeq(seq);
        sf02Vo.setSeq01(seq01);
        sf02Vo.setParents(parents);
        return sf02Vo;
    }

    public static SF02ListResponse entityListToResponse(List<SF02Vo> list, int parents, int nowPage, int totCnt) {

        List<SF02Response> responseList = new ArrayList<>();

        for (SF02Vo vo : list) {
            responseList.add(new SF02Response(vo.getSeq(), vo.getSeq01(), vo.getUserid(), vo.getText(), vo.getGood(),
                vo.getReply(), vo.getRegdate(), vo.isDeleteYn()));
        }

        return new SF02ListResponse(responseList, parents, nowPage, totCnt);
    }
}
