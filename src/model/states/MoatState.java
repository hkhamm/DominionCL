package model.states;

import main.Game;
import model.Player;

import java.util.ArrayList;

public class MoatState extends CardState {

    public MoatState() {
        super();
    }

    public void execute(Player player, ArrayList<Player> players, Game game) {
        player.drawCards(2);
    }
}
