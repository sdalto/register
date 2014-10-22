package com.rilla.register.services.generator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.db4o.config.Entry;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import com.rilla.register.repository.AccountingEntry;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class DBFGenerator {

	private static final int CANT_COLUMNS = 23;

	public InputStream createFile(String path, List<AccountingEntry> entries) {

		DBFField[] fields = createFiels();

		try {
			DBFWriter writer = new DBFWriter();
			writer.setFields(fields);

			// now populate DBFWriter
			if (CollectionUtils.isNotEmpty(entries)) {
				for (AccountingEntry accountingEntry : entries) {
					createRow(writer, accountingEntry);
				}
			}

			FileOutputStream fos = new FileOutputStream(path);
			writer.write(fos);
			fos.close();

			System.out.println("Excel written successfully..");
			return new BufferedInputStream(new FileInputStream(path));
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Double getNumericValue(int value){
		return new Double(value);
	}
	
	private Double getNumericValue(BigDecimal value){
		return value.doubleValue();
	}

	private void createRow(DBFWriter writer, AccountingEntry accountingEntry)
			throws DBFException {
		Object rowData[] = new Object[CANT_COLUMNS];

		// Set Nro. Asiento
		rowData[0] = getNumericValue(1);

		// Set Fecha de movimiento
		Date date = accountingEntry.getDate();
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);

			rowData[1] = getNumericValue(day);
			rowData[2] = getNumericValue(month);
			rowData[3] = getNumericValue(year) - 2000;
		}

		rowData[4] = getNumericValue(5);
		rowData[5] = getNumericValue(0);
		rowData[6] = getNumericValue(0);
		rowData[7] = getNumericValue(0);
		rowData[8] = getNumericValue(1);
		rowData[9] = getNumericValue(0);
		rowData[10] = getNumericValue(0);
		rowData[11] = accountingEntry.getConcept();
		
//		rowData[12] = accountingEntry.getAmount().compareTo(BigDecimal.ZERO) >= 0 ? "Debe" : "Haber"; 

		// Set Cuenta
		rowData[13] = accountingEntry.getAccount();

		rowData[14] = accountingEntry.getAmount().compareTo(BigDecimal.ZERO) >= 0 ? "Debe" : "Haber"; 

		// Set monto
		rowData[15] = accountingEntry.getCurrency();
		rowData[16] = getNumericValue(accountingEntry.getAmount());

		rowData[17] = getNumericValue(0);
		rowData[18] = getNumericValue(0);
		rowData[19] = getNumericValue(0);
		rowData[20] = getNumericValue(0);
		rowData[21] = getNumericValue(0);
		rowData[22] = getNumericValue(0);

		writer.addRecord(rowData);
	}

	private DBFField[] createFiels() {
		DBFField fields[] = new DBFField[CANT_COLUMNS];

		fields[0] = new DBFField();
		fields[0].setName("NROASIENTO");
		fields[0].setDataType(DBFField.FIELD_TYPE_N);
		fields[0].setFieldLength(10);

		fields[1] = new DBFField();
		fields[1].setName("DIA");
		fields[1].setDataType(DBFField.FIELD_TYPE_N);
		fields[1].setFieldLength(2);

		fields[2] = new DBFField();
		fields[2].setName("MES");
		fields[2].setDataType(DBFField.FIELD_TYPE_N);
		fields[2].setFieldLength(2);

		fields[3] = new DBFField();
		fields[3].setName("ANIO");
		fields[3].setDataType(DBFField.FIELD_TYPE_N);
		fields[3].setFieldLength(2);

		// TODO: que es?
		fields[4] = new DBFField();
		fields[4].setName("NROTA");
		fields[4].setDataType(DBFField.FIELD_TYPE_N);
		fields[4].setFieldLength(2);

		// TODO: que es?
		fields[5] = new DBFField();
		fields[5].setName("NROSUBTIPO");
		fields[5].setDataType(DBFField.FIELD_TYPE_N);
		fields[5].setFieldLength(1);

		// TODO: que es?
		fields[6] = new DBFField();
		fields[6].setName("CODIGOV");
		fields[6].setDataType(DBFField.FIELD_TYPE_N);
		fields[6].setFieldLength(1);

		// TODO: que es?
		fields[7] = new DBFField();
		fields[7].setName("VD");
		fields[7].setDataType(DBFField.FIELD_TYPE_N);
		fields[7].setFieldLength(2);

		// TODO: que es?
		fields[8] = new DBFField();
		fields[8].setName("VM");
		fields[8].setDataType(DBFField.FIELD_TYPE_N);
		fields[8].setFieldLength(2);

		// TODO: que es?
		fields[9] = new DBFField();
		fields[9].setName("VA");
		fields[9].setDataType(DBFField.FIELD_TYPE_N);
		fields[9].setFieldLength(2);

		// TODO: que es?
		fields[10] = new DBFField();
		fields[10].setName("BORRADOR");
		fields[10].setDataType(DBFField.FIELD_TYPE_N);
		fields[10].setFieldLength(1);

		fields[11] = new DBFField();
		fields[11].setName("LEYENDA");
		fields[11].setDataType(DBFField.FIELD_TYPE_C);
		fields[11].setFieldLength(128);

		// TODO: que es?
		fields[12] = new DBFField();
		fields[12].setName("LEYENDAL");
		fields[12].setDataType(DBFField.FIELD_TYPE_C);
		fields[12].setFieldLength(10);

		fields[13] = new DBFField();
		fields[13].setName("CUENTA");
		fields[13].setDataType(DBFField.FIELD_TYPE_C);
		fields[13].setFieldLength(20);

		// TODO: que es?
		fields[14] = new DBFField();
		fields[14].setName("SL");
		fields[14].setDataType(DBFField.FIELD_TYPE_C);
		fields[14].setFieldLength(12);

		fields[15] = new DBFField();
		fields[15].setName("SIMBOLO");
		fields[15].setDataType(DBFField.FIELD_TYPE_C);
		fields[15].setFieldLength(3);

		fields[16] = new DBFField();
		fields[16].setName("IMPORTE");
		fields[16].setDataType(DBFField.FIELD_TYPE_N);
		fields[16].setFieldLength(20);

		// TODO: que es?
		fields[17] = new DBFField();
		fields[17].setName("CL");
		fields[17].setDataType(DBFField.FIELD_TYPE_N);
		fields[17].setFieldLength(1);

		// TODO: que es?
		fields[18] = new DBFField();
		fields[18].setName("NS");
		fields[18].setDataType(DBFField.FIELD_TYPE_N);
		fields[18].setFieldLength(1);

		// TODO: que es?
		fields[19] = new DBFField();
		fields[19].setName("CAMPO1");
		fields[19].setDataType(DBFField.FIELD_TYPE_N);
		fields[19].setFieldLength(1);

		// TODO: que es?
		fields[20] = new DBFField();
		fields[20].setName("CAMPO2");
		fields[20].setDataType(DBFField.FIELD_TYPE_N);
		fields[20].setFieldLength(1);

		// TODO: que es?
		fields[21] = new DBFField();
		fields[21].setName("CAMPO3");
		fields[21].setDataType(DBFField.FIELD_TYPE_N);
		fields[21].setFieldLength(1);

		// TODO: que es?
		fields[22] = new DBFField();
		fields[22].setName("CAMPO4");
		fields[22].setDataType(DBFField.FIELD_TYPE_N);
		fields[22].setFieldLength(1);
		return fields;
	}

}
