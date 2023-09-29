public class Dealer {
    private Hand dealerHand;
    public Dealer(){
        this.dealerHand = new Hand();
    }
    public String takeCard(){
        dealerHand.takeCardFromDeck(Main.roundDeck);
        if(dealerHand.getHandSize()==1){
            return "";
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
    public String realFirstCard(){
        return dealerHand.getCardImg(0);
    }
    public int realTotalVal(){
        return dealerHand.calculatedValue();
    }
}
