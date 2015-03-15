package microservices.sample.discovery;

import java.util.Objects;

/**
 * Information about published service.
 *
 * @param S service class
 * @author mamad
 * @since 15/03/15.
 */
public class PublishedServiceInfo<S> {
    private final String version;
    private final int port;
    private final String ip;
    private final Class<S> serviceClass;

    public PublishedServiceInfo(Class<S> serviceClass, String ip, int port, String version) {
        this.serviceClass = serviceClass;
        this.ip = ip;
        this.port = port;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public Class<S> getServiceClass() {
        return serviceClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublishedServiceInfo)) {
            return false;
        }

        PublishedServiceInfo that = (PublishedServiceInfo) o;
        return Objects.equals(this.ip, that.ip) && Objects.equals(this.port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ip, this.port);
    }
}
