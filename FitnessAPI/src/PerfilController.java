import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

public class PerfilController {


    @FXML
    private Label userLB;

    @FXML
    private GridPane gridPane;

    @FXML
    private ProgressBar pgBar;

    @FXML
    private ProgressIndicator pgInd;

    @FXML
    private Label stepsLB;

    @FXML
    private Label stepsAtualLB;
    @FXML
    private ProgressBar pgBarLVL;

    @FXML
    private ProgressIndicator pgIntLevel;

    @FXML
    private Label levelLB;

    @FXML
    private Label levelAtualLB;


    @FXML
    private void initialize() {
        User u = MainWindowController.currentUser;
        if (u != null) {
            userLB.setText(u.getData());
            double value = 0;
            stepsAtualLB.setText(String.valueOf(u.getSteps()));
            levelAtualLB.setText("0");
            levelLB.setText("4");
            pgBarLVL.setProgress(u.getNivel()/4.0);
            pgIntLevel.setProgress(u.getNivel()/4.0);
            if (u.getSteps() < 1276) {
                value = (double) u.getSteps() / 1276.0;
                pgBar.setProgress(value);
                pgInd.setProgress(value);
                stepsLB.setText(String.valueOf(1276));

            } else {
                if (u.getSteps() >= 1276 && u.getSteps() < 54000) {
                    value = (double) u.getSteps() / 54000.0;
                    stepsLB.setText(String.valueOf(54000));
                    pgBar.setProgress(value);
                    pgInd.setProgress(value);
                } else {
                    stepsLB.setText(String.valueOf(54000));
                    pgBar.setProgress(1);
                    pgInd.setProgress(1);
                }
            }

            int i = 0, j = 0;
            for (Badge b : u.getConquistas()) {
                Label lb = new Label();
                ImageView img = new ImageView();

                img.setImage(new Image(new File(b.getImagePath()).toURI().toString()));
                lb.setText(String.valueOf(b.getName()));
                gridPane.add(lb, i, j);
                gridPane.add(img, i + 1, j);
                j++;
            }
        }

    }

    @FXML
    private void voltarBtClicked(MouseEvent event) throws Exception {
        Stage stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}