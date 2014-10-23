package com.rilla.register.services.generator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rilla.register.repository.AccountingEntry;
import com.rilla.register.repository.constants.EntryType;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ExcelGeneratorBean {

	private static final short DATE_COLUMN = 1;
	private static final short CONCEPT_COLUMN = 2;
	private static final short ACCOUNT_COLUMN = 3;
	private static final short CLIENT_COLUMN = 4;
	private static final short RUT_COLUMN = 5;
	private static final short DEBE_COLUMN = 6;
	private static final short HABER_COLUMN = 7;
	private CellStyle originalStyle;
	private CellStyle originalDateStyle;
	private CellStyle headerStyle;

	public InputStream createFile(String path, List<AccountingEntry> entries) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sample sheet");
		setHeaderStyle(workbook);
		setOriginalStyle(workbook);

		XSSFRow headerRow = sheet.createRow(2);
		CreateHeaders(headerRow);

		int rownum = 3;

		for (AccountingEntry entry : entries) {
			XSSFRow row = sheet.createRow(rownum);
			createRow(entry, row);
			rownum++;
		}
		
		for (int i = 1; i < 8; i++) {
			sheet.autoSizeColumn(i);
		}
		//
		// for (String key : keyset) {
		// Row row = sheet.createRow(rownum++);
		// Object[] objArr = data.get(key);
		// int cellnum = 0;
		// for (Object obj : objArr) {
		// Cell cell = row.createCell(cellnum++);
		// if (obj instanceof Date)
		// cell.setCellValue((Date) obj);
		// else if (obj instanceof Boolean)
		// cell.setCellValue((Boolean) obj);
		// else if (obj instanceof String)
		// cell.setCellValue((String) obj);
		// else if (obj instanceof Double)
		// cell.setCellValue((Double) obj);
		// }
		// }

		try {
			FileOutputStream out = new FileOutputStream(new File(path));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			return new BufferedInputStream(new FileInputStream(path));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void createRow(AccountingEntry entry, XSSFRow row) {

		// Date
		XSSFCell dateCell = row.createCell((short) DATE_COLUMN);
		dateCell.setCellStyle(originalDateStyle);
		dateCell.setCellValue(entry.getDate());

		// Concept
		XSSFCell conceptCell = row.createCell((short) CONCEPT_COLUMN);
		conceptCell.setCellStyle(originalStyle);
		conceptCell.setCellValue(entry.getConcept());

		// Account
		XSSFCell accountCell = row.createCell((short) ACCOUNT_COLUMN);
		accountCell.setCellStyle(originalStyle);
		accountCell.setCellValue(entry.getAccount());

		// Client
		XSSFCell clientCell = row.createCell((short) CLIENT_COLUMN);
		clientCell.setCellStyle(originalStyle);
		clientCell.setCellValue(entry.getClientName());

		// Rut
//		XSSFCell rutCell = row.createCell((short) RUT_COLUMN);
//		rutCell.setCellStyle(originalStyle);
//		rutCell.setCellValue(entry.getRut());
//
//		if (EntryType.DEBE == entry.getType()) {
//			// Debe
//			XSSFCell debeCell = row.createCell((short) DEBE_COLUMN);
//			debeCell.setCellStyle(originalStyle);
//			debeCell.setCellValue(entry.getAmount().doubleValue());
//		} else {
//			// Haber
//			XSSFCell haberCell = row.createCell((short) HABER_COLUMN);
//			haberCell.setCellStyle(originalStyle);
//			haberCell.setCellValue(entry.getAmount().doubleValue());
//		}

	}

	/**
	 * Setea el estilo para las filas de facturas originales.
	 */
	private void setOriginalStyle(XSSFWorkbook workbook) {
		originalStyle = workbook.createCellStyle();
		short fontColor = IndexedColors.BLACK.getIndex();
		short backgroundColor = IndexedColors.WHITE.getIndex();

		// Font style
		XSSFFont font = workbook.createFont();
		font.setColor(fontColor);
		originalStyle.setFont(font);

		originalStyle.setFillForegroundColor(backgroundColor);
		originalStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		setBorders(originalStyle);

		originalDateStyle = workbook.createCellStyle();
		XSSFCreationHelper createHelper = workbook.getCreationHelper();
		originalDateStyle.setDataFormat(
		createHelper.createDataFormat().getFormat("MMMM dd, yyyy"));
		
		font.setColor(fontColor);
		originalDateStyle.setFont(font);

		originalDateStyle.setFillForegroundColor(backgroundColor);
		originalDateStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		setBorders(originalDateStyle);
	}

	private void CreateHeaders(XSSFRow row) {

		List<String> headers = Arrays.asList("Fecha", "Concepto",
				"Cuenta Corriente", "Titular", "RUT", "Debe", "Haber");
		int column = 1;
		for (String value : headers) {
			XSSFCell xssfCell = row.createCell((short) column);
			xssfCell.setCellStyle(headerStyle);
			xssfCell.setCellValue(value);
			column++;
		}
	}

	/**
	 * Setea el estilo para la fila del header.
	 */
	private void setHeaderStyle(XSSFWorkbook workbook) {
		headerStyle = workbook.createCellStyle();
		short fontColor = IndexedColors.BLACK.getIndex();
		short backgroundColor = IndexedColors.GREY_40_PERCENT.getIndex();

		// Font style
		XSSFFont font = workbook.createFont();
		font.setColor(fontColor);
		// font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		headerStyle.setFont(font);

		headerStyle.setFillForegroundColor(backgroundColor);
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		setBorders(headerStyle);
	}

	/**
	 * Setea bordes a cada una de las celdas.
	 *
	 * @param style
	 */
	private void setBorders(CellStyle style) {
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	}

}
