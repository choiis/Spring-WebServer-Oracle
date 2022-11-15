package com.singer.application.dto.sv;

import com.singer.domain.entity.sv.SV02Entity;
import java.util.ArrayList;
import java.util.List;

public class SV03Composer {

    public static List<SV02Entity> requsetListToEntityList(List<SV03Request> requestList) {

        List<SV02Entity> list = new ArrayList<>();
        for (SV03Request request : requestList) {
            SV02Entity entity = new SV02Entity();
            entity.setSeq(request.getSeq());
            entity.setIdx(request.getIdx());
            list.add(entity);
        }

        return list;
    }
}
