package model.states;

import main.Game;
import model.Player;

import java.util.ArrayList;

public class VillageState extends CardState {

    public VillageState() {
        super();
    }

    public void execute(Player player, ArrayList<Player> players, Game game) {
        player.drawCards(2);
        player.setActions(player.getActions() + 2);
    }
}
