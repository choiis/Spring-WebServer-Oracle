package com.singer.application.dto.sv;


import com.singer.domain.entity.sv.SV01Entity;
import com.singer.domain.entity.sv.SV02Entity;
import java.util.ArrayList;
import java.util.List;

public class SV01Composer {

    public static SV01Entity requestToEntity(SV01Request request) {
        SV01Entity sv01Vo = new SV01Entity();
        sv01Vo.setTitle(request.getTitle());
        sv01Vo.setText(request.getText());
        sv01Vo.setMultiselect(request.getMultiselect());
        return sv01Vo;
    }


    public static List<SV02Entity> requestListToEntityList(List<SV02Request> list) {

        List<SV02Entity> voList = new ArrayList<>();
        for (SV02Request request : list) {
            SV02Entity vo = new SV02Entity();
            vo.setIdx(request.getIdx());
            vo.setContent(request.getContent());
            voList.add(vo);
        }

        return voList;
    }


    public static SV01ListResponse entityListToResponse(List<SV01Entity> list, List<SV02Response> sv02Vos, int nowPage,
        int totCnt) {

        List<SV01Response> responseList = new ArrayList<>();

        for (SV01Entity vo : list) {
            responseList.add(
                new SV01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
                    vo.getShowDate(), vo.getRegdate(), vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(),
                    vo.getVotedCnt(), vo.getMultiselect(), vo.getVotedYn(), vo.getTotCnt(), sv02Vos, vo.getResult()));
        }

        return new SV01ListResponse(responseList, nowPage, totCnt);
    }

    public static List<SV02Response> entityListToResponseList(List<SV02Entity> list) {
        List<SV02Response> responseList = new ArrayList<>();

        for (SV02Entity vo : list) {
            responseList.add(new SV02Response(vo.getIdx(), vo.getContent(), vo.getVoted()));
        }
        return responseList;
    }

    public static SV01Response entityToResponse(SV01Entity vo, List<SV02Response> list) {
        return new SV01Response(vo.getSeq(), vo.getTitle(), vo.getText(), vo.getUserid(), vo.getGood(), vo.getHit(),
            vo.getShowDate(), vo.getRegdate(),
            vo.getReply(), vo.isDeleteYn(), vo.getGoodlog(), vo.getHatelog(), vo.getVotedCnt(), vo.getMultiselect(),
            vo.getVotedYn(), vo.getTotCnt(), list, vo.getResult());
    }
}
