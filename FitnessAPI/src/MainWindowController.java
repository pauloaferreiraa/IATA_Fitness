import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class MainWindowController {

    @FXML
    private TableView<User> rankingTable;

    @FXML
    private TableColumn<User,String> userCol;

    @FXML
    private TableColumn<User,String> stepsCol;

    @FXML
    private TableColumn<User,Double> levelCol;




    public static User currentUser;

    public MainWindowController(){
        userCol = new TableColumn<>();
        stepsCol = new TableColumn<>();
        levelCol = new TableColumn<>();
    }


    @FXML
    private void initialize() {
        rankingTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    User rowData = row.getItem();
                    currentUser = rowData;
                    try {
                        Stage stage = Main.stage;
                        Parent root = FXMLLoader.load(getClass().getResource("/Perfil.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        userCol.setCellValueFactory(new PropertyValueFactory<User,String>("data"));
        userCol.setText("Utilizador");
        stepsCol.setCellValueFactory(new PropertyValueFactory<User,String>("steps"));
        stepsCol.setText("Passos");
        levelCol.setCellValueFactory(new PropertyValueFactory<User,Double>("nivel"));
        levelCol.setText("Nível");
        List<User> utilizadores = Main.utilizadores;
        ObservableList<User> data = FXCollections.observableArrayList();
        for(User u:utilizadores){
            data.add(u);
            //rankingTable.getItems().addAll(u.getData(),String.valueOf(u.getSteps()),String.valueOf(u.getNivel()));
        }
        rankingTable.getColumns().addAll(userCol,stepsCol, levelCol);
        rankingTable.setItems(data);
    }

}