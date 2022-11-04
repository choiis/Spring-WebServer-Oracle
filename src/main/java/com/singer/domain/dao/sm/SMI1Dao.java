package com.singer.domain.dao.sm;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.vo.sm.SM01Vo;

@Repository("smi1Dao")
public class SMI1Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SMI1";

	@SuppressWarnings("unchecked")
	public List<SM01Vo> selectByBrthSMI1Vo(SM01Vo sm01Vo) throws Exception {
		return (List<SM01Vo>) selectList(namespace + ".selectByBrth", sm01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SM01Vo> selectByNameSMI1Vo(SM01Vo sm01Vo) throws Exception {
		return (List<SM01Vo>) selectList(namespace + ".selectByName", sm01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SM01Vo> selectByPhoneSMI1Vo(SM01Vo sm01Vo) throws Exception {
		return (List<SM01Vo>) selectList(namespace + ".selectByPhone", sm01Vo);
	}
}
