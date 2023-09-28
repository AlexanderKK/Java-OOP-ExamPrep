package vehicleShop;

import  vehicleShop.core.Controller;
import vehicleShop.core.ControllerImpl;
import vehicleShop.core.Engine;
import vehicleShop.core.EngineImpl;
import vehicleShop.models.vehicle.Vehicle;

public class Main {
    public static void main(String[] args) {
        Engine engine = new EngineImpl();
        engine.run();

//        Controller controller = new ControllerImpl();
//        controller.addVehicle("vehicle1", 30);
//        controller.addWorker("FirstShift", "worker1");
//        controller.addWorker("SecondShift", "worker2");
//        controller.addToolToWorker("worker1", 10);
//        controller.addToolToWorker("worker1", 10);
//        controller.addToolToWorker("worker2", 20);
//
//        controller.makingVehicle("vehicle1");
//        System.out.println(controller.statistics());
    }
}
