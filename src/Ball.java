import acm.graphics.GCanvas;
import acm.graphics.GOval;

public class Ball extends GOval {

    private double deltaX = 1;
    private double deltaY = -1;
    private GCanvas screen;
    public boolean lost = false;

    public Ball(double x, double y, double size, GCanvas screen){
        super(x, y, size, size);
        setFilled(true);
        this.screen = screen;
    }

    public void handleMove(){
        // move the ball
        move(deltaX, -deltaY);



        // check to see if the ball is too high
        if(getY() <= 0){
            deltaY *= -1;
        }

        // check to see if the ball is too low
        if(getY() >= screen.getHeight() - getHeight()){
            lost = true;
            deltaX = 1;
            deltaY = -1;
        }

        // check to see if the ball hits the left side of the screen
        if(getX() <= 0){
            deltaX *= -1;
        }

        // check to see if the ball hits the right side of the screen
        if(getX() >= screen.getWidth()-getWidth()){
            deltaX *= -1;
        }
    }

    public void bounce(){
        //flip the sign of deltaY
        deltaY *= -1;
    }

    public void bounceLeft(){
        //flip the sign of deltaY
        deltaY *= -1;

        //force deltaX to be negative
        deltaX = -Math.abs(deltaX);

    }

    public void bounceRight(){
        //flip the sign of deltaY
        deltaY *= -1;
        //force deltaX to be positive
        deltaX = Math.abs(deltaX);
    }
}