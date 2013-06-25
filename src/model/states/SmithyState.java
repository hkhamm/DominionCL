package model.states;

import main.Game;
import model.Player;

import java.util.ArrayList;

public class SmithyState extends CardState {

    public SmithyState() {
        super();
    }

    public void execute(Player player, ArrayList<Player> players, Game game) {
        player.drawCards(3);
    }
}
