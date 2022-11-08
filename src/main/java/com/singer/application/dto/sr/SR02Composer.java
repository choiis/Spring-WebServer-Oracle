package com.singer.application.dto.sr;


import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.common.util.DateUtil;
import com.singer.domain.entity.sr.SR01Vo;
import com.singer.domain.entity.sr.SR02Vo;
import org.apache.commons.lang3.ObjectUtils;

public class SR02Composer {

    public static SR02Vo requestToEntitu(SR02Request request, String sessionid) {
        SR02Vo sr02Vo = new SR02Vo();
        sr02Vo.setSeq(request.getSeq());
        sr02Vo.setGrade(request.getGrade());
        sr02Vo.setUserid(sessionid);
        sr02Vo.setRegdate(DateUtil.getTodayTime());
        return sr02Vo;
    }

    public static SR02Response entityToResponse(SR01Vo vo) {
        if (ObjectUtils.isEmpty(vo)) {
            return new SR02Response(0, null, 0, null, RESULT_CODE.FAIL.getValue());
        } else {
            return new SR02Response(vo.getSeq(), vo.getUserid(), vo.getGrade(), vo.getRegdate(),
                RESULT_CODE.SUCCESS.getValue());
        }
    }


}
