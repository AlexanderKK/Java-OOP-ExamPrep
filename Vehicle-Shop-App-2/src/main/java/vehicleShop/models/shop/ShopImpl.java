package vehicleShop.models.shop;

import vehicleShop.models.tool.Tool;
import vehicleShop.models.vehicle.Vehicle;
import vehicleShop.models.worker.Worker;

import java.util.Collection;

public class ShopImpl implements Shop {

    @Override
    public void make(Vehicle vehicle, Worker worker) {
        //1. Check if worker has strength or fit tool
        //2. Worker starts making vehicle
        //3. Work on the vehicle until:
        // - vehicle is done or
        // - worker has strength or
        // - worker has tools to use
        //
        //4. if tools power reaches 0 or less -> it is broken
        //5 if tool is broken -> take next tool if any are left

        boolean done = false;
        while(!vehicle.reached() && worker.canWork() && hasFitTools(worker)) {
            Collection<Tool> tools = worker.getTools();

            for (Tool tool : tools) {
                while(!tool.isUnfit()) {
                    tool.decreasesPower();
                    worker.working();
                    vehicle.making();

                    if(vehicle.reached() || !worker.canWork()) {
                        done = true;
                        break;
                    }
                }

                if(done) {
                    break;
                }
            }
        }
    }

    private boolean hasFitTools(Worker worker) {
        return worker.getTools()
                .stream()
                .anyMatch(t -> !t.isUnfit());
    }

}
