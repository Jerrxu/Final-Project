import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Super Missile. Requires ammo to shoot. Stronger than missile.
 * 
 * @author Keith Wong
 * @version January 20, 2014
 */
public class SuperMissile extends Weapon
{
    private boolean goingLeft;
    private int speed = 15;
    private int damage = 10; //the damage of the bullet
    /**
     * Creates a Super missile.
     * 
     * @param goingLeft determines whether the super missile is going left or right.
     */
    public SuperMissile(boolean goingLeft){
        super(10);
        this.goingLeft = goingLeft;
    }

    /**
     * Moves. Removes self if it hits a tile.
     */
    public void act() 
    {

        if(goingLeft == true){
            setLocation(getX() - speed, getY());
            setImage("SuperMissileLeft.png");
        }
        else if (goingLeft == false){
            setLocation(getX() + speed, getY());
            setImage("SuperMissileRight.png");
        }

        if (touch(Tile.class)){
            Tile tile = (Tile)getOneIntersectingObject(Tile.class);
            if(tile.isSuperBreakable()){
                getWorld().removeObject(tile);
                getWorld().removeObject(this);
            }else{            
                getWorld().removeObject(this);
            }
        }    
    }
}
