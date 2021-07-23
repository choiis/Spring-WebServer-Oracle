package com.singer.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.singer.service.SB01Service;
import com.singer.vo.SB01Vo;

@Controller
public class ExcelController {

	private final Log log = LogFactory.getLog(ExcelController.class);

	@Inject
	private SB01Service sb01Service;

	@RequestMapping(value = "/excelDown", method = RequestMethod.GET)
	public String getExcelList(SB01Vo sb01Vo, Map<String, Object> modelMap, HttpServletResponse response)
			throws Exception {
		log.debug("enter excelDown");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Pragma", "public");
		response.setHeader("Expires", "0");
		String contentDisposition = ContentDisposition.builder("attachment")
				.filename("BoardList.xls", StandardCharsets.UTF_8).build().toString();
		response.setHeader("Content-Disposition", contentDisposition);

		List<SB01Vo> excelList = sb01Service.selectSB01Vo(sb01Vo);

		modelMap.put("excelList", excelList);
		modelMap.put("excelType", "board");

		log.debug("exit excelDown");

		return "excelView";
	}
}
