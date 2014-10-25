package com.rilla.register.services.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rilla.register.repository.AccountingEntry;
import com.rilla.register.repository.constants.EntryType;
import com.rilla.register.repository.constants.FinalFileType;
import com.rilla.register.repository.model.Company;
import com.rilla.register.repository.model.Metadata;
import com.rilla.register.repository.model.Provider;
import com.rilla.register.services.CompanyBean;
import com.rilla.register.services.ProviderBean;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ExcelReaderBean {

	@Autowired
	private ProviderBean providerBean;

	@Autowired
	private CompanyBean companyBean;

	public List<AccountingEntry> readFile(InputStream stream,
			Provider provider, Company company, String fileName, String currency) {
		return read(stream, provider, fileName, currency, company);
	}

	private List<AccountingEntry> read(InputStream stream, Provider provider,
			String fileName, String currency, Company company) {
		Metadata metadata = provider.getMetadata();
		try {
			Sheet sheet = getSheet(fileName, stream);
			Iterator rows = sheet.rowIterator();
			Row row = (Row) rows.next();

			for (int i = 0; i < metadata.getFirstRow(); i++) {
				row = (Row) rows.next();
			}
			int id = 1;
			List<AccountingEntry> entries = new LinkedList<>();
			while (rows.hasNext()) {
				// log(row.getPhysicalNumberOfCells()+"");

				// DATE
				Date date = null;
				Cell cell = row.getCell(metadata.getColumnDate());
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					if (DateUtil.isCellDateFormatted(cell)) {
						date = cell.getDateCellValue();
					}
				} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					String dateStr = cell.getStringCellValue();
					date = new Date(dateStr);
				}
				if (metadata.getFinalFileType() == FinalFileType.LAST_DATE
						&& date == null) {
					break;
				}
				// TODO: Ver que hago sino

				// CONCEPT
				String concept = "";
				for (Integer pos : metadata.getColumnsConcept()) {
					cell = row.getCell(pos);
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						concept = concept
								+ cell.getRichStringCellValue().getString()
								+ " ";
					}else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						concept = concept
								+ round(cell.getNumericCellValue(),0)
								+ " ";
					}
				}

				// ACCOUNT
				String account = null;
				cell = row.getCell(metadata.getColumnAccount());
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					account = cell.getRichStringCellValue().getString();
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					account = cell.getDateCellValue().toString();
				}

				// CLIENT NAME
				String clientName = null;
				cell = row.getCell(metadata.getColumnClientName());
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					clientName = cell.getRichStringCellValue().getString();
				}

				// IVA AMOUNT
				double ivaAmount = 0;
				cell = row.getCell(metadata.getColumnIvaAmount());
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					ivaAmount = cell.getNumericCellValue();
				}
			      
				ivaAmount = round(ivaAmount, metadata.getDecimals());
				
				// TOTAL AMOUNT
				double totalAmount = 0;
				EntryType entryType;
				cell = row.getCell(metadata.getColumnTotalAmount());
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					totalAmount = cell.getNumericCellValue();
				}
			      
				totalAmount = round(totalAmount, metadata.getDecimals());
				
				if (totalAmount < 0) {
					entryType = EntryType.HABER;
				} else {
					entryType = EntryType.DEBE;
				}
				

				AccountingEntry newEntry = new AccountingEntry();
				newEntry.setId(id);
				newEntry.setAccount(account);
				newEntry.setTotalAmount(BigDecimal.valueOf(totalAmount));
				newEntry.setIvaAmount(BigDecimal.valueOf(ivaAmount));
				newEntry.setClientName(clientName);
				newEntry.setConcept(concept);
				newEntry.setCurrency(currency);
				newEntry.setDate(date);
				newEntry.setType(entryType);
				newEntry.setCompany(company);

				BigDecimal subtotalAmount = newEntry.getTotalAmount().subtract(newEntry.getIvaAmount());
				newEntry.setSubtotalAmount(subtotalAmount);

				entries.add(newEntry);

				id++;
				row = (Row) rows.next();

			}

			System.out.println(entries.size());
			stream.close();
			return entries;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Sheet getSheet(String fileName, InputStream inp) throws IOException {
		String fileExtn = getFileExtension(fileName);
		Workbook wb_xssf; // Declare XSSF WorkBook
		Workbook wb_hssf; // Declare HSSF WorkBook
		Sheet sheet = null; // sheet can be used as common for XSSF and HSSF
							// WorkBook
		if (fileExtn.equalsIgnoreCase("xlsx")) {
			wb_xssf = new XSSFWorkbook(inp);
			log("xlsx=" + wb_xssf.getSheetName(0));
			sheet = wb_xssf.getSheetAt(0);
		}
		if (fileExtn.equalsIgnoreCase("xls")) {
			POIFSFileSystem fs = new POIFSFileSystem(inp);
			wb_hssf = new HSSFWorkbook(fs);
			log("xls=" + wb_hssf.getSheetName(0));
			sheet = wb_hssf.getSheetAt(0);
		}
		return sheet;
	}

	private static void log(String message) {
		System.out.println(message);
	}

	private static String getFileExtension(String fname2) {
		String fileName = fname2;
		String fname = "";
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		fname = fileName.substring(0, mid);
		ext = fileName.substring(mid + 1, fileName.length());
		return ext;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}
