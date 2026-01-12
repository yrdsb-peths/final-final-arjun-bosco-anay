import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor
{
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int speed = 1;
    boolean ninjaHasMoved = false;
    
    private int maxHealth = 100;
    private int health = 100; 
    private Healthbar healthBar;    
    
    private static GreenfootSound hitSound = new GreenfootSound("hurt_sound.mp3");
    private static GreenfootSound deathSound = new GreenfootSound("Death.mp3");

    public Enemy()
    {
        GreenfootImage img = new GreenfootImage("Enemy.png");
        img.scale(60, 80);
        setImage(img);
    }
    public void addedToWorld(World w)
    {
        Ninja ninja = w.getObjects(Ninja.class).get(0);
        
        int x;
        int y;
        
        if(ninja.getX() < w.getWidth() / 2)
        {
            x = Greenfoot.getRandomNumber(w.getWidth() / 2) + w.getWidth() / 2;
        }
        else
        {
            x = Greenfoot.getRandomNumber(w.getWidth() / 2);
        }
        
        if(ninja.getY() < w.getHeight() / 2)
        {
            y = Greenfoot.getRandomNumber(w.getHeight() / 2) + w.getHeight() / 2;
        }
        else
        {
            y = Greenfoot.getRandomNumber(w.getHeight() / 2);
        }
        
        healthBar = new Healthbar(maxHealth);
        w.addObject(healthBar, getX(), getY() - 50);
        
        
        
        setLocation(x, y);
        healthBar.setLocation(getX() - 5, getY() - 40);
    }
    public void act()
    {
        checkIfNinjaMoved();
        if (ninjaHasMoved)
        {
            followNinja();
        }

        
        if (healthBar != null)
        {
            healthBar.setLocation(getX() - 5, getY() - 40);
        }
    }
    public void checkIfNinjaMoved()
    {
        Ninja ninja = getWorld().getObjects(Ninja.class).get(0);
        
  
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right"))
        {
            ninjaHasMoved = true;
        }
    }
    public void followNinja()
    {
        if(getWorld().getObjects(Ninja.class).size() > 0)
        {
            Ninja ninja = getWorld().getObjects(Ninja.class).get(0);
            
            // Calculate horizontal and vertical distance to ninja
            int distanceX = Math.abs(getX() - ninja.getX());
            int distanceY = Math.abs(getY() - ninja.getY());
            
            // Only follow if ninja is within a 100x100 pixel box
            // (50 pixels in each direction from the enemy's center)
            if (distanceX <= 300 && distanceY <= 300)
            {
                int dx = 0;
                int dy = 0;
                
                if(getX() < ninja.getX())
                {
                    dx = speed;
                }
                else if (getX() > ninja.getX())
                {
                    dx = -speed;
                }
                
                if(getY() < ninja.getY())
                {
                    dy = speed;
                }
                else if (getY() > ninja.getY())
                {
                    dy = -speed;
                }
                setLocation(getX() + dx, getY() + dy);
            }
        }
    }
    public void takeDamage(int damage)
    {
        health -= damage;
        
        hitSound.stop();
        hitSound.play();
        
        if (health <= 0)
        {
            health = 0;
            die();
        }
        // Update health bar
        if (healthBar != null)
        {
            healthBar.update(health);
        }
    }
    private void die()
    {  
        deathSound.stop();
        deathSound.play();
        
        // Remove health bar first
        if (healthBar != null)
        {
            getWorld().removeObject(healthBar);
        }
        // Then remove enemy
        getWorld().removeObject(this);
    }
    public int getHealth()
    {
        return health;
    }
    public int getMaxHealth()
    {
        return maxHealth;
    }
}
