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

	public SR03Response insertSR03(SR03Request request, String userid) throws Exception {

		SR03Entity sr03Entity = SR03Composer.requestToEntity(request, userid);

		sr03Dao.insertSR03(sr03Entity);

		return SR03Composer.entityToResponse(sr03Entity);
	}

	public int likeSR03(SR03Entity entity) throws Exception {
		return sr03Dao.likeSR03(entity);
	}

	public SR03ListResponse selectSR03List(int seq01, int parents, int nowPage, String userid) throws Exception {

		SR03Entity sr03Entity = SR03Composer.selectInfoToEntity(seq01, parents, nowPage);
		if (sr03Entity.getNowPage() == 1) { // 泥ロ럹�씠吏� �슂泥��떆 Total�븣�븘�빞�븳�떎
			sr03Entity.setTotCnt(sr03Dao.selectSR03Count(sr03Entity));
		}
		List<SR03Entity> list;
		if (sr03Entity.getParents() > 0) {
			list = sr03Dao.selectReplySR03(sr03Entity);
		} else {
			list = sr03Dao.selectSR03(sr03Entity);
		}

		Stream<SR03Entity> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return SR03Composer.entityListToResponse(list, sr03Entity.getParents(), sr03Entity.getNowPage(), sr03Entity.getTotCnt());
	}

	public int updateSR03(SR03Entity entity) throws Exception {
		return sr03Dao.updateSR03(entity);
	}

	public int deleteSR03(int seq, int seq01, int parents, String sessionid) throws Exception {

		SR03Entity sr03Entity = SR03Composer.deleteInfoToEntity(seq, seq01, parents);
		SR03Entity sr03EntityResult = sr03Dao.checkUserSR03(sr03Entity);
		if (!StringUtils.equals(sessionid, sr03EntityResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}
		if (sr03Entity.getParents() > 0) {
			sr03Dao.deleteChild(sr03Entity);
			sr03Entity.setParents(0);
		}

		return sr03Dao.deleteSR03(sr03Entity);
	}

}
