package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Dylan on 3/10/2016.
 */
public class Blackjack {

    public java.util.List<Card> deck = new ArrayList<>();

    public java.util.List<java.util.List<Card>> cols = new ArrayList<>();

    public int money = 100;

    public User user = new User();
    public Dealer dealer = new Dealer();
    public int userTotal;
    public int dealerTotal;

    public Blackjack(){
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
    }

    public void clearBoard(){
        cols = new ArrayList<>();
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());

        deck = new ArrayList<>();
    }







    //I like space between my methods








    public void buildDeck(){
        for(int i = 2; i < 15; i++){
            deck.add(new Card(i, Suit.Clubs));
            deck.add(new Card(i, Suit.Hearts));
            deck.add(new Card(i, Suit.Diamonds));
            deck.add(new Card(i, Suit.Spades));
        }
    }








    //I like space between my methods







    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }


    public void startHand() {
        user.restart();
        dealer.restart();
        for(int i = 0; i < 4; i++){
            if(i < 3) {
                cols.get(0).add(deck.get(deck.size() - 1));
                deck.remove(deck.size() - 1);
            }
            else{
                user.userHit(cols.get(1), deck);
            }
        }
    }



    public void playerHit() {
        cols.get(1).add(deck.get(deck.size()-1));
        deck.remove(deck.size()-1);
    }

    public void dealerHit() {
        cols.get(0).add(deck.get(deck.size()-1));
        deck.remove(deck.size()-1);
    }


    public int dPlay() {
        dealerTotal = dealer.dealerPlay(cols.get(0), deck);
        return dealerTotal;
    }

    public String scoring(){
        if(dealerTotal > userTotal && dealerTotal <= 21){
            return "Dealer Wins!";
        }
        else if(dealerTotal == userTotal){
            return "Tie!";
        }
        else{
            return "You Win!";
        }
    }

    //customDeal to setup game for testing purposes
    public void customDeal(int c1, int c2, int c3, int c4) {
        cols.get(0).add(deck.get(c1));
        deck.remove(c1);
        cols.get(1).add(deck.get(c2));
        deck.remove(c2);
        cols.get(2).add(deck.get(c3));
        deck.remove(c3);
        cols.get(3).add(deck.get(c4));
        deck.remove(c4);
    }

    public void remove(int columnNumber) {
        if(colHasCards(columnNumber)) {
            Card c = getTopCard(columnNumber);
            boolean removeCard = false;
            for (int i = 0; i < 4; i++) {
                if (i != columnNumber) {
                    if (colHasCards(i)) {
                        Card compare = getTopCard(i);
                        if (compare.getSuit() == c.getSuit()) {
                            if (compare.getValue() > c.getValue()) {
                                removeCard = true;
                            }
                        }
                    }
                }
            }
            if (removeCard) {
                this.cols.get(columnNumber).remove(this.cols.get(columnNumber).size() - 1);
            }
        }
    }

    private boolean colHasCards(int colNumber) {
        if(this.cols.get(colNumber).size()>0){
            return true;
        }
        return false;
    }

    private Card getTopCard(int columnNumber) {
        return this.cols.get(columnNumber).get(this.cols.get(columnNumber).size()-1);
    }


    public void move(int colFrom, int colTo) {
        Card cardToMove = getTopCard(colFrom);
        this.removeCardFromCol(colFrom);
        this.addCardToCol(colTo,cardToMove);
    }

    private void addCardToCol(int colTo, Card cardToMove) {
        cols.get(colTo).add(cardToMove);
    }

    private void removeCardFromCol(int colFrom) {
        this.cols.get(colFrom).remove(this.cols.get(colFrom).size()-1);

        //I think that should conflict...
        //Did it work yet?

    }
}
