package com.singer.application.service.sv;

import com.singer.application.dto.sv.SV04Composer;
import com.singer.application.dto.sv.SV04ListResponse;
import com.singer.application.dto.sv.SV04Request;
import com.singer.application.dto.sv.SV04Response;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.domain.dao.sv.SV04Dao;
import com.singer.domain.entity.sv.SV04Vo;

@Service
public class SV04Service {

    @Autowired
    private SV04Dao sv04Dao;

    public SV04Response insertSV04Vo(SV04Request request, String userid) throws Exception {

        SV04Vo sv04Vo = SV04Composer.requestToEntity(request, userid);

        sv04Dao.insertSV04Vo(sv04Vo);

        return SV04Composer.entityToResponse(sv04Vo);
    }

    public int likeSV04Vo(SV04Vo sv04Vo) throws Exception {
        return sv04Dao.likeSV04Vo(sv04Vo);
    }

    public SV04ListResponse selectSV04Vo(int seq01,
        int parents, int nowPage, String userid) throws Exception {

        SV04Vo sv04Vo = SV04Composer.selectInfoToEntity(seq01, parents, nowPage);
        if (sv04Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
            sv04Vo.setTotCnt(sv04Dao.selectSV04Total(sv04Vo));
        }
        List<SV04Vo> list;
        if (sv04Vo.getParents() > 0) {
            list = sv04Dao.selectReplySV04Vo(sv04Vo);
        } else {
            list = sv04Dao.selectSV04Vo(sv04Vo);
        }

        Stream<SV04Vo> stream = list.stream();
        stream.forEach(s -> {
            if (userid.equals(s.getUserid())) {
                s.setDeleteYn(true);
            }
        });

        return SV04Composer.entityListToResponse(list, sv04Vo.getParents(), sv04Vo.getNowPage(), sv04Vo.getTotCnt());
    }

    public int updateSV04Vo(SV04Vo sv04Vo) throws Exception {
        return sv04Dao.updateSV04Vo(sv04Vo);
    }

    @Transactional(rollbackFor = {Exception.class})
    public int deleteSV04Vo(int seq,
        int seq01, int parents) throws Exception {

        SV04Vo sv04Vo = SV04Composer.deleteInfoToEntity(seq, seq01, parents);
        if (sv04Vo.getParents() > 0) {
            sv04Dao.deleteChild(sv04Vo);
            sv04Vo.setParents(0);
        }

        return sv04Dao.deleteSV04Vo(sv04Vo);
    }

}
