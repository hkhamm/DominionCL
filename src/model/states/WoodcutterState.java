package model.states;

import main.Game;
import model.Player;

import java.util.ArrayList;

public class WoodcutterState extends CardState {

    public WoodcutterState() {
        super();
    }

    public void execute(Player player, ArrayList<Player> players, Game game) {
        player.setBuys(player.getBuys() + 1);
        player.increaseTempBuyingPower(2);
        player.setBuyingPower();
    }
}
