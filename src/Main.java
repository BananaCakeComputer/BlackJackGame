public class Main {

    public static void main(String[] args) {


        //Create game window
        JFrame frame = new JFrame("BlackJack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set its size
        frame.setSize(800, 600);

        //Make a new blackjack Game object/JPanel
        Game blackjack = new Game();

        //Add it to the frame and make it visible
        frame.add(blackjack);
        frame.setVisible(true);
        



    }
}