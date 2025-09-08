import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


public class HostMachineTest {
    private HostMachine hostMachine;

    @BeforeEach
    public void setup() {
        String hostname = "gmk-n100-worker";
        boolean enabled = true;
        Double triggerOnTemp = 70.0;
        Double triggerOffTemp = 60.0;
        hostMachine = new HostMachine(hostname, enabled, triggerOnTemp, triggerOffTemp);
    }

    @DisplayName("Test 1-2-3")
    @Test
    public void testSingleSuccessTest() {
        assertTrue(true);
    }

    @DisplayName("Test getHostname - True")
    @Test
    public void getHostnameTest() {
        //  given
        String expectedHostname = "gmk-n100-worker";
        //  when
        String hostname = hostMachine.getHostname();
        //  then
        assertEquals(expectedHostname, hostname);
    }

    @DisplayName("Test isEnabled - True")
    @Test
    public void isEnabledTest() {
        //  given
        boolean expectedIsEnabled = true;
        //  when
        boolean isEnabled = hostMachine.isEnabled();
        //  then
        assertEquals(expectedIsEnabled, isEnabled);
    }

    @DisplayName("Test getTriggerOnTemp - True")
    @Test
    public void getTriggerOnTempTest() {
        //  given
        Double expectedTriggerOnTemp = 70.0;
        //  when
        Double triggerOnTemp = hostMachine.getTriggerOnTemp();
        //  then
        assertEquals(expectedTriggerOnTemp, triggerOnTemp);
    }

    @DisplayName("Test getTriggerOffTemp - True")
    @Test
    public void getTriggerOffTempTest() {
        //  given
        Double expectedTriggerOffTemp = 60.0;
        //  when
        Double triggerOffTemp = hostMachine.getTriggerOffTemp();
        //  then
        assertEquals(expectedTriggerOffTemp, triggerOffTemp);
    }

    @DisplayName("Test getReading - is Not Null")
    @Test
    public void getReadingTest() {
        //  given

        //  when
        Reading reading = hostMachine.getReading();
        //  then
        assertAll("first reading",
                () -> assertNotNull(reading),
                () -> assertEquals(reading.getHostname(), "gmk-n100-worker"),
                () -> assertEquals(reading.getTemperature(), Double.NEGATIVE_INFINITY),
                () -> assertEquals(reading.getTimestamp(), LocalDateTime.MIN)
        );
    }

    @DisplayName("updateReadingTest - old Reading is before")
    @Test
    public void updateReadingTest1() {
        //  given
        String jsonString = "{\"hostname\":\"gmk-n100-worker\",\"temperature\":\"65\",\"timestamp\":\"2025-09-04 23:43:30\"}";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //  when
        hostMachine.updateReading(jsonObject);
        //  then
        assertAll("updatedReading",
                () -> assertEquals(hostMachine.getReading().getHostname(), "gmk-n100-worker"),
                () -> assertEquals(hostMachine.getReading().getTemperature(), 65),
                () -> assertEquals(hostMachine.getReading().getTimestamp(), LocalDateTime.parse("2025-09-04 23:43:30", formatter))
        );
    }

    @DisplayName("updateReadingTest - old Reading is before - 2 inserts")
    @Test
    public void updateReadingTest2() {
        //  given
        String jsonString = "{\"hostname\":\"gmk-n100-worker\",\"temperature\":\"65\",\"timestamp\":\"2025-09-04 23:43:30\"}";
        String jsonString2 = "{\"hostname\":\"gmk-n100-worker\",\"temperature\":\"66\",\"timestamp\":\"2025-09-04 23:43:31\"}";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject jsonObject2 = JsonParser.parseString(jsonString2).getAsJsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //  when
        hostMachine.updateReading(jsonObject);
        hostMachine.updateReading(jsonObject2);
        //  then
        assertAll("updatedReading",
                () -> assertEquals(hostMachine.getReading().getHostname(), "gmk-n100-worker"),
                () -> assertEquals(hostMachine.getReading().getTemperature(), 66),
                () -> assertEquals(hostMachine.getReading().getTimestamp(), LocalDateTime.parse("2025-09-04 23:43:31", formatter))
        );
    }

    @DisplayName("updateReadingTest - old Reading is after - 2 inserts")
    @Test
    public void updateReadingTest3() {
        //  given
        String jsonString = "{\"hostname\":\"gmk-n100-worker\",\"temperature\":\"65\",\"timestamp\":\"2025-09-04 23:43:30\"}";
        String jsonString2 = "{\"hostname\":\"gmk-n100-worker\",\"temperature\":\"66\",\"timestamp\":\"2025-09-04 23:43:25\"}";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject jsonObject2 = JsonParser.parseString(jsonString2).getAsJsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //  when
        hostMachine.updateReading(jsonObject);
        hostMachine.updateReading(jsonObject2);
        //  then
        assertAll("updatedReading",
                () -> assertEquals(hostMachine.getReading().getHostname(), "gmk-n100-worker"),
                () -> assertEquals(hostMachine.getReading().getTemperature(), 65),
                () -> assertEquals(hostMachine.getReading().getTimestamp(), LocalDateTime.parse("2025-09-04 23:43:30", formatter))
        );
    }
}
