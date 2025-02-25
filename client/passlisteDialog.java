package t4WS3Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import t4WS3Client.t4WS3Server.StringArray;

public class passlisteDialog extends Dialog<ButtonType> {

	public passlisteDialog(StringArray passList) {
		
		Label nameLbl = new Label("Geben Sie den Namen des Passagiers ein");
		nameLbl.setPrefWidth(200);
		
		ObservableList<String> items = FXCollections.observableArrayList(passList.getItem());
		ListView<String> passLV = new ListView<>(items);

		VBox vb = new VBox(10, nameLbl, passLV);
		this.getDialogPane().setContent(vb);
		this.setTitle("Passagierliste");

		this.getDialogPane().getButtonTypes().add(new ButtonType("Beenden", ButtonData.CANCEL_CLOSE));
		
	}
}
