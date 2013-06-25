package model.cards;
import model.Player;
import model.states.MarketState;

public class Market extends Action {

    public Market() {
        super();
        this.name = "Market";
        this.cost = 5;
        this.description = "+1 Card; +1 Action; +1 Buy; +$1.";

        this.cardState = new MarketState();
    }

    public void play(Player player) {
        notifyObservers(cardState);
    }
}
