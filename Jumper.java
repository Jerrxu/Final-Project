import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A walking/jumping type enemy.
 * 
 * Walks for 50 to 200 acts in a random direction. At end of move time, leaps into the air and lands.
 * Then waits for 50 to 200 acts until next move sequence.
 * 
 * @author Jerry Xu
 * @version Jan 13
 */
public class Jumper extends Enemy
{
    private int hJumpSpeed;
    private int vJumpSpeed;
    private boolean inAir;

    public Jumper()
    {
        super(10,5);
        fallC = 0;
        moveDelay = 1;
        moving = false;
        hJumpSpeed = 0;
        vJumpSpeed = 0;
        inAir = false;
    }

    protected void attackMove() 
    {
        setLocation(getX() + moveDirection + hJumpSpeed, getY() + fallC + vJumpSpeed);
        //checkRemove();
        if (projectileCooldown == 0)
        {
            checkHeroLocation();
        }
        else
        {
            projectileCooldown --;
        }
    }

    private void gravitize()
    {
        if (onPlatform())
        {
            fallC = 0;
            vJumpSpeed = 0;
            hJumpSpeed = 0;
        }
        else
        {
            fallC ++;
        }
    }

    protected void randomMove()
    {
        gravitize();
        if (moveDelay == 0)
        {
            moveDirection = Greenfoot.getRandomNumber(2) - 1;
            if (moveDirection == 0)
            {
                moveDirection = 1;
            }
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
                moving = !moving;
                randomJump(moveDirection);
                moveDirection = 0;
            }
        }
        checkStopMovement();
        if (moveDirection < 0){
            setImage("Enemy/JumperLeft.png");
        } else if (moveDirection > 0){
            setImage("Enemy/JumperRight.png");
        }

        setLocation(getX() + moveDirection + hJumpSpeed, getY() + fallC + vJumpSpeed);
    }

    private void randomJump(int d)
    {
        if (d == 1)
        {
            hJumpSpeed = 3;
        }
        else
        {
            hJumpSpeed = -3;
        }
        vJumpSpeed = -(Greenfoot.getRandomNumber(8) + 12);
        inAir = true;
    }

    private void checkStopMovement()
    {
        Actor p1 = getOneObjectAtOffset(5, 0, Tile.class);
        if (p1 != null)
        {
            hJumpSpeed = 0;
            moveDirection = -moveDirection;
        }

        Actor p2 = getOneObjectAtOffset(-5, 0, Tile.class);
        if (p2 != null)
        {
            hJumpSpeed = 0;
            moveDirection = -moveDirection;
        }

        Actor p3 = getOneObjectAtOffset(0, 5, Tile.class);
        if (p3 != null)
        {
            vJumpSpeed = 0;
        }
    }

    protected void onDeath(){}
}