package goldDigger.repositories;

import goldDigger.models.spot.Spot;

import java.util.*;

public class SpotRepository implements Repository<Spot> {

    private Map<String, Spot> spots;

    public SpotRepository() {
        spots = new LinkedHashMap<>();
    }

    @Override
    public void add(Spot spot) {
        spots.putIfAbsent(spot.getName(), spot);
    }

    @Override
    public boolean remove(Spot spot) {
        return spots.remove(spot.getName(), spot);
    }

    @Override
    public Spot byName(String spotName) {
        return spots.get(spotName);
    }

    @Override
    public Collection<Spot> getCollection() {
        return Collections.unmodifiableCollection(spots.values());
    }

}
