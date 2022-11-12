package com.singer.application.dto.sv;

import com.singer.domain.entity.sv.SV02Entity;
import java.util.ArrayList;
import java.util.List;

public class SV03Composer {

    public static List<SV02Entity> requsetListToEntityList(List<SV03Request> requestList) {

        List<SV02Entity> list = new ArrayList<>();
        for (SV03Request request : requestList) {
            SV02Entity sv02Vo = new SV02Entity();
            sv02Vo.setSeq(request.getSeq());
            sv02Vo.setIdx(request.getIdx());
            list.add(sv02Vo);
        }

        return list;
    }
}
