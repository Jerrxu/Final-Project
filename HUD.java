import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.lang.NumberFormatException;
/**
 * Heads Up Display Showing the current Info
 * 
 * @author David Liu
 * @version 1.2
 */
public class HUD extends Actor
{
    /**
     * Creates a Head Up Display of info
     * 
     * @param s Health Level
     * @param s1 Missile Ammo
     * @param s2 Super Missile Ammo
     * @param s3 Current Level
     */
    public HUD(String s, String s1,String s2, String s3, int highlighted){     
        GreenfootImage display = new GreenfootImage("HUD.png");            
        display.setColor(new Color(208, 208, 217));
        //add texts
        display.drawString("LEVEL: "+s3, 50,22);
        if (highlighted == 3){
            display.setColor(new Color(255, 102, 0)); //orange
            display.drawString("SUPER MISSILE AMMO: " + s2,520,22);
            display.setColor(new Color(208,208, 217));
        } else {
            display.drawString("SUPER MISSILE AMMO: " + s2,520,22);
        }
        if (highlighted == 2){
            display.setColor(new Color(255, 102, 0));
            display.drawString("MISSILE AMMO: " + s1,380,22);
            display.setColor(new Color(208, 208, 217));
        } else {
            display.drawString("MISSILE AMMO: " + s1,380,22);
        }
        try{//draw the health level
            display.drawRect(255+(Integer.parseInt(s))/2,15,50-(Integer.parseInt(s))/2,9);

            display.fillRect(255,15,(Integer.parseInt(s))/2,10);
        }catch(NumberFormatException e){
        }
        display.drawString("HEALTH: " + s,160,22);
        setImage(display);  
    }

    /**
     * Runs every time screen Refreshes, add new HUD, delete this one
     */
    public void act() 
    {             
        //make a new HUD every frame
        if(getWorld() instanceof Map){
            Player p = ((Map)getWorld()).getPlayer();
            getWorld().addObject(new HUD(""+p.getHealth(),//tells it the health
                    ""+p.getMissileAmmo(),//tells it the ammos
                    ""+p.getSuperMissileAmmo(),
                    ""+((Map)getWorld()).getMapNum(), p.getWeaponChoice()),//tells it the current level
                ((SWorld)getWorld()).getScrolledX()+720/2,((SWorld)getWorld()).getScrolledY()+20);//add new HUD
            getWorld().removeObject(this);//remove this one
        }
    }    
}
