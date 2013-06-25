package model.states;

import main.Game;
import model.Player;
import model.cards.*;

import java.util.ArrayList;

public class MineState extends CardState {

    public MineState() {
        super();
    }

    public void execute(Player player, ArrayList<Player> players, Game game) {
        game.printHand();
        Card card = game.chooseTreasure();
        if (card instanceof Copper) {
            game.getSupply().getCardList().get(4).drawCard();
        }
        if (card instanceof Silver) {
            game.getSupply().getCardList().get(5).drawCard();
        }

        player.getHand().remove(card);
        player.setBuyingPower();
    }
}
