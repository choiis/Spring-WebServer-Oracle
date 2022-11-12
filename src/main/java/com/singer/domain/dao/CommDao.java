package com.singer.domain.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.singer.domain.entity.CommEntity;

@Repository("commDao")
public class CommDao extends SuperDao {

	private static final String namespace = "com.singer.mappers.Comm";

	@SuppressWarnings("unchecked")
	public List<CommEntity> selectCode(CommEntity commVo) throws Exception {
		return (List<CommEntity>) selectList(namespace + ".selectCode", commVo);
	}

	@SuppressWarnings("unchecked")
	public List<CommEntity> selectMenu(CommEntity commVo) throws Exception {
		return (List<CommEntity>) selectList(namespace + ".selectMenu", commVo);
	}

	@SuppressWarnings("unchecked")
	@Cacheable(value = "menuCacheData")
	public List<CommEntity> selectAllMenu() throws Exception {
		return (List<CommEntity>) selectList(namespace + ".selectAllMenu");
	}

	@SuppressWarnings("unchecked")
	public List<CommEntity> selectCodeGrp(CommEntity commVo) throws Exception {
		return (List<CommEntity>) selectList(namespace + ".selectCodeGrp", commVo);
	}

	public String checkCreateUser(CommEntity commVo) throws Exception {
		return (String) selectOne(namespace + ".checkCreateUser", commVo);
	}

	public int insertMenu(CommEntity commVo) throws Exception {
		return insert(namespace + ".insertMenu", commVo);
	}

	public int deleteMenu(CommEntity commVo) throws Exception {
		return insert(namespace + ".deleteMenu", commVo);
	}

	public int updateMenu(CommEntity commVo) throws Exception {
		return insert(namespace + ".updateMenu", commVo);
	}

	public int insertCode(CommEntity commVo) throws Exception {
		return insert(namespace + ".insertCode", commVo);
	}

	public int deleteCode(CommEntity commVo) throws Exception {
		return insert(namespace + ".deleteCode", commVo);
	}

	public int updateCode(CommEntity commVo) throws Exception {
		return insert(namespace + ".updateCode", commVo);
	}

}
