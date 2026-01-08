import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Healthbar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends Actor
{
    /**
     * Act - do whatever the Healthbar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int maxHealth;
    private int health;
    private int width = 40;
    private int height = 6;
    
    public HealthBar(int maxHealth)
    {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        updateImage();
    }
    
    public void update(int health)
    {
        this.health = health;
        updateImage();
    }
    
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
    
    public void act()
    {
                
    }
}
