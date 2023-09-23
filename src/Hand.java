import java.util.ArrayList;

/**
 * A hand of cards to play with
 */
public class Hand {

    private ArrayList<Card> hand;

    public Hand(){
        this.hand = new ArrayList<Card>();
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
        //2023-09-23: 修复计算问题
        int handVal = 0;
        int aces = 0;
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).value>10){
                handVal += 10;
            }else if(hand.get(i).value == 1){
                aces += 1;
            }else{
                handVal += hand.get(i).value;
            }
        }
        for(int i = 0; i < aces; i++){
            if(i==aces-1 && (handVal+11 <= 21)){
                handVal += 11;
            }else{
                handVal += 1;
            }
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

    public String getCardImg(int index){
        return "img/" + Card.getValueString(hand.get(index)) + Card.getSuitString(hand.get(index)) + ".png";
    }

    public static void main(String[] args) throws Exception {
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
