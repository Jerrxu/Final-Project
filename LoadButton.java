import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;
/**
 * The Button for Loading
 * 
 * @author David Liu
 * @version 1.2
 */
public class LoadButton extends Button
{
    /**
     * Creates a new Button
     * 
     * @param name The word to display
     */
    public LoadButton(String name){
        super(name);
    }    

    /**
     * Decides what to do when pressed
     * Will only load map6 and last save must be map 6
     */
    public void buttonPressed(){
        try{   
            String[]temp = loadSave(JOptionPane.showInputDialog("Please input gamefile name, no extention required"));
            if(temp[3].equals("6")){//asks user for file name
                if(getWorld() instanceof Map){
                    Greenfoot.setWorld(new Map("map6.txt",1104,768, 1200, 960, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),Integer.parseInt(temp[2]), true));
                }else if(getWorld() instanceof Screen){
                    Greenfoot.setWorld(new Map("map6.txt",1104,768, 1200, 960, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),Integer.parseInt(temp[2]), false));
                }
            }
            //
        }catch(ArrayIndexOutOfBoundsException e){
        }
    }    

    /**
     * Loads a save file with given file name
     * 
     * @param fileName The name of the File
     * @return String[] The array of things loaded from file
     */
    public String[] loadSave(String fileName){
        ArrayList<String> info = new ArrayList<String>();
        try{            
            if (fileName!=null){//if user doesn't click cancel
                Scanner scan = new Scanner(new File(fileName+".txt"));
                while(scan.hasNext()){
                    info.add(scan.next());
                }
                //Greenfoot.setWorld(new Map("map.txt", 100, 100));//, info.toArray(new String[info.size()])));
            }
        }catch(IOException e){
            System.out.println("cannot be loaded");
        } 
        return info.toArray(new String[info.size()]);
    }
}
