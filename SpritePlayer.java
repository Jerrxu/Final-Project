import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
/**
 * Loads Sprite and returns the sprites to set as images
 * 
 * @author David Liu
 * @version beta 1.0
 */
public class SpritePlayer
{
    private GreenfootImage[] images;
    private String spriteName;
    /**
     * Makes a new sprite player and imports all the sprite for given file name
     * <br>
     * Name must follow format of .png, and also named with NAME#.png
     * 
     * @param name Name or path of the file in general, no need for ".png" or number
     * @param amount The amount of pictures for this set
     */
    public SpritePlayer(String name, int amount) {
        spriteName = name;
        images = new GreenfootImage[amount];
        try{
            for(int i = 0; i<amount; i++) {
                images[i] = new GreenfootImage(name+i+".png");
            }     
        }catch (Exception e){
        } 
    }
    /**
     * Returns the image's name
     * 
     * @return String name of image to display 
     * @param index The number in the set you wnat the name of
     */
    public String getImageName(int index){
        return (spriteName + index%images.length + ".png");
    }

    /**
     * Returns the image to display
     * 
     * @param index the number from the array
     * @return GreenfootImage Image to display
     */
    public GreenfootImage returnImage(int index){
        return images[index];
    }
}

