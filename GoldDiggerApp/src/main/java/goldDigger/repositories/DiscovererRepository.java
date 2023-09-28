package goldDigger.repositories;

import goldDigger.models.discoverer.Discoverer;

import java.util.*;

public class DiscovererRepository implements Repository<Discoverer> {

    private Map<String, Discoverer> discoverers;

    public DiscovererRepository() {
        discoverers = new LinkedHashMap<>();
    }

    @Override
    public void add(Discoverer discoverer) {
        discoverers.putIfAbsent(discoverer.getName(), discoverer);
    }

    @Override
    public boolean remove(Discoverer discoverer) {
        return discoverers.remove(discoverer.getName(), discoverer);
    }

    @Override
    public Discoverer byName(String discovererName) {
        return discoverers.get(discovererName);
    }

    @Override
    public Collection<Discoverer> getCollection() {
        return Collections.unmodifiableCollection(discoverers.values());
    }

}
