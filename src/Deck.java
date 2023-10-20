import java.util.ArrayList;
public class Deck {

    //total number of 52 cards, NO JOKERS
    //private static Card[] deck;
    //public Card[] deck = new Card[52];
    public static ArrayList<Card> deck;
    private int cardsUsed;

    //deck[0].toString() ---> String form
    //deck[0] ----> Card

    //deck[0] = New Card(sth,sth)
    //deck[1] - new Card(sth,sth)................

    //constructor
    //TODO: create 52 cards by using 2 for loops and filling the Card[] deck out

    public Deck() throws Exception {
        deck = new ArrayList<Card>();
        int i = 0;
        for(int s = 0;s < 4; s++){
            for(int v = 1;v < 14; v++){
                //deck[i] = new Card(v, s);
                deck.add(new Card(v, s));
                i++;
            }
        }
    }

    //shuffle the Card[] deck randomly
    //TODO: go home brainstorm ideas on how to do this

    //myqueen.shuffle()
    //for(){sout(myqueen[i])}
    public void shuffle (){
        for(int i = 0; i < 300; i++){
            int ran1 = (int)(Math.random()*52);
            int ran2 = (int)(Math.random()*52);
            //Card randomCard = deck[ran1];
            Card randomCard = deck.get(ran1);
            deck.set(ran1, deck.get(ran2));
            deck.set(ran2, randomCard);
            //deck[ran1] = deck[ran2];
            //deck[ran2] = randomCard;
        }
    }

    //tells the user how many cards left in the deck
    //TODO" total number of cards - cardsUsed
    public int cardsLeft(){
        return deck.size();
    }

    //returns a card ready to give to user
    //TODO: removes the next card from the deck and returns it.
    public static Card dealCard(){
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public static void showDeck(){
        for(int i = 0; i < deck.size();i++){
            //String cardString = Card.toString(deck[i]);
            String cardString = Card.toString(deck.get(i));
            System.out.println(cardString);
        }
    }
    public static int calcTotalCardsByValue(int value){
        int total = 0;
        for(int i = 0; i < deck.size(); i++){
            if(deck.get(i).value == value || deck.get(i).value >= 10 && value == 10){
                total++;
            }
        }
        return total;
    }

    public static void main(String[] args) throws Exception {
        Deck mydeck = new Deck();
        mydeck.shuffle();
        mydeck.showDeck();


// testing the code
//        myQueen.shuffle();
//        for(int i = 0; i < myQueen.deck.length; i++){
//            System.out.println(myQueen.deck[i].toString());
//        }

        //myQueen.shuffle()

//        for(int i = 0; i<ccount;i++){
//            System.out.println(myQueen.deck[i]);
//            //myQueen.deck[i].toString();
//        }

    }
}
