package com.singer.application.dto.sv;


import com.singer.domain.entity.sv.SV01Entity;
import com.singer.domain.entity.sv.SV02Entity;
import java.util.ArrayList;
import java.util.List;

public class SV01Composer {

    public static SV01Entity requestToEntity(SV01Request request) {
        SV01Entity sv01Entity = new SV01Entity();
        sv01Entity.setTitle(request.getTitle());
        sv01Entity.setText(request.getText());
        sv01Entity.setMultiselect(request.getMultiselect());
        return sv01Entity;
    }


    public static List<SV02Entity> requestListToEntityList(List<SV02Request> list) {

        List<SV02Entity> entityList = new ArrayList<>();
        for (SV02Request request : list) {
            SV02Entity entity = new SV02Entity();
            entity.setIdx(request.getIdx());
            entity.setContent(request.getContent());
            entityList.add(entity);
        }

        return entityList;
    }


    public static SV01ListResponse entityListToResponse(List<SV01Entity> list, List<SV02Response> sv02Vos, int nowPage,
        int totCnt) {

        List<SV01Response> responseList = new ArrayList<>();

        for (SV01Entity entity : list) {
            responseList.add(
                new SV01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
                    entity.getShowDate(), entity.getRegdate(), entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(),
                    entity.getVotedCnt(), entity.getMultiselect(), entity.getVotedYn(), entity.getTotCnt(), sv02Vos, entity.getResult()));
        }

        return new SV01ListResponse(responseList, nowPage, totCnt);
    }

    public static List<SV02Response> entityListToResponseList(List<SV02Entity> list) {
        List<SV02Response> responseList = new ArrayList<>();

        for (SV02Entity entity : list) {
            responseList.add(new SV02Response(entity.getIdx(), entity.getContent(), entity.getVoted()));
        }
        return responseList;
    }

    public static SV01Response entityToResponse(SV01Entity entity, List<SV02Response> list) {
        return new SV01Response(entity.getSeq(), entity.getTitle(), entity.getText(), entity.getUserid(), entity.getGood(), entity.getHit(),
            entity.getShowDate(), entity.getRegdate(),
            entity.getReply(), entity.isDeleteYn(), entity.getGoodlog(), entity.getHatelog(), entity.getVotedCnt(), entity.getMultiselect(),
            entity.getVotedYn(), entity.getTotCnt(), list, entity.getResult());
    }
}
