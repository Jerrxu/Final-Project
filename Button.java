import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Defines the Buttons and their basic outlook
 * 
 * @author David Liu 
 * @version 1.2
 */
public abstract class Button extends Actor
{
    private GreenfootImage display = new GreenfootImage(120,40);//make new GreenfootImage to use and set the size
    private GreenfootImage newDisplay = new GreenfootImage(140,50);//make new GreenfootImage to use and set the size
    /**
     * Creates a button with the requested name
     */
    public Button(String name){
        display.setColor(new Color(208, 208, 217));//make the background images
        display.fill();
        display.setColor(new Color(255, 255, 164));
        display.fillRect(5, 5, 110, 30);        
        display.setColor(Color.BLUE);//set font color
        display.drawString(name,60-name.length()*4,25);//add the text on screen        

        newDisplay.setColor(new Color(200, 28, 28));//set font color
        newDisplay.fill();
        newDisplay.drawImage(display,10,5);
        unHighlight();
    }
    
    /**
     * Highlights the button
     */
    public void highlight(){
        setImage(newDisplay);
    }
    
    /**
     * Unhighlights the button
     */
    public void unHighlight(){
        setImage(display);
    }
    
    /**
     * Removes the button
     */
    protected void remove(){
        getWorld().removeObject(this);
    }
    
    /**
     * Action to perform when the button is pressed
     */
    public abstract void buttonPressed ();
}
