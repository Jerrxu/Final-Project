import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BossMissile2 here.
 * 
 * @author Jerry Xu
 * @version January 20, 2014
 */
public class BossMissile2 extends EnemyWeapons
{
    public BossMissile2()
    {
        setImage("yellow-draught.png");
    }
    
    public void addedToWorld(World world)
    {
        Map m = (Map)getWorld();
        turnTowards(m.getPlayerX(), m.getPlayerY());
    }
    
    protected void action() 
    {
        move(6);
        checkRemove();
    }

    public void checkRemove()
    {
        Actor tile = getOneIntersectingObject(Tile.class);
        if (tile != null)
        {
            getWorld().removeObject(this);
        }
        else
        {
            
            Player player = (Player)getOneIntersectingObject(Player.class);
            if (player != null)
            {
                player.enemyHit(player.getHealth()-5);
                getWorld().removeObject(this);
            }
        }
    }
}