import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Weapons here.
 * 
 * @author Jerry Xu
 * @version January 20, 2014
 */
public abstract class EnemyWeapons extends Actor
{
    /**
     * Act - do whatever the Weapons wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {

        if(((Map)(getWorld())).checkPause()==false){
            action();
        }
    }    

    protected abstract void action();
}
