package vehicleShop.core;

import vehicleShop.common.ConstantMessages;
import vehicleShop.common.ExceptionMessages;
import vehicleShop.models.shop.Shop;
import vehicleShop.models.shop.ShopImpl;
import vehicleShop.models.tool.Tool;
import vehicleShop.models.tool.ToolImpl;
import vehicleShop.models.vehicle.Vehicle;
import vehicleShop.models.vehicle.VehicleImpl;
import vehicleShop.models.worker.FirstShift;
import vehicleShop.models.worker.SecondShift;
import vehicleShop.models.worker.Worker;
import vehicleShop.repositories.Repository;
import vehicleShop.repositories.VehicleRepository;
import vehicleShop.repositories.WorkerRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static vehicleShop.common.ExceptionMessages.*;
import static vehicleShop.common.ConstantMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Worker> workerRepository;
    private Repository<Vehicle> vehicleRepository;

    public ControllerImpl() {
        this.workerRepository = new WorkerRepository();
        this.vehicleRepository = new VehicleRepository();
    }

    @Override
    public String addWorker(String type, String workerName) {
        Worker worker;

        switch(type) {
            case "FirstShift":
                worker = new FirstShift(workerName);
                break;
            case "SecondShift":
                worker = new SecondShift(workerName);
                break;
            default:
                throw new IllegalArgumentException(WORKER_TYPE_DOESNT_EXIST);
        }

        workerRepository.add(worker);

        return String.format(ADDED_WORKER, type, workerName);
    }

    @Override
    public String addVehicle(String vehicleName, int strengthRequired) {
        Vehicle vehicle = new VehicleImpl(vehicleName, strengthRequired);

        vehicleRepository.add(vehicle);

        return String.format(SUCCESSFULLY_ADDED_VEHICLE, vehicleName);
    }

    @Override
    public String addToolToWorker(String workerName, int power) {
        Worker worker = workerRepository.findByName(workerName);

        if(worker == null) {
            throw new IllegalArgumentException(HELPER_DOESNT_EXIST);
        }

        Tool tool = new ToolImpl(power);
        worker.addTool(tool);

        return String.format(SUCCESSFULLY_ADDED_TOOL_TO_WORKER, power, workerName);
    }

    @Override
    public String makingVehicle(String vehicleName) {
        Vehicle vehicle = vehicleRepository.findByName(vehicleName);

        List<Worker> workersReady = workerRepository.getWorkers()
                .stream()
                .filter(w -> w.getStrength() > 70)
                .collect(Collectors.toList());

        if(workersReady.isEmpty()) {
            throw new IllegalArgumentException(NO_WORKER_READY);
        }

        Shop shop = new ShopImpl();

        for (Worker worker : workersReady) {
            shop.make(vehicle, worker);
        }

        long brokenTools = 0L;
        for (Worker worker : workersReady) {
            brokenTools += worker.getTools().stream().filter(Tool::isUnfit).count();
        }

//        long brokenTools = workersReady
//                .stream()
//                .mapToLong(w -> w.getTools()
//                        .stream()
//                        .filter(Tool::isUnfit)
//                        .count()
//                ).sum();

        String vehicleState = vehicle.reached() ? "done" : "not done";

        String message = String.format(VEHICLE_DONE, vehicleName, vehicleState)
                + String.format(COUNT_BROKEN_INSTRUMENTS, brokenTools);

        return message;
    }

    @Override
    public String statistics() {
        StringBuilder sb = new StringBuilder();

        long vehiclesMade = vehicleRepository.getWorkers().stream().filter(Vehicle::reached).count();

        sb.append(String.format("%d vehicles are ready!",vehiclesMade )).append(System.lineSeparator());
        sb.append("Info for workers:").append(System.lineSeparator());

        Collection<Worker> workers = workerRepository.getWorkers();
        for (Worker worker : workers) {
            sb.append(String.format("Name: %s, Strength: %d", worker.getName(), worker.getStrength()))
                    .append(System.lineSeparator());

            int toolsFit = worker.getTools().stream().filter(t -> !t.isUnfit()).mapToInt(t -> 1).sum();

            sb.append(String.format("Tools: %d fit left", toolsFit)).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

}
