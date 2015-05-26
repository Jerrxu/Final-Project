import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Enemy that chases after player
 * 
 * @author David Liu
 * @version 1.2
 */
public class DavidLiu extends Enemy
{
    private int speed = 0;//speed positive - right, negative - left
    private int counter = 61;
    private int fallingRate = 0;
    private SpritePlayer left, right;
    /**
     * Creates a New Enemy made by David Liu
     */
    public DavidLiu(){
        super(10,5);//hp of 10, damage each time of 5
        setImage(new GreenfootImage("Enemy/1/Right0.png"));
        left = new SpritePlayer("Enemy/1/Left",4); 
        right = new SpritePlayer("Enemy/1/Right",4); 
    }   
    
    /**
     * No attacking Move
     */
    protected void attackMove(){
        //attacks are its charges
    }
    /**
     * Chases After the player
     */
    protected void randomMove(){  
        if(speed == 0&&counter>70){
            counter = 0;//rest fo 70 frames in between chasing
        }
        if(getOneObjectAtOffset(0,getImage().getHeight()/2,Tile.class)!=null){
            fallingRate=0;//if ontop of a block, don't fall anymore
            if(counter>60){
                if(((Map)getWorld()).getPlayerX()>getX()){//location of player is to the right
                    if(speed<7){//not too fast
                        speed++;//add to speed, max at 6
                        if(Math.abs(speed)>3){//load images
                            setImage(right.returnImage(2));
                        }else{
                            setImage(right.returnImage(Math.abs(speed)));
                        }
                    }
                }else{//if on left side
                    if (speed>-7){//not too fast
                        speed --;
                        if(Math.abs(speed)>3){//load images
                            setImage(left.returnImage(2));
                        }else{
                            setImage(left.returnImage(Math.abs(speed)));
                        }
                    }
                }
                if((!touchSide(false, this)&&!touchSide(true, this))){
                    setLocation(getX()+speed,getY());  //if not touching anything at side, just move
                }
                if(touchSide(false, this)&&!touchSide(true, this)){//touching right and not left
                    if(speed<0){//move toward left
                        setLocation(getX()+speed,getY()); //allow movement
                    }
                }if (touchSide(true, this)&&!touchSide(false, this)){//touching left and not right
                    if(speed>0){//move toward right
                        setLocation(getX()+speed,getY()); //allow
                    }
                }

            }
        }
        else{      
            if(fallingRate<10)
                fallingRate++;//falling rate maxes out at 10
            setLocation(getX()+speed,getY()+fallingRate);    //change location accordingly    
        }   
        counter++;//add counter each time
    }
    /**
     * Drops a Missile Pickup when Dead
     */
    protected void onDeath(){
        getWorld().addObject(new MissilePickUp(), (int)(((SWorld)(getWorld())).getScrolledX())+getX(), (((SWorld)(getWorld())).getScrolledY())+getY());
    }//drops a new Missile Pickup
}
