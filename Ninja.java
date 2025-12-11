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
    public void act()
    {
        // Add your action code here.
        
        if (Greenfoot.isKeyDown("w"))
        {
            
            dy = -speed;
        }
        if (Greenfoot.isKeyDown("s"))
        {
            
            dy = speed;
        }
        if (Greenfoot.isKeyDown("a"))
        {
            dx = -speed;
            
        }
        if (Greenfoot.isKeyDown("d"))
        {
            dx = speed;
            
        }
        
        
        setLocation(getX() + dx, getY() + dy);
    }
}
