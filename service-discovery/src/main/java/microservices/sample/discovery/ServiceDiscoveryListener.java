package microservices.sample.discovery;

/**
 * Listener for notifying other services when new services join or leave the cluster.
 *
 * @author mamad
 * @since 17/03/15.
 */
public interface ServiceDiscoveryListener {
    /**
     * This method will be called by ServiceDiscovery when a new service joins the cluster.
     *
     * @param info service information
     */
    <S> void onServiceJoined(PublishedServiceInfo<S> info);

    /**
     * This method will be called by ServiceDiscovery when a service leaves the cluster.
     *
     * @param info service information
     */
    <S> void onServiceLeft(PublishedServiceInfo<S> info);
}
