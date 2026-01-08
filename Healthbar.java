import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Healthbar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Healthbar extends Actor
{
    /**
     * Act - do whatever the Healthbar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int maxHealth;
    private int health;
    private int width = 40;
    private int height = 6;
    
    public Healthbar(int maxHealth)
    {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        
    }
    
    
    
    public void act()
    {
                
    }
}
