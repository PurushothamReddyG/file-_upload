package com.cisco.petool.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.cisco.pettol.model.HostDetails;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class FileReaderDaoImpl {

	@Value("${file}")
	String csvFilePath;

	@Autowired
	private MongoClient client;

	private MongoDatabase database;
	private MongoCollection<Document> collection;

	@PostConstruct
	public void setup() {
		database = client.getDatabase("test");
		collection = database.getCollection("host_details");
	}

	public List<HostDetails> readFromExcel() throws IOException {
		// get file that needs to be mapped into object.
		Resource resource = new ClassPathResource(csvFilePath);

		FileInputStream inputStream = new FileInputStream(resource.getFile());
		// get workbook and sheet
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		List<HostDetails> exampleDTOList = new ArrayList<>();
		// iterate through rows
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			// skip heading row.
			if (currentRow.getRowNum() == 0) {
				continue;
			}
			// mapped to example object.
			HostDetails hostDetails = new HostDetails();
			hostDetails.setqId(currentRow.getCell(0).getNumericCellValue());
			hostDetails.setPort(currentRow.getCell(1).getNumericCellValue());
			hostDetails.setHostName(currentRow.getCell(2).getStringCellValue());

			Document insert_document = new Document();
			insert_document.put("q_id", currentRow.getCell(0).getNumericCellValue());
			insert_document.put("port", currentRow.getCell(1).getNumericCellValue());
			insert_document.put("host_name", currentRow.getCell(2).getStringCellValue());

			collection.insertOne(insert_document);

			exampleDTOList.add(hostDetails);
		}

		return exampleDTOList;
	}

	public List<Document> fetchFromDb() {
		List<Document> list = new ArrayList<>();
		return collection.find().projection(new Document("_id",0)).into(list);
	}

}
