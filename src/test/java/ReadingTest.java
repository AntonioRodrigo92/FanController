import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class ReadingTest {
    private Reading reading;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void setup() {
        String hostname = "gmk-n100-worker";
        Double temperature = 55.0;
        LocalDateTime timestamp = LocalDateTime.parse("2025-09-04 23:43:30", formatter);
        this.reading = new Reading(hostname, temperature, timestamp);
    }

    @DisplayName("getHostnameTest")
    @Test
    public void getHostnameTest() {
        //  given
        String expectedHostname = "gmk-n100-worker";
        //  when
        String hostname = reading.getHostname();
        //  then
        assertEquals(expectedHostname, hostname);
    }

    @DisplayName("getTemperatureTest")
    @Test
    public void getTemperatureTest() {
        //  given
        Double expectedTemperature = 55.0;
        //  when
        Double temperature = reading.getTemperature();
        //  then
        assertEquals(expectedTemperature, temperature);
    }

    @DisplayName("getTimestampTest")
    @Test
    public void getTimestampTest() {
        //  given
        LocalDateTime expectedTimestamp = LocalDateTime.parse("2025-09-04 23:43:30", formatter);
        //  when
        LocalDateTime timestamp = reading.getTimestamp();
        //  then
        assertEquals(expectedTimestamp, timestamp);
    }

    @DisplayName("isObsoleteTest - TRUE")
    @Test
    public void isReadingObsoleteTest_true() {
        //  given
        LocalDateTime newTimestamp = LocalDateTime.parse("2025-09-05 21:30:30", formatter);
        Reading newReading = new Reading(null, null, newTimestamp);
        //  when
        boolean obsoleteReading = newReading.isReadingObsolete();
        //  then
        assertTrue(obsoleteReading);
    }

    @DisplayName("isObsoleteTest - FALSE")
    @Test
    public void isReadingObsoleteTestTest_false() {
        //  given
        String timestamp = LocalDateTime.now().format(formatter);
        LocalDateTime newTimestamp = LocalDateTime.parse(timestamp, formatter);
        Reading newReading = new Reading(null, null, newTimestamp);
        //  when
        boolean obsoleteReading = newReading.isReadingObsolete();
        //  then
        assertFalse(obsoleteReading);
    }
}
