import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.ArrayList;
/**
 * Pause Screen that will show up when P is pressed
 * 
 * @author David Liu and Keith Wong (sounds)
 * @version 1.2
 */
public class PauseScreen extends Actor
{
    GreenfootImage display = new GreenfootImage("Untitled-2.gif");//make new GreenfootImage to use and set the size
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private int currentChoice = 0;
    private boolean previousClicked = false;
    private boolean justLoaded = true;
    private GreenfootSound pauseSound = new GreenfootSound("Pause.wav");
    private Map map;
    /**
     * Creates a new pause screen
     * 
     * @param type True for Save Screen, False for pause Screen
     */
    public PauseScreen(boolean type){        
        setImage(display);        
        buttons.add(new NullButton("RESUME"));//add resume button

        if (type){//if true add save button sintread of load button
            buttons.add(new SaveButton("Save"));
        }else{
            buttons.add(new LoadButton("LOAD"));   
        }

        buttons.add(new QuitButton("BACK TO MENU",1));//add quit button leads to menu
        pauseSound.play();        
    }
    
    /**
     * Runs every time screen refreshes, checks the move and highlight accordingly
     */
    public void act(){         
        getWorld().setPaintOrder(Button.class,PauseScreen.class);//set the paint order for this need
        ((Map)(getWorld())).setPause(true);
        if(justLoaded){
            for(int i =0; i < buttons.size(); i++){
                getWorld().addObject(buttons.get(i), (int)(((SWorld)(getWorld())).getScrolledX())+360, (((SWorld)(getWorld())).getScrolledY())+220+i*50);
                justLoaded = false;
            }
        } 
        for(int i = 0; i<buttons.size(); i++){
            if(i==currentChoice){
                buttons.get(i).highlight();
            }else{
                buttons.get(i).unHighlight();
            }
        }
        checkMoves();//check what is pressed
    }
    
    /**
     * Check movement - copied from Screen Class
     */
    private void checkMoves(){
        if(currentChoice==0){
            if(Greenfoot.isKeyDown("down")){//if at top, only cna go down in choices
                currentChoice++;
                previousClicked = true;
            }
        }else if(currentChoice ==buttons.size()-1){//if at bottom, vice versa
            if(Greenfoot.isKeyDown("up")){
                currentChoice--;
                previousClicked = true;
            }
        }else{
            if(Greenfoot.isKeyDown("up")){//choose up or down
                if(!previousClicked){
                    currentChoice--;
                }
                previousClicked = true;
            }else if(Greenfoot.isKeyDown("down")){
                if(!previousClicked){
                    currentChoice++;
                }
                previousClicked = true;
            }else{
                previousClicked = false;
            }
        }
        if(Greenfoot.isKeyDown("enter")){
            buttons.get(currentChoice).buttonPressed();
            if(buttons.get(currentChoice) instanceof NullButton){
                ((Map)(getWorld())).setPause(false);
                remove();
            }
        }
    }
    
    /**
     * Removes this and reset paint order
     */
    private void remove(){
        getWorld().setPaintOrder(HUD.class, Player.class, Collision.class, Tile.class, Spawner.class);//reset paint order
        for(Button b : buttons){
            getWorld().removeObject(b);//remove all of the buttons
        }
        getWorld().removeObject(this);//remove this
    }
}
