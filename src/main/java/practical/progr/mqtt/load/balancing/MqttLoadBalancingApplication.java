package practical.progr.mqtt.load.balancing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MqttLoadBalancingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttLoadBalancingApplication.class, args);
    }

}
