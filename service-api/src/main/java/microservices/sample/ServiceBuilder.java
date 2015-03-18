package microservices.sample;

import com.squareup.okhttp.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * @author mamad
 * @since 18/03/15.
 */
public class ServiceBuilder<S> {
    private final Class<S> serviceClass;

    public ServiceBuilder(Class<S> serviceClass) {

        this.serviceClass = serviceClass;
    }

    public static <S> ServiceBuilder<S> create(Class<S> serviceClass) {
        return new ServiceBuilder<>(serviceClass);
    }

    public S build(String ip, int port) {
        return build(String.format("http://%s:%d", ip, port));
    }

    public S build(String url) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .setClient(new OkClient(new OkHttpClient()))
                .build();

        return restAdapter.create(serviceClass);
    }
}
