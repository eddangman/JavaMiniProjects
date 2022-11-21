package CoinEat;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;


public class CoinEat extends JFrame {

    private Image bufferImage;
    private Graphics screenGraphic;
    private Clip clip;


    private Image backgroundImage = new ImageIcon("src/images/mainScreen.PNG").getImage();
    private Image player = new ImageIcon("src/images/player.png").getImage();
    private Image coin = new ImageIcon("src/images/coin.png").getImage();

    private int playerX, playerY;
    private int playerWidth =player.getWidth(null);
    private int playerHeight=player.getHeight(null);
    private int coinX, coinY;
    private int coinWidth = coin.getWidth(null);
    private int coinHeight = coin.getHeight(null);

    private int score;
    private boolean up, down, left, right;

    public  CoinEat(){
        setTitle("동전 먹기 게임");
        setVisible(true);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_W:
                        up=true;
                        break;
                    case KeyEvent.VK_S:
                        down=true;
                        break;
                    case KeyEvent.VK_A:
                        left=true;
                        break;
                    case KeyEvent.VK_D:
                        right=true;
                        break;
                }
            }
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_W:
                        up=false;
                        break;
                    case KeyEvent.VK_S:
                        down=false;
                        break;
                    case KeyEvent.VK_A:
                        left=false;
                        break;
                    case KeyEvent.VK_D:
                        right=false;
                        break;
                }
            }

        });
        init();//intit 선언을 부모클래스한테서 생성했어야했다.
        while (true){
            try {
                Thread.sleep(20);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            keyProcess();
            crashCheck();
        }
    }



    public void init(){
        score=0;


        playerX=(500 - playerWidth)/2;
        playerY=(500 - playerHeight)/2;

        coinX =(int) (Math.random()*(501-playerWidth));
        coinY =(int) (Math.random()*(501-playerHeight-30))+30;

        playSound("src/audio/backgroundMusic.wav",true);



    }
    public void keyProcess(){
        if(up&&playerY-3>30)playerY-=3;
        if(down&&playerY+playerHeight+3<500)playerY+=3;
        if(left&&playerX-3>0)playerX-=3;
        if(right&&playerX+playerWidth+3<500)playerX+=3;
    }
    public void crashCheck(){
        if (playerX+playerWidth >coinX && coinX+coinWidth > playerX && playerY + playerHeight > coinY && coinY + coinHeight > playerY){
            score+=100;
            playSound("src/audio/getCoin.wav",false);
            coinX =(int) (Math.random()*(501-playerWidth));
            coinY =(int) (Math.random()*(501-playerHeight-30))+30;

        }

    }
    public void playSound(String pathName, boolean isloop){
        try {
            clip= AudioSystem.getClip();
            File audioFile= new File(pathName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioStream);
            clip.start();
            if(isloop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);

        }catch (LineUnavailableException e) {

            e.printStackTrace();

        }catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void paint(Graphics g){
        bufferImage = createImage(500,500);
        screenGraphic= bufferImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(bufferImage,0,0,null);

    }

    public void screenDraw(Graphics g){

        g.drawImage(backgroundImage,0,0,null);
        g.drawImage(coin,coinX,coinY,null);
        g.drawImage(player,playerX,playerY,null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,40));
        g.drawString("SCORE : "+score,30,80);
        this.repaint();
    }









    public static void main(String[] args) {
        new CoinEat();

    }
}
