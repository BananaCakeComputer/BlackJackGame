public class Player {
    public static Hand hand;
    public Player(){
        this.hand = new Hand();
    }
    public String takeCard(){
        hand.takeCardFromDeck(Main.roundDeck);
        //System.out.println(hand.toString());
        return hand.getCardImg(hand.getHandSize()-1);
    }
    public int totalVal(){
        return hand.calculatedValue();
    }
}
