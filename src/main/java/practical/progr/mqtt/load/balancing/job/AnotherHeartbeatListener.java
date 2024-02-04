package practical.progr.mqtt.load.balancing.job;

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "practical.progr.mqtt.use.alternative.client", havingValue = "true", matchIfMissing = true)
public class AnotherHeartbeatListener extends HeartbeatListener {

    public AnotherHeartbeatListener(Mqtt5AsyncClient alternativeMqttClient) {
        super(alternativeMqttClient);
    }

    @Override
    protected String getListenerIdentifier() {
        return "Another Heartbeat listener";
    }
}
