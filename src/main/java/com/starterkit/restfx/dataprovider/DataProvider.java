package com.starterkit.restfx.dataprovider;

import java.util.Collection;

import com.starterkit.restfx.dataprovider.data.BookVO;
import com.starterkit.restfx.dataprovider.data.StatusVO;
import com.starterkit.restfx.dataprovider.impl.DataProviderImpl;

public interface DataProvider{
	/**
	 * Instance of this interface.
	 */
	DataProvider INSTANCE = new DataProviderImpl();

	/**
	 * Finds persons with their name containing specified string and/or given
	 * sex.
	 *
	 * @param name
	 *            string contained in name
	 * @param sex
	 *            sex
	 * @return collection of persons matching the given criteria
	 */
	Collection<BookVO> findBooks(String title, String author, StatusVO status);
}
