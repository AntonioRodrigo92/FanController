import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HostMachine {
    private String hostname;
    private boolean enabled;
    private Double triggerOnTemp;
    private Double triggerOffTemp;
    private Reading reading;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public HostMachine(String hostname, boolean enabled, Double triggerOnTemp, Double triggerOffTemp) {
        this.hostname = hostname;
        this.enabled = enabled;
        this.triggerOnTemp = triggerOnTemp;
        this.triggerOffTemp = triggerOffTemp;
        this.reading = new Reading(hostname, Double.NEGATIVE_INFINITY, LocalDateTime.MIN);
    }

    public String getHostname() {
        return hostname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Double getTriggerOnTemp() {
        return triggerOnTemp;
    }

    public Double getTriggerOffTemp() {
        return triggerOffTemp;
    }

    public Reading getReading() {
        return reading;
    }

    public void updateReading(JsonObject jsonObject) {
        String newHostname = jsonObject.get("hostname").getAsString();
        Double newTemperature = jsonObject.get("temperature").getAsDouble();
        LocalDateTime newTimestamp = LocalDateTime.parse(jsonObject.get("timestamp").getAsString(), formatter);

        if (newTimestamp.isAfter(this.reading.getTimestamp())) {
            Reading newReading = new Reading(newHostname, newTemperature, newTimestamp);
            this.reading = newReading;
        }
    }

}
