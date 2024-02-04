package practical.progr.mqtt.load.balancing.job;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationHeartbeatJob {

    private final Mqtt5AsyncClient mqttClient;
    private static int counter = 0;

    public static final String TOPIC_HEARTBEAT = "practical.progr.mqtt.hearthbeat";
    public static final String GROUP_HEARTBEAT = "heartbeat-group";

    @Scheduled(fixedRateString = "${practical.progr.mqtt.heartbeat.rate:1000}")
    public void beat() {
        String heartbeat = getHeartbeatMessage();
        log.info("Application heartbeat {}", heartbeat);
        mqttClient.publish(Mqtt5Publish.builder()
            .topic(TOPIC_HEARTBEAT)
            .qos(MqttQos.AT_LEAST_ONCE)
            .payload(heartbeat.getBytes())
            .build()
        ).join();
        log.debug("Heartbeat sent");
    }

    private String getHeartbeatMessage() {
        return "Counter=" + counter++;
    }
}
