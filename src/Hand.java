import java.util.ArrayList;

/**
 * A hand of cards to play with
 */
public class Hand {

    private ArrayList<Card> hand;

    public Hand(){
        hand = new ArrayList<Card>();
    }

    /**
     * Take a single card from the top of this deck and add it to the hand, removing it from the previous deck
     * @param deck The deck of cards we're taking from
     */
    public void takeCardFromDeck(Deck deck){
        /*if(deck.cardsLeft()<1){

        }*/
        hand.add(deck.dealCard());
    }

    /**
     * Add a single card to this hand
     * @param c The card being added
     */
    /**
     *
     * @param discardDeck The deck we're discarding this hand to
     */
    public void discardHandToDeck(Deck discardDeck){
        for(int i = 0; i < hand.size(); i++){
            //discardDeck.add(hand.get(i));
            discardDeck.deck.add(hand.get(i));
            hand.remove(i);
        }
    }

    /**
     *
     * @return The hand with all its cards in a single line String
     */
    public String toString(){
        String toStr = "";
        for(int i = 0; i < hand.size(); i++){
            toStr = toStr + Card.toString(hand.get(i));
        }
        return toStr;
    }


    /**
     *
     * @return The calculated numerical value of the hand as an integer
     */
    public int calculatedValue(){
        int handVal = 0;
        for(int i = 0; i < hand.size(); i++){
            handVal += hand.get(i).value;
        }
        return handVal;
    }


    /**
     *
     * @param idx the index of the card we're getting
     * @return the card we got
     */
    public Card getCard(int idx){
        return hand.get(idx);
    }

    /**
     * Get the number of cards in this hand
     * @return
     */
    public int getHandSize(){
        return hand.size();
    }

    public static void main(String[] args) throws Exception {
        //测试
        Hand player = new Hand();
        Deck testDeck = new Deck();
        testDeck.shuffle();
        player.takeCardFromDeck(testDeck);
        System.out.println(player.toString());
        System.out.println(player.calculatedValue());
        System.out.println(Card.toString(player.getCard(0)));
        System.out.println(player.getHandSize());
        player.discardHandToDeck(testDeck);
        System.out.println(player.getHandSize());
    }
}
