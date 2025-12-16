import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor
{
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int speed = 1;
    boolean ninjaHasMoved = false;
    public Enemy()
    {
            GreenfootImage img = new GreenfootImage("Enemy.png");
            img.scale(60, 80);
            setImage(img);
    }
    
    public void addedToWorld(World w)
    {
        Ninja ninja = w.getObjects(Ninja.class).get(0);
        
        int x;
        int y;
        
        if(ninja.getX() < w.getWidth() / 2)
        {
            x = Greenfoot.getRandomNumber(w.getWidth() / 2) + w.getWidth() / 2;
        }
        else
        {
            x = Greenfoot.getRandomNumber(w.getWidth() / 2);
        }
        
        if(ninja.getY() < w.getHeight() / 2)
        {
            y = Greenfoot.getRandomNumber(w.getHeight() / 2) + w.getHeight() / 2;
        }
        else
        {
            y = Greenfoot.getRandomNumber(w.getHeight() / 2);
        }
        
        
        
        setLocation(x, y);
    }
    
    public void act()
    {
        
        followNinja();
    }
    
    public void checkIfNinjaMoved()
    {
        Ninja ninja = getWorld().getObjects(Ninja.class).get(0);
        
        if
    }
    
    public void followNinja()
    {
        if(getWorld().getObjects(Ninja.class).size() > 0)
        {
            Ninja ninja = getWorld().getObjects(Ninja.class).get(0);
            
            int dx = 0;
            int dy = 0;
            
            if(getX() < ninja.getX())
            {
                dx = speed;
            }
            else if (getX() > ninja.getX())
            {
                dx = -speed;
            }
            
            if(getY() < ninja.getY())
            {
                dy = speed;
            }
            else if (getY() > ninja.getY())
            {
                dy = -speed;
            }
            setLocation(getX() + dx, getY() + dy);
        }
    }
}
