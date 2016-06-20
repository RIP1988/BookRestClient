package com.starterkit.restfx.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.restfx.dataprovider.data.BookVO;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class BookSearch {
	private final StringProperty title = new SimpleStringProperty();
	private final StringProperty author = new SimpleStringProperty();
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final ObjectProperty<Status> status = new SimpleObjectProperty<>();
	private final ListProperty<BookVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));
	
	public String getTitle() {
		return title.get();
	}
	public void setTitle(String value) {
		title.set(value);
	}
	public StringProperty titleProperty() {
		return title;
	}
	
	public String getAuthor() {
		return author.get();
	}
	public void setAuthor(String value) {
		author.set(value);
	}
	public StringProperty authorProperty() {
		return author;
	}
	
//	public IntegerProperty idProperty() {
//		return id;
//	}
//	public Integer getId() {
//		return id.get();
//	}
//	public void setId(int value) {
//		id.set(value);
//	}
	
	public ObjectProperty<Status> statusProperty() {
		return status;
	}
	public Status getStatus() {
		return status.get();
	}
	public void setStatus(Status value) {
		status.setValue(value);
	}
	
	public ListProperty<BookVO> resultProperty() {
		return result;
	}
	public List<BookVO> getResult() {
		return result.get();
	}
	public void setResult(List<BookVO> value) {
		result.setAll(value);
	}
	
	@Override
	public String toString() {
		return "BookSearch [title=" + title + ", author=" + author + ", result=" + result + "]";
	}
	
	
}
