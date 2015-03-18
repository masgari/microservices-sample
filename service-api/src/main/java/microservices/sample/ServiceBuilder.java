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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(String.format("http://%s:%d", ip, port))
                .setClient(new OkClient(new OkHttpClient()))
                .build();

        return restAdapter.create(serviceClass);
    }
}
