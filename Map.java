import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;
/**
 * Imports and load up a map that is playable. Loaded with textfiles (*.txt). 
 * 
 * @author David Liu and Jessie Leung and Keith Wong (sounds)
 * @version 1.2
 */
public class Map extends SWorld
{
    //variables
    private Tile[][] tiles;//2d array for tiles
    private char[][] map;//not used
    private Player player = new Player();//new player
    private Boss boss;//boss
    private int stage;//stage number
    private boolean paused = false; //is the game paused
    private int mapNum;//map number (1-7)
    private boolean bgmPlayed = false;
    private GreenfootSound bgm = new GreenfootSound("bgm.mp3");
    private GreenfootSound bossDefeatMusic = new GreenfootSound("GuileTheme.mp3");
    /**
     * When everything is first loaded up, changes the screen to the Title Screen
     */
    public Map()
    {   
        super(720, 480, 1, 1855 ,3700); 
        Greenfoot.setWorld(new Screen(2));//sets world to that of title screen first
    }

    /**
     * Starts a map with the asked file and adds the player's x and y location, as well as the current player attributes
     * 
     * @param fileName The name of the file
     * @param xq The x location of the player
     * @param yq The y location of the player
     */
    public Map (String fileName, int xq, int yq, int worldX, int worldY, int health, int ammo,int ammo2, boolean bgmPlayed){
        super(720, 480, 1, worldX ,worldY);//sets world size according to worldx, wolrdy   
        map= importMap(fileName);//imports map to filename
        stage = Character.getNumericValue(fileName.charAt(3));//thats the stage        
        setPaintOrder(HUD.class, Player.class, Collision.class, Tile.class,Spawner.class);//sets paint order to avoid blocking
        addMainActor(player,xq,yq,0,0);//adds player to xq,yq
        //sets player stats
        player.setHealth(health);
        player.setMissileAmmo(ammo);
        player.setSuperMissileAmmo(ammo2);
        this.bgmPlayed = bgmPlayed;
        //sets background images
        GreenfootImage bg = new GreenfootImage("background.png");
        setScrollingBackground(bg);
        //loads map
        loadMap();
        if (bgmPlayed == false){
            bgm.setVolume(80);
            bgm.playLoop();
        }

        //add different objects according to the filename
        if(fileName.equals("map1.txt")){
            addObject(new Door(1,1,1),920,3600);            //Door location
            addObject(new NullButton("DEFEAT"),280,3600);   //sign
            addObject(new NullButton("THE"),480,3600);
            addObject(new NullButton("BOSS"),680,3600);
            addObject(new MissilePickUp(), 850, 3600);      //pick up missiles
            mapNum=1;
        }
        if(fileName.equals("map2.txt")){
            addObject(new Door(2,2,1),280,1056);            
            addObject(new Door(2,1,2),1450,240);
            mapNum=2;
        }
        if(fileName.equals("map3.1.txt")){
            addObject(new Door(3,2,1),40,672);
            addObject(new Door(3,1,2),1540,672);
            mapNum=3;
        }
        if(fileName.equals("map4.txt")){
            addObject(new Door(4,2,1),50,192);
            addObject(new Door(4,4,2),50,1440);
            addObject(new Door(4,1,3),825,1200);
            mapNum=4;
        }
        if(fileName.equals("map5.txt")){
            addObject(new Door(5,2,1),50,384);            
            addObject(new SuperMissilePickUp(), 1272, 600); //pick up supermissiles
            addObject(new SuperMissilePickUp(), 1272+48, 600);
            addObject(new SuperMissilePickUp(), 1272+48+48, 600);
            mapNum=5;
        }
        if(fileName.equals("map6.txt")){
            addObject(new Door(6,1,1),1165,768);
            addObject(new Door(6,4,2),50,240);
            addObject(new ShootToSave(), 72, 792);          //save point
            addObject(new SuperMissilePickUp(), 110, 792);
            mapNum=6;
        }
        if(fileName.equals("map4Boss.txt")){
            boss = new Boss();
            addObject(boss, 360, 600);
            mapNum=7;
        }
    }

    /**
     * Loads up the map with the given array of chars
     */
    private void loadMap(){
        tiles = new Tile[map[0].length][map.length];//new tile grid with size
        for (int y = 0; y < tiles.length; y++){
            for (int x = 0; x < tiles[y].length; x++){
                switch (map[x][y]){//find the value at the certain point
                    case '1'://tile type 1 - horizontal
                    addObject (new Tile(1),48*y+24,48*x+24);
                    break;
                    case '2'://tile type 2 - vertical
                    addObject (new Tile(2),48*y+24,48*x+24);                   
                    break;
                    case '3'://tile type 3 - end tiles
                    addObject (new Tile(3),48*y+24,48*x+24);                    
                    break;
                    case '4':
                    addObject (new Tile(4),48*y+24,48*x+24);                    
                    break;
                    case '5':
                    addObject (new Tile(5),48*y+24,48*x+24);                   
                    break;
                    case '6':
                    addObject (new Tile(6),48*y+24,48*x+24);
                    break;
                    case '7':
                    addObject (new Tile(7),48*y+24,48*x+24);      
                    break;
                    case '8':
                    addObject (new Tile(8),48*y+24,48*x+24);      
                    break;
                    case '9':
                    addObject (new Tile(9),48*y+24,48*x+24);      
                    break;
                    case 'a':
                    addObject (new Tile(10),48*y+24,48*x+24);      
                    break;
                    case 'A':
                    addObject (new Tile(11),48*y+24,48*x+24);      
                    break;
                    case 'b':
                    addObject (new Tile(12),48*y+24,48*x+24);      
                    break;
                    case 'B':
                    addObject (new Tile(13),48*y+24,48*x+24);      
                    break;
                    case 'c':
                    addObject (new Tile(14),48*y+24,48*x+24);      
                    break;
                    case 'C':
                    addObject (new Tile(15),48*y+24,48*x+24);      
                    break;
                    case 'D':
                    addObject (new Tile(16),48*y+24,48*x+24);      
                    break;
                    case 'd':
                    addObject (new Tile(17),48*y+24,48*x+24);      
                    break;
                    case 'E':
                    addObject (new Tile(18),48*y+24,48*x+24);      
                    break;
                    case 'e':
                    addObject (new Tile(19),48*y+24,48*x+24);      
                    break;
                    case 'f':
                    addObject (new Tile(20),48*y+24,48*x+24);      
                    break;
                    case 'F':
                    addObject (new Tile(21),48*y+24,48*x+24);      
                    break;
                    case 'g':
                    addObject (new Tile(22),48*y+24,48*x+24);      
                    break;
                    case 'h':
                    addObject (new Tile(23),48*y+24,48*x+24);      
                    break;
                    case 'H':
                    addObject (new Tile(24),48*y+24,48*x+24);      
                    break;
                    case 'i':
                    addObject (new Tile(25),48*y+24,48*x+24);      
                    break;
                    case 'I':
                    addObject (new Tile(26),48*y+24,48*x+24);      
                    break;
                    case 'j':
                    addObject (new Tile(27),48*y+24,48*x+24);      
                    break;
                    case 'J':
                    addObject (new Tile(28),48*y+24,48*x+24);      
                    break;
                    case 'k':
                    addObject (new Tile(29),48*y+24,48*x+24);      
                    break;
                    case 'K':
                    addObject (new Tile(30),48*y+24,48*x+24);      
                    break;
                    case 'l':
                    addObject (new Tile(31),48*y+24,48*x+24);      
                    break;
                    case 'L':
                    addObject (new Tile(32),48*y+24,48*x+24);      
                    break;
                    case 'm':
                    addObject (new Tile(33),48*y+24,48*x+24);      
                    break;
                    case 'M':
                    addObject (new Tile(34),48*y+24,48*x+24);      
                    break;
                    case 'G':
                    addObject (new Tile(35),48*y+24,48*x+24);      
                    break;
                    case 's'://spawner type 1 - DavidLiu per 300
                    addObject (new Spawner(1), 48*y+24,48*x+24);    
                    break;
                    case 'S'://spawner type 2 - KeithWong per 400
                    addObject (new Spawner(2), 48*y+24,48*x+24);      
                    break;
                    case 'P'://spawner type 3 - KeithWong per 500
                    addObject (new Spawner(3), 48*y+24,48*x+24);    
                    break;
                    case 'p'://spawner type 4 - DavidLiu per 400
                    addObject (new Spawner(4), 48*y+24,48*x+24);
                    break;                   
                    case 'Q'://spawner type 6 - jumper
                    addObject (new Spawner(6), 48*y+24, 48*x+24);
                    break;
                    case 'q'://spawner type 7 - flight
                    addObject (new Spawner(7), 48*y+24, 48*x+24);
                    break;
                }
            }
        }

        addObject(new HUD("","","","", 1),720/2,20);
    }

    /**
     * Reads the txt file for the map, will report if file is invalid(not found)
     * 
     * @param fileName The name of the text file to be imported from
     * @return chars[][] the arrays of blocks to be loaded with the loadMap()
     */
    private char[][] importMap(String fileName){
        ArrayList<String> lines = new ArrayList<String>();//add all the lines of teh text file to this
        try{
            Scanner scanFile = new Scanner(new File(fileName));
            while(scanFile.hasNext()){
                lines.add(scanFile.next());//load all the lines 
            }            
            scanFile.close();
        }catch (FileNotFoundException e){
            System.out.println("invalid File");
        }
        char[][] importedMap = new char[0][0];
        for (int i = 0; i<lines.size(); i++){            
            String[] tempList= lines.get(i).split(",");//split each line by the commas
            if(i ==0){
                importedMap = new char[lines.size()][tempList.length];//if the first time running initiallize the 2D array
            }
            for(int j = 0; j<tempList.length; j++){
                importedMap[i][j] = tempList[j].charAt(0);//load the first character, whatever it is
            }
        }
        return importedMap;
    }

    /**
     * Return Paused or not
     */ 
    public boolean checkPause(){
        return paused;
    }   

    /**
     * Pauses/Unpauses the game
     * 
     * @param input Is the game paused or not
     */
    public void setPause(boolean input){
        paused = input;
        if (input)
            stopBGM();
        else
            bgm.play();
    }

    /**
     * Return The player's X location
     */
    public int getPlayerX(){
        return player.getX();
    }

    /**
     * Return The player's Y location
     */
    public int getPlayerY(){
        return player.getY();
    }

    /**
     * Return Player The main player
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Return int Map Title that player is on
     */
    public int getLevel(){
        return stage;
    }

    /**
     * Return int Level that player is on
     */
    public int getMapNum(){
        return mapNum;
    }

    /**
     * Return The Boss's X-coordinate
     */
    public int getBossX()
    {
        return boss.getX();
    }

    /**
     * Return The Boss's Y-coordinate
     */
    public int getBossY()
    {
        return boss.getY();
    }

    /**
     * Removes boss object
     */
    public void removeBoss()
    {
        removeObject(boss);
        bgm.stop();
        bossDefeatMusic.playLoop();
    }

    public void stopBGM(){
        bgm.stop();
    }
}