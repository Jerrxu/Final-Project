import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * For player to pickup and increase super missile ammo number
 * 
 * @author Jessie Leung and Keith Wong
 * @version Jaunary 20, 2014
 */
public class SuperMissilePickUp extends Collision
{
    private GreenfootSound pickUp = new GreenfootSound("PickUp.wav");
    /**
     * Runs detect() every act
     */
    public void act() 
    {
        detect();
    }    

    /**
     * Detects if pickUp touches player, play sound, set player super missile ammo number, removes itself if yes
     */
    private void detect(){
        if(touch(Player.class)){
            pickUp.play();
            Player p =new Player();
            p=(Player)getOneTouchedObject(Player.class);
            p.setSuperMissileAmmo(p.getSuperMissileAmmo()+10);
            getWorld().removeObject(this);
        }
    }
}
