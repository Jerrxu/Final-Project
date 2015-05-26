import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * Button for Saving
 * 
 * @author David Liu
 * @version 1.2
 */
public class SaveButton extends Button
{
    /**
     * Creates a new Button
     * 
     * @param name The word to display
     */
    public SaveButton(String name){
        super(name);
    }    

    /**
     * Decides what to do when pressed
     * 
     */
    public void buttonPressed(){
        saveFile(JOptionPane.showInputDialog("Please input a save name, no extention required"));//asks user for file name);
        ((Map)getWorld()).setPause(false);//unpause
        getWorld().removeObjects(getWorld().getObjects(PauseScreen.class));
        getWorld().removeObjects(getWorld().getObjects(Button.class));
    } 

    /**
     * Save the current game's key info as a .txt file
     * 
     * @param fileName File to be named as
     */
    public void saveFile(String fileName){
        try{
            if (fileName!=null&&getWorld() instanceof Map){//if user doesn't click cancel
                FileWriter write = new FileWriter(fileName+".txt");
                PrintWriter printOntoFile = new PrintWriter (write);                
                Map m = (Map)getWorld();
                printOntoFile.printf("%s",m.getPlayer().getHealth());//write health                
                printOntoFile.printf("%n"+"%s",m.getPlayer().getMissileAmmo());//ammos                
                printOntoFile.printf("%n"+"%s",m.getPlayer().getSuperMissileAmmo());
                printOntoFile.printf("%n"+"%s",m.getLevel());//level
                write.close();//close scanners
                printOntoFile.close();
            }
        }catch(IOException e){
            //tell user if file cannot be saved
            System.out.println("cannot be saved");
        }       
    }
}
