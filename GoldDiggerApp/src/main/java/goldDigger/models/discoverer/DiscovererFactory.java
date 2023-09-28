package goldDigger.models.discoverer;

import goldDigger.common.ExceptionMessages;

public class DiscovererFactory {

    public static Discoverer createDiscoverer(DiscovererKind discovererKind, String discovererName) {
        Discoverer discoverer = null;

        if (discovererKind == DiscovererKind.Anthropologist) {
            discoverer = new Anthropologist(discovererName);
        } else if (discovererKind == DiscovererKind.Archaeologist) {
            discoverer = new Archaeologist(discovererName);
        } else if (discovererKind == DiscovererKind.Geologist) {
            discoverer = new Geologist(discovererName);
        }

        return discoverer;
    }

}
