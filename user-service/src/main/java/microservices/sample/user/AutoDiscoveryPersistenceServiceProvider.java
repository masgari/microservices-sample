package microservices.sample.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import microservices.sample.discovery.PublishedServiceInfo;
import microservices.sample.discovery.ServiceDiscoveryListener;
import microservices.sample.persistence.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit.RestAdapter;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * PersistenceService provider using Retrofit.
 * <p>
 * This class listens to ServiceDiscovery events and keeps track of available services of type PersistenceService.
 *
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class AutoDiscoveryPersistenceServiceProvider implements PersistenceServiceProvider, ServiceDiscoveryListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoDiscoveryPersistenceServiceProvider.class);

    private static final CacheLoader<String, PersistenceService> CACHE_LOADER =
            new CacheLoader<String, PersistenceService>() {
                @Override
                @SuppressWarnings("unchecked")
                public PersistenceService load(String url) throws Exception {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(url)
                            .build();
                    return restAdapter.create(PersistenceService.class);
                }
            };
    private final Set<PublishedServiceInfo<PersistenceService>> availableServices = Sets.newConcurrentHashSet();
    private final String persistenceServiceVersion;

    private Cache<String, PersistenceService> serviceCache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(CACHE_LOADER);

    public AutoDiscoveryPersistenceServiceProvider(String persistenceServiceVersion) {
        this.persistenceServiceVersion = persistenceServiceVersion;
    }

    @Override

    public PersistenceService get() {
        Optional<PublishedServiceInfo<PersistenceService>> optional = availableServices
                .stream()
                .filter(info -> Objects.equals(info.getVersion(), persistenceServiceVersion))
                .findAny();
        if (!optional.isPresent()) {
            throw new RuntimeException("No service available");
        }
        PublishedServiceInfo<PersistenceService> info = optional.get();
        String url = String.format("http://%s:%d", info.getIp(), info.getPort());
        try {
            return serviceCache.get(url, () -> CACHE_LOADER.load(url));
        } catch (ExecutionException e) {
            LOGGER.error("Error in creating persistence service for url:{}", url, e);
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S> void onServiceJoined(PublishedServiceInfo<S> info) {
        if (PersistenceService.class.equals(info.getServiceClass())) {
            availableServices.add((PublishedServiceInfo<PersistenceService>) info);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S> void onServiceLeft(PublishedServiceInfo<S> info) {
        if (PersistenceService.class.equals(info.getServiceClass())) {
            availableServices.remove(info);
        }
    }
}
