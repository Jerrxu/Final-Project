import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * For player to pickup and increase missile ammo number
 * 
 * @author David Liu and Jessie Leung and Keith Wong
 * @version January 20,2014
 */
public class MissilePickUp extends Collision
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
     * Detects if pickUp touches player, play sound, set player missile ammo number, removes itself if yes
     */
    private void detect(){
        if(touch(Player.class)){
            pickUp.play();
            Player p =new Player();
            p=(Player)getOneTouchedObject(Player.class);
            p.setMissileAmmo(p.getMissileAmmo()+20);
            getWorld().removeObject(this);
        }
    }
}
