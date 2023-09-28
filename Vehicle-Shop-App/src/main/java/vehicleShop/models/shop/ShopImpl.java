package vehicleShop.models.shop;

import vehicleShop.models.tool.Tool;
import vehicleShop.models.vehicle.Vehicle;
import vehicleShop.models.worker.Worker;

import java.util.Collection;

public class ShopImpl implements Shop {

    @Override
    public void make(Vehicle vehicle, Worker worker) {

        Collection<Tool> workerTools = worker.getTools();

        while(worker.canWork() && workerTools.size() > 0 && vehicle.reached()) {
            vehicle.making();
            worker.working();

            worker.getTools().stream()
                    .filter(t -> !t.isUnfit())
                    .findFirst()
                    .ifPresent(Tool::decreasesPower);
        }
    }

}
