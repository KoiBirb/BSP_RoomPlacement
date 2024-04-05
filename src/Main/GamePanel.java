package Main;

import Map.Map;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

        public final int tileSize = 8; // size of each cell
        public final int gridWidth = 150; // width of grid
        public final int gridHeight = 100; // height of grid

        public final int screenWidth = tileSize * gridWidth; // game screen width
        public final int screenHeight = tileSize * gridHeight; // game screen height

        int FPS = 60; // frames per second

        Thread gameThread;
        KeyInput keyInput = new KeyInput(this);


        public GamePanel(){
            this.setPreferredSize(new Dimension(screenWidth, screenHeight));
            this.setBackground(Color.BLACK);
            this.addKeyListener(keyInput);
            this.setFocusable(true);
        }

        public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public void run() {

            // Delta method FPS clock
            double drawInterval = 1000000000.0/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;

            while (gameThread != null) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if(delta >= 1) {
                    repaint();
                    delta--;
                    drawCount++;
                }
                if(timer>= 1000000000) {
                    System.out.println("FPS:" + drawCount);
                    drawCount = 0;
                    timer = 0;
                }
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.dispose();
    }
}
