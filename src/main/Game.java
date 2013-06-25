package main;

import model.*;
import model.cards.*;
import model.states.*;
import view.View;

import java.util.*;

public class Game implements CardObserver {

    private View view;
    private Supply supply;
    private ArrayList<Card> trash;
    private ArrayList<Player> turnOrder;
    private int emptySupply;
    private int numberOfPlayers;
    private int currentPlayerIndex;
    Player currentPlayer;
    CardState cardState;

    public Game() {
        view = new View();
        cardState = new CardState();
        numberOfPlayers = view.getPlayers();
        supply = new Supply(numberOfPlayers);
        trash = new ArrayList<Card>();
        turnOrder = new ArrayList<Player>();
        currentPlayerIndex = 0;

        addAsObserver();
        getStartingDecks();
        playGame();
    }

    private void playGame() {
        while (supply.getProvinces() > 0) {
            currentPlayer = turnOrder.get(currentPlayerIndex);

            for (SupplyPile pile : supply.getCardList()) {
                if (pile.size() == 0) {
                    emptySupply += 1;
                }
            }

            if (emptySupply >= 3) {
                break;
            }
            else {
                emptySupply = 0;
            }

            view.printNewTurn(currentPlayer);

            currentPlayer.setBuyingPower();

            choosePhase();

            // if (currentPlayerIndex == 0)  // one round test
            //     break;
        }
        Collections.sort(turnOrder, new PlayerComparator());
        view.endGame(turnOrder);
    }

    private void getStartingDecks() {
        for (int i = 1; i < numberOfPlayers + 1; ++i) {
            ArrayList<Card> deck = new ArrayList<Card>();

            for (int j = 0; j < 3; ++j) {
                deck.add(new Estate());
            }
            for (int k = 0; k < 7; ++k) {
                deck.add(new Copper());
            }

            shuffle(deck);

            turnOrder.add(new Player("Player " + i, deck));
        }

        for (Player player : turnOrder) {
            player.drawCards(5);
        }
    }

    private void choosePhase() {
        int actions = currentPlayer.getActions();
        int buys = currentPlayer.getBuys();
        printPlayArea();
        printNiceHand();
        int phase = view.choosePhase(actions, buys);

        if (phase == 1) {
            playAction();
        }
        if (phase == 2) {
            buyCard();
        }
        if (phase == 3) {
            examineCard();
        }
        if (phase == 4) {
            endTurn();
        }

    }

    private void playAction() {
        int num;
        int cardIndex = 0;

        printHand();
        num = getInput();
        if (num > 0) {
            cardIndex = num - 1;
        }
        else {
            choosePhase();
        }

        Card card = currentPlayer.getHand().get(cardIndex);
        if (card instanceof Action || card instanceof ActionAttack || card instanceof ActionReaction) {
            currentPlayer.playCard(card);
            currentPlayer.setActions(currentPlayer.getActions() - 1);
            choosePhase();
        }
        else {
            print("You must choose an action, try again.");
            playAction();
        }
    }

    private void buyCard() {
        int num;
        int supplyPile;
        int cardValue;
        String cardName;

        print("Choose a card from the supply:");
        view.printFormattedSupply(supply);
        view.printBuyingPower(currentPlayer);

        num = getInput();
        supplyPile = 0;
        if (num > 0) {
            supplyPile = num - 1;
        }
        else {
            choosePhase();
        }

        cardName = supply.getName(supplyPile);

        cardValue = 0;
        cardValue += supply.getCost(supplyPile);

        if (cardValue <= currentPlayer.getBuyingPower()) {
            print("You purchased a " + cardName + ".");
            view.setBuyFlag(true);
            supply.purchaseCard(supplyPile, currentPlayer);
            currentPlayer.setBuys(currentPlayer.getBuys() - 1);

            for (int i = 0; i < currentPlayer.getHand().size(); ++i) {
                currentPlayer.getPlayArea().addAll(currentPlayer.getHand());
                currentPlayer.getHand().clear();
            }

            currentPlayer.increaseUsedBuyingPower(cardValue);
            currentPlayer.setBuyingPower();
        }
        else {
            print("You can't afford a " + cardName + ". Try again.");
        }
        choosePhase();
    }

    private void examineCard() {
        int num;
        int supplyPile;

        print("\nChoose a card to examine:");
        view.printFormattedSupply(supply);

        num = getInput();
        supplyPile = 0;
        if (num > 0) {
            supplyPile = num - 1;
        }
        else {
            choosePhase();
        }

        view.printCard(supply.getCardList().get(supplyPile).getCard());
        choosePhase();
    }

    private void endTurn() {
        view.setBuyFlag(false);
        view.printEndTurn();
        currentPlayer.cleanUp();
        if (currentPlayerIndex != numberOfPlayers - 1) {
            currentPlayerIndex++;
        }
        else {
            currentPlayerIndex = 0;
        }
    }

    public static void shuffle(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); ++i) {
            Random generator = new Random();
            int randomNum = generator.nextInt(cards.size());

            Card randomCard = cards.get(randomNum);
            Card temp = cards.get(0);

            cards.set(0, randomCard);
            cards.set(randomNum, temp);
        }
    }

    public void discardDraw(Player player) {
        int num = view.discardCards();
        for (int i = 0; i < num; ++i) {
            view.printHand(player);
            int index = view.chooseInt();
            player.discardCard(index);
        }
        for (int i = 0; i < num; ++i) {
            player.drawCards(num);
        }
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public void executeCard() {
        cardState.execute(currentPlayer, turnOrder, this);
    }

    private void addAsObserver() {
        for (SupplyPile supplyPile : supply.getCardList()) {
            for (Card card : supplyPile.getPile()) {
                card.addObserver(this);
            }
        }
    }

    public Supply getSupply() {
        return supply;
    }

    public ArrayList<Card> getTrash() {
        return trash;
    }

    public void trashCard(Card card) {
        trash.add(card);
    }

    public void update(CardState cardState) {
        this.cardState = cardState;
        executeCard();
    }

    public void print(String string) {
        view.print(string);
    }

    public Card chooseTreasure() {
        return view.chooseTreasure(currentPlayer, this);
    }

    public int discardCards() {
        return view.discardCards();
    }

    public void printControlledSupply(int cost) {
        view.printControlledSupply(supply, cost);
    }

    public void printBack() {
        view.printBack();
    }

    public void printHand() {
        view.printHand(currentPlayer);
    }

    public void printNiceHand() {
        view.printNiceHand(currentPlayer);
    }

    public void printPlayArea() {
        view.printPlayArea(currentPlayer);
    }

    public int getInput() {
        return view.chooseInt();
    }

    public static void main(String[] args) {
        new Game();
    }
}