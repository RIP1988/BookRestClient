
package com.starterkit.restfx.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.restfx.dataprovider.DataProvider;
import com.starterkit.restfx.dataprovider.data.BookVO;
import com.starterkit.restfx.dataprovider.data.StatusVO;
import com.starterkit.restfx.dataprovider.impl.JSONParserImpl;
import com.starterkit.restfx.model.BookSearch;
import com.starterkit.restfx.model.Status;
import javafx.concurrent.Task;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class BookController {

	private static final Logger LOG = Logger.getLogger(BookController.class);
//	final TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter());
	/**
	 * Resource bundle loaded with this controller. JavaFX injects a resource
	 * bundle specified in {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * URL of the loaded FXML file. JavaFX injects an URL specified in
	 * {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	/**SS
	 * JavaFX injects an object defined in FXML with the same "fx:id" as the
	 * variable name.
	 */
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField authorField;
	
	@FXML
	private ComboBox<Status> statusField;
	
	/*@FXML
	private TextField idField;*/

	@FXML
	private Button searchButton;
	
	@FXML
	private Button addButton;

	@FXML
	private TableView<BookVO> resultTable;

	@FXML
	private TableColumn<BookVO, String> titleColumn;
	
	@FXML
	private TableColumn<BookVO, String> authorColumn;
	
	@FXML
	private TableColumn<BookVO, String> statusColumn;

//	@FXML
//	private TableColumn<BookVO, String> idColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final BookSearch model = new BookSearch();

	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 */
	public BookController() {
		LOG.debug("Constructor: titleField = " + titleField);
	}

	/**
	 * The JavaFX runtime calls this method after loading the FXML file.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() {
		LOG.debug("initialize(): titleField = " + titleField);
		//idField.setTextFormatter(formatter);
		initializeStatusField();
		initializeResultTable();

		/*
		 * Bind controls properties to model properties.
		 */
		authorField.textProperty().bindBidirectional(model.authorProperty());
		titleField.textProperty().bindBidirectional(model.titleProperty());
		statusField.valueProperty().bindBidirectional(model.statusProperty());
		model.setStatus(Status.ANY);
		//idField.textProperty().bindBidirectional(model.idProperty());
		resultTable.itemsProperty().bind(model.resultProperty());
	}

		
		private void initializeStatusField() {
			/*
			 * Add items to the list in combo box.
			 */
			for (Status status : Status.values()) {
				statusField.getItems().add(status);
			}

			/*
			 * Set cell factory to render internationalized texts for list items.
			 */
			statusField.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>() {

				@Override
				public ListCell<Status> call(ListView<Status> param) {
					return new ListCell<Status>() {

						@Override
						protected void updateItem(Status item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								return;
							}
//							String text = item.name();
							String text = getInternationalizedText(item);
							setText(text);
						}
					};
				}
			});

			/*
			 * Set converter to display internationalized text for selected value.
			 */
			statusField.setConverter(new StringConverter<Status>() {

				@Override
				public String toString(Status object) {
//					return object.name();
					return getInternationalizedText(object);
				}

				@Override
				public Status fromString(String string) {
					/*
					 * Not used, because combo box is not editable.
					 */
					return null;
				}
			});
		/*
		 * This works also, because we are using bidirectional binding.
		 */
		// sexField.setValue(Sex.ANY);

		/*
		 * Make the Search button inactive when the Title field is empty.
		 */
		addButton.disableProperty().bind(titleField.textProperty().isEmpty());
		addButton.disableProperty().bind(authorField.textProperty().isEmpty());
		addButton.disableProperty().bind(statusField.valueProperty().isEqualTo(Status.ANY));
	}
	private void initializeResultTable() {
		/*
		 * Define what properties of PersonVO will be displayed in different
		 * columns.
		 */
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		authorColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAuthors()));
		statusColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BookVO, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BookVO, String> param) {
						StatusVO status = param.getValue().getStatus();
						String text = status.name();
						return new ReadOnlyStringWrapper(text);
					}
				});
		/*
		 * Show specific text for an empty table. This can also be done in FXML.
		 */
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		/*
		 * When table's row gets selected say given person's name.
		 */
//		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PersonVO>() {
//
//			@Override
//			public void changed(ObservableValue<? extends PersonVO> observable, PersonVO oldValue, PersonVO newValue) {
//				LOG.debug(newValue + " selected");
//
//				if (newValue != null) {
//					Task<Void> backgroundTask = new Task<Void>() {
//
//						@Override
//						protected Void call() throws Exception {
//							speaker.say(newValue.getName());
//							return null;
//						}
//
//						@Override
//						protected void failed() {
//							LOG.error("Could not say name: " + newValue.getName(), getException());
//						}
//					};
//					new Thread(backgroundTask).start();
//				}
//			}
//		});
	}

	private String getInternationalizedText(Status status) {
		return resources.getString("status." + status.name());
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event 
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");

		searchButtonAction();
	}
	
	@FXML
	private void addButtonAction(ActionEvent event) throws IOException {
		JSONParserImpl parser = new JSONParserImpl();
		LOG.debug("'Add' button clicked");
		final String targetURL = "http://localhost:8080/webstore/add";
		URL restServiceURL = new URL(targetURL);
        HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json");
	 
        String jsonString = parser.toJsonString(model.getTitle(),
				model.getAuthor(),
				model.getStatus().toStatusVO());
	    LOG.debug("JSONString: " + jsonString);
        OutputStream output = httpConnection.getOutputStream();
        output.write(jsonString.getBytes());
        output.flush(); 
        LOG.debug("Writing JSON finished.");
	}

	private void searchButtonAction() {
		
		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			@Override
			protected Collection<BookVO> call() throws Exception {
				LOG.debug("call() called");
				LOG.debug("Status in call(): " + model.getStatus().name());
				/*
				 * Get the data.
				 */
				Collection<BookVO> result = dataProvider.findBooks( //
						model.getTitle(),
						model.getAuthor(),//
						model.getStatus().toStatusVO());

				/*
				 * Value returned from this method is stored as a result of task
				 * execution.
				 */
				return result;
			}

			/**
			 * This method will be executed in the JavaFX Application Thread
			 * when the task finishes.
			 */
			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				Collection<BookVO> result = getValue();
				LOG.debug("Found: " + result.size() + " books.");

				model.setResult(new ArrayList<BookVO>(result));

				resultTable.getSortOrder().clear();
			}
		};
		new Thread(backgroundTask).start();
	
		
	}

}

