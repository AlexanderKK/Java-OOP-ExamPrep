package goldDigger.models.spot;

import goldDigger.common.ExceptionMessages;
import goldDigger.utils.StringUtil;

import java.util.*;

public class SpotImpl implements Spot {

    private String name;
    private Collection<String> exhibits;

    public SpotImpl(String name) {
        setName(name);
        this.exhibits = new ArrayList<>();
    }

    private void setName(String name) {
        if(StringUtil.isNullOrEmpty(name)) {
            throw new NullPointerException(ExceptionMessages.SPOT_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<String> getExhibits() {
        return exhibits;
    }

}
