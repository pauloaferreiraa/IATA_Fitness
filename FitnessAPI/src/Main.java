
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.api.client.googleapis.util.Utils;
import com.google.api.services.fitness.Fitness;
import com.google.api.services.fitness.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fitness.FitnessScopes;


public class Main  extends Application{

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Fitness API Java Quickstart";

    /**
     * File client_secret.json
     **/
    private static final String CLIENT_SECRET = "client_secret.json";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
            ".credentials/drive-java-quickstart");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    public static List<User> utilizadores;
    public static Stage stage;
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials at
     * ~/.credentials/drive-java-quickstart
     */
    private static final Set<String> SCOPES = FitnessScopes.all();


    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStreamReader in = new InputStreamReader(new FileInputStream(CLIENT_SECRET));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }


    public static void main(String[] args) throws IOException, GeneralSecurityException {
        List<Integer> mes_30 = new ArrayList<>(Arrays.asList(4,6,9,11));
        System.out.println("Getting step count!");
        utilizadores = new ArrayList<User>();
        double sum = 0;
        Credential credential = authorize();
        Fitness fitness = new Fitness.Builder(
                Utils.getDefaultTransport(),
                Utils.getDefaultJsonFactory(),
                credential //prerequisite
        ).setApplicationName(APPLICATION_NAME).build();
        AggregateRequest aggregateRequest = new AggregateRequest();
        aggregateRequest.setAggregateBy(Collections.singletonList(
                new AggregateBy()
                        .setDataSourceId("derived:com.google.step_count.delta:com.google.android.gms:estimated_steps")));

//        aggregateRequest.setStartTimeMillis(DateMidnight.now().getMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String start_date, end_date;

        Date start=null, end=null;



        /*for (int i = 0; i < 24; i++) {
            start_date = "08/08/2017 " + i + ":00:00";
            end_date = "08/08/2017 " + i + 1 + ":00:00";*/
        Map<Integer,Integer> passosHora ;
        Calendar calendar = Calendar.getInstance();
        Date atual = calendar.getTime();
        calendar.add(Calendar.DATE,-15);
        System.out.println(calendar.getTime());
        int dia = calendar.get(Calendar.DAY_OF_MONTH), mes = calendar.get(Calendar.MONTH)+1;
        int d1 = 00;
        int steps;
        while (true) {
            steps=0;
            passosHora = new TreeMap<Integer, Integer>();


            d1 = 00;
            for (int i = 0; i < 4; i++) {
                start_date = dia + "/" + mes + "/2017 " + d1 + ":00:00";
                d1 += 06;
                end_date = dia + "/" + mes + "/2017 " + d1 + ":00:00";

                try {

                    start = sdf.parse(start_date);
                    end = sdf.parse(end_date);

                    aggregateRequest.setStartTimeMillis(start.getTime());
                    aggregateRequest.setEndTimeMillis(end.getTime());
                    AggregateResponse response = fitness.users().dataset().aggregate("me", aggregateRequest).execute();
                    for (AggregateBucket aggregateBucket : response.getBucket()) {
                        for (Dataset dataset : aggregateBucket.getDataset()) {
                            for (DataPoint dataPoint : dataset.getPoint()) {
                                for (Value value : dataPoint.getValue()) {
                                    if (value.getIntVal() != null) {
                                        sum += value.getIntVal(); //for steps you only receive int values
                                    }
                                }
                            }
                        }
                    }
                    passosHora.put(d1,(int)sum);
                    steps+=(int)sum;
                    System.out.println("Day: " + start_date + " End: " + end_date + " Total steps: " + sum);
                    sum = 0.0;
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
            // acabou um dia
            utilizadores.add(new User(dia+"/"+mes+"/2017",steps,passosHora));
            if (dia == 31 ||(mes_30.contains(mes) && dia==30)) {
                dia = 01;
                mes++;
            } else {
                if (mes == 02 && dia == 28) {
                    dia = 01;
                    mes++;
                } else {

                    dia++;
                }
            }
            if (start.after(atual)) {
                break;
            }
        }

        for(User u : utilizadores){
            System.out.println(u.getData()+" "+u.getSteps()+" "+u.getNivel());
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Conquistas");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.stage = primaryStage;
    }
}
