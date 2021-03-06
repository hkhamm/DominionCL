package model.states;


import main.Game;
import model.Player;

import java.util.ArrayList;

public class MarketState extends CardState{

    public MarketState() {
        super();
    }

    public void execute(Player player, ArrayList<Player> players, Game game) {
        player.drawCards(1);
        player.setActions(player.getActions() + 1);
        player.setBuys(player.getBuys() + 1);
        player.increaseTempBuyingPower(1);
    }
}
