package t4WS3Client;

import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import t4WS3Client.t4WS3Server.StringArray;
import t4WS3Client.t4WS3Server.T4WS3Server;
import t4WS3Client.t4WS3Server.T4WS3ServerService;

public class buchungDialog extends Dialog<ButtonType> {

	public buchungDialog(String flightId, StringArray emptySeats) {
		T4WS3Server port = new T4WS3ServerService().getT4WS3ServerPort();

		Label nameLbl = new Label("Geben Sie den Namen des Passagiers ein");
		nameLbl.setPrefWidth(200);
		TextField nameTxt = new TextField();
		nameTxt.setPrefWidth(250);
		Label seatLbl = new Label("Wählen Sie den Sitzplatz");
		seatLbl.setPrefWidth(200);

		if (emptySeats != null && emptySeats.getItem() != null) {
			ComboBox<String> cbSeats = new ComboBox<>(FXCollections.observableArrayList(emptySeats.getItem()));

			VBox vb = new VBox(10, nameLbl, nameTxt, seatLbl, cbSeats);
			this.getDialogPane().setContent(vb);
			this.setTitle("Flug buchen - " + flightId);

			ButtonType buchen = new ButtonType("Passagier buchen", ButtonData.OK_DONE);
			this.getDialogPane().getButtonTypes().addAll(buchen, new ButtonType("Beenden", ButtonData.CANCEL_CLOSE));

			this.setResultConverter(new Callback<ButtonType, ButtonType>() {
				@Override
				public ButtonType call(ButtonType arg0) {
					if (arg0 == buchen) {
						System.out.println(flightId + ", " + nameTxt.getText() + ", "
								+ cbSeats.getSelectionModel().getSelectedItem());
						boolean booked = port.addPassenger(flightId, nameTxt.getText(),
								cbSeats.getSelectionModel().getSelectedItem());
						if (booked) {
							System.out.println("Passagier wurde gebucht");
						}
						else
							System.out.println("Not managed");
					}
					return arg0;
				}

			});
		} else {
			System.out.println("Seats are empty.");
			this.getDialogPane().setContent(new Label("No empty seats available."));
		}
	}
}
