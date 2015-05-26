import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BackButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackButton extends Button
{
    public BackButton(String name)
    {
        super(name);
    }
    
    public void buttonPressed()
    {
        Greenfoot.setWorld(new Screen(2));
    }
}
