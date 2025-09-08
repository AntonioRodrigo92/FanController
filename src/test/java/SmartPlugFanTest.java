import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SmartPlugFanTest {
    private SmartPlugFan fan;

    @BeforeEach
    public void setup() {
        this.fan = new SmartPlugFan(
                "http://hp-i7-master.lan:8123/api/services/switch/",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIyZTZkNDQ0MzlmNzc0NzIwOGY5MTY4ZDhiZTBlMjU1OSIsImlhdCI6MTc1NTQ0MTIzMiwiZXhwIjoyMDcwODAxMjMyfQ.LWvSYvN4qGRR-OSc0hBqObp8Vb3LlWFcZhK8qJL-mUg",
                "switch.chuangmi_hmi206_dd01_switch_2"
        );
    }

    @DisplayName("turnOn")
    @Test
    public void turnOnTest() {
        fan.turnOn();
    }

    @DisplayName("turnOff")
    @Test
    public void turnOffTest() {
        fan.turnOff();
    }
}
