package com.singer.application.dto.sv;

import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sv.SV04Vo;
import java.util.ArrayList;
import java.util.List;

public class SV04Composer {

    public static SV04Vo requestToEntity(SV04Request request, String userid) {
        SV04Vo sv04Vo = new SV04Vo();
        sv04Vo.setSeq01(request.getSeq01());
        sv04Vo.setParents(request.getParents());
        sv04Vo.setNowPage(request.getNowPage());
        sv04Vo.setText(request.getText());
        sv04Vo.setUserid(userid);
        sv04Vo.setRegdate(DateUtil.getTodayTime());
        return sv04Vo;
    }

    public static SV04Response entityToResponse(SV04Vo sv04Vo) {

        return new SV04Response(sv04Vo.getSeq(), sv04Vo.getSeq01(), sv04Vo.getUserid(), sv04Vo.getText(),
            sv04Vo.getGood(), sv04Vo.getReply(), sv04Vo.getRegdate(), sv04Vo.isDeleteYn());
    }

    public static SV04Vo selectInfoToEntity(int seq01,
        int parents, int nowPage) {

        SV04Vo sv04Vo = new SV04Vo();
        sv04Vo.setSeq01(seq01);
        sv04Vo.setParents(parents);
        sv04Vo.setNowPage(nowPage);
        return sv04Vo;
    }

    public static SV04Vo deleteInfoToEntity(int seq,
        int seq01, int parents) {

        SV04Vo sv04Vo = new SV04Vo();
        sv04Vo.setSeq(seq);
        sv04Vo.setSeq01(seq01);
        sv04Vo.setParents(parents);
        return sv04Vo;
    }

    public static SV04ListResponse entityListToResponse(List<SV04Vo> list, int parents, int nowPage, int totCnt) {

        List<SV04Response> responseList = new ArrayList<>();

        for (SV04Vo vo : list) {
            responseList.add(new SV04Response(vo.getSeq(), vo.getSeq01(), vo.getUserid(), vo.getText(), vo.getGood(),
                vo.getReply(), vo.getRegdate(), vo.isDeleteYn()));
        }

        return new SV04ListResponse(responseList, parents, nowPage, totCnt);
    }
}
