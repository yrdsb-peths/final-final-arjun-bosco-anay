import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartScreen extends World
{

    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        GreenfootImage bg = new GreenfootImage("StartScreen.jpg");
        setBackground(bg);
        
        addObject(new Button("Press Start", "start"), getWidth() / 2, getHeight() / 2 + 100);
        
        // Instructions (middle-left area)
        Label moveLabel = new Label("WASD / Arrow Keys to Move", 28);
        Label attackLabel = new Label("SPACE to Attack", 28);
        
        Label firstDoorLabel = new Label("Walk into the", 28);
        Label secondDoorLabel = new Label("door to progress", 28);
        
        addObject(moveLabel, getWidth() / 3 - 40, getHeight() / 2);
        addObject(attackLabel, getWidth() / 3 - 40, getHeight() / 2 + 40);
        addObject(firstDoorLabel, getWidth() / 2 + 150, getHeight() / 2 - 40);
        addObject(secondDoorLabel, getWidth() / 2 + 150, getHeight() / 2 - 20);
    }
    
}
