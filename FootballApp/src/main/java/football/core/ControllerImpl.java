package football.core;

import football.common.ConstantMessages;
import football.common.ExceptionMessages;
import football.entities.field.ArtificialTurf;
import football.entities.field.Field;
import football.entities.field.FieldType;
import football.entities.field.NaturalGrass;
import football.entities.player.Men;
import football.entities.player.Player;
import football.entities.player.PlayerType;
import football.entities.player.Women;
import football.entities.supplement.Liquid;
import football.entities.supplement.Powdered;
import football.entities.supplement.Supplement;
import football.entities.supplement.SupplementType;
import football.repositories.SupplementRepository;
import football.repositories.SupplementRepositoryImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ControllerImpl implements Controller {

    private SupplementRepository supplementRepository;
    private Collection<Field> fields;

    public ControllerImpl() {
        supplementRepository = new SupplementRepositoryImpl();
        fields = new ArrayList<>();
    }

    private Field getFieldByName(String fieldName) {
        return fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
    }

    @Override
    public String addField(String fieldType, String fieldName) {
        FieldType type;

        try {
            type = FieldType.valueOf(fieldType);
        } catch (IllegalArgumentException ignored) {
            throw new NullPointerException(ExceptionMessages.INVALID_FIELD_TYPE);
        }

        Field field;
        switch(type) {
            case ArtificialTurf:
                field = new ArtificialTurf(fieldName);
                break;
            case NaturalGrass:
                field = new NaturalGrass(fieldName);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_FIELD_TYPE);
        }

        fields.add(field);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FIELD_TYPE, fieldType);
    }

    @Override
    public String deliverySupplement(String type) {
        SupplementType supplementType;

        try {
            supplementType = SupplementType.valueOf(type);
        } catch (IllegalArgumentException ignored) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_SUPPLEMENT_TYPE);
        }

        Supplement supplement;
        switch(supplementType) {
            case Powdered:
                supplement = new Powdered();
                break;
            case Liquid:
                supplement = new Liquid();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_SUPPLEMENT_TYPE);
        }

        supplementRepository.add(supplement);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForField(String fieldName, String supplementType) {
        Supplement supplement = supplementRepository.findByType(supplementType);

        if(supplement == null) {
            throw new IllegalArgumentException(
                    String.format(ExceptionMessages.NO_SUPPLEMENT_FOUND, supplementType)
            );
        }

        Field field = getFieldByName(fieldName);

        Objects.requireNonNull(field).addSupplement(supplement);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_IN_FIELD, supplementType, fieldName);
    }

    @Override
    public String addPlayer(String fieldName, String playerType, String playerName, String nationality, int strength) {
        PlayerType type;

        try {
            type = PlayerType.valueOf(playerType);
        } catch (IllegalArgumentException ignored) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_TYPE);
        }

        Field field = getFieldByName(fieldName);

        Player player;
        switch(type) {
            case Men:
                player = new Men(playerName, nationality, strength);
                break;
            case Women:
                player = new Women(playerName, nationality, strength);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_TYPE);
        }

        //Reflection Usage For Adding Player (Men) In (NaturalGrass) Field Or (Women) In (ArtificialTurf) Field

        //1. Get generic of field
        Type genericSuperType = field.getClass().getGenericSuperclass();
        //2. Get generic arguments
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperType).getActualTypeArguments();

        boolean isInstance = false;
        Type actualTypeArgument = actualTypeArguments[0];

        //3. Check if generic argument is Class
        if(actualTypeArgument instanceof Class) {
            //4. Check if Generic Type of FIELD is the same as Type of PLAYER
            isInstance = ((Class<?>) actualTypeArgument).isInstance(player);
        }

        //5. If true add player with positive message
        if(isInstance) {
            field.addPlayer(player);

            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType, fieldName);
        }

        //6. If false return negative message
        return ConstantMessages.FIELD_NOT_SUITABLE;
    }

    @Override
    public String dragPlayer(String fieldName) {
        Field field = getFieldByName(fieldName);

        field.drag();

        return String.format(ConstantMessages.PLAYER_DRAG, field.getPlayers().size());
    }

    @Override
    public String calculateStrength(String fieldName) {
        Field field = getFieldByName(fieldName);
        int fieldStrength = field.getPlayers().stream().mapToInt(Player::getStrength).sum();

        return String.format(ConstantMessages.STRENGTH_FIELD, fieldName, fieldStrength);
    }

    @Override
    public String getStatistics() {
        StringBuilder out = new StringBuilder();
        fields.forEach(f -> out.append(String.format("%s (%s):", f.getName(), f.getClass().getSimpleName()))
                .append(System.lineSeparator())
                .append(f.getInfo()));

        return out.toString().trim();
    }

}
