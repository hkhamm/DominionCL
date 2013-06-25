package model.cards;

import model.Player;
import model.states.WoodcutterState;

public class Woodcutter extends Action {

    public Woodcutter() {
        super();
        this.name = "Woodcutter";
        this.cost = 3;
        this.description = "+1 Buy; +$2.";

        this.cardState = new WoodcutterState();
    }

    public void play(Player player) {
        notifyObservers(cardState);
    }
}