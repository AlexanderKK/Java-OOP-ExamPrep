package goldDigger.core;

import goldDigger.common.ConstantMessages;
import goldDigger.common.ExceptionMessages;
import goldDigger.models.discoverer.*;
import goldDigger.models.operation.Operation;
import goldDigger.models.operation.OperationImpl;
import goldDigger.models.spot.Spot;
import goldDigger.models.spot.SpotImpl;
import goldDigger.repositories.DiscovererRepository;
import goldDigger.repositories.SpotRepository;

import java.util.*;
import java.util.stream.Collectors;

import static goldDigger.common.ConstantMessages.*;

public class ControllerImpl implements Controller{

    private DiscovererRepository discovererRepository;
    private SpotRepository spotRepository;
    private int inspectedSpots;

    public ControllerImpl() {
        this.discovererRepository = new DiscovererRepository();
        this.spotRepository = new SpotRepository();
    }

    @Override
    public String addDiscoverer(String kind, String discovererName) {
        DiscovererKind discovererKind;

        try {
            discovererKind = DiscovererKind.valueOf(kind);
        } catch (IllegalArgumentException ignored) {
            throw new IllegalArgumentException(ExceptionMessages.DISCOVERER_INVALID_KIND);
        }

        Discoverer discoverer = DiscovererFactory.createDiscoverer(discovererKind, discovererName);
        discovererRepository.add(discoverer);

        return String.format(ConstantMessages.DISCOVERER_ADDED, kind, discovererName);
    }

    @Override
    public String addSpot(String spotName, String... exhibits) {
        Spot spot = new SpotImpl(spotName);

        for (String exhibit : exhibits) {
            spot.getExhibits().add(exhibit);
        }

        spotRepository.add(spot);

        return String.format(ConstantMessages.SPOT_ADDED, spotName);
    }

    @Override
    public String excludeDiscoverer(String discovererName) {
        Discoverer discoverer = discovererRepository.byName(discovererName);
        if(discoverer == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DISCOVERER_DOES_NOT_EXIST, discovererName));
        }

        discovererRepository.remove(discoverer);

        return String.format(ConstantMessages.DISCOVERER_EXCLUDE, discovererName);
    }

    @Override
    public String inspectSpot(String spotName) {
        List<Discoverer> suitableDiscoverers = discovererRepository.getCollection().stream()
                .filter(d -> d.getEnergy() > 45)
                .collect(Collectors.toList());

        if(suitableDiscoverers.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.SPOT_DISCOVERERS_DOES_NOT_EXISTS);
        }

        Operation operation = new OperationImpl();

        Spot spot = spotRepository.byName(spotName);

        //Start collecting exhibitions
        operation.startOperation(spot, suitableDiscoverers);

        //Get amount of discoverers that cannot dig anymore
        int excludedDiscoverersCount = suitableDiscoverers.stream()
                .filter(d -> !d.canDig())
                .mapToInt(d -> 1)
                .sum();

        inspectedSpots++;

        return String.format(ConstantMessages.INSPECT_SPOT, spotName, excludedDiscoverersCount);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(FINAL_SPOT_INSPECT, inspectedSpots)).append(System.lineSeparator());
        sb.append(FINAL_DISCOVERER_INFO).append(System.lineSeparator());

        for (Discoverer discoverer : discovererRepository.getCollection()) {
            sb.append(String.format(FINAL_DISCOVERER_NAME, discoverer.getName())).append(System.lineSeparator());
            sb.append(String.format(FINAL_DISCOVERER_ENERGY, discoverer.getEnergy())).append(System.lineSeparator());

            Collection<String> discovererExhibits = discoverer.getMuseum().getExhibits();
            String exhibitsData = discovererExhibits.isEmpty()
                    ? "None"
                    : String.join(", ", discovererExhibits);

            sb.append(String.format(FINAL_DISCOVERER_MUSEUM_EXHIBITS, exhibitsData)).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

}
