import acm.graphics.GObject;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class breakout extends GraphicsProgram {

    /*
    1)What do I do about lives?
    2)All of the bricks take the same number of hits
    3) How do I know how many live I have?
    4) How do I know how many points I have?
    5) What happens if I run out of lives?
    6) How can I tell that a brick has been hit?

    7)Powerups in some bricks?
    7) Multiple levels?
    9) An animation for a broken brick


    */
    private Ball ball;
    private Paddle paddle;

    private int lives= 3;

    private int numBricksInRow;

    private  Color[] rowColors = {Color.GREEN, Color.GREEN, Color.MAGENTA, Color.MAGENTA,Color.RED, Color.RED, Color.YELLOW, Color.YELLOW, Color.ORANGE, Color.ORANGE};

    @Override
    public void init(){

        numBricksInRow= getWidth() / (Brick.WIDTH + 5);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {



                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY =  Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                        Brick brick = new Brick(brickX, brickY, rowColors[row], row );
                        add(brick);

            }
        }

        ball= new Ball(getWidth()/2,350,10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50, 10);
        add(paddle);

    }


    @Override
    public  void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent m){


        //constrain the paddle to the edges of the window
        if ((m.getX()<getWidth()- paddle.getWidth()/2) && (m.getX()> paddle.getWidth()/2)){
            paddle.setLocation(m.getX()- paddle.getWidth()/2, paddle.getY());
        }
    }


    private void gameLoop(){
        while(true) {
            // move the ball
            ball.handleMove();

            //check for collisons
            handleCollisions();

            // check if the ball was lost
            if (ball.lost){
                handleLoss();
            }

            pause(5);
        }
    }

    private void handleCollisions(){
        //create a container
        GObject obj = null;

        //see if we hit something
        if(obj == null){
            obj = this.getElementAt( ball.getX()+ball.getWidth(), ball.getY());
        }
        //check lower top corner
        if(obj == null){
            obj = this.getElementAt( ball.getX(),ball.getY()+ ball.getHeight());
        }

        //check lower right corner
        if(obj == null){
            obj = this.getElementAt( ball.getX()+ball.getWidth(), ball.getY() +ball.getHeight());
        }

        //lower left
        if(obj == null){
            obj = this.getElementAt( ball.getX()+ball.getWidth(), ball.getY());
        }



       //if we hit something...
        if (obj != null) {
            //what did i hit?


            if (obj instanceof Paddle){


                if(ball.getX() < (paddle.getX() + (paddle.getWidth() * .2))){
                    // I hit the left edge of the paddle
                    ball.bounceLeft();
                }else if (ball.getX() > (paddle.getX() + (paddle.getWidth()  *.8))) {
                    //I hit the right edge of the paddle
                    ball.bounceRight();
                }else {
                    //I hit the middle of the paddle
                    ball.bounce();
                }
                }



            //did I hit a brick
            if(obj instanceof Brick){
                //ball bounces
                ball.bounce();
                //destroy brick
                this.remove(obj);
            }

            }
        }


    private void handleLoss() {
        ball.lost=false;
        reset();
    }

    private void reset(){
        // put the ball back where you found it
        ball.setLocation(getWidth()/2, 350);
        //put the paddle back
        paddle.setLocation(230,430);
        // wait for click
        waitForClick();
    }


    public static void main(String[] args) {
        new breakout().start();
    }

// lets gooo
        //react accordingly

        //if we make it to the end and obj is still null, that means we hit nothing/
    }



