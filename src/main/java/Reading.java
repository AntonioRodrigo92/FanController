import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reading {
    private String hostname;
    private Double temperature;
    private LocalDateTime timestamp;

    public Reading(String hostname, Double temperature, LocalDateTime timestamp) {
        this.hostname = hostname;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public String getHostname() {
        return hostname;
    }

    public Double getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isReadingObsolete() {
        long diff = ChronoUnit.MINUTES.between(timestamp, LocalDateTime.now());
        return diff > 60;
    }

}
