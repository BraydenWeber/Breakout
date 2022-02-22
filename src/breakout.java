import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import java.awt.*;
import java.awt.event.MouseEvent;

class Breakout extends GraphicsProgram {


    /*
    How do i know how many points i have
    Powerups
    Multiple levels
    An animation for a brick broke
     */

    private Ball ball;
    private Paddle paddle;
    private int lives = 3;

    private int numBrickInRow;

    private GLabel livesLabel;

    private Color[] rowColors = {Color.red, Color.red, Color.orange, Color.orange, Color.yellow, Color.yellow, Color.GREEN, Color.GREEN, Color.cyan, Color.cyan};

    private int[] hitLives = {5, 5, 4, 4, 3, 3, 2, 2, 1, 1};

    private Color[] lifeHealth = {Color.cyan, Color.GREEN, Color.YELLOW, Color.orange, Color.red};


    @Override
    public void init(){
        livesLabel = new GLabel("Lives:" + " " + String.valueOf(lives));
        add(livesLabel, 50, 15);

        numBrickInRow = getWidth() / (Brick.WIDTH + 5);


        setBackground(Color.gray);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBrickInRow; col++) {

                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY = Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX, brickY, rowColors[row], row, hitLives[row]);
                add(brick);
            }

        }

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50, 10);
        add(paddle);
    }


    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){

        //Constrain the Paddle to the edges of the window
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth()/2)){
            paddle.setLocation(me.getX()-paddle.getWidth()/2, paddle.getY());
        }


    }

    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            handleCollisions();

            if (ball.lost){
                handleLoss();
            }

            pause(5);
        }
    }

    private void handleCollisions() {
        // create a container
        GObject obj = null;

        if(obj == null){
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY());
        }

        if(obj == null){
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        if(obj == null){
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        if(obj == null){
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }

        if (obj != null){
            if (obj instanceof Paddle){


                if (ball.getX() <= (paddle.getX() + (paddle.getWidth() / 5))){
                    ball.bounceLeft();
                } else if(ball.getX() >= (paddle.getX()+ (paddle.getWidth() * 0.8))){
                    ball.bounceRight();
                } else {
                    ball.bounce();
                }
            }

            if (obj instanceof Brick){
                ((Brick) obj).loseLife();
                switch (((Brick) obj).getLives()){
                    case 1: obj.setColor(rowColors[9]);
                        break;
                    case 2: obj.setColor(rowColors[7]);
                        break;
                    case 3: obj.setColor(rowColors[5]);
                        break;
                    case 4: obj.setColor(rowColors[3]);
                        break;
                    case 5: obj.setColor(rowColors[1]);
                        break;
                }
                ball.bounce();

                if (((Brick) obj).getLives() == 0){
                    
                    remove(obj);
                }
            }
        }

    }

    private void handleLoss() {
        ball.lost = false;
        reset();
    }

    private void reset() {
        // put the ball back where you found it
        ball.setLocation(getWidth()/2, 350);

        // put the paddle back
        paddle.setLocation(230, 430);



        // wait for click
        waitForClick();
        lives -= 1;
        livesLabel.setLabel("Lives:" + " " + String.valueOf(lives));
        if(lives == 0) {
            Dialog.showMessage("You lost loser");
            removeAll();
            lives = 3;
            init();
            return;
        }

    }


    public static void main(String[] args) {
        new Breakout().start();
    }
}