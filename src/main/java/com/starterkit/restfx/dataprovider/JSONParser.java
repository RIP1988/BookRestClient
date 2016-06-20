package com.starterkit.restfx.dataprovider;

import java.util.Collection;

import com.starterkit.restfx.dataprovider.data.BookVO;
import com.starterkit.restfx.dataprovider.data.StatusVO;

public interface JSONParser {
//	id, title , author, status taka kolejnosc
	Collection<BookVO> toBookVO(String stringJSON);
	String toJsonString(String title, String author, StatusVO status);
}
