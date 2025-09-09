import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;


public class FanController {

    public static void main(String[] args) {

        try {
            Config config = new Config(args[0]);
            HostMachineSet machines = new HostMachineSet(config.getMachines());
            ControlFan controlFan = new SmartPlugFan(config.getSmartPlugURL(), config.getToken(), config.getEntityId());
            ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();
            MosquittoInstance mosquitto = new MosquittoInstance(config.getMqttBroker(), config.getMqttUsername(), config.getMqttPassword(), config.getMqttTopic(), "", queue);
            mosquitto.startWaitForMessage();

            while (true) {
                while (! queue.isEmpty()) {
                    String message = queue.poll();
//                    System.out.println(message);
                    JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
                    machines.updateReadingMap(jsonObject);
                }

                if (machines.triggerFanOn()) {
                    controlFan.turnOn();
                }

                if (machines.triggerFanOff()) {
                    controlFan.turnOff();
                }

                Thread.sleep(config.getSleepTime() * 1000);
//                System.out.println("####################################################################################");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
