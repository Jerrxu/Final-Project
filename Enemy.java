import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Abstract Supercalss for all of the enemies each person has designed. <br> Each person 
 * will have to create an enemy, based on their own AI. The act method is already preset so don't worry 
 * about that! <br><br> Each subclass constructor will have to contain a "super(insert_health_point);"<br><br>
 * 
 * Each team member is required (aside from Jerry who is designing the boss and enemies of course) to
 * create 2 methods - randomMove() and attackMove(). They must be PROTECTED methods. Any other
 * wording, naming, convention, etc will result in an error. randomMove() must also cotain a setLocation(x_Location, y_Location);     
 * 
 * @author David Liu and Jerry Xu and Keith Wong (sounds)
 * @version 1.2
 */
public abstract class Enemy extends Collision
{
    protected int hP, physicalDamage, uniqueDamage, moveDirection, moveDelay, moveTime, fallC, projectileCooldown;
    protected boolean moving;
    private GreenfootSound death = new GreenfootSound("EnemyDeath.wav");
    /**
     * Defines the Enemy that each team member coded
     * 
     * @param hitpoints The health you want your enemy to have
     */
    public Enemy(int hitpoints, int damage){
        hP = hitpoints;
        this.uniqueDamage = damage;
    }

    /**
     * Act - do whatever the PlayerEnemy wants to do. This method is called every time screen refreshes
     */
    public void act() 
    {
        if(!((Map)(getWorld())).checkPause()){
            damagePlayer();
            randomMove();
            attackMove();
            shouldKillSelf();
        }
    }    

    /**
     * Controls how the enemy would move like
     */
    protected abstract void randomMove();

    /**
     * Controls how the enemy would perform a killing move, leave empty if N/A
     */
    protected abstract void attackMove();

    /**
     * Predict circumstances to kill self
     */
    private void shouldKillSelf(){
        if(touch(Weapon.class)){
            int damage = ((Weapon)getOneIntersectingObject(Weapon.class)).getDamage();
            getWorld().removeObject(getOneIntersectingObject(Weapon.class));
            hP-=damage;
        }
        if(hP <=0){
            onDeath();
            death.play();
            getWorld().removeObject(this);
        }
    }

    /**
     * Checks and applies damage to player
     */
    private void damagePlayer(){
        Player p = (Player)getOneIntersectingObject(Player.class);
        if(p!=null){
            if(!p.recentlyAttacked()){
                p.enemyHit(p.getHealth()-uniqueDamage);
            }
        }
    }

    /**
     * See where the player is
     * @author Jerry Xu
     */
    protected void checkHeroLocation()
    {
        Map w = (Map)getWorld();
        //if a Hero is less than 300 pixel lengths away from the hero, it will fire at it
        if (Math.sqrt(Math.pow(w.getPlayerX() - getX(),2) + Math.pow(w.getPlayerY() - getY(),2)) < 300)
        {
            projectileCooldown = Greenfoot.getRandomNumber(30) + 70;
            EnemyMissile eM = new EnemyMissile();
        }
    }

    /**
     * What to do when hit, what code to run on death
     */
    protected abstract void onDeath();
}