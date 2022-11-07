package com.singer.application.service.sf;

import com.singer.application.dto.sf.SF02Composer;
import com.singer.application.dto.sf.SF02ListResponse;
import com.singer.application.dto.sf.SF02Request;
import com.singer.application.dto.sf.SF02Response;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sf.SF02Dao;
import com.singer.domain.entity.sf.SF02Vo;

@Service
public class SF02Service {

    @Autowired
    private SF02Dao sf02Dao;

    public SF02Response insertSF02Vo(SF02Request request, String userid) throws Exception {

        SF02Vo sf02Vo = SF02Composer.requestToEntity(request, userid);

        sf02Dao.insertSF02Vo(sf02Vo);

        return SF02Composer.entityToResponse(sf02Vo);
    }

    public int likeSF02Vo(SF02Vo sf02Vo) throws Exception {
        return sf02Dao.likeSF02Vo(sf02Vo);
    }

    public SF02ListResponse selectSF02Vo(int seq01,
        int parents, int nowPage, String userid) throws Exception {

        SF02Vo sf02Vo = SF02Composer.selectInfoToEntity(seq01, parents, nowPage);
        if (sf02Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
            sf02Vo.setTotCnt(sf02Dao.selectSF02Total(sf02Vo));
        }
        List<SF02Vo> list;
        if (sf02Vo.getParents() > 0) {
            list = sf02Dao.selectReplySF02Vo(sf02Vo);
        } else {
            list = sf02Dao.selectSF02Vo(sf02Vo);
        }

        Stream<SF02Vo> stream = list.stream();
        stream.forEach(s -> {
            if (userid.equals(s.getUserid())) {
                s.setDeleteYn(true);
            }
        });

        return SF02Composer.entityListToResponse(list, sf02Vo.getParents(), sf02Vo.getNowPage(), sf02Vo.getTotCnt());
    }

    public int updateSF02Vo(SF02Vo sf02Vo) throws Exception {
        return sf02Dao.updateSF02Vo(sf02Vo);
    }

    @Transactional(rollbackFor = {Exception.class})
    public int deleteSF02Vo(int seq,
        int seq01, int parents) throws Exception {

        SF02Vo sf02Vo = SF02Composer.deleteInfoToEntity(seq, seq01, parents);
        if (sf02Vo.getParents() > 0) {
            sf02Dao.deleteChild(sf02Vo);
            sf02Vo.setParents(0);
        }
        return sf02Dao.deleteSF02Vo(sf02Vo);
    }

}
