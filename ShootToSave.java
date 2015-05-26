import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShootToSave here.
 * 
 * @author Jessie Leung
 * @version January 20, 2014
 */
public class ShootToSave extends Collision
{
    /**
     * Checks if it touches bullet every act. If yes adds pause screen to world to enable save, resume, quit
     */
    public void act() 
    {
        if(touch(Bullet.class)){
            ((Map)(getWorld())).setPause(true);
            getWorld().addObject(new PauseScreen(true),(((SWorld)(getWorld())).getScrolledX())+360, (((SWorld)(getWorld())).getScrolledY())+240);
        }
    }    
}
