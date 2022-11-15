package com.singer.application.dto.sf;

import com.singer.domain.entity.sf.SF01Entity;
import java.util.ArrayList;
import java.util.List;

public class SF01Composer {

    public static SF01ListResponse entityListToResponse(List<SF01Entity> list, int nowPage, int totCnt) {

        List<SF01Response> responseList = new ArrayList<>();

        for (SF01Entity entity : list) {
            responseList.add(
                new SF01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
                    entity.getShowDate(), entity.getRegdate(), entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(),
                    entity.getFilename(), entity.getDowncnt(), entity.getResult()));
        }

        return new SF01ListResponse(responseList, nowPage, totCnt);
    }

    public static SF01Response entityToResponse(SF01Entity entity) {
        return new SF01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
            entity.getShowDate(), entity.getRegdate(),
            entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(), entity.getFilename(), entity.getDowncnt(),
            entity.getResult());
    }


    public static SF01Entity requestToentity(SF01Request request, String userid) {
        SF01Entity sf01Entity = new SF01Entity();
        sf01Entity.setTitle(request.getTitle());
        sf01Entity.setText(request.getTitle());
        sf01Entity.setUserid(userid);

        return sf01Entity;
    }
}
