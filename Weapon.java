import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Super class for all player weapons, keeps track of damage
 * 
 * @author David Liu, Keith Wong
 * @version January 20, 2014
 */
public abstract class Weapon extends Collision
{
    private int damage; //the damage of the weapon
    /**
     * Gives the damage of the weapon.
     * 
     * @param damage The damage of the weapon.
     */
    public Weapon(int damage){
        this.damage = damage;
    }
    
    /**
     *Returns the damage of the weapon.
     *
     *@return int The damage.
     */
    public int getDamage(){
        return damage;
    }
}
