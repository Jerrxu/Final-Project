import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The boss's weakspot. E.g. The only spot where he'll take damage.
 * Has specific health and if is destroyed, the boss is dead.
 * 
 * @author Jerry Xu
 * @version Jan 19, v1.2
 */
public class BossEye extends Enemy
{
    private int health; //the number of hits required to defeat the boss, essentially

    public BossEye()
    {
        super(300,15);
        setImage("BossEye.png");
        health = 250;
    }

    /**
     * Act method. Maintains its position above the boss.
     */
    public void act() 
    {
        if(((Map)(getWorld())).checkPause()==false){
            Map m = (Map)getWorld();
            setLocation(m.getBossX(), m.getBossY() - 150);
            checkHitAndDeath();
        }
    }

    /**
     * Checks if the eye is hit. If so, minus 1 to health.
     * Then checks if the health is 0. If so, remove the object. The boss is dead.
     */
    private void checkHitAndDeath()
    {
        Weapon bullet = (Weapon)getOneIntersectingObject(Weapon.class);
        if (bullet != null)
        {
            health -=bullet.getDamage();
            getWorld().removeObject(bullet);
        }

        if (health <=0)
        {
            Map m = (Map)getWorld();
            m.removeObject(this);
            m.removeBoss();
            Greenfoot.setWorld(new Screen(4));
        }
    }

    public int getHp()
    {
        return health;
    }

    protected void onDeath(){}

    protected void attackMove(){}

    protected void randomMove(){}
}