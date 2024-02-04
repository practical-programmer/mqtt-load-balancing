package practical.progr.mqtt.load.balancing.job;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import practical.progr.mqtt.load.balancing.utils.SharedSubscriptionHelper;

import static practical.progr.mqtt.load.balancing.job.ApplicationHeartbeatJob.GROUP_HEARTBEAT;
import static practical.progr.mqtt.load.balancing.job.ApplicationHeartbeatJob.TOPIC_HEARTBEAT;

@Component
@Slf4j
public class HeartbeatListener {

    @Getter
    private int heartbeatsReceived = 0;

    public HeartbeatListener(Mqtt5AsyncClient mqttClient) {
        log.info("Creating heartbeat listener");
        mqttClient.subscribeWith()
            .topicFilter(SharedSubscriptionHelper.toSharedSubscription(TOPIC_HEARTBEAT, GROUP_HEARTBEAT))
            .qos(MqttQos.AT_LEAST_ONCE)
            .callback(this::handleCallback)
            .send();
    }

    private void handleCallback(Mqtt5Publish mqtt5Publish) {
        log.info("{}: {} with payload: {}. Heartbeats received: {}", getListenerIdentifier(),
            mqtt5Publish.toString(), getHeartbeatPayload(mqtt5Publish), ++heartbeatsReceived);
    }

    private String getHeartbeatPayload(Mqtt5Publish mqtt5Publish) {
        return new String(mqtt5Publish.getPayloadAsBytes());
    }

    protected String getListenerIdentifier() {
        return "Heartbeat listener";
    }
}
