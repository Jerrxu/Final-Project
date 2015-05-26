import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Button for Nothing
 * 
 * @author David Liu
 * @version 1.2
 */
public class NullButton extends Button
{
    /**
     * Creates a new Button
     * 
     * @param name The word to display
     */
    public NullButton(String name){
        super(name);
    }    

    /**
     * Decides what to do when pressed
     * 
     * Nothing should happen, its null for a reason
     */
    public void buttonPressed(){
    }     
}
