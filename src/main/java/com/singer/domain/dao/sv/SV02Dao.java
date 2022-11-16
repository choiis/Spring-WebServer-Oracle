package com.singer.domain.dao.sv;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sv.SV02Entity;

@Repository("sv02Dao")
public class SV02Dao extends SuperDao {
	private static final String namespace = "com.singer.mappers.SV02";

	public int insertSV02(List<SV02Entity> list) throws Exception {
		return insert(namespace + ".insert", list);
	}

	public int updateSV02(SV02Entity entity) throws Exception {
		return insert(namespace + ".update", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SV02Entity> selectSV02(SV02Entity entity) throws Exception {
		return (List<SV02Entity>) selectList(namespace + ".select", entity);
	}

	public int selectCount(SV02Entity entity) throws Exception {
		return selectCnt(namespace + ".selectTot", entity);
	}

	public int deleteSV02(SV02Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public int insertSV03(List<SV02Entity> list) throws Exception {
		return insert(namespace + ".insertSv03Vo", list);
	}
}
