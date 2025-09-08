import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;


public class MosquittoInstance {
    private MqttClient client;
    private String topic;
    private Queue<String> queue;

    public MosquittoInstance(String broker, String username, String password, String topic,
                             String clientid, ConcurrentLinkedDeque<String> queue) throws MqttException {
        this.client = new MqttClient(broker, clientid, new MemoryPersistence());
        this.topic = topic;
        this.queue = queue;
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(60);
        options.setKeepAliveInterval(60);
        client.connect(options);
    }

    public void startWaitForMessage() throws MqttException {
        client.subscribe(topic, (topic, msg) -> {
            queue.offer(msg.toString());
        });
    }

}
