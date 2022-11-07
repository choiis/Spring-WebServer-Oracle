package com.singer.application.dto.sb;

import com.singer.domain.entity.sb.SB01Vo;
import java.util.ArrayList;
import java.util.List;

public class SB01Composer {

    public static SB01ListResponse entityListToResponse(List<SB01Vo> list, int nowPage, int totCnt) {

        List<SB01Response> responseList = new ArrayList<>();

        for (SB01Vo vo : list) {
            responseList.add(
                new SB01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
                    vo.getShowDate(), vo.getRegdate(), vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(),
                    vo.getVideo(), vo.getVideopath(), vo.getVideobool(), vo.getResult()));
        }

        return new SB01ListResponse(responseList, nowPage, totCnt);
    }

    public static SB01Response entityToResponse(SB01Vo vo) {
        return new SB01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
            vo.getShowDate(), vo.getRegdate(),
            vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(), vo.getVideo(), vo.getVideopath(),
            vo.getVideobool(), vo.getResult());
    }


    public static SB01Vo requestToentity(SB01Request request, String userid) {
        SB01Vo sb01Vo = new SB01Vo();
        sb01Vo.setTitle(request.getTitle());
        sb01Vo.setText(request.getTitle());
        sb01Vo.setUserid(userid);

        return sb01Vo;
    }
}
