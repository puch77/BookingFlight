package t4WS3Client;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import t4WS3Client.t4WS3Server.StringArray;
import t4WS3Client.t4WS3Server.T4WS3Server;
import t4WS3Client.t4WS3Server.T4WS3ServerService;

public class T4WS3Client extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		T4WS3Server port = new T4WS3ServerService().getT4WS3ServerPort();
		StringArray dA = port.getDestinationList();
		ComboBox<String> cbDestination = new ComboBox<>(FXCollections.observableArrayList(dA.getItem()));

		ComboBox<String> cbFlight = new ComboBox<>(FXCollections.observableArrayList());
		cbDestination.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				StringArray fA = port.getFlightList(newValue);
				if (fA != null && fA.getItem() != null) {
					cbFlight.setItems(FXCollections.observableArrayList(fA.getItem()));
				}
			}
		});

		Button buchenBtn = new Button("Flug buchen");
		Button passListeBtn = new Button("Passagierliste anzeigen");

		buchenBtn.setOnAction(e -> {
			// System.out.println("empty seats:");
			StringArray emptySeats = port.getEmptySeatList(cbFlight.getSelectionModel().getSelectedItem());
			// for(String s: emptySeats.getItem())
			// System.out.println(s);

			new buchungDialog(cbFlight.getSelectionModel().getSelectedItem(), emptySeats).showAndWait();

		});

		passListeBtn.setOnAction(e -> {
			String selectedFlight = cbFlight.getSelectionModel().getSelectedItem();
			if (selectedFlight != null) {
				StringArray pA = port.getPassengerList(selectedFlight);
				new passlisteDialog(pA).showAndWait();
			//	if (pA != null) {
			//	    for (String s : pA.getItem()) {
			//	        System.out.println(s);
			//	    }
			//	} else
			//		System.out.println("List is empty");
			 } 
				else {
				System.out.println("No flight selected");
			}
		});

		VBox vb = new VBox(10, new Label("Wählen Sie den Zielflughafen aus"), cbDestination,
				new Label("Wählen Sie den Flug aus"), cbFlight, buchenBtn, passListeBtn);

		vb.setPrefWidth(300);
		vb.setPadding(new Insets(15));
		arg0.setScene(new Scene(vb));
		arg0.setTitle("T4WS3Client");
		arg0.show();
	}

}
