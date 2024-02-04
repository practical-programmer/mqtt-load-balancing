package practical.progr.mqtt.load.balancing.configuration;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.embedded.EmbeddedHiveMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures and connects two MQTT Clients on different client ids.
 * Two clients are used to demo how load balancing works. It can be disabled and started as two instances too
 */
@Slf4j
@Configuration
public class MqttClientConfiguration {

    // forcing order of configurations - broker should be created before client
    public MqttClientConfiguration(@Autowired(required = false) EmbeddedHiveMQ embeddedHiveMQ) {}

    @Bean
    public Mqtt5AsyncClient mqttClient(@Value("${practical.progr.mqtt.broker.uri:tcp://localhost:1883}") String mqttBrokerUri,
                                       @Value("${practical.progr.mqtt.client.id:practical-progr-demo}") String mqttClientId) {
        return connectToBroker(mqttBrokerUri, mqttClientId);
    }

    @Bean
    @ConditionalOnProperty(name = "practical.progr.mqtt.use.alternative.client", havingValue = "true", matchIfMissing = true)
    public Mqtt5AsyncClient alternativeMqttClient(@Value("${practical.progr.mqtt.broker.uri:tcp://localhost:1883}") String mqttBrokerUri,
                                            @Value("${practical.progr.mqtt.alternative.client.id:practical-progr-demo2}") String mqttClientId) {
        return connectToBroker(mqttBrokerUri, mqttClientId);
    }

    private Mqtt5AsyncClient connectToBroker(String mqttBrokerUri, String mqttClientId) {
        log.info("Connecting to MQTT Broker at URI: {} with client id: {}", mqttBrokerUri, mqttClientId);
        Mqtt5Client mqttClient = MqttClient.builder()
                .identifier(mqttClientId)
                .useMqttVersion5()
                .build();
        Mqtt5AsyncClient mqtt5AsyncClient = mqttClient.toAsync();
        mqtt5AsyncClient.connect();
        log.info("Connected");
        return mqtt5AsyncClient;
    }
}
