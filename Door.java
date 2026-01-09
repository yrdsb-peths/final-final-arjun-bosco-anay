import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


public class Door extends Actor
{
    /**
     * Act - do whatever the Door wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    
    public Door()
    {
        GreenfootImage img = new GreenfootImage(60, 80);
        img.setColor(Color.BLACK);
        img.fillRect(0, 0, 60, 80);
        setImage(img);
    }
}
