package com.singer.application.dto.sr;

import com.singer.domain.entity.sr.SR01Entity;
import java.util.ArrayList;
import java.util.List;

public class SR01Composer {

    public static SR01ListResponse entityListToResponse(List<SR01Entity> list, int nowPage, int totCnt) {

        List<SR01Response> responseList = new ArrayList<>();

        for (SR01Entity vo : list) {
            responseList.add(
                new SR01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
                    vo.getShowDate(), vo.getRegdate(), vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(),
                    vo.getMarkertitle(), vo.getMapx(), vo.getMapy(), vo.getAvggrade(), vo.getPhotocnt(),
                    vo.getResult()));
        }

        return new SR01ListResponse(responseList, nowPage, totCnt);
    }

    public static SR01Response entityToResponse(SR01Entity vo) {
        return new SR01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
            vo.getShowDate(), vo.getRegdate(),
            vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(), vo.getMarkertitle(), vo.getMapx(),
            vo.getMapy(), vo.getAvggrade(), vo.getPhotocnt(),
            vo.getResult());
    }


    public static SR01Entity requestToentity(SR01Request request, String userid) {
        SR01Entity sr01Vo = new SR01Entity();
        sr01Vo.setTitle(request.getTitle());
        sr01Vo.setText(request.getTitle());
        sr01Vo.setUserid(userid);
        sr01Vo.setMarkertitle(request.getMarkertitle());
        sr01Vo.setGrade(request.getGrade());
        sr01Vo.setMapx(request.getMapx());
        sr01Vo.setMapy(request.getMapy());
        return sr01Vo;
    }
}
