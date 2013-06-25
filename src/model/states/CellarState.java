package model.states;

import main.Game;
import model.Player;

import java.util.ArrayList;

public class CellarState extends CardState {

    public CellarState() {
        super();
    }

    public void execute(Player player, ArrayList<Player> players, Game game) {
        player.setActions(player.getActions() + 1);
        int num = game.discardCards();
        for (int i = 0; i < num; ++i) {
            game.printHand();
            player.discardCard(game.getInput());
        }
        for (int i = 0; i < num; ++i) {
            player.drawCards(num);
        }
        player.setBuyingPower();
    }
}
