package practical.progr.mqtt.load.balancing.utils;

public class SharedSubscriptionHelper {

    public static final String SHARE = "$share";

    /**
     * Constructs a topic subscription which is shared based on given group id. See documentation here:
     * <a href="https://docs.hivemq.com/hivemq/latest/user-guide/shared-subscriptions.html">...</a>
     * @param topic - name of a topic, can contain wildcards
     * @param groupId - name of subscription group
     * @return concatenated string which can be used to subscribe to topic in given subscription group
     */
    public static String toSharedSubscription(String topic, String groupId) {
        return String.join("/", SHARE, groupId, topic);
    }
}
