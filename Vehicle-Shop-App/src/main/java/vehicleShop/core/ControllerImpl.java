package vehicleShop.core;

import vehicleShop.common.ConstantMessages;
import vehicleShop.common.ExceptionMessages;
import vehicleShop.models.tool.Tool;
import vehicleShop.models.tool.ToolImpl;
import vehicleShop.models.vehicle.Vehicle;
import vehicleShop.models.vehicle.VehicleImpl;
import vehicleShop.models.worker.FirstShift;
import vehicleShop.models.worker.SecondShift;
import vehicleShop.models.worker.Worker;
import vehicleShop.models.worker.WorkerType;
import vehicleShop.repositories.VehicleRepository;
import vehicleShop.repositories.WorkerRepository;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {

    private WorkerRepository workers;
    private VehicleRepository vehicles;

    public ControllerImpl() {
        this.workers = new WorkerRepository();
        this.vehicles = new VehicleRepository();
    }

    @Override
    public String addWorker(String type, String workerName) {
        WorkerType workerType;

        try {
            workerType = WorkerType.valueOf(type);
        } catch (IllegalArgumentException ignored) {
            throw new IllegalArgumentException(ExceptionMessages.WORKER_TYPE_DOESNT_EXIST);
        }

        Worker worker = null;
        switch (workerType) {
            case FirstShift:
                worker = new FirstShift(workerName);
                break;
            case SecondShift:
                worker = new SecondShift(workerName);
                break;
        }

        workers.add(worker);

        return String.format(ConstantMessages.ADDED_WORKER, type, workerName);
    }

    @Override
    public String addVehicle(String vehicleName, int strengthRequired) {
        Vehicle vehicle = new VehicleImpl(vehicleName, strengthRequired);

        vehicles.add(vehicle);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_VEHICLE, vehicleName);
    }

    @Override
    public String addToolToWorker(String workerName, int power) {
        Worker worker = workers.findByName(workerName);
        if(worker == null) {
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
        }

        Tool tool = new ToolImpl(power);
        worker.addTool(tool);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOOL_TO_WORKER, power, workerName);
    }

    @Override
    public String makingVehicle(String vehicleName) {
        final int STRENGTH_REQUIRED = 70;

        ArrayDeque<Worker> workersReady = workers.getWorkers().stream()
                .filter(w -> w.getStrength() > STRENGTH_REQUIRED)
                .collect(Collectors.toCollection(ArrayDeque::new));

        if(workersReady.size() == 0) {
            throw new IllegalArgumentException(ExceptionMessages.NO_WORKER_READY);
        }

        Vehicle vehicle = vehicles.findByName(vehicleName);

        //1. Get first worker
        //2. Get tools of first worker
        //3. Use tools until all are broken
        //4. Until tool is unfit make vehicle and decrease power of tool
        //5. Count broken tools
        //6. Return if vehicle is ready and broken tools count

        int brokenToolsCount = 0;
        boolean isVehicleReady = false;

        while(!areAllToolsBroken(workersReady) && !isVehicleReady) {
            Worker currentWorker = workersReady.peek();

            Collection<Tool> tools = currentWorker.getTools();
            for (Tool tool : tools) {
                while(currentWorker.canWork() && !tool.isUnfit() && !vehicle.reached()) {
                    currentWorker.working();
                    tool.decreasesPower();
                    vehicle.making();
                }

                if(!currentWorker.canWork()) {
                    workersReady.poll();
                }

                if(tool.isUnfit()) {
                    brokenToolsCount++;
                }

                if(vehicle.reached()) {
                    isVehicleReady = true;
                    break;
                }
            }
        }

        String vehicleState = isVehicleReady ? "done" : "not done";

        String result = String.format(ConstantMessages.VEHICLE_DONE, vehicleName, vehicleState) +
                String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS, brokenToolsCount);

        return result;
    }

    private boolean areAllToolsBroken(ArrayDeque<Worker> workersReady) {
        return workersReady
                .stream()
                .allMatch(w -> w.getTools()
                        .stream()
                        .allMatch(Tool::isUnfit));
    }

    @Override
    public String statistics() {
        int countMadeVehicles = (int) vehicles.getWorkers().stream()
                .filter(Vehicle::reached)
                .count();

        StringBuilder sb = new StringBuilder()
                .append(String.format("%d vehicles are ready!", countMadeVehicles))
                .append(System.lineSeparator())
                .append("Info for workers:")
                .append(System.lineSeparator());

        for (Worker worker : workers.getWorkers()) {
            int fitToolsCount = (int) worker.getTools().stream()
                    .filter(t -> !t.isUnfit())
                    .count();

            sb.append(String.format("Name: %s, Strength: %d", worker.getName(), worker.getStrength()))
                    .append(System.lineSeparator())
                    .append(String.format("Tools: %d fit left", fitToolsCount))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

}
