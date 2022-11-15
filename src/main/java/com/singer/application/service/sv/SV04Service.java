package com.singer.application.service.sv;

import com.singer.application.dto.sv.SV04Composer;
import com.singer.application.dto.sv.SV04ListResponse;
import com.singer.application.dto.sv.SV04Request;
import com.singer.application.dto.sv.SV04Response;
import com.singer.common.exception.ClientException;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.domain.dao.sv.SV04Dao;
import com.singer.domain.entity.sv.SV04Entity;

@Service
public class SV04Service {

	@Autowired
	private SV04Dao sv04Dao;

	public SV04Response insertSV04(SV04Request request, String userid) throws Exception {

		SV04Entity sv04Entity = SV04Composer.requestToEntity(request, userid);

		sv04Dao.insertSV04Vo(sv04Entity);

		return SV04Composer.entityToResponse(sv04Entity);
	}

	public int likeSV04(SV04Entity entity) throws Exception {
		return sv04Dao.likeSV04Vo(entity);
	}

	public SV04ListResponse selectSV04(int seq01, int parents, int nowPage, String userid) throws Exception {

		SV04Entity sv04Entity = SV04Composer.selectInfoToEntity(seq01, parents, nowPage);
		if (sv04Entity.getNowPage() == 1) { // 泥ロ럹�씠吏� �슂泥��떆 Total�븣�븘�빞�븳�떎
			sv04Entity.setTotCnt(sv04Dao.selectSV04Total(sv04Entity));
		}
		List<SV04Entity> list;
		if (sv04Entity.getParents() > 0) {
			list = sv04Dao.selectReplySV04Vo(sv04Entity);
		} else {
			list = sv04Dao.selectSV04Vo(sv04Entity);
		}

		Stream<SV04Entity> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return SV04Composer.entityListToResponse(list, sv04Entity.getParents(), sv04Entity.getNowPage(), sv04Entity.getTotCnt());
	}

	public int updateSV04(SV04Entity entity) throws Exception {
		return sv04Dao.updateSV04Vo(entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int deleteSV04(int seq, int seq01, int parents, String sessionid) throws Exception {

		SV04Entity sv04Entity = SV04Composer.deleteInfoToEntity(seq, seq01, parents);
		SV04Entity sv04EntityResult = sv04Dao.checkUserSV04Vo(sv04Entity);
		if (!StringUtils.equals(sessionid, sv04EntityResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}
		if (sv04Entity.getParents() > 0) {
			sv04Dao.deleteChild(sv04Entity);
			sv04Entity.setParents(0);
		}

		return sv04Dao.deleteSV04Vo(sv04Entity);
	}

}
