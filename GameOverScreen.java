import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOverScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOverScreen extends World
{

    /**
     * Constructor for objects of class GameOverScreen.
     * 
     */
    public GameOverScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        
        GreenfootImage bg = new GreenfootImage("GameOverScreen.jpg");
        setBackground(bg);
        
        addObject(new Button("Restart", "restart"), getWidth() / 2, getHeight() / 3);
        
        addObject(new Button("Exit", "exit"), getWidth() / 2, getHeight() / 2);
    }
}
