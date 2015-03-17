package hazelcast;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MapEvent;

/**
 * @author mamad
 * @since 16/03/15.
 */
public class MapEntryListener implements EntryListener {
    @Override
    public void entryAdded(EntryEvent event) {
        System.out.printf("entry added  - Name:%s, Key:%s, Value:%s (old:%s), Member:%s%n", event.getName(), event.getKey(),
                event.getValue(), event.getOldValue(), event.getMember());
    }

    @Override
    public void entryRemoved(EntryEvent event) {

    }

    @Override
    public void entryUpdated(EntryEvent event) {

    }

    @Override
    public void entryEvicted(EntryEvent event) {

    }

    @Override
    public void mapEvicted(MapEvent event) {
        System.out.printf("map evicted - Name:%s, Source:%s, Member:%s%n", event.getName(), event.getSource(), event.getMember());
    }

    @Override
    public void mapCleared(MapEvent event) {

    }
}
