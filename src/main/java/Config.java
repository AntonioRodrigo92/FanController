import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Config {
    private String smart_plug_URL;
    private String token;
    private String entity_id;
    private String mqtt_broker;
    private String mqtt_username;
    private String mqtt_password;
    private String mqtt_topic;
    private List<HostMachine> machines;
    private long sleepTime;

    public Config(String filePath) throws IOException {
        Path path = Path.of(filePath);

        String content = Files.readString(path);
        JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();

        this.smart_plug_URL = jsonObject.get("smart_plug_URL").getAsString();
        this.token = jsonObject.get("token").getAsString();
        this.entity_id = jsonObject.get("entity_id").getAsString();
        this.mqtt_broker = jsonObject.get("mqtt_broker").getAsString();
        this.mqtt_username = jsonObject.get("mqtt_username").getAsString();
        this.mqtt_password = jsonObject.get("mqtt_password").getAsString();
        this.mqtt_topic = jsonObject.get("mqtt_topic").getAsString();
        this.sleepTime = jsonObject.get("sleep_time_in_seconds").getAsLong();
        this.machines = new ArrayList<>();

        Iterator<JsonElement> it = jsonObject.get("machines").getAsJsonArray().iterator();
        while (it.hasNext()) {
            JsonElement element = it.next();
            JsonObject jsonElement = element.getAsJsonObject();
            String hostname = jsonElement.get("hostname").getAsString();
            boolean enabled = jsonElement.get("enabled").getAsBoolean();
            Double triggerOnTemp = jsonElement.get("triggerOnTemp").getAsDouble();
            Double triggerOffTemp = jsonElement.get("triggerOffTemp").getAsDouble();

            HostMachine mch = new HostMachine(hostname, enabled, triggerOnTemp, triggerOffTemp);
            machines.add(mch);
        }
    }

    public String getSmartPlugURL() {
        return smart_plug_URL;
    }

    public String getToken() {
        return token;
    }

    public String getEntityId() {
        return entity_id;
    }

    public String getMqttBroker() {
        return mqtt_broker;
    }

    public String getMqttUsername() {
        return mqtt_username;
    }

    public String getMqttPassword() {
        return mqtt_password;
    }

    public String getMqttTopic() {
        return mqtt_topic;
    }

    public List<HostMachine> getMachines() {
        return machines;
    }

    public long getSleepTime() {
        return sleepTime;
    }
}
