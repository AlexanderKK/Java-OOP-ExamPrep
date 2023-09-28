package goldDigger.models.operation;

import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.spot.Spot;

import java.util.Collection;

public class OperationImpl implements Operation {

    @Override
    public void startOperation(Spot spot, Collection<Discoverer> discoverers) {
        Collection<String> spotExhibits = spot.getExhibits();

        for (Discoverer discoverer : discoverers) {
            while(discoverer.canDig() && spotExhibits.iterator().hasNext()) {
                //Decrease strength of discoverer
                discoverer.dig();

                //Get exhibit
                String exhibit = spotExhibits.iterator().next();

                //Add exhibit to discoverer
                discoverer.getMuseum().getExhibits().add(exhibit);

                //Remove exhibit
                spotExhibits.remove(exhibit);
            }

            if(spotExhibits.isEmpty()) {
                break;
            }
        }
    }

}
