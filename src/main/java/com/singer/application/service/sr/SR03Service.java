package com.singer.application.service.sr;

import com.singer.application.dto.sr.SR03Composer;
import com.singer.application.dto.sr.SR03ListResponse;
import com.singer.application.dto.sr.SR03Request;
import com.singer.application.dto.sr.SR03Response;
import com.singer.common.exception.ClientException;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.singer.domain.dao.sr.SR03Dao;
import com.singer.domain.entity.sr.SR03Entity;

@Service
public class SR03Service {

	@Autowired
	private SR03Dao sr03Dao;

	public SR03Response insertSR03Vo(SR03Request request, String userid) throws Exception {

		SR03Entity sr03Vo = SR03Composer.requestToEntity(request, userid);

		sr03Dao.insertSR03Vo(sr03Vo);

		return SR03Composer.entityToResponse(sr03Vo);
	}

	public int likeSR03Vo(SR03Entity sr03Vo) throws Exception {
		return sr03Dao.likeSR03Vo(sr03Vo);
	}

	public SR03ListResponse selectSR03Vo(int seq01, int parents, int nowPage, String userid) throws Exception {

		SR03Entity sr03Vo = SR03Composer.selectInfoToEntity(seq01, parents, nowPage);
		if (sr03Vo.getNowPage() == 1) { // 泥ロ럹�씠吏� �슂泥��떆 Total�븣�븘�빞�븳�떎
			sr03Vo.setTotCnt(sr03Dao.selectSR03Total(sr03Vo));
		}
		List<SR03Entity> list;
		if (sr03Vo.getParents() > 0) {
			list = sr03Dao.selectReplySR03Vo(sr03Vo);
		} else {
			list = sr03Dao.selectSR03Vo(sr03Vo);
		}

		Stream<SR03Entity> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return SR03Composer.entityListToResponse(list, sr03Vo.getParents(), sr03Vo.getNowPage(), sr03Vo.getTotCnt());
	}

	public int updateSR03Vo(SR03Entity sr03Vo) throws Exception {
		return sr03Dao.updateSR03Vo(sr03Vo);
	}

	public int deleteSR03Vo(int seq, int seq01, int parents, String sessionid) throws Exception {

		SR03Entity sr03Vo = SR03Composer.deleteInfoToEntity(seq, seq01, parents);
		SR03Entity sr03voResult = sr03Dao.checkUserSR03Vo(sr03Vo);
		if (!StringUtils.equals(sessionid, sr03voResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}
		if (sr03Vo.getParents() > 0) {
			sr03Dao.deleteChild(sr03Vo);
			sr03Vo.setParents(0);
		}

		return sr03Dao.deleteSR03Vo(sr03Vo);
	}

}
