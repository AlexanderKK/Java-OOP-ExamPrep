package football.entities.field;

import football.entities.player.BasePlayer;
import football.entities.player.Player;
import football.entities.supplement.Supplement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static football.utils.StringUtil.isNullOrEmpty;
import static football.common.ExceptionMessages.*;
import static football.common.ConstantMessages.*;

public abstract class BaseField<T extends BasePlayer> implements Field {

    private String name;
    private int capacity;
    private Collection<Supplement> supplements;
    private Collection<Player> players;

    public BaseField(String name, int capacity) {
        setName(name);
        this.capacity = capacity;
        this.supplements = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    private void setName(String name) {
        if(isNullOrEmpty(name)) {
            throw new NullPointerException(FIELD_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int sumEnergy() {
        return supplements.stream().mapToInt(Supplement::getEnergy).sum();
    }

    @Override
    public void addPlayer(Player player) {
        if(players.size() == capacity) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }

        players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public void addSupplement(Supplement supplement) {
        supplements.add(supplement);
    }

    @Override
    public void drag() {
        players.forEach(Player::stimulation);
    }

    @Override
    public String getInfo() {
        StringBuilder sbInfo = new StringBuilder();

        sbInfo.append("Player: ");

        String playersData = players.isEmpty()
                ? "none"
                : players.stream().map(Player::getName).collect(Collectors.joining(" "));

        sbInfo.append(playersData)
                .append(System.lineSeparator())
                .append("Supplement: ")
                .append(supplements.size())
                .append(System.lineSeparator())
                .append("Energy: ")
                .append(sumEnergy())
                .append(System.lineSeparator());

        return sbInfo.toString();
    }

    @Override
    public Collection<Player> getPlayers() {
        return Collections.unmodifiableCollection(players);
    }

    @Override
    public Collection<Supplement> getSupplements() {
        return Collections.unmodifiableCollection(supplements);
    }

}
