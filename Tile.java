import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Tiles to show and let player stand above on, also will allow for enemy spawning
 * 
 * @author Jessie and David
 * @version (a version number or a date)
 */
public class Tile extends Actor
{
    private boolean easyBreakable = false;//if tile is breakable by bullet
    private boolean superBreakable = false;//if tile is breakable by super missiles
    private boolean firstTime;
    private Spawner spawner;
    /**
     * Creates a Tile with inputted request
     * 
     * @param x The type of block to add
     */
    public Tile(int x){
        firstTime = true;
        if (x==1){//tile type
            setImage("block.png");
        }
        if (x==2){
            setImage("fullSlopeLeft.png");
        }
        if (x==3){
            setImage("fullSlopeRight.png");
        }
        if (x==4){
            setImage("fullSlopeUpRight.png");
        }
        if (x==5){
            setImage("fullSlopeUpLeft.png");
        }
        if (x==6){
            setImage("halfLeft.png");
        }
        if (x==7){
            setImage("halfRight.png");
        }
        if (x==8){
            setImage("easyBlock.png");
            easyBreakable = true;
        }
        if (x==9){
            setImage("superBlock.png");
            superBreakable = true;
        }
        if (x==10){
            setImage("map1LeftSide.png");
        }
        if (x==11){
            setImage("map1RightSide.png");
        }
        if (x==12){
            setImage("map1LeftMid.png");
        }
        if (x==13){
            setImage("map1RightMid.png");
        }
        if (x==14){
            setImage("map1Floor.png");
        }
        if (x==15){
            setImage("map1FloorMid.png");
        }
        if (x==16){
            setImage("map1LeftSideBreakUp.png");
        }
        if (x==17){
            setImage("map1LeftSideBreakDown.png");
        }
        if (x==18){
            setImage("map1RightSideBreakUp.png");
        }
        if (x==19){
            setImage("map1RightSideBreakDown.png");
        }
        if (x==20){
            setImage("map1DownLeftCorner.png");
        }
        if (x==21){
            setImage("map1DownLeftCorner2.png");
        }
        if (x==22){
            setImage("map1Filler.png");
        }
        if (x==23){
            setImage("map1LeftFiller.png");
        }
        if (x==24){
            setImage("map1RightFiller.png");
        }
        if (x==25){
            setImage("map1LeftFillerEnd.png");
        }
        if (x==26){
            setImage("map1RightFillerEnd.png");
        }
        if (x==27){
            setImage("map1MidFiller.png");
        }
        if (x==28){
            setImage("map1MidFillerEnd.png");
        }
        if (x==29){
            setImage("DoorLeftUp.png");
        }
        if (x==30){
            setImage("DoorLeftDown.png");
        }
        if (x==31){
            setImage("DoorRightUp.png");
        }
        if (x==32){
            setImage("DoorRightDown.png");
        }
        if (x==33){
            setImage("DoorLeftUp2.png");
        }
        if (x==34){
            setImage("DoorRightUp2.png");
        }
        if (x==35){
            setImage("map1Filler2.png");
        }
        if (x==36){
            setImage("map1Filler2.png");
        }
        if (x==37){
            setImage("map1Filler2.png");
        }
    }

    /**
     * Return boolean Breakable by bullets or not
     */
    public boolean isEasyBreakable(){//for bullets
        return easyBreakable;
    }

    /**
     * Return boolean Breakable by super missile or not
     */
    public boolean isSuperBreakable(){//for super missile
        return superBreakable;
    }
}
