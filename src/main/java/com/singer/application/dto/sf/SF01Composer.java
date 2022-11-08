package com.singer.application.dto.sf;

import com.singer.domain.entity.sf.SF01Vo;
import java.util.ArrayList;
import java.util.List;

public class SF01Composer {

    public static SF01ListResponse entityListToResponse(List<SF01Vo> list, int nowPage, int totCnt) {

        List<SF01Response> responseList = new ArrayList<>();

        for (SF01Vo vo : list) {
            responseList.add(
                new SF01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
                    vo.getShowDate(), vo.getRegdate(), vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(),
                    vo.getFilename(), vo.getDowncnt(), vo.getResult()));
        }

        return new SF01ListResponse(responseList, nowPage, totCnt);
    }

    public static SF01Response entityToResponse(SF01Vo vo) {
        return new SF01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
            vo.getShowDate(), vo.getRegdate(),
            vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(), vo.getFilename(), vo.getDowncnt(),
            vo.getResult());
    }


    public static SF01Vo requestToentity(SF01Request request, String userid) {
        SF01Vo sf01Vo = new SF01Vo();
        sf01Vo.setTitle(request.getTitle());
        sf01Vo.setText(request.getTitle());
        sf01Vo.setUserid(userid);

        return sf01Vo;
    }
}
