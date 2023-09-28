package vehicleShop.models.worker;

import vehicleShop.common.ExceptionMessages;
import vehicleShop.models.tool.Tool;
import static vehicleShop.utils.StringUtil.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class BaseWorker implements Worker {

    protected static final int STRENGTH_DECREASE_FACTOR = 10;
    private String name;
    private int strength;
    private Collection<Tool> tools;

    protected BaseWorker(String name, int strength) {
        setName(name);
        setStrength(strength);
        tools = new ArrayList<>();
    }

    private void setName(String name) {
        if(isNullOrEmpty(name)) {
            throw new IllegalArgumentException(ExceptionMessages.WORKER_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    public void setStrength(int strength) {
        if(strength < 0) {
            throw new IllegalArgumentException(ExceptionMessages.WORKER_STRENGTH_LESS_THAN_ZERO);
        }

        this.strength = strength;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public Collection<Tool> getTools() {
        return Collections.unmodifiableCollection(tools);
    }

    @Override
    public void working() {
        strength -= STRENGTH_DECREASE_FACTOR;

        if(strength < 0) {
            strength = 0;
        }
    }

    @Override
    public void addTool(Tool tool) {
        tools.add(tool);
    }

    @Override
    public boolean canWork() {
        return strength > 0;
    }

}
