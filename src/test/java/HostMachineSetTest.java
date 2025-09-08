import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class HostMachineSetTest {
    private HostMachineSet machines;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void setup() throws IOException {
        Config config = new Config("C:\\Users\\Antonio\\IdeaProjects\\FanController\\src\\test\\resources\\application.conf");
        this.machines = new HostMachineSet(config.getMachines());
    }

    @DisplayName("Test 1-2-3")
    @Test
    public void testSingleSuccessTest() {
        assertTrue(true);
    }

    @DisplayName("updateReadingMap - SUCCESS")
    @Test
    public void updateReadingMapTest_SUCCESS() {
        //  given
        String hostname = "gmk-n100-worker";
        String temperature = "65";
        String timestamp = LocalDateTime.now().format(formatter);
        String jsonString = "{" +
                    "\"hostname\":\"" + hostname + "\"," +
                    "\"temperature\":\"" + temperature + "\"," +
                    "\"timestamp\":\"" + timestamp + "\"" +
                "}";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        //  when
        machines.updateReadingMap(jsonObject);
        //  then
        assertAll("reading updated",
                () -> assertEquals(hostname, machines.getMachineSet().get(hostname).getHostname()),
                () -> assertEquals(Double.parseDouble(temperature), machines.getMachineSet().get(hostname).getReading().getTemperature()),
                () -> assertEquals(timestamp,
                        machines.getMachineSet().get(hostname).getReading().getTimestamp().format(formatter)
                )
        );
    }

    @DisplayName("updateReadingMap - FAILURE")
    @Test
    public void updateReadingMapTest_FAILURE() {
        //  given
        String hostname = "gmk-n100-worker";
        String temperature1 = "65";
        String timestamp1 = LocalDateTime.parse("2025-09-01 00:00:30", formatter).format(formatter);
        String jsonString1 = "{" +
                "\"hostname\":\"" + hostname + "\"," +
                "\"temperature\":\"" + temperature1 + "\"," +
                "\"timestamp\":\"" + timestamp1 + "\"" +
                "}";
        JsonObject jsonObject1 = JsonParser.parseString(jsonString1).getAsJsonObject();
        String temperature2 = "70";
        String timestamp2 = LocalDateTime.parse("2025-09-01 00:01:30", formatter).format(formatter);
        String jsonString2 = "{" +
                "\"hostname\":\"" + hostname + "\"," +
                "\"temperature\":\"" + temperature2 + "\"," +
                "\"timestamp\":\"" + timestamp2 + "\"" +
                "}";
        JsonObject jsonObject2 = JsonParser.parseString(jsonString2).getAsJsonObject();
        //  when
        machines.updateReadingMap(jsonObject1);
        machines.updateReadingMap(jsonObject2);
        //  then
        assertAll("reading updated",
                () -> assertEquals(hostname, machines.getMachineSet().get(hostname).getHostname()),
                () -> assertNotEquals(Double.parseDouble(temperature1), machines.getMachineSet().get(hostname).getReading().getTemperature()),
                () -> assertNotEquals(timestamp1,
                        machines.getMachineSet().get(hostname).getReading().getTimestamp().format(formatter)
                ),

                () -> assertEquals(hostname, machines.getMachineSet().get(hostname).getHostname()),
                () -> assertEquals(Double.parseDouble(temperature2), machines.getMachineSet().get(hostname).getReading().getTemperature()),
                () -> assertEquals(timestamp2,
                        machines.getMachineSet().get(hostname).getReading().getTimestamp().format(formatter)
                )
        );
    }

    //  triggerFanOn - is enabled + 1 maquina temp > triggerOn -> true
    //  triggerFanOn - is enabled + 1 maquina temp < triggerOn -> false
    //  triggerFanOn - is disabled + 1 maquina temp > triggerOn -> false
    //  triggerFanOn - is enabled + 1 maquina temp > triggerOn + readingIsObsolete -> false



}
