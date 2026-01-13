import greenfoot.*;  // Imports Greenfoot classes for actors, images, sounds, and input

/**
 * This class represents an enemy that can follow the ninja,
 * take damage, and be defeated.
 */
public class Enemy extends Actor
{
    // Movement speed of the enemy
    int speed = 1;
    
    // Tracks whether the ninja has started moving
    boolean ninjaHasMoved = false;
    
    // Health values for the enemy
    private int maxHealth = 100;
    private int health = 100; 
    
    // Health bar displayed above the enemy
    private Healthbar healthBar;    
    
    // Sound effects for taking damage and dying
    private static GreenfootSound hitSound = new GreenfootSound("hurt_sound.mp3");
    private static GreenfootSound deathSound = new GreenfootSound("Death.mp3");

    /**
     * Constructor that sets and scales the enemy image.
     */
    public Enemy()
    {
        GreenfootImage img = new GreenfootImage("Enemy.png");
        img.scale(60, 80);
        setImage(img);
    }

    /**
     * Runs when the enemy is added to the world.
     * Spawns the enemy away from the ninja and adds a health bar.
     */
    public void addedToWorld(World w)
    {
        Ninja ninja = w.getObjects(Ninja.class).get(0);
        
        int x;
        int y;
        
        // Spawns enemy on opposite side of the ninja (horizontal)
        if(ninja.getX() < w.getWidth() / 2)
        {
            x = Greenfoot.getRandomNumber(w.getWidth() / 2) + w.getWidth() / 2;
        }
        else
        {
            x = Greenfoot.getRandomNumber(w.getWidth() / 2);
        }
        
        // Spawns enemy on opposite side of the ninja (vertical)
        if(ninja.getY() < w.getHeight() / 2)
        {
            y = Greenfoot.getRandomNumber(w.getHeight() / 2) + w.getHeight() / 2;
        }
        else
        {
            y = Greenfoot.getRandomNumber(w.getHeight() / 2);
        }
        
        // Create and add health bar
        healthBar = new Healthbar(maxHealth);
        w.addObject(healthBar, getX(), getY() - 50);
        
        // Set enemy and health bar positions
        setLocation(x, y);
        healthBar.setLocation(getX() - 5, getY() - 40);
    }

    /**
     * Main behavior loop.
     * Checks movement and updates health bar position.
     */
    public void act()
    {
        checkIfNinjaMoved();
        if (ninjaHasMoved)
        {
            followNinja();
        }

        // Keeps health bar above the enemy
        if (healthBar != null)
        {
            healthBar.setLocation(getX() - 5, getY() - 40);
        }
    }

    /**
     * Detects if the ninja has moved using keyboard input.
     */
    public void checkIfNinjaMoved()
    {
        Ninja ninja = getWorld().getObjects(Ninja.class).get(0);
        
        // Checks common movement keys
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("s") || 
            Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down") || 
            Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right"))
        {
            ninjaHasMoved = true;
        }
    }

    /**
     * Moves the enemy toward the ninja if close enough.
     */
    public void followNinja()
    {
        if(getWorld().getObjects(Ninja.class).size() > 0)
        {
            Ninja ninja = getWorld().getObjects(Ninja.class).get(0);
            
            // Distance between enemy and ninja
            int distanceX = Math.abs(getX() - ninja.getX());
            int distanceY = Math.abs(getY() - ninja.getY());
            
            // Only follow if ninja is within range
            if (distanceX <= 300 && distanceY <= 300)
            {
                int dx = 0;
                int dy = 0;
                
                // Horizontal movement
                if(getX() < ninja.getX()) dx = speed;
                else if (getX() > ninja.getX()) dx = -speed;
                
                // Vertical movement
                if(getY() < ninja.getY()) dy = speed;
                else if (getY() > ninja.getY()) dy = -speed;
                
                setLocation(getX() + dx, getY() + dy);
            }
        }
    }

    /**
     * Reduces health when damaged and plays sound effects.
     */
    public void takeDamage(int damage)
    {
        health -= damage;
        
        hitSound.stop();
        hitSound.play();
        
        // Check for death
        if (health <= 0)
        {
            health = 0;
            die();
        }
        
        // Update health bar display
        if (healthBar != null)
        {
            healthBar.update(health);
        }
    }

    /**
     * Handles enemy death and cleanup.
     */
    private void die()
    {  
        deathSound.stop();
        deathSound.play();
        
        // Remove health bar first
        if (healthBar != null)
        {
            getWorld().removeObject(healthBar);
        }
        
        // Remove enemy from the world
        getWorld().removeObject(this);
    }

    // Returns current health
    public int getHealth()
    {
        return health;
    }

    // Returns maximum health
    public int getMaxHealth()
    {
        return maxHealth;
    }
}
