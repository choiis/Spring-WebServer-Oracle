package com.singer.domain.dao.sm;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sm.SM01Entity;

@Repository("smi1Dao")
public class SMI1Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SMI1";

	@SuppressWarnings("unchecked")
	public List<SM01Entity> selectByBrthSMI1(SM01Entity sm01Entity) throws Exception {
		return (List<SM01Entity>) selectList(namespace + ".selectByBrth", sm01Entity);
	}

	@SuppressWarnings("unchecked")
	public List<SM01Entity> selectByNameSMI1(SM01Entity sm01Entity) throws Exception {
		return (List<SM01Entity>) selectList(namespace + ".selectByName", sm01Entity);
	}

	@SuppressWarnings("unchecked")
	public List<SM01Entity> selectByPhoneSMI1(SM01Entity sm01Entity) throws Exception {
		return (List<SM01Entity>) selectList(namespace + ".selectByPhone", sm01Entity);
	}
}
