import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.text.html.ImageView;
import java.util.List;

public class MainWindowController {

    @FXML
    private TableView<UserTable> rankingTable;

    @FXML
    private TableColumn<UserTable,String> userCol;

    @FXML
    private TableColumn<UserTable,String> stepsCol;

    @FXML
    private TableColumn<UserTable,String> levelCol;



    public class UserTable{
        private String date;
        private int steps;
        private int level;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public UserTable(String date, int steps, int level) {

            this.date = date;
            this.steps = steps;
            this.level = level;
        }
    }

    public MainWindowController(){
        userCol = new TableColumn<>();
        stepsCol = new TableColumn<>();
        levelCol = new TableColumn<>();
    }


    @FXML
    private void initialize() {
        userCol.setCellValueFactory(new PropertyValueFactory<UserTable,String>("Date"));
        userCol.setText("Utilizador");
        stepsCol.setCellValueFactory(new PropertyValueFactory<UserTable,String>("Steps"));
        stepsCol.setText("Passos");
        levelCol.setCellValueFactory(new PropertyValueFactory<UserTable,String>("Level"));
        levelCol.setText("Nível");

        List<User> utilizadores = Main.utilizadores;
        ObservableList<UserTable> data = FXCollections.observableArrayList();
        for(User u:utilizadores){
            data.add(new UserTable(u.getData(),u.getSteps(),u.getNivel()));
            //rankingTable.getItems().addAll(u.getData(),String.valueOf(u.getSteps()),String.valueOf(u.getNivel()));
        }
        rankingTable.getColumns().addAll(userCol,stepsCol, levelCol);
        rankingTable.setItems(data);
    }

}