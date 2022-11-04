package com.singer.application.service.sr;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.domain.vo.sr.SR01Vo;

public interface SR01Service {
	public int insertSR01Vo(SR01Vo sr01Vo, MultipartHttpServletRequest request, String sessionid) throws Exception;

	public List<SR01Vo> selectSR01Vo(SR01Vo sr01Vo) throws Exception;

	public List<SR01Vo> selectFindSR01Vo(SR01Vo sr01Vo) throws Exception;

	public SR01Vo selectOneSR01Vo(SR01Vo sr01Vo, String userid) throws Exception;

	public int updateSR01Vo(SR01Vo sr01Vo) throws Exception;

	public SR01Vo likeSR01Vo(SR01Vo sr01Vo, String sessionid) throws Exception;

	public SR01Vo hateSR01Vo(SR01Vo sr01Vo, String sessionid) throws Exception;

	public int deleteSR01Vo(SR01Vo sr01Vo) throws Exception;

	public InputStream selectPhoto(SR01Vo sr01Vo) throws Exception;
}
