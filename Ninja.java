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
    
    SimpleTimer animationTimer = new SimpleTimer();
    public Ninja()
    {
        for (int i = 0; i < idleNorth.length; i++)
        {
            idleNorth[i] = new GreenfootImage("images/ninja_idle/idle" + i + ".png");
            idleNorth[i].scale(45, 60);
        }
            
        for (int i = 0; i < idleNorth.length; i++)
        {
            idleNorth[i] = new GreenfootImage("images/ninja_idle/idle" + i + ".png");
            idleNorth[i].scale(45, 60);
        }    
            
            
    }
    
    int imageIndex = 0;
    public void animateNinja()
    {
        if (animationTimer.millisElapsed() < 200)
        {
            return;
        }
        animationTimer.mark();
        
        setImage(idleNorth[imageIndex]);
        
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
        
        if (!up && !down && !left && !right)
        {
            dx = 0; dy = 0;
        }
        
        if (up)
        {
            if (!left || !right)
            {
                dx = 0;
            }
            
            dy = -speed;
        }
        if (down)
        {
            if (!left || !right)
            {
                dx = 0;
            }
            dy = speed;
        }
        if (left)
        {
            if (!up || !down)
            {
                dy = 0;
            }
            dx = -speed;
            
        }
        if (right)
        {
            if (!up || !down)
            {
                dy = 0;
            }
            dx = speed;
        }
        
        
        
        
        setLocation(getX() + dx, getY() + dy);
    }
}
