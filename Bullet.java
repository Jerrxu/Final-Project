import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A bullet. No ammo required to shoot this. Weak.
 * 
 * @author Keith Wong 
 * @version January 20, 2014
 */
public class Bullet extends Weapon
{
    private boolean goingLeft;
    private int speed = 15; //horizontal velocity
    private int damage = 2; //damage of the bullets
    /**
     * Creates a bullet.
     * 
     * @param goingLeft Determines whether the bullet is moving left or right.
     */
    public Bullet(boolean goingLeft){
        super(2);
        this.goingLeft = goingLeft;
    }

    /**
     * Moves, removes self when it touches a tile.
     */
    public void act() 
    {

        if(goingLeft == true){
            setLocation(getX() - speed, getY());
            setImage("bulletLeft.png");
        }
        else if (goingLeft == false){
            setLocation(getX() + speed, getY());
            setImage("bulletRight.png");
        } 

        if (touch(Tile.class)){
            Tile tile = (Tile)getOneIntersectingObject(Tile.class);
            if(tile.isEasyBreakable()){
                getWorld().removeObject(tile);
                getWorld().removeObject(this);
            }else{            
                getWorld().removeObject(this);
            }
        }
    }
}
