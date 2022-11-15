package com.singer.application.service.sb;

import com.singer.application.dto.sb.SB02Composer;
import com.singer.application.dto.sb.SB02ListResponse;
import com.singer.application.dto.sb.SB02Request;
import com.singer.application.dto.sb.SB02Response;
import com.singer.common.exception.ClientException;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.singer.domain.dao.sb.SB02Dao;
import com.singer.domain.entity.sb.SB02Entity;

@Service
public class SB02Service {

	@Autowired
	private SB02Dao sb02Dao;

	public SB02Response insertSB02Vo(SB02Request request, String userid) throws Exception {

		SB02Entity sb02Entity = SB02Composer.requestToEntity(request, userid);

		sb02Dao.insertSB02Vo(sb02Entity);

		return SB02Composer.entityToResponse(sb02Entity);
	}

	public int likeSB02Vo(SB02Entity sb02Entity) throws Exception {
		return sb02Dao.likeSB02Vo(sb02Entity);
	}

	public SB02ListResponse selectSB02Vo(int seq01, int parents, int nowPage, String userid) throws Exception {
		SB02Entity sb02Entity = SB02Composer.selectInfoToEntity(seq01, parents, nowPage);

		if (sb02Entity.getNowPage() == 1) { // 泥ロ럹�씠吏� �슂泥��떆 Total�븣�븘�빞�븳�떎
			sb02Entity.setTotCnt(sb02Dao.selectSF02Total(sb02Entity));
		}
		List<SB02Entity> list;
		if (sb02Entity.getParents() > 0) {
			list = sb02Dao.selectReplySB02Vo(sb02Entity);
		} else {
			list = sb02Dao.selectSB02Vo(sb02Entity);
		}

		Stream<SB02Entity> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return SB02Composer.entityListToResponse(list, sb02Entity.getParents(), sb02Entity.getNowPage(), sb02Entity.getTotCnt());
	}

	public int updateSB02Vo(SB02Entity entity) throws Exception {
		return sb02Dao.updateSB02Vo(entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int deleteSB02Vo(int seq, int seq01, int parents, String sessionid) throws Exception {

		SB02Entity sb02Entity = SB02Composer.deleteInfoToEntity(seq, seq01, parents);
		SB02Entity sb02voResult = sb02Dao.checkUserSB02Vo(sb02Entity);
		if (!StringUtils.equals(sessionid, sb02voResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}

		if (sb02Entity.getParents() > 0) {
			sb02Dao.deleteChild(sb02Entity);
			sb02Entity.setParents(0);
		}
		return sb02Dao.deleteSB02Vo(sb02Entity);
	}

}
