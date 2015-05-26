import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A flying-type enemy.
 * 
 * Flies for 50 to 200 acts every time, then rests for 50 to 200 acts.
 * At the beginning of every move, it chooses a random direction to fly in.
 * If it encounters a tile, it will turn around.
 * 
 * @author Jerry Xu
 * @version Jan 19
 */
public class Flight extends Enemy
{
    private int speed;

    public Flight()
    {
        super(15,3);
        moveDelay = 0;
    }

    public void addedToWorld(World w){
        setImage("Flight.png");
    }
    
    /**
     * Act - do whatever the Flight wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected void attackMove() 
    {
        if (projectileCooldown == 0)
        {
            checkHeroLocation();
        }
        else
        {
            projectileCooldown --;
        }
    }

    protected void randomMove()
    {
        if (moveDelay == 0)
        {
            turnTowards(getX() + Greenfoot.getRandomNumber(20) - 10, getY() + Greenfoot.getRandomNumber(20) - 10);
            speed = 1;
            moving = true;
            moveDelay = Greenfoot.getRandomNumber(150) + 50;
            moveTime = Greenfoot.getRandomNumber(150) + 50;
        }
        else if (!moving)
        {
            moveDelay --;
        }

        if (moveTime > 0)
        {
            moveTime --;
            if (moveTime == 0)
            {
                speed = 0;
                moving = !moving; 
            }
        }

        checkStopMovement();
        move(speed);
    }

    private void checkStopMovement()
    { 
        Actor p = getOneIntersectingObject(Tile.class);
        if (p != null)
        {
            turn(180);
        }
    }
    
    protected void onDeath(){}
}