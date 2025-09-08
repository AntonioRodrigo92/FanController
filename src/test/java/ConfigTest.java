import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {
    private Config config;

    @DisplayName("Test 1-2-3")
    @Test
    public void testSingleSuccessTest() {
        assertTrue(true);
    }

    @BeforeEach
    public void setup() throws IOException {
        String configPath = "C:\\Users\\Antonio\\IdeaProjects\\FanController\\src\\test\\resources\\application.conf";
        this.config = new Config(configPath);
    }

    @DisplayName("throwIOExceptionTest")
    @Test
    public void throwIOExceptionTest() {
        //  given
        String wrongConfigPath = "C:\\Users\\Antonio\\IdeaProjects\\FanController\\src\\test\\resources\\application.conf2";
        //  when

        //  then
        Exception ex = assertThrows(
                IOException.class,
                () -> new Config(wrongConfigPath)
        );
    }

    @DisplayName("getSmartPlugURLTest")
    @Test
    public void getSmartPlugURLTest() {
        //  given
        String expectedURL = "http://ueueueueue.lan:8123/api/services/switch/";
        //  when
        String url = config.getSmartPlugURL();
        //  then
        assertEquals(expectedURL, url);
    }

    @DisplayName("getTokenTest")
    @Test
    public void getTokenTest() {
        //  given
        String expectedToken = "tokenzinho";
        //  when
        String token = config.getToken();
        //  then
        assertEquals(expectedToken, token);
    }

    @DisplayName("getEntityIdTest")
    @Test
    public void getEntityIdTest() {
        //  given
        String expectedEntityId = "entitinhas";
        //  when
        String entityId = config.getEntityId();
        //  then
        assertEquals(expectedEntityId, entityId);
    }

    @DisplayName("getMqttBrokerTest")
    @Test
    public void getMqttBrokerTest() {
        //  given
        String expectedMqttBroker = "tcp://broker.lan:1883";
        //  when
        String mqttBroker = config.getMqttBroker();
        //  then
        assertEquals(expectedMqttBroker, mqttBroker);
    }

    @DisplayName("getMqttUsernameTest")
    @Test
    public void getMqttUsernameTest() {
        //  given
        String expectedMqttUser = "RinchoaStress";
        //  when
        String mqttUser = config.getMqttUsername();
        //  then
        assertEquals(expectedMqttUser, mqttUser);
    }

    @DisplayName("getMqttPasswordTest")
    @Test
    public void getMqttPasswordTest() {
        //  given
        String expectedMqttPass = "JuroPelaMinhaMorte,Joca";
        //  when
        String mqttPass = config.getMqttPassword();
        //  then
        assertEquals(expectedMqttPass, mqttPass);
    }

    @DisplayName("getMqttTopicTest")
    @Test
    public void getMqttTopicTest() {
        //  given
        String expectedMqttTopic = "test_topic";
        //  when
        String mqttTopic = config.getMqttTopic();
        //  then
        assertEquals(expectedMqttTopic, mqttTopic);
    }

    @DisplayName("getMachinesTest")
    @Test
    public void getMachinesTest() {
        //  given
        int expectedMachineCount = 2;
        //  when
        ArrayList<HostMachine> machines = (ArrayList<HostMachine>) config.getMachines();
        //  then
        assertAll("machines and stuff",
                () -> assertEquals(ArrayList.class, machines.getClass()),
                () -> assertEquals(machines.size(), expectedMachineCount)
        );
    }

    @DisplayName("getSleepTimeTest")
    @Test
    public void getSleepTimeTest() {
        //  given
        long expectedSleepTime = 60;
        //  when
        long sleepTime = config.getSleepTime();
        //  then
        assertEquals(expectedSleepTime, sleepTime);
    }

}
