import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A missile. Ammo is required to shoot this. Stronger than bullet.
 * 
 * @author Keith Wong
 * @version January 20, 2014
 */
public class Missile extends Weapon
{
    private boolean goingLeft;
    private int speed = 13; //horizontal velocty of the missile
    private int damage = 4;// the damage of the missile
    /**
     * Creates a missile.
     * 
     * @param goingLeft Determines whether the bullet is going left or right.
     */
    public Missile(boolean goingLeft){
        super(4);
        this.goingLeft = goingLeft;
    }

    /**
     * Moves. Removes self when it touches a tile.
     */
    public void act() 
    {

        if(goingLeft == true){
            setLocation(getX() - speed, getY());
            setImage("missileLeft.png");
        }
        else if (goingLeft == false){
            setLocation(getX() + speed, getY());
            setImage("missileRight.png");
        }

        if (touch(Tile.class) == true){
            getWorld().removeObject(this);
        }
    }    
}
