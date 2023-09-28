package vehicleShop.models.worker;

import vehicleShop.models.tool.Tool;
import vehicleShop.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static vehicleShop.common.ExceptionMessages.*;

public abstract class BaseWorker implements Worker {

    private String name;
    private int strength;
    private Collection<Tool> tools;
    private static final int STRENGTH_DECREASE_FACTOR = 10;

    protected BaseWorker(String name, int strength) {
        setName(name);
        setStrength(strength);
        this.tools = new ArrayList<>();
    }

    private void setName(String name) {
        if(StringUtils.isNullOrEmpty(name)) {
            throw new IllegalArgumentException(WORKER_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    public void setStrength(int strength) {
        if(strength < 0) {
            throw new IllegalArgumentException(WORKER_STRENGTH_LESS_THAN_ZERO);
        }

        this.strength = strength;
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

}
