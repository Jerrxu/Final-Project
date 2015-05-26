import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ds here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class pl2 extends Collision
{
    private int vSpeed = 0;
    private int acceleration = 1;
    private boolean jumping;
    private int jumpStrength = 15;
    private int speed = 5;
    private boolean lastMovedLeft=false;
    private boolean lastMovedRight=false;
    
    /**
     * Act - do whatever the pl wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        checkFall();
        checkKey();
    }    
    
    public void checkKey(){
        if(Greenfoot.isKeyDown("w")&&(jumping==false)){
            jump();
        }
        
        if(Greenfoot.isKeyDown("d")){
            moveRight();
            lastMovedRight=true;
            lastMovedLeft=false;
        }
        
        if(Greenfoot.isKeyDown("a")){
            moveLeft();
            lastMovedLeft=true;
            lastMovedRight=false;
        }
    }
    
    public void moveRight(){
        pl2 doppelganger = new pl2();//creates a copy of the player
        doppelganger.setImage("player.png");
        getWorld().addObject(doppelganger,getX(),getY());//sets position of the copy to that of the player
        
        int xNow=getX()+speed;
        int yNow=getY();
        int xNow2=xNow;
        int yNow2=yNow;
        int xPrev=xNow;
        int yPrev=yNow;
        
        doppelganger.setLocation(xNow,yNow);//sets the location of the copy to that of the original
        
        if(doppelganger.getOneTouchedObject(Tile.class)!=null){//the copy is in a tile or touching a tile
            while(doppelganger.getOneTouchedObject(Tile.class)!=null){//copy moves up by 1 pixel at a time, and check if it still touches a tile
                yNow-=1;
                doppelganger.setLocation(xNow,yNow);
            }
            if((yPrev-yNow)<=speed+1){//cellHeight - if amount previously clipped in has a slope of less than about 45 degrees, slope is not too steep, sets location of the original to where the copy is +1(while loop makes it float 1 pixel above)
                doppelganger.setLocation(xNow,yNow+1);
                this.setLocation(xNow,yNow+1);
            }
            getWorld().removeObject(doppelganger);//removes copy to avoid eating up memory
            return;
        }
        //setLocation(getX()+speed,getY()); original
        if(doppelganger.getOneTouchedObject(Tile.class)==null){//if copy is found to be floating
            while(doppelganger.getOneTouchedObject(Tile.class)==null){//moves down by 1 pixel until copy touches tile
                yNow2+=1;
                doppelganger.setLocation(xNow2,yNow2);
            }
            if((yNow2-yPrev)<=speed){//cellHeight - if slope is less than about 45 degrees, set location of origianl to that of the copy
                doppelganger.setLocation(xNow2,yNow2);
                this.setLocation(xNow2,yNow2);
            }else if(yNow2>=1215){//if the copy exceeds screen height
                this.setLocation(getX()+speed,getY());
            }else{//i have no idea why this is here
                this.setLocation(getX()+speed,getY());
            }
            getWorld().removeObject(doppelganger);//remove copy to avoid eating up memory
            return;
        }
        
        return;
        
    }
    
    public void moveLeft(){//same as moveRight(), but speed is subtracted
        pl2 doppelganger = new pl2();
        doppelganger.setImage("player.png");
        getWorld().addObject(doppelganger,getX(),getY());
        
        int xNow=getX()-speed;
        int yNow=getY();
        int xNow2=xNow;
        int yNow2=yNow;
        int xPrev=xNow;
        int yPrev=yNow;
        
        doppelganger.setLocation(xNow,yNow);
        
        if(doppelganger.getOneTouchedObject(Tile.class)!=null){
            while(doppelganger.getOneTouchedObject(Tile.class)!=null){
                yNow-=1;
                doppelganger.setLocation(xNow,yNow);
            }
            if((yPrev-yNow)<=speed+1){//cellHeight
                doppelganger.setLocation(xNow,yNow+1);
                this.setLocation(xNow,yNow+1);
            }
            getWorld().removeObject(doppelganger);
            return;
        }
        //setLocation(getX()+speed,getY()); original
        if(doppelganger.getOneTouchedObject(Tile.class)==null){
            while(doppelganger.getOneTouchedObject(Tile.class)==null){
                yNow2+=1;
                doppelganger.setLocation(xNow2,yNow2);
            }
            if((yNow2-yPrev)<=speed){//cellHeight
                doppelganger.setLocation(xNow2,yNow2);
                this.setLocation(xNow2,yNow2);
            }else if(yNow2>=1215){
                this.setLocation(getX()-speed,getY());
            }else{
                this.setLocation(getX()-speed,getY());
            }
            getWorld().removeObject(doppelganger);
            return;
        }
        
        return;
    }
    
    public void fall(){
        setLocation(getX(),getY()+vSpeed);
        if(vSpeed<=8){
            vSpeed+=acceleration;  
        }
        jumping = true;
    }
    
    public boolean onGround(){
        int height = getImage().getHeight();
        int look  = (int)(height/2);
        
        //pl2 doppelganger = new pl2();
        //doppelganger.setImage("player.png");
        //doppelganger.setlocation(getX(),getY());
        
        Actor groundMid  = getOneTouchedObject(Tile.class);
        //Actor groundLeft  = doppelganger.getOneTouchedObject(Tile.class);
        //Actor groundRight  = doppelganger.getOneTouchedObject(Tile.class);
        
        if ((groundMid == null)){//&&(groundLeft==null)&&(groundRight==null)){
            jumping = true;
            return false;
        }
        
        

        moveToGround();//ground);
        jumping=false;
        //unexpectedly creates a drop-to-ground-after-high-fall-rebound effect
        return true;
    }
    
    public void checkFall(){
        if(onGround()){
            vSpeed=0;
        }
        else
        {
            fall();
        }
    }
    
    public void moveToGround(){//Actor ground){
        int groundX=getX();
        int groundY=getY();
        
        if (getOneTouchedObject(Tile.class)!=null){
            while(getOneTouchedObject(Tile.class)!=null){
                groundY-=1;
                setLocation(getX(),groundY);
            }
            setLocation(getX(),groundY+1);
        }
        
        //int groundHeight = ground.getImage().getHeight();
        //int newY = ground.getY() - (groundHeight + getImage().getHeight())/2;
        
        //setLocation(getX(), newY);
        
        jumping = false;
    }
    
    public void jump(){
        vSpeed -= jumpStrength;
        jumping = true;
        fall();
    }
}
