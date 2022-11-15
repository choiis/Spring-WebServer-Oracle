package com.singer.application.service.sf;

import com.singer.application.dto.sf.SF02Composer;
import com.singer.application.dto.sf.SF02ListResponse;
import com.singer.application.dto.sf.SF02Request;
import com.singer.application.dto.sf.SF02Response;
import com.singer.common.exception.ClientException;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.domain.dao.sf.SF02Dao;
import com.singer.domain.entity.sf.SF02Entity;

@Service
public class SF02Service {

	@Autowired
	private SF02Dao sf02Dao;

	public SF02Response insertSF02Vo(SF02Request request, String userid) throws Exception {

		SF02Entity sf02Entity = SF02Composer.requestToEntity(request, userid);

		sf02Dao.insertSF02Vo(sf02Entity);

		return SF02Composer.entityToResponse(sf02Entity);
	}

	public int likeSF02Vo(SF02Entity sf02Vo) throws Exception {
		return sf02Dao.likeSF02Vo(sf02Vo);
	}

	public SF02ListResponse selectSF02Vo(int seq01, int parents, int nowPage, String userid) throws Exception {

		SF02Entity sf02Entity = SF02Composer.selectInfoToEntity(seq01, parents, nowPage);
		if (sf02Entity.getNowPage() == 1) { // 泥ロ럹�씠吏� �슂泥��떆 Total�븣�븘�빞�븳�떎
			sf02Entity.setTotCnt(sf02Dao.selectSF02Total(sf02Entity));
		}
		List<SF02Entity> list;
		if (sf02Entity.getParents() > 0) {
			list = sf02Dao.selectReplySF02Vo(sf02Entity);
		} else {
			list = sf02Dao.selectSF02Vo(sf02Entity);
		}

		Stream<SF02Entity> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return SF02Composer.entityListToResponse(list, sf02Entity.getParents(), sf02Entity.getNowPage(), sf02Entity.getTotCnt());
	}

	public int updateSF02Vo(SF02Entity entity) throws Exception {
		return sf02Dao.updateSF02Vo(entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int deleteSF02Vo(int seq, int seq01, int parents, String sessionid) throws Exception {

		SF02Entity sf02Entity = SF02Composer.deleteInfoToEntity(seq, seq01, parents);
		SF02Entity sf02EntityResult = sf02Dao.checkUserSF02Vo(sf02Entity);
		if (!StringUtils.equals(sessionid, sf02EntityResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}

		if (sf02Entity.getParents() > 0) {
			sf02Dao.deleteChild(sf02Entity);
			sf02Entity.setParents(0);
		}
		return sf02Dao.deleteSF02Vo(sf02Entity);
	}

}
