package model.cards;
import model.Player;
import model.states.CellarState;

public class Cellar extends Action {

    public Cellar() {
        super();
        this.name = "Cellar";
        this.cost = 2;
        this.description = "+1 Action\n" +
                           "Discard any number of cards.\n" +
                           "+1 Card per card discarded.\n";

        this.cardState = new CellarState();
    }

    public void play(Player player) {
        notifyObservers(cardState);
    }
}