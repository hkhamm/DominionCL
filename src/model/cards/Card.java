package model.cards;
import model.CardObservable;
import model.Player;
import model.states.CardState;

public class Card extends CardObservable {

    String name;
    int cost;
    int value;
    int victoryPoints;
    String description;
    CardState cardState;

    public Card() {
        this.name = "Name";
        this.cost = 0;
        this.value = 0;
        this.victoryPoints = 0;
        this.description = "This is a card.";

        this.cardState = new CardState();
    }

    public void play(Player player) {
        notifyObservers(cardState);
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getDescription() {
        return description;
    }
}