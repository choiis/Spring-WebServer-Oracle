package com.singer.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.singer.common.CommonUtil;
import com.singer.vo.SB01Vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExcelView extends AbstractExcelView {

	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Object excelType = model.get("excelType");
		if (CommonUtil.isNull(excelType)) {
			return;
		}
		String type = excelType.toString();

		if ("board".equals(type)) {
			List<SB01Vo> excelList = (List<SB01Vo>) model.get("excelList");
			Sheet sheet = workbook.createSheet(type);
			Row row = null;
			int rowCount = 0;
			int cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.createCell(cellCount++).setCellValue("번호");
			row.createCell(cellCount++).setCellValue("제목");
			row.createCell(cellCount++).setCellValue("글쓴이");
			row.createCell(cellCount++).setCellValue("좋아요");
			row.createCell(cellCount++).setCellValue("조회수");
			row.createCell(cellCount++).setCellValue("등록일자");

			for (SB01Vo vo : excelList) {
				row = sheet.createRow(rowCount++);
				cellCount = 0;
				row.createCell(cellCount++).setCellValue(vo.getSeq());
				row.createCell(cellCount++).setCellValue(vo.getTitle());
				row.createCell(cellCount++).setCellValue(vo.getUserid());
				row.createCell(cellCount++).setCellValue(vo.getGood());
				row.createCell(cellCount++).setCellValue(vo.getHit());
				row.createCell(cellCount++).setCellValue(vo.getRegdate());
			}
		}
	}
}
