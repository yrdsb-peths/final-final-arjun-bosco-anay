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
        
        GreenfootImage img = new GreenfootImage(text, 36, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(img);
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            if(action.equals("start"))
            {
                // Reset kills only when starting fresh from main menu
                MyWorld.resetKills(); // You'll need to add this method
                Greenfoot.setWorld(new MyWorld());
            }
            else if(action.equals("restart"))
            {
                // Don't reset kills on restart after death
                Greenfoot.setWorld(new MyWorld());
            }
            else if(action.equals("exit"))
            {
                Greenfoot.setWorld(new StartScreen());
            }
        }
    }
}
