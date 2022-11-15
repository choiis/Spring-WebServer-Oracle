package com.singer.application.dto.sb;

import com.singer.domain.entity.sb.SB01Entity;
import java.util.ArrayList;
import java.util.List;

public class SB01Composer {

    public static SB01ListResponse entityListToResponse(List<SB01Entity> list, int nowPage, int totCnt) {

        List<SB01Response> responseList = new ArrayList<>();

        for (SB01Entity entity : list) {
            responseList.add(
                new SB01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
                    entity.getShowDate(), entity.getRegdate(), entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(),
                    entity.getVideo(), entity.getVideopath(), entity.getVideobool(), entity.getResult()));
        }

        return new SB01ListResponse(responseList, nowPage, totCnt);
    }

    public static SB01Response entityToResponse(SB01Entity entity) {
        return new SB01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
            entity.getShowDate(), entity.getRegdate(),
            entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(), entity.getVideo(), entity.getVideopath(),
            entity.getVideobool(), entity.getResult());
    }


    public static SB01Entity requestToentity(SB01Request request, String userid) {
        SB01Entity sb01Entity = new SB01Entity();
        sb01Entity.setTitle(request.getTitle());
        sb01Entity.setText(request.getTitle());
        sb01Entity.setUserid(userid);

        return sb01Entity;
    }
}
