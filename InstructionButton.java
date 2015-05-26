import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InstructionButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstructionButton extends Button
{
    /**
     * Act - do whatever the InstructionButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public InstructionButton(String name)
    {
        super(name);
    }    
    
    /**
     * Decides what to do when pressed,
     * Loads the instruction screen
     */
    public void buttonPressed(){
        Greenfoot.setWorld(new Screen(3));
    }
}
