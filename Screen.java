import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * Makes a Screen of options based on choices. Contains options to start a Death Screen (1), Start Screen (2), 
 * Info Screen(3) and Congrats Screen(4)
 * 
 * @author David Liu and Adam Li
 * @version 1.2
 */
public class Screen extends SWorld
{
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private boolean alreadyPressed;
    private boolean instructScrn;
    private int type = 0;
    private int currentChoice = 0;
    private boolean previousClicked = false;
    /**
     * Constructor for objects of class Screen.
     * 
     * @param choice The choice for the screen to display 1- Death screen, 2-Start Screen, 3-Info Screen, 4- Congrats Screen
     */
    public Screen(int choice)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(720, 480, 1, 450,300); 
        type = choice;
        GreenfootImage display = new GreenfootImage(120,40);//make new GreenfootImage to use and set the size
        setBackground(new GreenfootImage("Untitled-1.gif"));//set the background iamge
        switch(type){
            case 1://Death Screen
            buttons.add(new StartButton("RETRY?"));
            buttons.add(new LoadButton("LOAD"));                       
            buttons.add(new QuitButton("QUIT", 2));
            break;
            case 2://start Screen
            buttons.add(new StartButton("START"));
            buttons.add(new LoadButton("LOAD"));            
            buttons.add(new QuitButton("QUIT",2));
            buttons.add(new InstructionButton("HOW TO PLAY"));
            break;
            case 3://instruction screen
            instructScrn = true;
            buttons.add(new StartButton("START"));
            buttons.add(new BackButton("BACK"));
            setBackground(new GreenfootImage("howToPlay.gif"));
            break;
            case 4://finished screen
            setBackground(new GreenfootImage("CongratsScreen.jpg"));
            buttons.add(new StartButton("RETRY?"));
            buttons.add(new QuitButton("QUIT", 1));
            break;
        }

        if (!instructScrn)
        {
            for (int i = 0; i<buttons.size(); i++){
                addObject (buttons.get(i), getWidth()/2,240+50*i);//add buttons to screen
            }
        }
        else
        {
            for (int i = 0; i<buttons.size(); i++){
                addObject (buttons.get(i), getWidth()/2+260,60+50*i);//add buttons to screen
            }
        }
        if(Greenfoot.isKeyDown("enter")){//make sure that the holding of eneter is not allowed
            alreadyPressed = true;
        }else{
            alreadyPressed = false;
        }
    }

    /**
     * Runs every time screen Refreshes
     */
    public void act(){
        for(int i = 0; i<buttons.size(); i++){//determine which one to highlight
            if(i==currentChoice){
                buttons.get(i).highlight();
            }else{
                buttons.get(i).unHighlight();
            }
        }
        checkMoves();//check which is pressed
    }

    /**
     * Check what the player has chosen, allow certain movement
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
        if(Greenfoot.isKeyDown("enter") &&!alreadyPressed){//enter will choose button (not held when first enter screen)
            buttons.get(currentChoice).buttonPressed();
        }else if(!Greenfoot.isKeyDown("enter") &&alreadyPressed){
            alreadyPressed =false;
        }
    }
}
