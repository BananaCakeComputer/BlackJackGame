public class Dealer {
    private Hand dealerHand;
    public Dealer(){
        this.dealerHand = new Hand();
    }
    public String takeCard(){
        dealerHand.takeCardFromDeck(Main.roundDeck);
        if(dealerHand.getHandSize()==1){
            return "img/CardDown.png";
        }else{
            return dealerHand.getCardImg(dealerHand.getHandSize()-1);
        }
    }
    public int totalVal(){
        if(dealerHand.getHandSize()<=2){
            return 0;
        }else{
            return dealerHand.calculatedValue();
        }
    }
    public String firstCard(){
        if(dealerHand.getHandSize()>2){
            return dealerHand.getCardImg(0);
        }else{
            return "img/CardDown.png";
        }
    }
}
