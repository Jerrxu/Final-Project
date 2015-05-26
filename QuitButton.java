import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Button for Quitting
 * 
 * @author David Liu
 * @version 1.2
 */
public class QuitButton extends Button
{
    int type;
    /**
     * Creates a new Button
     * 
     * @param name The word to display
     * @param type What type of quit
     */
    public QuitButton(String name, int type){
        super(name);
        this.type = type;
    }    
    
    /**
     * Decides what to do when pressed
     * 
     * 1 exit to home screen, 2 stops whole game
     */
    public void buttonPressed(){
        if(type ==1){
            Greenfoot.setWorld(new Screen(2));
        }else{
            Greenfoot.stop();
            System.exit(0);
        }
    }    
}
