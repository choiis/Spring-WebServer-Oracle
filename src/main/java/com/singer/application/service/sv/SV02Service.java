package com.singer.application.service.sv;

import com.singer.application.dto.sv.SV03Composer;
import com.singer.application.dto.sv.SV03ListRequest;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.singer.common.exception.AppException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sv.SV02Dao;
import com.singer.domain.entity.sv.SV02Entity;

@Service
public class SV02Service {

    @Autowired
    private SV02Dao sv02Dao;

    public int updateSV01Vo(SV02Entity entity) throws Exception {
        return sv02Dao.updateSV02Vo(entity);
    }

    @Transactional(rollbackFor = {Exception.class})
    public int insertSv03Vo(SV03ListRequest listRequest, String userid) throws Exception {
        List<SV02Entity> list = SV03Composer.requsetListToEntityList(listRequest.getList());
        if (CollectionUtils.isEmpty(list)) {
            throw new AppException(ExceptionMsg.EXT_MSG_INPUT_8);
        }

        String regDate = DateUtil.getTodayTime();

        Stream<SV02Entity> stream = list.stream();
        stream.forEach(s -> {
            s.setUserid(userid);
            s.setRegdate(regDate);
        });
        sv02Dao.insertSV03Vo(list);
        return 1;
    }

}
