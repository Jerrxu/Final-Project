import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyMissile1 here.
 * 
 * @author Jerry Xu
 * @version January 20, 2014
 */
public class BossMissile1 extends EnemyWeapons
{
    private int turn;
    
    public BossMissile1(int t)
    {
        this.turn = t;
    }
    
    public void addedToWorld(World world)
    {
        turn(turn);
    }
    
    /**
     * Act - do whatever the EnemyMissile1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
   protected void action() 
    {
        move(5);
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