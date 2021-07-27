package com.singer.view;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.singer.vo.SB01Vo;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExcelView extends AbstractExcelView {

	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Optional<Object> excelType = Optional.of(model.get("excelType"));
		if (!excelType.isPresent()) {
			return;
		}
		String type = excelType.get().toString();

		if ("board".equals(type)) {
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

			Optional<Object> optionalList = Optional.of(model.get("excelList"));
			if (!optionalList.isPresent()) {
				return;
			}
			List<SB01Vo> excelList = (List<SB01Vo>) optionalList.get();
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
