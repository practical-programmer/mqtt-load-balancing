package practical.progr.mqtt.load.balancing.configuration;

import com.hivemq.embedded.EmbeddedHiveMQ;
import com.hivemq.embedded.EmbeddedHiveMQBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

/**
 * Configures Embedded, in-memory MQTT Broker based on HiveMQ library. Can be disabled to connect to external broker
 * with `practical.progr.mqtt.use.embedded.broker=false`
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "practical.progr.mqtt.use.embedded.broker", havingValue = "true", matchIfMissing = true)
public class EmbeddedMqttBrokerConfiguration {

    @Bean
    public EmbeddedHiveMQ embeddedMqttBroker() {
        log.info("Creating MQTT Embedded broker");
        final EmbeddedHiveMQBuilder builder = EmbeddedHiveMQ.builder();
        EmbeddedHiveMQ embeddedHiveMQ = builder
            .withConfigurationFolder(Path.of("src/main/resources/mqtt-config-not-existing")) // this config will not work until jakarta is used by hivemq for jaxb
            .withDataFolder(Path.of("build"))
            .build();

        embeddedHiveMQ.start().join(); //join thread to wait for embedded broker initialization
        log.info("MQTT Embedded broker started");
        return embeddedHiveMQ;
    }
}
