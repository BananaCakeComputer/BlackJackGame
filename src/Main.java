import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main{
    public static Deck roundDeck;
    private static int page;
    private static JLabel[] tokenImages;
    public static ArrayList<Integer> roundToken;
    public static JFrame frame;
    private static int denomination[] = {1, 5, 10, 20, 50, 100};
    private static JLabel tokenAmountText;
    private static ArrayList<JLabel> tokenLabels;
    public static int balance = 500;
    private static JLabel insertToken;
    private static JButton[] selections;
    private static JPanel blackjackTop;
    private static JPanel blackjack;
    private static ArrayList<JLabel> playerCardImages;
    private static Player player = new Player();
    private static JLabel playerTotal;
    private static ArrayList<JLabel> dealerCardImages;
    private static Dealer dealer = new Dealer();
    private static JLabel dealerTotal;
    private static JLabel dealerFirstCard;
    private static JButton hitBtn;
    private static JButton standBtn;
    private static JButton doubleDownBtn;
    private static JButton checkout;
    private static JLabel chancesText;
    public static void mouseEnter(int btnIdx){
        if(page==1){
            tokenImages[btnIdx].setBorder(new LineBorder(new Color(0, 90, 255), 4, true));
        }
    }
    public static void mouseExit(int btnIdx){
        if(page==1){
            tokenImages[btnIdx].setBorder(null);
        }
    }
    public static int getTotalTokens(){
        int calc = 0;
        for(int i = 0; i < roundToken.size(); i++){
            calc += denomination[roundToken.get(i)];
        }
        if(player.isDoubleDowned){
            return calc*2;
        }
        return calc;
    }
    public static void addToken(int imgIdx){
        roundToken.add(imgIdx);
        tokenLabels.add(new JLabel(new ImageIcon(new ImageIcon("token/" + imgIdx + ".jpg").getImage().getScaledInstance(170, 80, Image.SCALE_SMOOTH))));
        tokenLabels.get(tokenLabels.size()-1).setBounds(-35+roundToken.size()*40, 370, 170, 80);
        frame.add(tokenLabels.get(tokenLabels.size()-1));
        //没有这两行图片不会显示, so weird
        tokenLabels.get(tokenLabels.size()-1).setBorder(new LineBorder(new Color(0, 0, 0), 0, true));
        tokenLabels.get(tokenLabels.size()-1).setBorder(null);
        tokenAmountText.setText("Total Token(s): ¥" + getTotalTokens() + ", Balance: ¥" + (balance - getTotalTokens()));
        if(balance < getTotalTokens()){
            clearToken();
        }
    }
    public static void clearToken(){
        for(int i = 0; i < tokenLabels.size(); i++){
            frame.remove(tokenLabels.get(i));
        }
        tokenLabels = new ArrayList<JLabel>();
        tokenAmountText.setText("Total Token(s): ¥0, Balance: ¥" + balance);
        frame.revalidate();
        frame.repaint();
        roundToken = new ArrayList<Integer>();
    }
    public static void newRound(){
        player = new Player();
        tokenLabels = new ArrayList<JLabel>();
        roundToken = new ArrayList<Integer>();
        page = 1;
        //Create game window
        frame = new JFrame("BlackJack");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set its size
        frame.setSize(800, 500);

        //下面板
        blackjack = new JPanel();
        blackjack.setBounds(-1, 461, 802, 42);
        blackjack.setBackground(new Color(175, 100, 0));
        blackjack.setBorder(new LineBorder(new Color(70, 40, 0)));
        blackjack.setLayout(null);
        JButton DealBtn = new JButton("Deal");
        DealBtn.setBounds(720, 5, DealBtn.getPreferredSize().width, DealBtn.getPreferredSize().height);
        blackjack.add(DealBtn);
        JButton ClearBtn = new JButton("Clear");
        DealBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(getTotalTokens()>0){
                    roundStart();
                    blackjack.remove(DealBtn);
                    blackjack.remove(ClearBtn);
                }
            }
        });
        ClearBtn.setBounds(8, 5, ClearBtn.getPreferredSize().width, ClearBtn.getPreferredSize().height);
        ClearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearToken();
            }
        });
        blackjack.add(ClearBtn);

        //上面板
        blackjackTop = new JPanel();
        blackjackTop.setBounds(-1, -1, 802, 42);
        blackjackTop.setBackground(new Color(175, 100, 0));
        blackjackTop.setBorder(new LineBorder(new Color(70, 40, 0)));
        blackjackTop.setLayout(null);
        JButton exit = new JButton("X");
        exit.setBounds(770, 10, 20, 20);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        blackjackTop.add(exit);
        selections = new JButton[6];
        for(int i = 0; i < selections.length; i++){
            selections[i] = new JButton("▼");
            selections[i].setBounds(115 + i*100, 10, 70, 20);
            int buttonIndex = i;
            selections[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    addToken(buttonIndex);
                }
            });
            selections[i].addMouseListener(new java.awt.event.MouseAdapter(){
                public void mouseEntered(java.awt.event.MouseEvent evt){
                    mouseEnter(buttonIndex);
                }
                public void mouseExited(java.awt.event.MouseEvent evt){
                    mouseExit(buttonIndex);
                }
            });
            blackjackTop.add(selections[i]);
        }

        //中间内容
        insertToken = new JLabel("Select the amount of token");
        insertToken.setFont(new Font("", Font.BOLD,35));
        insertToken.setBounds(10, 50, 500, 40);
        frame.add(insertToken);
        tokenAmountText = new JLabel("Total Token(s): ¥0, Balance: ¥" + balance);
        tokenAmountText.setBounds(5, 355, 250, 10);
        frame.add(tokenAmountText);
        tokenImages = new JLabel[6];
        for(int i = 0; i < tokenImages.length; i++){
            tokenImages[i] = new JLabel(new ImageIcon(new ImageIcon("token/" + i + ".jpg").getImage().getScaledInstance(250, 120, Image.SCALE_SMOOTH)));
            tokenImages[i].setBounds(5 + i%3*270, 100 + (int)Math.ceil(i/3)*130, 250, 120);
            frame.add(tokenImages[i]);
        }

        //窗口
        frame.getContentPane().setBackground(new Color(180, 145, 85));
        frame.add(blackjackTop);
        frame.add(blackjack);
        frame.setUndecorated(true);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    public static int getChances(int playerTotal, int dealerUpCard){
        //0: Stand 1: Hit 2: DoubleDown
        if(playerTotal <= 8){
            return 1;
        }else if(playerTotal >= 17){
            return 0;
        }
        //[玩家的牌数值总和-9][庄家显示的牌的数值-2]
        int[][] table = {{1, 2, 2, 2, 2, 1, 1, 1, 1, 1}, {2, 2, 2, 2, 2, 2, 2, 2, 1, 1}, {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, {1, 1, 0, 0, 0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1}};
        return table[playerTotal-9][dealerUpCard-2];
    }
    public static void result(int token, String title, String second){
        System.out.println(title + second + "玩家获得筹码" + token);
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        balance += token;
        JButton endGame = new JButton("Quit");
        endGame.setBounds(10, 460, endGame.getPreferredSize().width, endGame.getPreferredSize().height);
        frame.add(endGame);
        endGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setVisible(false);
                frame.dispose();
            }
        });
        if(balance>0){
            JButton restart = new JButton("Restart");
            restart.setBounds(100, 460, restart.getPreferredSize().width, restart.getPreferredSize().height);
            frame.add(restart);
            restart.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    frame.setVisible(false);
                    frame.dispose();
                    newRound();
                }
            });
        }
    }
    public static void roundEnd(int token, String title, String second){
        //token: token gained, title:"dealer wins"/"player wins"/"push", second:"bust"/"blackjack"
        blackjack.remove(hitBtn);
        blackjack.remove(standBtn);
        blackjack.remove(chancesText);
        checkout = new JButton("Check out Result");
        checkout.setBounds(650, 5, checkout.getPreferredSize().width, checkout.getPreferredSize().height);
        blackjack.add(checkout);
        checkout.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                result(token, title, second);
            }
        });
    }
    public static void dealerAdd(){
        //给庄家发牌，直到>=17
        while(dealer.realTotalVal() < 17){
            dealerAddCard();
        }
        frame.remove(dealerFirstCard);
        dealerFirstCard = new JLabel((new ImageIcon(new ImageIcon(dealer.realFirstCard()).getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH))));
        dealerFirstCard.setBounds(10, 50, 100, 145);
        frame.add(dealerFirstCard);
        dealerTotal.setText("Total: " + dealer.realTotalVal());
        dealerTotal.setBounds(80, 18, dealerTotal.getPreferredSize().width, dealerTotal.getPreferredSize().height);
    }
    public static void hit(){
        playerAddCard();
        if(player.totalVal()>21){
            //player输
            roundEnd(-getTotalTokens(), "Dealer Wins", "Bust");
        }else if(player.totalVal()==21){
            //若house的总和不等于21，player赢
            stand();
        }
    }
    public static void stand(){
        //after stand
        dealerAdd();
        if(dealer.realTotalVal() > player.totalVal()){
            //dealer比player更高
            if(dealer.realTotalVal()<=21){
                //dealer没有爆牌
                roundEnd(-getTotalTokens(), "Dealer Wins", "");
            }else{
                //dealer爆牌
                roundEnd(getTotalTokens(), "Player Wins", "Bust");
            }
        }else if(dealer.realTotalVal() == player.totalVal()){
            roundEnd(0, "Push", "");
        }else{
            roundEnd(getTotalTokens(), "Player Wins", "");
        }
        blackjack.remove(hitBtn);
        blackjack.remove(standBtn);
        frame.revalidate();
        frame.repaint();
    }
    public static void playerAddCard(){
        playerCardImages.add(new JLabel((new ImageIcon(new ImageIcon(player.takeCard()).getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH)))));
        playerCardImages.get(playerCardImages.size()-1).setBounds(-100 + playerCardImages.size()*110, 300, 100, 145);
        frame.add(playerCardImages.get(playerCardImages.size()-1));
        playerTotal.setText("Total: " + player.totalVal());
        playerTotal.setBounds(80, 15, playerTotal.getPreferredSize().width, playerTotal.getPreferredSize().height);
        if(getChances(player.totalVal(), dealer.getUpCardVal()) == 0){
            chancesText.setText("We suggest you to Stand");
        }else if(getChances(player.totalVal(), dealer.getUpCardVal()) == 1){
            chancesText.setText("We suggest you to Hit");
        }else{
            chancesText.setText("We suggest you to Double Down");
        }
        chancesText.setBounds(300, 12, chancesText.getPreferredSize().width, chancesText.getPreferredSize().height);
        System.out.println("Chances of not bust if hit in %: " + player.calculateChanceOfWining());
        System.out.println("Chances of bust if hit in %: " + (100 - player.calculateChanceOfWining()));
        frame.revalidate();
        frame.repaint();
        if(player.totalVal()==21){
            stand();
        }
    }
    public static void dealerAddCard(){
        dealerCardImages.add(new JLabel((new ImageIcon(new ImageIcon(dealer.takeCard()).getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH)))));
        dealerCardImages.get(dealerCardImages.size()-1).setBounds(-100 + dealerCardImages.size()*110, 50, 100, 145);
        frame.add(dealerCardImages.get(dealerCardImages.size()-1));
        if(dealer.totalVal()==0){
            dealerTotal.setText("Total: ?");
        }else{
            dealerTotal.setText("Total: " + dealer.totalVal());
        }
        dealerTotal.setBounds(80, 18, dealerTotal.getPreferredSize().width, dealerTotal.getPreferredSize().height);
        if(dealerFirstCard!=null){
            frame.remove(dealerFirstCard);
        }
        dealerFirstCard = new JLabel((new ImageIcon(new ImageIcon(dealer.firstCard()).getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH))));
        dealerFirstCard.setBounds(10, 50, 100, 145);
        frame.add(dealerFirstCard);
        frame.revalidate();
        frame.repaint();
    }
    private static void removeDoubleDownBtn(){
        blackjack.remove(doubleDownBtn);
        frame.revalidate();
        frame.repaint();
    }
    public static void roundStart(){
        page = 2;
        frame.remove(tokenAmountText);
        frame.remove(insertToken);
        for(int i = 0; i < tokenImages.length; i++){
            frame.remove(tokenImages[i]);
        }
        for(int i = 0; i < tokenLabels.size(); i++){
            frame.remove(tokenLabels.get(i));
        }
        for(int i = 0; i < selections.length; i++){
            blackjackTop.remove(selections[i]);
        }
        frame.revalidate();
        frame.repaint();
        dealer = new Dealer();
        tokenLabels = new ArrayList<JLabel>();
        for(int i = 0; i < roundToken.size(); i++){
            tokenLabels.add(new JLabel((new ImageIcon(new ImageIcon("token/" + roundToken.get(i) + ".jpg").getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH)))));
            tokenLabels.get(i).setBounds(370-(roundToken.size()*40/2)+i*40, 215, 100, 50);
            /*try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}*/
            frame.add(tokenLabels.get(i));
            /*frame.revalidate();
            frame.repaint();*/
        }
        JLabel houseText = new JLabel("House");
        houseText.setFont(new Font("", Font.BOLD,20));
        houseText.setBounds(10, 10, houseText.getPreferredSize().width, houseText.getPreferredSize().height);
        blackjackTop.add(houseText);
        JLabel playerText = new JLabel("Player");
        playerText.setFont(new Font("", Font.BOLD,20));
        playerText.setBounds(10, 8, playerText.getPreferredSize().width, playerText.getPreferredSize().height);
        blackjack.add(playerText);
        doubleDownBtn = new JButton("Double Down");
        doubleDownBtn.setBounds(500, 5, doubleDownBtn.getPreferredSize().width, doubleDownBtn.getPreferredSize().height);
        blackjack.add(doubleDownBtn);
        doubleDownBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                removeDoubleDownBtn();
                player.isDoubleDowned = true;
                hit();
                if(player.totalVal()<21){
                    stand();
                }
            }
        });
        hitBtn = new JButton("Hit");
        hitBtn.setBounds(640, 5, hitBtn.getPreferredSize().width, hitBtn.getPreferredSize().height);
        hitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                hit();
                removeDoubleDownBtn();
            }
        });
        blackjack.add(hitBtn);
        standBtn = new JButton("Stand");
        standBtn.setBounds(720, 5, standBtn.getPreferredSize().width, standBtn.getPreferredSize().height);
        blackjack.add(standBtn);
        standBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                stand();
                removeDoubleDownBtn();
            }
        });
        //将roundDeck设置为新的deck并作为本局游戏的deck
        try {
            roundDeck = new Deck();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //洗牌
        roundDeck.shuffle();
        //player取2张牌
        playerCardImages = new ArrayList<JLabel>();
        playerTotal = new JLabel("Total: 0");
        blackjack.add(playerTotal);
        dealerCardImages = new ArrayList<JLabel>();
        dealerTotal = new JLabel("Total: ?");
        blackjackTop.add(dealerTotal);
        chancesText = new JLabel("We suggest you to Hit");
        blackjack.add(chancesText);
        dealerAddCard();
        dealerAddCard();
        playerAddCard();
        playerAddCard();
    }
    public static void main(String[] args) {
        newRound();
    }
}
