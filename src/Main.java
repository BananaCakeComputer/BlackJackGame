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
        tokenAmountText.setText("Total Token(s): ¥0, Balance: ¥500");
        frame.revalidate();
        frame.repaint();
        roundToken = new ArrayList<Integer>();
    }
    public static void newRound(){
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
        tokenAmountText = new JLabel("Total Token(s): ¥0, Balance: ¥500");
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
    public static void hit(){
        playerAddCard();
        if(player.totalVal()>21){
            //player输
            frame.getContentPane().removeAll();
            frame.repaint();
        }else if(player.totalVal()==21){
            //若house的总和不等于21，player赢
        }
    }
    public static void stand(){
        System.out.println("Stand");
    }
    public static void playerAddCard(){
        playerCardImages.add(new JLabel((new ImageIcon(new ImageIcon(player.takeCard()).getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH)))));
        playerCardImages.get(playerCardImages.size()-1).setBounds(-100 + playerCardImages.size()*110, 300, 100, 145);
        frame.add(playerCardImages.get(playerCardImages.size()-1));
        playerTotal.setText("Total: " + player.totalVal());
        playerTotal.setBounds(80, 15, playerTotal.getPreferredSize().width, playerTotal.getPreferredSize().height);
        frame.revalidate();
        frame.repaint();
    }
    public static void dealerAddCard(){
        dealerCardImages.add(new JLabel((new ImageIcon(new ImageIcon(dealer.firstCard()).getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH)))));
        dealerCardImages.get(dealerCardImages.size()-1).setBounds(-100, 50, 100, 145);
        frame.add(dealerCardImages.get(dealerCardImages.size()-1));
        dealerCardImages.add(new JLabel((new ImageIcon(new ImageIcon(dealer.takeCard()).getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH)))));
        dealerCardImages.get(dealerCardImages.size()-1).setBounds(-100 + dealerCardImages.size()*110, 50, 100, 145);
        frame.add(dealerCardImages.get(dealerCardImages.size()-1));
        if(dealer.totalVal()==0){
            dealerTotal.setText("Total: ?");
        }else{
            dealerTotal.setText("Total: " + dealer.totalVal());
        }
        dealerTotal.setBounds(80, 18, dealerTotal.getPreferredSize().width, dealerTotal.getPreferredSize().height);
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
        JButton hitBtn = new JButton("Hit");
        hitBtn.setBounds(640, 5, hitBtn.getPreferredSize().width, hitBtn.getPreferredSize().height);
        hitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                hit();
            }
        });
        blackjack.add(hitBtn);
        JButton standBtn = new JButton("Stand");
        standBtn.setBounds(720, 5, standBtn.getPreferredSize().width, standBtn.getPreferredSize().height);
        blackjack.add(standBtn);
        standBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                stand();
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
        playerAddCard();
        playerAddCard();
        dealerCardImages = new ArrayList<JLabel>();
        dealerTotal = new JLabel("Total: ?");
        blackjackTop.add(dealerTotal);
        dealerAddCard();
        dealerAddCard();
    }
    public static void main(String[] args) {
        newRound();
    }
}
