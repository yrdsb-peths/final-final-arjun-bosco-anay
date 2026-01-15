import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays a visual hitbox for debugging.
 * The hitbox is shown as a red rectangle.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class HitboxVisualizer extends Actor
{
    // Hitbox size
    private int width;
    private int height;
    
    /**
     * Creates a hitbox visual with a given size.
     *
     * @param width the hitbox width
     * @param height the hitbox height
     */
    public HitboxVisualizer(int width, int height)
    {
        this.width = width;
        this.height = height;
        updateImage();
    }
    
    // Updates the hitbox image
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(new Color(255, 0, 0, 100)); // Transparent red
        img.fillRect(0, 0, width, height);
        img.setColor(Color.RED);
        img.drawRect(0, 0, width - 1, height - 1);
        setImage(img);
    }
    
    /**
     * Updates the size of the hitbox.
     *
     * @param width the new width
     * @param height the new height
     */
    public void updateSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        updateImage();
    }
}
