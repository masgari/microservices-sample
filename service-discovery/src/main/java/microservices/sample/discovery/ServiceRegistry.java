package microservices.sample.discovery;

/**
 * @author mamad
 * @since 17/03/15.
 */
public interface ServiceRegistry {
    <S> void register(PublishedServiceInfo<S> serviceInfo);

    <S> void deRegister(PublishedServiceInfo<S> serviceInfo);

    void shutdown();
}
