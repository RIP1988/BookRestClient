package com.starterkit.restfx.dataprovider.impl;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.starterkit.restfx.dataprovider.DataProvider;
import com.starterkit.restfx.dataprovider.data.BookVO;
import com.starterkit.restfx.dataprovider.data.StatusVO;

public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	/**
	 * Delay (in ms) for method calls.
	 */
	private static final long CALL_DELAY = 3000;

	private Collection<BookVO> books = new ArrayList<>();

	public DataProviderImpl() {

	}

	@Override
	public List<BookVO> findBooks(String title, String author, StatusVO status) {
		LOG.debug("find1");
		JSONParserImpl jsonParser = new JSONParserImpl();
		List<BookVO> result = new ArrayList<BookVO>();
		String wantedTitle = "";
		String wantedAuthor = "";
		
		if (title!=null) {
			wantedTitle = title;
		};
		if (author!=null) {
			wantedAuthor = author;
		};
		LOG.debug("find2");
		String targetURL;
		if (status == null) {
			targetURL = "http://localhost:8080/webstore/bookAuthorTitle?author=" + (String)wantedAuthor
					+ "&title=" + (String)wantedTitle;
		}
		else {
			targetURL = "http://localhost:8080/webstore/bookAuthorTitleStatus?author=" + (String)wantedAuthor
					+ "&title=" + (String)wantedTitle + "&status=" + status.name();
		}
		
		LOG.debug("find3");
		LOG.debug("spytany URL: " + targetURL);
		try {
			 LOG.debug("find4");
			            URL restServiceURL = new URL(targetURL);
			            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			            httpConnection.setRequestMethod("GET");
			            httpConnection.setRequestProperty("Accept", "application/json");

			            if (httpConnection.getResponseCode() != 200) {
			            	return new ArrayList<BookVO>();
			            }
			            LOG.debug("find5");
			            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
			                (httpConnection.getInputStream())));

			            String output;
//			            System.out.println("Output from Server:  \n");
			            LOG.debug("find6");
			            while ((output = responseBuffer.readLine()) != null) {
				            LOG.debug("find7");
				            result.addAll(jsonParser.toBookVO(output));
			               // result.add(jsonParser.toBookVO(output));
			                LOG.debug("Result size: " + result.size());
			            }
			            httpConnection.disconnect();
			          } catch (MalformedURLException exept) {
			            exept.printStackTrace();
			          } catch (IOException e) {
			            e.printStackTrace();
			          }
						return result;
			        }
	}
	


