import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Door which lead to perv/next level
 * 
 * @author Jessie and David an Keith
 * @version January 20, 2014
 */
public class Door extends Collision
{
    private boolean isOpen = false;//is the door is opened or not
    private int stageNum, style, number, health, ammo, ammo2;
    private GreenfootSound openDoor = new GreenfootSound("DoorOpen.wav");
    private boolean soundPlayed = false;
    /**
     * Constructor (sets door facing direction, type)
     * @param stage the room number to set player spawning location
     */
    public Door (int stage, int type, int num){
        style = type;//facing direction, opened by what bullet
        stageNum=stage;//number of the map
        number=num;//if door if opened from left/right location of room

        if(type == 1){//door face left
            setImage("DoorLeftClose.png");
        }else if(type == 2){//door face right
            setImage("DoorRightClose.png");
        }else if(type == 3){//superdoor face left
            setImage("superDoorLeftClose.png");
        }else if(type ==4){//superdoor face right
            setImage("superDoorRightClose.png");
        }
    }

    
    /**
     * Opens a door
     * 
     */public void open(){
        isOpen = true;
        if (soundPlayed == false){
            openDoor.play();
        }
        soundPlayed = true;
        //sets images to opened doors
        if (style==1){
            setImage("DoorLeftOpen.png");
        }
        if (style==2){
            setImage("DoorRightOpen.png");
        }
        if (style==3){
            setImage("superDoorLeftOpen.png");
        }
        if (style==4){
            setImage("superDoorRightOpen.png");
        }
    }

    /**
     * Returns true if door is opened
     * 
     */
    public boolean opened(){
        if(isOpen==true){
            return true;
        }
        return false;
    }

    /**
     * Removes all objects in the world except door, sets new World with different constructors depending on door types
     */
    public void act() 
    {
        if((style!=3)&&(style!=4)&&(this.touch(Bullet.class))){//for normal door, need bullets
            open();
        }
        if((style!=1)&&(style!=2)&&(this.touch(SuperMissile.class))){//for super door, need super missile
            open();
        }
        if((opened())&&touch(Player.class)){//if player touches opened door
            //sets new player to existing one to get stats
            Player p =new Player();
            p=(Player)getOneTouchedObject(Player.class);
            //get ammo stats
            health=p.getHealth();
            ammo=p.getMissileAmmo();
            ammo2=p.getSuperMissileAmmo();
            //removes objects of the old map
            getWorld().removeObjects(getWorld().getObjects(Tile.class));
            getWorld().removeObjects(getWorld().getObjects(Enemy.class));
            getWorld().removeObjects(getWorld().getObjects(Player.class));
            getWorld().removeObjects(getWorld().getObjects(Weapon.class));
            //sets a new world with different parameters according to door types
            //parameters include player location, player stat, world size
            if(stageNum==1){
                Greenfoot.setWorld(new Map("map2.txt",336,1053,1488,1296,health,ammo, ammo2, true));
            }
            if(stageNum==2){
                if (number==1){
                    Greenfoot.setWorld(new Map("map1.txt",850,3600, 960, 3840,health,ammo, ammo2, true));
                }else{
                    Greenfoot.setWorld(new Map("map3.1.txt",96,668, 1584, 1056,health,ammo, ammo2, true));
                }
            }
            if(stageNum==3){
                if (number==1){
                    Greenfoot.setWorld(new Map("map2.txt",1392,240, 1488, 1296,health,ammo, ammo2, true));
                }else{
                    Greenfoot.setWorld(new Map("map4.txt",96,192, 864, 1728,health,ammo, ammo2, true));
                }
            }
            if(stageNum==4){
                if (number==1){
                    Greenfoot.setWorld(new Map("map3.1.txt",1488,672, 1584, 1056,health,ammo, ammo2, true));
                }else if(number==3){
                    Greenfoot.setWorld(new Map("map5.txt",96,384, 1536, 672,health,ammo, ammo2, true));
                }else{
                    Greenfoot.setWorld(new Map("map6.txt",1104,768, 1200, 960,health,ammo, ammo2, true));
                }
            }
            if(stageNum==5){
                Greenfoot.setWorld(new Map("map4.txt",768,1200, 864, 1728,health,ammo, ammo2, true));
            }
            if(stageNum==6){
                if (number==1){
                    Greenfoot.setWorld(new Map("map4.txt",96,1440, 960, 3840,health,ammo, ammo2, true));
                }else{
                    Greenfoot.setWorld(new Map("map4Boss.txt",960,912, 1056,1152,health,ammo, ammo2, true));
                }
            }
        }
    }
}
