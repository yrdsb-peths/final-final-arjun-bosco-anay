import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ninja here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ninja extends Actor
{
    /**
     * Act - do whatever the Ninja wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    int dx = 0;
    int dy = 0;
    int speed = 2;
    
    String facing = "south";
    GreenfootImage[] idleNorth = new GreenfootImage[2];
    GreenfootImage[] idleSouth = new GreenfootImage[2];
    GreenfootImage[] idleEast = new GreenfootImage[2];
    GreenfootImage[] idleWest = new GreenfootImage[2];
    
    SimpleTimer animationTimer = new SimpleTimer();
    
    
    public Ninja()
    {
        
        for (int i = 0; i < idleNorth.length; i++)
        {
            idleNorth[i] = new GreenfootImage("images/ninja_idle_back/idle_back" + i + ".png");
            idleNorth[i].scale(45, 60);
        }
            
        for (int i = 0; i < idleSouth.length; i++)
        {
            idleSouth[i] = new GreenfootImage("images/ninja_idle/idle" + i + ".png");
            idleSouth[i].scale(45, 60);
        }    
         
        for (int i = 0; i < idleEast.length; i++)
        {
            idleEast[i] = new GreenfootImage("images/ninja_idle_side/idle_side" + i + ".png");
            idleEast[i].scale(45, 60);
        }   
        
        for (int i = 0; i < idleWest.length; i++)
        {
            idleWest[i] = new GreenfootImage(idleEast[i]);
            idleWest[i].mirrorHorizontally();
        }
        setImage(idleSouth[1]);  
    }
    
    int imageIndex = 0;
    public void animateNinja()
    {
        if (animationTimer.millisElapsed() < 200)
        {
            return;
        }
        animationTimer.mark();
        if(facing.equals("north"))
        {
            setImage(idleNorth[imageIndex]);
        } 
        else if(facing.equals("south"))
        {
            setImage(idleSouth[imageIndex]);
        }
        else if(facing.equals("east"))
        {
            setImage(idleEast[imageIndex]);
        }
        else if(facing.equals("west"))
        {
            setImage(idleWest[imageIndex]);
        }
        
        
        imageIndex = (imageIndex + 1) % idleNorth.length;
    }
    
    public void act()
    {
        // Add your action code here.
        playerMovement();
        animateNinja();
        
    }
    
    public void playerMovement()
    {
        boolean up = Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("up");
        boolean down = Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("down");
        boolean left = Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("left");
        boolean right = Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D") || Greenfoot.isKeyDown("right");
        
        dx = 0;
        dy = 0;
        facing = "south";
        if (up)
        {
            dy = -speed;
            
            facing = "north";
        }
        if (down)
        {
            dy = speed;
            
            facing = "south";
        }
        if (left)
        {
            dx = -speed;
            
            facing = "west";
        }
        if (right)
        {
            dx = speed;
            
            facing = "east";
        }
        
        
        
        
        setLocation(getX() + dx, getY() + dy);
    }
}
