public class Player {
    private Hand hand;
    private Hand sehand;
    public boolean isDoubleDowned;
    public Player(){
        this.hand = new Hand();
        this.sehand = new Hand();
        this.isDoubleDowned = false;
    }
    public String takeCard(){
        hand.takeCardFromDeck(Main.roundDeck);
        //System.out.println(hand.toString());
        return hand.getCardImg(hand.getHandSize()-1);
    }
    public int totalVal(){
        return hand.calculatedValue();
    }
    public int calculateChanceOfWining(){
        double suitableCards = 0.0;
        for(int i = 0; i <= 21-totalVal(); i++){
            suitableCards += (double)(Deck.calcTotalCardsByValue(21-i));
        }
        return (int)((suitableCards/Deck.deck.size())*100);
    }
}
