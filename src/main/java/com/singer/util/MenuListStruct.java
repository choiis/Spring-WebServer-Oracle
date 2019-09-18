package com.singer.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.singer.dao.CommDao;
import com.singer.vo.CommVo;

@Component("menuListStruct")
public class MenuListStruct {

	private final Log log = LogFactory.getLog(MenuListStruct.class);

	@Resource(name = "commDao")
	private CommDao commDao;

	private List<CommVo> allMenuList;

	@PostConstruct
	private void init() throws Exception {
		allMenuList = commDao.selectAllMenu();
		log.debug("allMenuList size :  " + allMenuList.size());
	}

	public List<CommVo> getAllMenuList() {
		return allMenuList;
	}

}
