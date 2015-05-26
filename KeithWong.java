import greenfoot.*;
import java.util.ArrayList; 
/**
 * Splits up when killed into smaller enemies, jumps and follows the player around
 * 
 * @author Keith Wong
 * @version January 20, 2014
 */
public class KeithWong extends Enemy
{
    //moving things
    private int speedX = 3; // horizontal speed
    private int speedY = 6; // jump speed
    private int deltaX = 0; //change in X per act
    private int deltaY = 0; // change in Y per act
    //image things
    private GreenfootImage g = new GreenfootImage("Enemy/k1Left.png");
    private int imgHeight = g.getHeight()/2;
    private int imgWidth = g.getWidth()/2;
    private int scale; // higher number = smaller image

    //on spawn
    private boolean flyRight;
    private boolean producedFromSplit = false; // if true, will fly in a certain direction

    //follow player
    private Player p;
    private ArrayList player;

    //gravity
    private int gravity = 0;

    //number of acts to jump
    private int jumpCycles = 4;

    //collision
    private boolean hitGround = false;
    private boolean jumped = false;
    private Tile tile; 

    /**
     * Creates an enemy KeithWong.
     * 
     * @param producedFromSplit whether or not the enemy was spawned from another enemy dying
     * @param scale how big to make the object. Bigger number = smaller enemy
     */
    public KeithWong(boolean producedFromSplit, int scale){
        super(2,5);
        this.producedFromSplit = producedFromSplit;
        this.scale = scale;
    }

    /**
     * Sets the image, spawn movements, and gets the player.
     * 
     * @param w The World.
     */
    protected void addedToWorld(World w){
        g.scale(imgWidth*2/scale, imgHeight*2/scale);
        imgHeight = g.getHeight()/2;
        imgWidth = g.getWidth()/2;
        setImage(g);
        scale++;
        if (producedFromSplit = true){
            if (getWorld().getObjectsAt(getX() +imgWidth + 5, getY(), KeithWong.class).size() > 0){
                flyRight = true;
            } else {
                flyRight = false;
            }
            jump();
        }
        if (flyRight = true){
            deltaY = 6;
            deltaX = -4;
        } else if (flyRight = false){
            deltaY = 6;
            deltaX = 4;
        }

        if (onPlatform() == true){
            jumpCycles = 4;
        }

        player = (ArrayList) getWorld().getObjects(Player.class);
        p = (Player) player.get(0);
        setLocation(getX() + deltaX, getY() + deltaY);

    }

    /**
     * Nothing.
     */
    protected void attackMove(){
    }

    /**
     * Moves KeithWong. Jumps around and follows the player.
     * 
     */
    protected void randomMove(){
        gravity();
        //stops the enemy after the split

        if (onPlatform() == true && Math.abs(this.getX() - p.getX()) < 500){
            if (onPlatform() == true){
                if (this.getX() > p.getX()){
                    moveLeft();
                } else if (this.getX() < p.getX()){
                    moveRight();
                } else {
                    deltaX = 0;
                }
            }
            if (this.getY() > p.getY()){
                jump();
            }
        }

        if (jumped == true){
            jump();
        }

        setLocation(getX() + deltaX, getY() + deltaY + gravity);
    }

    /**
     * Makes KeithWong come back down.
     * 
     */
    private void gravity(){
        if (onPlatform() == false){
            gravity++;
        } else {
            gravity = 0;
        }
    }

    /**
     * Moves left.
     */
    private void moveLeft(){
        deltaX = -speedX;
        g = new GreenfootImage("Enemy/k1Left.png");
        g.scale(imgWidth*2, imgHeight*2);
        setImage(g);
    }

    /**
     * Moves right.
     */
    private void moveRight(){
        deltaX = speedX;
        g = new GreenfootImage("Enemy/k1Right.png");
        g.scale(imgWidth*2, imgHeight*2);
        setImage(g);
    }

    /**
     * Jumps. Will jump for jumpCycles amount of acts.
     */
    private void jump(){
        if (jumpCycles > 0){
            deltaY -= speedY;
            jumpCycles--;
            jumped = true;
        }
    }

    /**
     * Spawns 2 more of this (smaller versions) when it dies.
     * 
     */
    protected void onDeath(){
        if (scale < 5){
            getWorld().addObject(new KeithWong(true, scale), (((SWorld) getWorld())).getScrolledX() + getX() + 10, (((SWorld) getWorld())).getScrolledY() + getY()) ;
            getWorld().addObject(new KeithWong(true, scale), (((SWorld) getWorld())).getScrolledX() + getX() - 10, (((SWorld) getWorld())).getScrolledY() + getY()) ;
        }
    }

    /**
     * Checks to see if this is on a tile.
     * 
     * @return boolean True when on a Tile, false when not.
     */
    protected boolean onPlatform(){
        tile = (Tile) getOneObjectAtOffset(0, imgHeight + 1, Tile.class);

        if (tile != null){
            return true;
        }
        return false;
    }
}