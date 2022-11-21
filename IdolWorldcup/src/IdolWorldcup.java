



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class IdolWorldcup {
    static JPanel panelNorth;
    static JPanel panelCenter;
    static JLabel labelMessage;
    static JLabel labelVs;
    static JButton buttonLeft;
    static JButton buttonRight;
    static String[]images={
            "i1.png","i2.png","i3.png","i4.png","i5.png",
            "i6.png","i7.png","i8.png","i9.png","i10.png",
            "i11.png","i12.png","i13.png","i14.png","i15.png",
            "i16.png",
    };
    static int imageIndex=2;

    static ImageIcon changeImage(String filename){
        ImageIcon icon= new ImageIcon("./"+filename);
        Image originImage = icon.getImage();
        Image changedImage= originImage.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon icon_new =new ImageIcon(changedImage);
        return icon_new;
    }






    static class MyFrame extends JFrame implements ActionListener {
        public MyFrame(String title) {
            super(title);
            //UI Init
            this.setSize(450,250);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



            panelNorth= new JPanel();
            labelMessage= new JLabel("Find Your Women!");
            labelMessage.setFont(new Font("Arial",Font.BOLD,20));
            panelNorth.add(labelMessage);
            this.add("North",panelNorth);

            panelCenter= new JPanel();
            labelVs = new JLabel("vs");
            labelVs.setFont(new Font("Arial",Font.BOLD,20));
            buttonLeft= new JButton("leftButton");
            buttonLeft.setIcon(changeImage("i1.png"));
            buttonLeft.setPreferredSize(new Dimension(190,200));

            buttonRight= new JButton("RightButton");
            buttonRight.setIcon(changeImage("i2.png"));
            buttonRight.setPreferredSize(new Dimension(190,200));

            buttonLeft.addActionListener(this);
            buttonRight.addActionListener(this);
            panelCenter.add(buttonLeft);
            panelCenter.add(labelVs);
            panelCenter.add(buttonRight);
            this.add("Center",panelCenter);

            this.pack();


        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(imageIndex ==16){
                //last pic!
                labelMessage.setText("Found your Women!");
                if(e.getActionCommand().equals("LeftButton")){
                    buttonRight.hide();
                    labelVs.hide();
                }else {
                    buttonLeft.hide();
                    labelVs.hide();

                }
            }

            if(e.getActionCommand().equals("RightButton")){
                // LeftButton clicked!
                buttonLeft.setIcon(changeImage(images[imageIndex]));
                imageIndex++;
            }else{
                // RightButton clicked!
                buttonRight.setIcon(changeImage(images[imageIndex]));
                imageIndex++;


            }



        }
    }

    public static void main(String[] args) {
        new MyFrame("Idol World Cup");

    }
}
