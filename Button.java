import greenfoot.*;  // Imports Greenfoot classes for actors, worlds, images, and input

/**
 * This class creates a clickable button.
 * Each button performs a specific action when clicked.
 */
public class Button extends Actor
{
    /**
     * Stores the button’s function (start, restart, or exit).
     * The action determines what happens when the button is clicked.
     */
    private String action; // Signals "start", "restart", or "exit"
    
    /**
     * Constructor that creates a text-based button
     * and assigns its action.
     */
    public Button(String text, String action)
    {
        this.action = action; // Saves the assigned action
        
        // Creates the button image with transparent background
        GreenfootImage img = new GreenfootImage(text, 36, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(img); // Displays the button on screen
    }
    
    /**
     * Runs repeatedly to check for mouse clicks.
     * Performs an action based on the button type.
     */
    public void act()
    {
        // Checks if this button was clicked
        if(Greenfoot.mouseClicked(this))
        {
            // Starts or restarts the game
            if(action.equals("start") || action.equals("restart"))
            {
                Greenfoot.setWorld(new MyWorld());
            }
            // Returns to the start screen
            else if(action.equals("exit"))
            {
                Greenfoot.setWorld(new StartScreen());
            }
        }
    }
}
