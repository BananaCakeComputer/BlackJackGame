import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main{
    private static int page;
    private static JLabel[] tokenImages;
    public static ArrayList<Integer> roundToken;
    public static JFrame frame;
    private static int denomination[] = {1, 5, 10, 20, 50, 100};
    private static JLabel tokenAmountText;
    public static void mouseEnter(int btnIdx){
        tokenImages[btnIdx].setBorder(new LineBorder(new Color(0, 90, 255), 4, true));
    }
    public static void mouseExit(int btnIdx){
        tokenImages[btnIdx].setBorder(null);
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
        JLabel token = new JLabel(new ImageIcon(new ImageIcon("token/" + imgIdx + ".jpg").getImage().getScaledInstance(170, 80, Image.SCALE_SMOOTH)));
        token.setBounds(-35+roundToken.size()*40, 370, 170, 80);
        frame.add(token);
        //没有这两行图片不会显示, so weird
        token.setBorder(new LineBorder(new Color(0, 0, 0), 0, true));
        token.setBorder(null);
        tokenAmountText.setText("Total token(s): ¥" + getTotalTokens());

    }
    public static void main(String[] args) {
        roundToken = new ArrayList<Integer>();
        page = 1;
        //Create game window
        frame = new JFrame("BlackJack");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set its size
        frame.setSize(800, 500);

        //下面板
        JPanel blackjack = new JPanel();
        blackjack.setBounds(-1, 461, 802, 42);
        blackjack.setBackground(new Color(175, 100, 0));
        blackjack.setBorder(new LineBorder(new Color(70, 40, 0)));
        blackjack.setLayout(null);
        JButton DealBtn = new JButton("Deal");
        DealBtn.setBounds(720, 5, DealBtn.getPreferredSize().width, DealBtn.getPreferredSize().height);
        blackjack.add(DealBtn);

        //上面板
        JPanel blackjackTop = new JPanel();
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
        JButton[] selections = new JButton[6];
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
        JLabel insertToken = new JLabel("Select the amount of token");
        insertToken.setFont(new Font("", Font.BOLD,35));
        insertToken.setBounds(10, 50, 500, 40);
        frame.add(insertToken);
        tokenAmountText = new JLabel("Total Token(s): ¥0");
        tokenAmountText.setBounds(5, 355, 200, 10);
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
}
