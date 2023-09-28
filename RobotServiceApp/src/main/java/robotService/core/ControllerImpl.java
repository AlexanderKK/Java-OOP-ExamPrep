package robotService.core;

import robotService.common.*;
import robotService.entities.robot.*;
import robotService.entities.services.*;
import robotService.entities.supplements.*;
import robotService.repositories.*;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {

    private SupplementRepository supplements;
    private Collection<Service> services;

    public ControllerImpl() {
        this.supplements = new SupplementRepository();
        this.services = new ArrayList<>();
    }

    @Override
    public String addService(String type, String name) {
        ServiceType serviceType;

        try {
            serviceType = ServiceType.valueOf(type);
        } catch(IllegalArgumentException ignored) {
            throw new NullPointerException(ExceptionMessages.INVALID_SERVICE_TYPE);
        }

        Service service = null;
        switch(serviceType) {
            case MainService:
                service = new MainService(name);
                break;
            case SecondaryService:
                service = new SecondaryService(name);
                break;
        }

        boolean isServicePresent = getServiceByName(name) != null;
        if(!isServicePresent) {
            services.add(service);
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SERVICE_TYPE, type);
    }

    @Override
    public String addSupplement(String type) {
        Supplement supplement = getSupplement(type, ExceptionMessages.INVALID_SUPPLEMENT_TYPE);

        supplements.addSupplement(supplement);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForService(String serviceName, String supplementType) {
        Service service = getServiceByName(serviceName);
        Supplement supplement = supplements.findFirst(supplementType);

        if(service == null || supplement == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_SUPPLEMENT_FOUND, supplementType));
        }

        service.addSupplement(supplement);
        supplements.removeSupplement(supplement);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_IN_SERVICE, supplementType, serviceName);
    }

    private Supplement getSupplement(String type, String message) {
        SupplementType supplementType;

        try {
            supplementType = SupplementType.valueOf(type);
        } catch(IllegalArgumentException ignored) {
            throw new IllegalArgumentException(message);
        }

        Supplement supplement = null;
        switch(supplementType) {
            case MetalArmor:
                supplement = new MetalArmor();
                break;
            case PlasticArmor:
                supplement = new PlasticArmor();
                break;
        }

        return supplement;
    }

    @Override
    public String addRobot(String serviceName, String robotType, String robotName, String robotKind, double price) {
        RobotType robotGender;

        try {
            robotGender = RobotType.valueOf(robotType);
        } catch(IllegalArgumentException ignored) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_ROBOT_TYPE);
        }

        Robot robot = null;
        switch (robotGender) {
            case MaleRobot:
                robot = new MaleRobot(robotName, robotKind, price);
                break;
            case FemaleRobot:
                robot = new FemaleRobot(robotName, robotKind, price);
                break;
        }

        Service service = getServiceByName(serviceName);

        service.addRobot(robot);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_ROBOT_IN_SERVICE, robotType, serviceName);
    }

    private Service getServiceByName(String serviceName) {
        return services.stream().filter(s -> s.getName().equals(serviceName)).findFirst().orElse(null);
    }

    @Override
    public String feedingRobot(String serviceName) {
        Service service = getServiceByName(serviceName);

        service.feeding();

        return String.format(ConstantMessages.FEEDING_ROBOT, service.getRobots().size());
    }

    @Override
    public String sumOfAll(String serviceName) {
        Service service = getServiceByName(serviceName);

        double sumRobotPrices = service.getRobots().stream().mapToDouble(Robot::getPrice).sum();
        double sumSupplementPrices = service.getSupplements().stream().mapToDouble(Supplement::getPrice).sum();

        double totalSum = sumRobotPrices + sumSupplementPrices;

        return String.format(ConstantMessages.VALUE_SERVICE, serviceName, totalSum);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();

        services.forEach(s -> sb.append(s.getStatistics()).append(System.lineSeparator()));

        return sb.toString().trim();
    }

}
