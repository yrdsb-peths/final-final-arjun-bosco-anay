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
        // Load the door image from images folder
        GreenfootImage img = new GreenfootImage("images/door.png");
        // Scale it to 350x350 pixels as requested
        img.scale(100, 30);
        setImage(img);
    }
}
