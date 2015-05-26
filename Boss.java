import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The main boss. Currently has 3 unique attacks.
 * To be loaded along with rendering of map: map4boss.TXT
 * 
 * @author Jerry Xu
 * @version Jan 19, v1.2
 */
public class Boss extends Collision
{
    protected int moveDirection, moveDelay, moveTime, fallC, projectileCooldown;
    protected boolean moving;
    private int hJumpSpeed, vJumpSpeed, attackCooldown, attackChoice;
    private int rSATurn, rSASequence;
    private int fAPATurn;
    private int wATurn;
    private int actCount;
    private boolean inAir;
    private boolean usingRandomSprayAttack, usingFireAtPlayerAttack, usingWaveAttack;
    private BossEye eye = new BossEye();
    //image loop
    private SpritePlayer bossSprite;
    private int index = 0;
    public Boss()
    {
        fallC = 0;
        moveDelay = 1;
        moveDirection = 0;
        moving = false;
        hJumpSpeed = 0;
        vJumpSpeed = 0;
        inAir = false;
        usingRandomSprayAttack = false;
        rSATurn = 0;
        rSASequence = 1;
        fAPATurn = 0;
        wATurn = 0;
        actCount = 0;
        setImage("boss0.PNG");
        bossSprite = new SpritePlayer("boss", 4);
    }

    public void addedToWorld(World world)
    {
        getWorld().addObject(eye, getX(), getY() - 150);
    }

    public void act() 
    {
        if(((Map)(getWorld())).checkPause()==false){
            gravitize();
            checkStopMovement();
            index++;
            if (index > 3){
                index = 0;
            }
            this.setImage(bossSprite.returnImage(index));
            setLocation(getX(), getY() + fallC);
            checkAttack();
            moveSequence();
            checkHitPlayer();
        }
    }

    /**
     * Makes the actor fall, simulating gravity. The greatest fall speed is 20pixels/act
     */
    private void gravitize()
    {
        if (onTile())
        {
            fallC = 0;
            vJumpSpeed = 0;
            hJumpSpeed = 0;
        }
        else if (fallC < 20)
        {
            fallC ++;
        }
    }

    /**
     * Checks if the boss is on a tile.
     * 
     * @return True if boss is on a tile. False otherwise.
     */
    private boolean onTile()
    {
        Actor under = getOneObjectAtOffset(0, 150, Tile.class);
        if (under != null)
        {
            return true;
        }
        return false;
    }

    /**
     * Checks to stop the movement of the boss when it hits a solid object.
     */
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

    /**
     * Checks which attack to use.
     */
    private void checkAttack()
    {
        if (usingRandomSprayAttack)
        {
            randomSprayAttack();
        }
        else if (usingFireAtPlayerAttack)
        {
            fireAtPlayerAttack();
        }
        else if (usingWaveAttack)
        {
            waveAttack();
        }
    }

    /**
     * Sprays a rotating shower of projectiles, starting from rotation = 0 (leftward), going clockwise.
     * Lasts for variable amount of acts depending on the health of the BossEye. 
     */
    private void randomSprayAttack()
    {
        rSATurn += (8 + eye.getHp());
        Map m = (Map)getWorld();
        BossMissile1 bM = new BossMissile1(rSATurn);
        m.addObject(bM, getX(), getY());
        if (rSATurn == 360)
        {
            rSATurn = 0;
            usingRandomSprayAttack = false;
        }
    }

    /**
     * Fires directly at the player's location, regardless of obstacles in the way.
     * Lasts for variable amount of acts dependent on the eye's health.
     */
    private void fireAtPlayerAttack()
    {
        fAPATurn ++;
        if (fAPATurn % (20 + eye.getHp()) == 0)
        {
            Map m = (Map)getWorld();
            BossMissile2 eM = new BossMissile2();
            m.addObject(eM, getX(), getY());
        }
        else if (fAPATurn > 161 + eye.getHp())
        {
            usingFireAtPlayerAttack = false;
            fAPATurn = 0;
        }
    }

    /**
     * Pulsates 3 waves of projectiles, each with 2 times the concentration of projectiles than the last.
     * Lasts for 100 acts.
     */
    private void waveAttack()
    {
        wATurn ++;
        if (wATurn == 1)
        {
            for(int i = 0; i >= -360; i -= 40)
            {
                Map m = (Map)getWorld();

                BossMissile1 bM = new BossMissile1(i);
                m.addObject(bM, eye.getX(), eye.getY() - 100);
            }
        }
        else if (wATurn == 51)
        {
            for(int i = 0; i >= -360; i -= 20)
            {
                Map m = (Map)getWorld();

                BossMissile1 bM = new BossMissile1(i);
                m.addObject(bM, eye.getX(), eye.getY());
            }
        }
        else if (wATurn == 101)
        {
            for(int i = 0; i >= -360; i -= 10)
            {
                Map m = (Map)getWorld();

                BossMissile1 bM = new BossMissile1(i);
                m.addObject(bM, eye.getX(), eye.getY());
            }
            wATurn = 0;
            usingWaveAttack = false;
        }
    }

    /**
     * Describes the boss's moves since the beginning of the boss stage.
     * The boss's movement and attacks are preset in 2000 act intervals.
     */
    private void moveSequence()
    {
        actCount ++;
        if (actCount <= 400)
        {
            setLocation(getX() + 1, getY());
            setImage("boss2.PNG");
            if (actCount == 50)
            {
                usingRandomSprayAttack = true;
            }
            else if (actCount == 100)
            {
                usingFireAtPlayerAttack = true;
            }
            else if (actCount == 300)
            {
                usingWaveAttack = true;
            }
        }
        else if (actCount <= 600)
        {
            //stays still for 200 acts
            setImage("boss3.PNG");
            if (actCount == 401)
            {
                usingFireAtPlayerAttack = true;
            }
            else if (actCount == 570)
            {
                usingRandomSprayAttack = true;
            }
        }
        else if (actCount <= 1000)
        {
            setImage("boss0.PNG");
            setLocation(getX() - 1, getY());
            if (actCount == 601)
            {
                usingFireAtPlayerAttack = true;
            }
        }
        else if (actCount <= 1200)
        {
            //stays still for 200 acts
            setImage("boss1.PNG");
            if (actCount == 1001)
            {
                usingFireAtPlayerAttack = true;
            }
        }
        else if (actCount <= 1600)
        {
            setLocation(getX() + 1, getY());
            setImage("boss2.PNG");
            if (actCount == 1201)
            {
                usingWaveAttack = true;
            }
            else if (actCount == 1401)
            {
                usingWaveAttack = true;
            }
        }
        else if (actCount <= 2000)
        {
            setLocation(getX() - 1, getY());
            setImage("boss3.PNG");
            if (actCount == 1601 || actCount == 1701 || actCount == 1801)
            {
                usingRandomSprayAttack = true;
            }
            else if (actCount == 2000)
            {
                actCount = 0;
            }
        }
    }

    private void checkHitPlayer()
    {
//         if(getWorld() instanceof Map){
//             Player player = (Player)getOneIntersectingObject(Player.class);
//             if(player != null)
//             {
//                 player.enemyHit(player.getHealth());
//             }
//         }
    }
}