package com.starterkit.restfx.dataprovider.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.starterkit.restfx.controller.BookController;
import com.starterkit.restfx.dataprovider.JSONParser;
import com.starterkit.restfx.dataprovider.data.BookVO;
import com.starterkit.restfx.dataprovider.data.StatusVO;

public class JSONParserImpl implements JSONParser{
	private static final Logger LOG = Logger.getLogger(BookController.class);

	@Override
	public List<BookVO> toBookVO(String stringJSON) {
		 LOG.debug("pars1. String: " + stringJSON);
		 Gson gson = new Gson();
		 LOG.debug("pars2");
		 List<BookVO> booksCollection = new ArrayList<BookVO>();
		 BookVO[] books  = gson.fromJson(stringJSON, BookVO[].class);
		 for (int i=0; i<books.length; i++) {
			 booksCollection.add(books[i]);
		 }
		LOG.debug("tytul: " + books[0].getTitle());
		LOG.debug("author: " + books[0].getAuthors());
		LOG.debug("status: " + books[0].getStatus());
		LOG.debug("id: " + books[0].getId());
//		LOG.debug("tytul: " + books[1].getTitle());
//		LOG.debug("author: " + books[1].getAuthors());
//		LOG.debug("status: " + books[1].getStatus());
//		LOG.debug("id: " + books[1].getId());
		return booksCollection;
	}
	
	@Override
	public String toJsonString(String title, String author, StatusVO status) {
		LOG.debug("Enter toJsonString method.");
		Gson gson = new Gson();
		BookVO book = new BookVO(title, author, status);
		String jsonInString = gson.toJson(book);
		LOG.debug("JSONString in method: " + jsonInString);
		return jsonInString;
	}

}
