package model.cards;

import model.Player;
import model.states.SmithyState;

public class Smithy extends Action {

    public Smithy() {
        super();
        this.name = "Smithy";
        this.cost = 4;
        this.description = "+3 cards.";

        this.cardState = new SmithyState();
    }

    public void play(Player player) {
        notifyObservers(cardState);
    }
}
