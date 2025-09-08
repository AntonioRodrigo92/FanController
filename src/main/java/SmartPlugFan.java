import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SmartPlugFan implements ControlFan {
    private String baseURL;
    private String token;
    private String entityID;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SmartPlugFan(String baseURL, String token, String entityID) {
        this.baseURL = baseURL;
        this.token = token;
        this.entityID = entityID;
    }

    @Override
    public void turnOn() {
        try {
            int responseCode = sendSmartPlugRequest("turn_on");
            System.out.println(LocalDateTime.now().format(formatter) + " - turnOn" + ". Response code: " + responseCode);
        }
        catch (Exception e) {
            System.err.println("sendSmartPlugRequest Failed");
            e.printStackTrace();
        }
    }

    @Override
    public void turnOff() {
        try {
            int responseCode = sendSmartPlugRequest("turn_off");
            System.out.println(LocalDateTime.now().format(formatter) + " - turnOff" + ". Response code: " + responseCode);
        }
        catch (Exception e) {
            System.err.println("sendSmartPlugRequest Failed");
            e.printStackTrace();
        }
    }

    private int sendSmartPlugRequest(String actionSuffix) throws Exception {
        URL url = new URL(baseURL + actionSuffix);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput = String.format("{\"entity_id\": \"%s\"}", entityID);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes());
            os.flush();
        }

//        System.out.println("Response Code: " + conn.getResponseCode());
        return conn.getResponseCode();
    }

}
