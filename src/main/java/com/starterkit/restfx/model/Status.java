package com.starterkit.restfx.model;

import org.apache.log4j.Logger;

import com.starterkit.restfx.controller.BookController;
import com.starterkit.restfx.dataprovider.data.StatusVO;

public enum Status {
	
	ANY, FREE, LOAN, MISSING;
	private static final Logger LOG = Logger.getLogger(BookController.class);
	
	public static Status fromStatusVO(StatusVO status) {
		LOG.debug("nazwa statusu: "+status.name());
		return Status.valueOf(status.name());
	}

	public StatusVO toStatusVO() {
		if (this == ANY) {
			return null;
		}
		return StatusVO.valueOf(this.name());
	}
}

