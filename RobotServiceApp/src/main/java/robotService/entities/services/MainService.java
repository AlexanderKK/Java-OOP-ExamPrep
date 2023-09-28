package robotService.entities.services;

import robotService.common.ConstantMessages;
import robotService.entities.robot.MaleRobot;
import robotService.entities.robot.Robot;

public class MainService extends BaseService {

    private static final int CAPACITY = 30;

    public MainService(String name) {
        super(name, CAPACITY);
    }

    @Override
    public void addRobot(Robot robot) {
        if(robot instanceof MaleRobot) {
            super.addRobot(robot);
        } else {
            throw new IllegalArgumentException(ConstantMessages.UNSUITABLE_SERVICE);
        }

    }

}
