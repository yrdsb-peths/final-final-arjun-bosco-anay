import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor
{
    /**
     * Act - do whatever the Button wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private String action; // Signals "start", "restart", or "exit"
    
    public Button(String text, String action)
    {
        this.action = action;
        GreenfootImage img = new GreenfootImage(200, 50);
        img.setColor(Color.WHITE);
        img.drawString(text, 50, 50);
        setImage(img);
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            if(action.equals("start") || action.equals("restart"))
            {
                Greenfoot.setWorld(new MyWorld());
            }
            else if(action.equals("exit"))
            {
                Greenfoot.setWorld(new StartScreen());
            }
        }
    }
}
