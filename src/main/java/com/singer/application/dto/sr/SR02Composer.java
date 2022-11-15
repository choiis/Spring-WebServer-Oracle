package com.singer.application.dto.sr;


import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sr.SR01Entity;
import com.singer.domain.entity.sr.SR02Entity;
import org.apache.commons.lang3.ObjectUtils;

public class SR02Composer {

    public static SR02Entity requestToEntitu(SR02Request request, String sessionid) {
        SR02Entity sr02Entity = new SR02Entity();
        sr02Entity.setSeq(request.getSeq());
        sr02Entity.setGrade(request.getGrade());
        sr02Entity.setUserid(sessionid);
        sr02Entity.setRegdate(DateUtil.getTodayTime());
        return sr02Entity;
    }

    public static SR02Response entityToResponse(SR01Entity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return new SR02Response(0, null, 0, null, RESULT_CODE.FAIL.getValue());
        } else {
            return new SR02Response(entity.getSeq(), entity.getUserid(), entity.getGrade(), entity.getRegdate(),
                RESULT_CODE.SUCCESS.getValue());
        }
    }


}
