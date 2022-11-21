import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.*;


public class BingoGame {
    static JPanel panelNorth;
    static JPanel panelCenter;
    static JLabel labelMessage;
    static JButton[] buttons= new JButton[16];
    static String[] images ={
       "f1.png","f2.png","f3.png","f4.png",
            "f5.png","f6.png","f7.png","f8.png",
            "f1.png","f2.png","f3.png","f4.png",
            "f5.png","f6.png","f7.png","f8.png"

    };
    static int openCount =0; //opened card count:0,1,2
    static int buttonIndexSave1 =0;
    static int buttonIndexSave2= 0; //0~15 card
    static Timer timer;
    static int tryCount = 0; // try count
    static int successCount =0; // bingo count :0~8 game end



    static class MyFrame extends JFrame implements ActionListener{
        public MyFrame(String title){
            super(title);
            this.setLayout(new BorderLayout());
            this.setSize(400,500);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            initUI(this);
            mixCard();
            this.pack();
        }

        static void PlaySound(String filename){
            File file =new File("./wav/"+filename);
            if(file.exists()){
                try{
                    AudioInputStream stream =AudioSystem.getAudioInputStream(file);
                    Clip clip=AudioSystem.getClip();
                    clip.open(stream);
                    clip.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("File Not Found!");
            }
        }

        @Override
        public void actionPerformed(ActionEvent e){
            if(openCount ==2){
                return;
            }


            JButton btn=(JButton)e.getSource();
            int index = getButtonIndex(btn);


            btn.setIcon(changeImage(images[index]));
            openCount++;
            if(openCount ==1){  //first card?
                buttonIndexSave1 =index;
            } else if(openCount ==2){
                buttonIndexSave2 =index;
                tryCount++;
                labelMessage.setText("Find Same Fruit! "+"Try "+tryCount);
                //judge logic

                boolean isBingo = checkCard(buttonIndexSave1, buttonIndexSave2);
                if(isBingo ==true){

                    PlaySound("bingo.wav");
                    openCount =0;
                    successCount++;
                    if (successCount==8){
                        labelMessage.setText("Mission Clear "+"Try "+tryCount);
                    }

                }else {
                    backToQuestion();

                }
            }

        }

        public void backToQuestion(){
            //Timer 1sec
            timer =new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Timer");


                    PlaySound("fail.wav");
                    openCount=0;
                    buttons[buttonIndexSave1].setIcon(changeImage("question.png"));
                    buttons[buttonIndexSave2].setIcon(changeImage("question.png"));
                    timer.stop();

                }
            });
            timer.start();
        }

        public boolean checkCard(int index1, int index2){
            if(index1 == index2){
                return false;
            }
            if(images[index1].equals(images[index2])){
                return true;
            }else {
                return false;
            }
        }

        public int getButtonIndex(JButton btn){
            int index = 0;
            for(int i=0; i<16; i++){
                if(buttons[i]==btn){
                    index=i;
                }
            }
            return index;
        }


    }
    static void mixCard(){
        Random rand = new Random();
        for(int i=0; i<1000; i++){
            int random =rand.nextInt(15)+1; //1~15
            //swap
            String temp = images[0];
            images[0]=images[random];
            images[random]=temp;
        }
    }

    static void initUI( MyFrame myFrame){
        panelNorth= new JPanel();
        panelNorth.setPreferredSize(new Dimension(400,100));
        panelNorth.setBackground(Color.BLUE);
        labelMessage= new JLabel("Find Same Fruit!"+"Try 0");
        labelMessage.setPreferredSize(new Dimension(400,100));
        labelMessage.setForeground(Color.WHITE);
        labelMessage.setFont(new Font("monaco",Font.BOLD,20));
        labelMessage.setHorizontalAlignment(JLabel.CENTER);
        panelNorth.add(labelMessage);
        myFrame.add("North",panelNorth);

        panelCenter =new JPanel();
        panelCenter.setLayout(new GridLayout(4,4));
        panelCenter.setPreferredSize(new Dimension(400,400));
        for(int i =0; i<16; i++){
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(100,100));
            buttons[i].setIcon(changeImage("question.png"));
            buttons[i].addActionListener(myFrame);
            panelCenter.add(buttons[i]);
        }
        myFrame.add("Center", panelCenter);

    }
    static ImageIcon changeImage(String filename){
        ImageIcon icon =new ImageIcon("./images/"+filename);
        Image originImage = icon.getImage();
        Image changedImage = originImage.getScaledInstance(80,80,Image.SCALE_SMOOTH);
        ImageIcon icon_new =new ImageIcon(changedImage);
        return icon_new;
    }

    public static void main(String[] args) {
        new MyFrame("Bingo Game");


    }
}
