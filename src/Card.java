public class Card {

    //suit
    public final static int CLUBS = 0;
    public final static int DIAMONDS = 1;
    public final static int HEARTS = 2;
    public final static int SPADES = 3;
    public final static int JOKER = 4;

    //values
    public final static int ACE = 1;
    public final static int JACK = 10;
    public final static int QUEEN = 10;
    public final static int KING = 10;

    //this contains one of the constants SPADES......
    public int suit;

    //this contains the value of card
    public int value;

    //constructor
    //TODO: make this into a random card generator
    //TODO: so, suit and value are int with the range of 0-3, 1-13
    public Card(){
        this.suit = (int)(Math.random()*4);
        this.value = (int)(Math.random()*13)+1;
    }

    //creates a card with a specified suit and value
    //TODO: "Suit" Parameter must be a number of 0,1,2,3,4
    //TODO: "Value" Parameter must be a number from 1 to 13
    //hint: use if statements
    public Card(int Value, int Suit) throws Exception {
        if(Value>13||Value<1){
            throw new Exception("参数Value不合法");
        } else if (Suit>4||Suit<0) {
            throw new Exception("参数Suit不合法");
        }
        this.suit = Suit;
        this.value = Value;
    }


    //returns the suit of this card
    public static int getSuit(Card card){
        //TODO:
        return card.suit;
    }

    //returns value of this card
    public static int getValue(Card card){
        //TODO;
        return card.value;
    }

    //TODO: return the string version of the cards "Suit"
    //hint: use switch case
    public static String getSuitString(Card card){
        //TODO:
        switch (card.suit) {
            case 0:
                return "Clubs";
            case 1:
                return "Diamonds";
            case 2:
                return "Hearts";
            case 3:
                return "Spades";
            case 4:
                return "Joker";
        }
        return "";
    }

    //TODO: return the string version of the cards "Value"
    //hint: use switch case
    //example: card1.suit = 2, card1.value = 12
    //card1.getValueString -----> returns "Queen"
    //card1.getSuitString ---> returns "DIAMONDS"
    public static String getValueString(Card card){
        //TODO:
        switch (card.value) {
            case 1:
                return "Ace";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            case 10:
                return "Ten";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
        }
        return "";
    }

    //TODO:  this function will use getValueString() and getSuitString()
    //example; exampleCard.suit = 0, value = 13
    //exampleCard.toString() -----> "King of Spades"
    //basically add in the "of"

    public static String toString(Card card){
        //TODO:
        return getValueString(card) + " of " + getSuitString(card);
    }

    //for example demonstration purposes
    //write tests to show me all your functions are working
    public static void main(String[] args) throws Exception {
        Card myQueen = new Card();
//        System.out.println(myQueen.toString());
        System.out.println(getSuit(myQueen));
        System.out.println(getValue(myQueen));
        System.out.println(getSuitString(myQueen));
        System.out.println(getValueString(myQueen));
        System.out.println(toString(myQueen));
        Card test = new Card(114,514);
    }
}