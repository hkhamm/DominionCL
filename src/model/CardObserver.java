package model;

import model.states.CardState;

public interface CardObserver {

    public void update(CardState cardState);
}
