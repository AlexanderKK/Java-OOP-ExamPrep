package robotService.entities.services;

import robotService.common.ConstantMessages;
import robotService.entities.robot.FemaleRobot;
import robotService.entities.robot.Robot;

public class SecondaryService extends BaseService {

    private static final int CAPACITY = 15;

    public SecondaryService(String name) {
        super(name, CAPACITY);
    }

    @Override
    public void addRobot(Robot robot) {
        if(robot instanceof FemaleRobot) {
            super.addRobot(robot);
        } else {
            throw new IllegalArgumentException(ConstantMessages.UNSUITABLE_SERVICE);
        }
    }

}
