package com.singer.domain.dao.sv;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sv.SV02Entity;

@Repository("sv02Dao")
public class SV02Dao extends SuperDao {
	private static final String namespace = "com.singer.mappers.SV02";

	public int insertSV02Vo(List<SV02Entity> list) throws Exception {
		return insert(namespace + ".insert", list);
	}

	public int updateSV02Vo(SV02Entity sv02Vo) throws Exception {
		return insert(namespace + ".update", sv02Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SV02Entity> selectSV02Vo(SV02Entity sv02Vo) throws Exception {
		return (List<SV02Entity>) selectList(namespace + ".select", sv02Vo);
	}

	public int selectCnt(SV02Entity sv02Vo) throws Exception {
		return selectCnt(namespace + ".selectTot", sv02Vo);
	}

	public int deleteSV02Vo(SV02Entity sv02Vo) throws Exception {
		return delete(namespace + ".delete", sv02Vo);
	}

	public int insertSV03Vo(List<SV02Entity> list) throws Exception {
		return insert(namespace + ".insertSv03Vo", list);
	}
}
