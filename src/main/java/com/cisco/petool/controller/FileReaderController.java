package com.cisco.petool.controller;

import java.io.IOException;
import java.util.List;

import org.bson.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.cisco.petool.dao.FileReaderDaoImpl;

import lombok.RequiredArgsConstructor;

@RequestScope
@RestController
@RequiredArgsConstructor
public class FileReaderController {

	private final FileReaderDaoImpl fileReaderDaoImpl;

	@RequestMapping(value = "/readFromExcel", method = RequestMethod.GET)
	public void readFromExcel() {
		try {
			fileReaderDaoImpl.readFromExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/fetchFromDb", method = RequestMethod.GET)
	public Document fetchFromDb() {
		Document response = new Document();
		List<Document> result = fileReaderDaoImpl.fetchFromDb();
		response.put("result", result);
		return response;

	}

}
