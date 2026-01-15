import greenfoot.*;  // Imports Greenfoot classes

/**
 * Creates a clickable button.
 * Each button performs an action when clicked.
 */
public class Button extends Actor
{
    /**
     * Stores the button action.
     */
    private String action; // "start", "restart", or "exit"
    
    /**
     * Creates a button with text and an action.
     *
     * @param text the text shown on the button
     * @param action the action when clicked
     */
    public Button(String text, String action)
    {
        this.action = action; // Saves the action
        
        // Creates the button image
        GreenfootImage img = new GreenfootImage(text, 36, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(img); // Sets the image
    }
    
    /**
     * Checks for mouse clicks and performs the button action.
     */
    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            if(action.equals("start"))
            {
                // Starts a new game
                MyWorld.resetCurrentKills();
                Greenfoot.setWorld(new MyWorld());
            }
            else if(action.equals("restart"))
            {
                // Restarts the game
                Greenfoot.setWorld(new MyWorld());
            }
            else if(action.equals("exit"))
            {
                // Returns to start screen
                Greenfoot.setWorld(new StartScreen());
            }
        }
    }
}
