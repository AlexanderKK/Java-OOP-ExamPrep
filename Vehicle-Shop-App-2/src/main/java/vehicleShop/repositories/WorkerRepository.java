package vehicleShop.repositories;

import vehicleShop.models.worker.Worker;

import java.util.*;

public class WorkerRepository implements Repository<Worker> {

    private Map<String, Worker> workers;

    public WorkerRepository() {
        this.workers = new LinkedHashMap<>();
    }

    @Override
    public Collection<Worker> getWorkers() {
        return Collections.unmodifiableCollection(workers.values());
    }

    @Override
    public void add(Worker worker) {
        workers.putIfAbsent(worker.getName(), worker);
    }

    @Override
    public boolean remove(Worker worker) {
        return workers.remove(worker.getName(), worker);
    }

    @Override
    public Worker findByName(String name) {
        return workers.get(name);
    }

}
