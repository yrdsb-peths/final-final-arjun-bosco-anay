import greenfoot.*;  // Imports Greenfoot classes

/**
 * Displays a health bar that shows current health.
 */
public class Healthbar extends Actor
{
    // Health values
    private int maxHealth;
    private int health;
    
    // Health bar size
    private int width = 40;
    private int height = 6;
    
    /**
     * Creates a health bar with full health.
     *
     * @param maxHealth the maximum health value
     */
    public Healthbar(int maxHealth)
    {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        updateImage(); // Draws the bar
    }
    
    /**
     * Updates the health bar value.
     *
     * @param health the new health value
     */
    public void update(int health)
    {
        this.health = health;
        updateImage();
    }
    
    /**
     * Runs each game cycle (no action needed).
     */
    public void act()
    {
        
    }
    
    // Redraws the health bar image
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(width, height);
        
        img.setColor(Color.RED);
        img.fillRect(0, 0, width, height);
        
        img.setColor(Color.GREEN);
        int greenWidth = (int)((double) health / maxHealth * width);
        img.fillRect(0, 0, greenWidth, height);
        
        setImage(img);
    }
}
