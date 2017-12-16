import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.joda.time.DateMidnight;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.fitness.Fitness;
import com.google.api.services.fitness.FitnessScopes;
import com.google.api.services.fitness.model.AggregateBucket;
import com.google.api.services.fitness.model.AggregateBy;
import com.google.api.services.fitness.model.AggregateRequest;
import com.google.api.services.fitness.model.AggregateResponse;
import com.google.api.services.fitness.model.DataPoint;
import com.google.api.services.fitness.model.Dataset;
import com.google.api.services.fitness.model.Value;
import org.joda.time.DateTime;


public class Main {

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
        System.out.println("Getting step count!");
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
        Date start, end;

        /*for (int i = 0; i < 24; i++) {
            start_date = "08/08/2017 " + i + ":00:00";
            end_date = "08/08/2017 " + i + 1 + ":00:00";*/

        int dia = 8, mes = 8;
        int d1 = 00;
        while (true) {
            if (mes == 13) {
                break;
            }
            d1 = 00;
            for (int i = 0; i < 4; i++) {
                d1 += 06;
                start_date = dia + "/" + mes + "/2017 00:00:00";
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
                    System.out.println("Day: " + start_date + " End: " + end_date + " Total steps: " + sum);
                    sum = 0.0;
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
            if (dia == 31) {
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
        }
    }
}
