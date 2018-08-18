package com.singer.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.singer.service.SB01Service;
import com.singer.vo.SB01Vo;

@Controller("excelController")
public class ExcelController {

	private final Log log = LogFactory.getLog(ExcelController.class);

	@Resource(name = "sb01Service")
	private SB01Service sb01Service;

	@RequestMapping(value = "/excelDown.do")
	public String getExcelList(SB01Vo sb01Vo, Map<String, Object> modelMap, HttpServletResponse response)
			throws Exception {
		log.debug("enter excelDown.do");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Pragma", "public");
		response.setHeader("Expires", "0");
		response.setHeader("Content-Disposition", "attachment; filename = BoardList.xls");

		List<SB01Vo> excelList = sb01Service.selectSB01Vo(sb01Vo);

		modelMap.put("excelList", excelList);
		modelMap.put("excelType", "board");

		log.debug("exit excelDown.do");

		return "excelView";
	}
}
