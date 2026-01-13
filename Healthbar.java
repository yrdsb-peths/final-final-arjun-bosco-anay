import greenfoot.*;  // Imports Greenfoot classes for actors, images, and colors

/**
 * This class displays a health bar that visually shows
 * the current health of an object.
 */
public class Healthbar extends Actor
{
    // Maximum and current health values
    private int maxHealth;
    private int health;
    
    // Size of the health bar
    private int width = 40;
    private int height = 6;
    
    /**
     * Constructor that initializes health values
     * and draws the full health bar.
     */
    public Healthbar(int maxHealth)
    {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        updateImage(); // Draws the initial health bar
    }
    
    /**
     * Updates the current health value
     * and redraws the health bar.
     */
    public void update(int health)
    {
        this.health = health;
        updateImage();
    }
    
    /**
     * Redraws the health bar image.
     * Red shows max health, green shows current health.
     */
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(width, height);
        
        // Draw background (max health)
        img.setColor(Color.RED);
        img.fillRect(0, 0, width, height);
        
        // Draw current health amount
        img.setColor(Color.GREEN);
        int greenWidth = (int)((double) health / maxHealth * width);
        img.fillRect(0, 0, greenWidth, height);
        
        setImage(img); // Updates the displayed image
    }
    
    /**
     * Act method is unused since the health bar
     * only updates when health changes.
     */
    public void act()
    {
                
    }
}
