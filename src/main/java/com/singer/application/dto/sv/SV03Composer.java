package com.singer.application.dto.sv;

import com.singer.domain.entity.sv.SV02Vo;
import java.util.ArrayList;
import java.util.List;

public class SV03Composer {

    public static List<SV02Vo> requsetListToEntityList(List<SV03Request> requestList) {

        List<SV02Vo> list = new ArrayList<>();
        for (SV03Request request : requestList) {
            SV02Vo sv02Vo = new SV02Vo();
            sv02Vo.setSeq(request.getSeq());
            sv02Vo.setIdx(request.getIdx());
            list.add(sv02Vo);
        }

        return list;
    }
}
