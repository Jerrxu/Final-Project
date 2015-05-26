import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Spawns Enemy <br>
 * 1-4, 6-7 all spawn typical enemies, 5 spawns the Boss
 * 
 * @author David Liu, Adam Li
 * @version 1.2
 */
public class Spawner extends Actor
{
    private int type,       //the type of spawner
    counter,    //the scroll counter
    size;       //how big the spawner is
    private boolean start;
    /**
     * Creates a spawn point for enemies
     * 
     * @param type The type of spawner
     */
    public Spawner(int type){
        GreenfootImage image = getImage();
        image.setTransparency(0);
        setImage(image);
        counter = 0;
        this.type = type;                       
        start=false;                            //to start spawning
        setUp();
    }

    /**
     * Sets up the Spawner
     */
    private void setUp() 
    {        
        switch(type){
            case 1://determine amount to spawn before the spawner disappears
            size = 3;
            break;
            case 2:
            size = 4;
            break;
            case 3:
            size = 4;
            break;
            case 4:
            size = 3;
            break;
            case 6:
            size = 2;
            break;
            case 7:
            size = 3;
            break;
        }
    }   

    /**
     * Gets run every time screen refreshes
     */
    public void act(){        
        //updates screen position
        int scrolledX = ((SWorld)getWorld()).getScrolledX();
        int scrolledY = ((SWorld)getWorld()).getScrolledY();
        if(getX()<721&&getY()<481&&getX()>-1&&getY()>-1){
            start =true;
        }
        counter++;
        if(start){
            switch(type){
                case 1://type 1 - make DavidLiu, every 300 acts
                if(counter<299)
                    counter = 299;
                if(counter%300 ==0){
                    getWorld().addObject(new DavidLiu(),getX()+scrolledX, getY()+scrolledY+30);            
                    counter++;
                    if(counter/300==size){
                        getWorld().removeObject(this);
                    }
                }
                break;
                case 2://type 2 - make KeithWong, every 400 acts
                if(counter<299)
                counter = 399;
                if(counter%400 ==0){
                    getWorld().addObject(new KeithWong(false,1),getX()+scrolledX, getY()+scrolledY+30);            
                    counter++;
                    if(counter/400==size){
                        getWorld().removeObject(this);
                    }
                }
                break;
                case 3://type 3 - make KeithWong, every 500 acts
                if(counter<299)
                counter=499;
                if(counter%500 ==0){
                    getWorld().addObject(new KeithWong(false, 2),getX()+scrolledX, getY()+scrolledY+30);            
                    counter++;
                    if(counter/500==size){
                        getWorld().removeObject(this);
                    }
                }
                break;
                case 4://type 4 - make DavidLiu, every 400 acts
               if(counter<299)
               counter=399;
                if(counter%400 ==0){
                    getWorld().addObject(new Walker(),getX()+scrolledX, getY()+scrolledY+30);            
                    counter++;
                    if(counter/400==size){
                        getWorld().removeObject(this);
                    }
                }
                break;
                case 5://type 5 - make Boss (there's only one boss)
                if(counter<=200){
                    getWorld().addObject(new Boss(),getX()+scrolledX, getY()+scrolledY+30);    
                    counter=500;
                    getWorld().removeObject(this);
                }
                getWorld().removeObject(this);
                break;
                case 6://type 6 - Jumper, every 350 acts
                if(counter<299)
                counter = 349;
                if(counter%350==0){
                    getWorld().addObject(new Jumper(), getX()+scrolledX, getY()+scrolledY+30);
                    counter++;
                    if(counter/350==size){
                        getWorld().removeObject(this);
                    }
                }
                break;
                case 7://type 7 - Flight, every 500 acts
                if(counter<299)
                counter = 499;
                if(counter%500==0){
                    getWorld().addObject(new Flight(), getX()+scrolledX, getY()+scrolledY+30);
                    counter++;
                    if(counter/500==size){
                        getWorld().removeObject(this);
                    }
                }

            }
        }
    }
}
