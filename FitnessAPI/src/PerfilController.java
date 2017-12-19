import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private void initialize(){
        User u = MainWindowController.currentUser;
        if(u!=null){
            userLB.setText(u.getData());
            int i = 0, j = 0;
            for(Badge b:u.getConquistas()){
                Label lb = new Label();
                ImageView img = new ImageView();

                img.setImage(new Image(new File(b.getImagePath()).toURI().toString()));
                lb.setText(String.valueOf(b.getName()));
                gridPane.add(lb,i,j);
                gridPane.add(img,i+1,j);
                j++;
            }
        }

    }

    @FXML
    private void voltarBtClicked(MouseEvent event) throws Exception{
        Stage stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}