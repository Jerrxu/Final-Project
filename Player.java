import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
import java.util.ArrayList;
/**
 * Player is able to run, jump, shoot, and stuff like that.
 * 
 * @author Keith Wong help from David Liu
 * @version January 20, 2014
 */
public class Player extends Collision
{
    //attributes
    private int health = 100;

    //moving
    private int speedY = 7; //vertical jump sped
    private int speedX = 12; //hoizontal speed
    private int gravity = 1; // acceleration due to gravity
    private int deltaY = 0; // change in Y for setting new location
    private int deltaX = 0; // change in X for setting new location
    private int jumpCounter = 0; // number of cycles that the player may continue jumping
    private boolean goingLeft = false, allowLeft = true, allowRight = true; // left and right trackers/limiters
    private boolean jumped = false; // jump tracker
    private boolean continueJump = false; // jump limiter
    private boolean allowJump = true; // jump limiter
    private int maxY = 20; // max fall speed
    private int knockbackCounter = 0; //the number of cycles that the player is knocked back for

    //weapons
    private boolean bulletShot = false; // makes it so that the player has to spam z to shoot
    private int bulletCD = 10; // cooldown for shooting
    private int weaponChoice = 1; //1 is regular bullets, 2 is missiles, 3 is super missiles
    private int missileAmmo = 0; //when 0, can not shoot missiles
    private int superMissileAmmo = 0; //when 0, can not shoot super missiles

    //position
    private boolean crouched = false; //tracks crouching

    //image things
    private GreenfootImage g = new GreenfootImage("RIGHT/runRight0.png"); //
    private String image = "RIGHT/runRight0.png"; // used to keep track of which image to display to lower memory usage
    private int transparency = 255; // for flickering when player  is hit
    private int flashCycles = 0; // number of times to flash

    //Collision things
    private ArrayList<Tile> tiles; //a list of tiles that the player is hitting
    private boolean grounded = false; //if true, player is on ground
    private int imgHeight = g.getHeight()/2; //distance from center of player to top or bottom
    private int imgWidth = g.getWidth()/2; // distance from center of player to left or right
    private ArrayList<Actor> enemies; // a list of enemies the player is hitting
    private Actor enemy; // the one enemy i care about
    private boolean knockLeft; // knockLeft is true when I am going to be knocked left, false when knocked right

    //game flow things
    private boolean previouslyAttacked; // invincible when true
    private int counter = 0; // counts up to 150, then allows self to be hit again

    //graphics
    private int indexL = 0; // which image to use in the cycle of images for moving left
    private int indexR = 0; // which image to use in the cycle of images for moving right
    private SpritePlayer leftRun, rightRun; // the animated gif player

    //sounds
    private GreenfootSound shootBullet = new GreenfootSound("BulletShot.wav");
    private GreenfootSound shootMissile = new GreenfootSound("MissileShot.wav");
    private GreenfootSound shootSuperMissile = new GreenfootSound("SuperMissileShot.wav");
    private GreenfootSound jumpSound = new GreenfootSound("Jump.wav");
    /**
     * Creates the player, sets its image, and loads the left and right walking aminations.
     * 
     */
    public Player(){
        setImage(g); //sets the image so that it doesn't break when you switch computers
        leftRun = new SpritePlayer("LEFT/runLeft", 10); // loads the images for running left, 10 images to cycle through
        rightRun = new SpritePlayer("RIGHT/runRight", 10); // loads the images for running right, 10 images to cycle through
    }

    /**
     * Move and shoot.
     * 
     */
    public void act(){
        if(((Map)(getWorld())).checkPause()==false){
            movement();
            weaponChoice();
            shooting();
            if(health<1){
                Greenfoot.setWorld(new Screen(1));
            }
        }
    }

    /**
     * Player movement.
     * 
     */
    private void movement(){
        if(((Map)getWorld()).getMapNum() ==7){//detetcion if hit boss at boss level only
            Boss boss = (Boss)getOneIntersectingObject(Boss.class);
            if(touch(Boss.class))//use of collision class to detect
            {
                enemyHit(getHealth()-1);//lose one health at a time
                if(getOneTouchedObject(Boss.class).getX()>getX() ){
                    setLocation(getX()-10,getY());//set location so wont get stuck in boss                    
                }else{
                    setLocation(getX()+10,getY());
                }
            }else{
            }
        }
        //invincibility frames, invincible for 150 acts
        if(previouslyAttacked){
            counter++;
            if(counter>150){
                previouslyAttacked =false;
                counter=0;
            }
        }

        //resets limiters so they can be checked again
        allowLeft = true;
        allowRight = true;
        crouched = false;
        deltaX = 0;

        //sets the new image of the player
        g = new GreenfootImage(image);
        //shows off the invincibility frames of the player
        if (flashCycles > 0){
            transparency = Math.abs(transparency - 255);
            g.setTransparency(transparency);
            flashCycles--;
        }
        this.setImage(g);
        //sets the width and height for easier calculation during collision detection
        int imgWidth = g.getWidth()/2;
        int imgHeight = g.getHeight()/2;

        //collision detection, checks every tile that the player is touching
        tiles = (ArrayList) getObjectsInRange(g.getHeight(),Tile.class);
        if (tiles.size() > 0){
            for(int i = 0; i<tiles.size(); i++){
                collision(tiles.get(i));
            }
        }

        //sets the image for left or right
        if(goingLeft){
            image = "standLeft.png";
        }else{
            image = "standRight.png";
        }

        //check for pause button
        if(Greenfoot.isKeyDown("P")){
            ((Map)(getWorld())).setPause(true);
            getWorld().addObject(new PauseScreen(false),(((SWorld)(getWorld())).getScrolledX())+360, (((SWorld)(getWorld())).getScrolledY())+240);
        }

        //applys graivty
        gravity();

        //jumps
        if (Greenfoot.isKeyDown("x") && knockbackCounter == 0 && allowJump == true){
            jump();
        }
        //makes it so that you must let go of x and press it again to jump a second time
        if (Greenfoot.isKeyDown("x") == false){            
            jumped = false;
            allowJump = true;            
        }
        //crouches
        if (Greenfoot.isKeyDown("down")){
            crouch();
        }
        //allows the player to move left and right if the player is not crouching or being hit
        if (crouched == false && knockbackCounter == 0){
            //moves left
            if (Greenfoot.isKeyDown("left")&&allowLeft){
                moveLeft();
            }
            //moves right
            if (Greenfoot.isKeyDown("right")&&allowRight){
                moveRight();
            }
        }

        //knocks back the player if it has been hit
        if (knockbackCounter > 0){
            if(enemy == null){
                enemies = (ArrayList) getIntersectingObjects(Enemy.class);
            }
            if(enemies.size()>0){
                enemy = enemies.get(0);
                if(enemy!=null){
                    if (this.getX() > enemy.getX()){
                        knockLeft = false;
                        image = "knockBackRight.png";
                    } else {
                        knockLeft = true;
                        image = "knockBackLeft.png";
                    }

                    if (knockLeft == true){
                        deltaY = -knockbackCounter;
                        deltaX = -4;
                    } else if (knockLeft == false) {
                        deltaY = -knockbackCounter;
                        deltaX = 4;
                    }
                }
            }
            knockbackCounter--;
        } else {
            enemy = null;
        }

        //speed limit for falling
        if (deltaY > maxY){
            deltaY = maxY;
        }
        if (deltaY < -maxY){
            deltaY = -maxY;
        }

        //after all calculations are done for movement, sets the location of the player
        setLocation(getX() + deltaX, getY() + deltaY);
    }

    /**
     * Hit detection for tiles. If the player has clipped into a tile, it takes it out.
     * 
     * @param tileA The tile to be checked.
     * 
     */
    private void collision(Tile tileA){
        int thisX = getX();
        int thisY = getY();

        int tileX = tileA.getX();
        int tileY = tileA.getY();
        if (thisY + imgHeight >= tileY - 24 &&Math.abs(thisX-tileX)<imgWidth+1 ){ // object is above the tile
            deltaY = ((tileY - 24) - (thisY + imgHeight));
            grounded = true;
            jumpCounter = 0;
        } 
        if ((tileY + 24)<=(thisY - imgHeight)&&Math.abs(thisX-tileX)<imgWidth*2){ //object is under the tile
            deltaY =  (thisY  - imgHeight)-(tileY + 24);
        }
        if (thisX + imgWidth > tileX - 24-1 && thisX - imgWidth <= tileX - 24-1 && thisX + imgWidth < tileX&&Math.abs(thisY-tileY)<imgHeight+1 ){ //object is on the left of the tile
            allowRight = false;
            deltaX = ((tileX - 24) - (thisX + imgWidth));
        }
        if (thisX - imgWidth < tileX + 24+1 && thisX + imgWidth >= tileX + 24+1 && thisX - imgWidth > tileX&&Math.abs(thisY-tileY)<imgHeight+1){ //object is on the right of the tile
            allowLeft = false;
            deltaX = (tileX + 24) - (thisX - imgWidth);
        }

        if(Math.abs(thisX-tileX)<imgWidth+1&&Math.abs(thisY-tileY)<imgHeight+1){//touches corner piece
            if(thisX>tileX){
                deltaX = Math.abs(thisX-tileX);
            }else if(thisX<tileX){
                deltaX = -Math.abs(thisX-tileX);
            }if(thisY>tileY){
                deltaY = Math.abs(thisY-tileY);
            }else if(thisY<tileY){
                deltaY = -Math.abs(thisY-tileY);
            }
            setLocation(getX() + deltaX, getY() + deltaY);//quickly reset back location
        }
    }

    /**
     * Makes the character move left.
     */
    private void moveLeft(){
        deltaX -= speedX;
        goingLeft = true;

        //image change
        indexR = 0;
        image = leftRun.getImageName(indexL);
        indexL++;
        if (indexL >= 10){
            indexL = 0;
        }
    }

    /**
     * Makes the charcter move right.
     * 
     */
    private void moveRight(){
        deltaX += speedX;
        goingLeft = false;

        //image change
        indexL = 0;
        image = rightRun.getImageName(indexR);
        indexR++;

        if(indexR >= 10){
            indexR = 0;
        }
    }

    /**
     * Makes the character jump. Holding down x for longer makes the player jump higher.
     * 
     */
    private void jump(){
        //initial jump
        if (jumped == false && deltaY == 0){
            setLocation(getX(), getY() - speedY);
            jumped = true;
            continueJump = true;
            jumpSound.play();
        }
        //held jump
        if (continueJump == true){
            deltaY -= (speedY - jumpCounter); // the amount to increase the jump by decreases the longer you hold it
            if (jumpCounter < 7){
                jumpCounter++;
            } else {
                allowJump = false;
            }
        }
        grounded = false;
    }

    /**
     * Makes the player crouch.
     */
    private void crouch(){
        if (goingLeft == true){
            image = "crouchLeft.png";
        } else {
            image = "crouchRight.png";
        }
        crouched = true;
        setLocation(getX(), getY() + 12);
    }

    /**
     * 
     * Applys gravity.
     */
    private void gravity(){
        deltaY += gravity;
    }

    /**
     * Changes the current weapon. 1 is bullets, 2 is missiles, 3 is superMissiles.
     * 
     */
    private void weaponChoice(){
        if (Greenfoot.isKeyDown("1"))
            weaponChoice = 1;
        if (Greenfoot.isKeyDown("2"))
            weaponChoice = 2;
        if (Greenfoot.isKeyDown("3"))
            weaponChoice = 3;
    }

    /**
     * Shoots the weapon.
     */
    private void shooting(){
        //only allows to player to shoot if it is off cooldown, the player is not being knocked back, or the player has pressed z again.
        if(Greenfoot.isKeyDown("z") && bulletCD == 0 && knockbackCounter == 0 && bulletShot == false){
            if (weaponChoice == 1)
                shootBullet();
            if (weaponChoice == 2 && missileAmmo > 0){
                shootMissile();
                missileAmmo--;
            }
            if (weaponChoice == 3 && superMissileAmmo > 0){
                shootSuperMissile();
                superMissileAmmo--;
            }
            bulletShot = true;
            bulletCD = 10;
        }

        //makes the player have to constantly press z to shoot buttlets
        if (Greenfoot.isKeyDown("z") == false){
            bulletShot = false;
        }

        //cooldown
        if(bulletCD > 0){
            bulletCD--;
        }
    }

    /**
     * Shoots a bullet.
     */
    private void shootBullet(){
        shootBullet.play();
        //Bullet spawns in a different location depending on if the player is crouched or not
        if (crouched == false){
            if (goingLeft == false)
                getWorld().addObject(new Bullet(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() + 30, (((SWorld) getWorld())).getScrolledY() + getY() - 27) ;
            else
                getWorld().addObject(new Bullet(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() - 30, (((SWorld) getWorld())).getScrolledY() + getY() - 27) ;
        } else {
            if (goingLeft == false)
                getWorld().addObject(new Bullet(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() + 30, (((SWorld) getWorld())).getScrolledY() + getY() - 4) ;
            else
                getWorld().addObject(new Bullet(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() - 30, (((SWorld) getWorld())).getScrolledY() + getY() - 4) ;
        }
    }

    /**
     * Shoots a missile.
     * 
     */
    private void shootMissile(){
        shootMissile.play();
        //Missile spawns in a different location depending on if the player is crouched or not
        if (crouched == false){
            if (goingLeft == false)
                getWorld().addObject(new Missile(goingLeft),(((SWorld) getWorld())).getScrolledX() + getX() + 30, (((SWorld) getWorld())).getScrolledY() + getY() - 27);
            else
                getWorld().addObject(new Missile(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() - 30, (((SWorld) getWorld())).getScrolledY() + getY() - 27) ;
        } else {
            if (goingLeft == false)
                getWorld().addObject(new Missile(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() + 30, (((SWorld) getWorld())).getScrolledY() + getY() - 4) ;
            else
                getWorld().addObject(new Missile(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() - 30, (((SWorld) getWorld())).getScrolledY() + getY() - 4) ;
        }
    }

    /**
     * Shoots a SuperMissile.
     * 
     */
    private void shootSuperMissile(){
        shootSuperMissile.play();
        //Super Missile spawns in a different location depending on if the player is crouched or not
        if (crouched == false){
            if (goingLeft == false)
                getWorld().addObject(new SuperMissile(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() + 30, (((SWorld) getWorld())).getScrolledY() + getY()- 27);
            else
                getWorld().addObject(new SuperMissile(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() - 30, (((SWorld) getWorld())).getScrolledY() + getY() - 27) ;
        } else {
            if (goingLeft == false)
                getWorld().addObject(new SuperMissile(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() + 30, (((SWorld) getWorld())).getScrolledY() + getY() - 4) ;
            else
                getWorld().addObject(new SuperMissile(goingLeft), (((SWorld) getWorld())).getScrolledX() + getX() - 30, (((SWorld) getWorld())).getScrolledY() + getY() - 4) ;
        }

    }

    /**
     * Returns health.
     * 
     * @return int the health of the player.
     */
    public int getHealth(){
        return health;
    }

    /**
     * Returns missile ammo.
     * 
     * @return int The ammo left for missiles.
     */
    public int getMissileAmmo(){
        return missileAmmo;
    }

    /**
     * Returns super missile ammo.
     * 
     * @return int The ammo left for super missiles.
     */
    public int getSuperMissileAmmo(){
        return superMissileAmmo;
    }

    /**
     * Sets the health of the player. Only use for healing.
     * 
     * @param amount The amount to set the health to.
     */
    public void setHealth(int amount){
        health = amount;
    }

    /**
     * Sets the health of the player, determines that it is hit by an enemy, and knocks it back. Only use if hit by an enemy.
     * 
     * @param amount The amount to set the health to.
     */
    public void enemyHit(int amount){
        previouslyAttacked = true;
        knockbackCounter = 6; //player will be knocked back for 6 acts
        flashCycles = 149; // player will flash for 149 acts
        health = amount;
    }

    /**
     * Sets the missile ammo.
     * 
     * @ param amount the amount of missile ammo.
     */
    public void setMissileAmmo(int amount){
        missileAmmo = amount;   
    }

    /**
     * Sets the super missile ammo.
     * 
     * @param amount the amount of super missile ammo.
     */
    public void setSuperMissileAmmo(int amount){
        superMissileAmmo = amount;
    }

    /**
     * Returns whether the player was attacked.
     * 
     * @return boolean True when attacked, false when not.
     */
    public boolean recentlyAttacked(){
        return previouslyAttacked;
    }

    /**
     * Returns the current weapon.
     * 
     * @return int The current weapon.
     */
    public int getWeaponChoice(){
        return weaponChoice;
    }
}