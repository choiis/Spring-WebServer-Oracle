package com.singer.domain.dao.sr;

import com.singer.domain.dao.SuperDao;
import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sr.SR01Entity;
import com.singer.domain.entity.sr.SR02Entity;

@Repository("sr02Dao")
public class SR02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR02";

	public int insertSR02(SR02Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	public int deleteSR02(SR01Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public SR01Entity selectOneSR02(SR01Entity entity) throws Exception {
		return (SR01Entity) selectOne(namespace + ".selectOne", entity);
	}

	public SR01Entity selectGradeSR02(SR01Entity entity) throws Exception {
		return (SR01Entity) selectOne(namespace + ".selectGrade", entity);
	}
}