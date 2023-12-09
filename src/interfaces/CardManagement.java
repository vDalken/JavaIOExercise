package interfaces;

import classes.Card;

import java.util.ArrayList;

public interface CardManagement {
    void blockCard(ArrayList<String> updatedResourceFile, Card loggedCard);
}
