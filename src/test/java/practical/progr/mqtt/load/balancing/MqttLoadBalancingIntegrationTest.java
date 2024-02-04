package practical.progr.mqtt.load.balancing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practical.progr.mqtt.load.balancing.job.AnotherHeartbeatListener;
import practical.progr.mqtt.load.balancing.job.HeartbeatListener;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class MqttLoadBalancingIntegrationTest {

    @Autowired
    HeartbeatListener heartbeatListener;

    @Autowired
    AnotherHeartbeatListener anotherHeartbeatListener;

    /**
     * Waits until both listeners receive at least one heartbeat from heartbeat job
     * Then waits until one of the listeners has different events received than other
     * to ensure that events are not received by both at the same time
     */
    @Test
    void ensureHeartbeatsAreLoadBalancedAcrossListeners() {
        await().until(() -> heartbeatListener.getHeartbeatsReceived() > 0);
        await().until(() -> anotherHeartbeatListener.getHeartbeatsReceived() > 0);
        await().until(() -> anotherHeartbeatListener.getHeartbeatsReceived() != heartbeatListener.getHeartbeatsReceived());
    }
}
