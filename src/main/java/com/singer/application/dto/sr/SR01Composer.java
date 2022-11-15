package com.singer.application.dto.sr;

import com.singer.domain.entity.sr.SR01Entity;
import java.util.ArrayList;
import java.util.List;

public class SR01Composer {

    public static SR01ListResponse entityListToResponse(List<SR01Entity> list, int nowPage, int totCnt) {

        List<SR01Response> responseList = new ArrayList<>();

        for (SR01Entity entity : list) {
            responseList.add(
                new SR01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
                    entity.getShowDate(), entity.getRegdate(), entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(),
                    entity.getMarkertitle(), entity.getMapx(), entity.getMapy(), entity.getAvggrade(), entity.getPhotocnt(),
                    entity.getResult()));
        }

        return new SR01ListResponse(responseList, nowPage, totCnt);
    }

    public static SR01Response entityToResponse(SR01Entity entity) {
        return new SR01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
            entity.getShowDate(), entity.getRegdate(),
            entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(), entity.getMarkertitle(), entity.getMapx(),
            entity.getMapy(), entity.getAvggrade(), entity.getPhotocnt(),
            entity.getResult());
    }


    public static SR01Entity requestToentity(SR01Request request, String userid) {
        SR01Entity sr01Entity = new SR01Entity();
        sr01Entity.setTitle(request.getTitle());
        sr01Entity.setText(request.getTitle());
        sr01Entity.setUserid(userid);
        sr01Entity.setMarkertitle(request.getMarkertitle());
        sr01Entity.setGrade(request.getGrade());
        sr01Entity.setMapx(request.getMapx());
        sr01Entity.setMapy(request.getMapy());
        return sr01Entity;
    }
}
