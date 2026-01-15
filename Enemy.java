import greenfoot.*;  // Imports Greenfoot classes

/**
 * Represents an enemy that can follow the ninja and be defeated.
 */
public class Enemy extends Actor
{
    // Enemy movement speed
    int speed = 1;
    
    // Checks if the ninja has moved
    boolean ninjaHasMoved = false;
    
    // Enemy health values
    private int maxHealth = 100;
    private int health = 100; 
    
    // Health bar above the enemy
    private Healthbar healthBar;    
    
    // Sound effects
    private static GreenfootSound hitSound = new GreenfootSound("hurt_sound.mp3");
    private static GreenfootSound deathSound = new GreenfootSound("Death.mp3");
    
    private boolean isMiniBoss = false;
    private int bossLevel = 0;
    private Label bossTitleLabel = null;

    /**
     * Creates an enemy and sets its health and image.
     */
    public Enemy()
    {   
        GreenfootImage img = new GreenfootImage("Enemy.png");
        img.scale(60, 80);
        setImage(img);
        
        maxHealth = 100 + MyWorld.getEnemyHealthBoost();
        health = maxHealth;
    }   

    /**
     * Sets the enemy position and adds a health bar.
     */
    public void addedToWorld(World w)
    {
        Ninja ninja = w.getObjects(Ninja.class).get(0);
        
        int x;
        int y;
        
        // Spawn position
        if (isMiniBoss)
        {
            x = w.getWidth() / 2;
            y = 100;
        }
        else
        {
            if(ninja.getX() < w.getWidth() / 2)
                x = Greenfoot.getRandomNumber(w.getWidth() / 2) + w.getWidth() / 2;
            else
                x = Greenfoot.getRandomNumber(w.getWidth() / 2);
            
            if(ninja.getY() < w.getHeight() / 2)
                y = Greenfoot.getRandomNumber(w.getHeight() / 2) + w.getHeight() / 2;
            else
                y = Greenfoot.getRandomNumber(w.getHeight() / 2);
        }
        
        healthBar = new Healthbar(maxHealth);
        w.addObject(healthBar, getX(), getY() - 50);
        
        setLocation(x, y);
        healthBar.setLocation(getX() - 5, getY() - 40);
        
        if (bossTitleLabel != null)
            bossTitleLabel.setLocation(getX(), getY() - 90);
    }

    /**
     * Controls enemy movement and updates displays.
     */
    public void act()
    {
        checkIfNinjaMoved();
        
        if (ninjaHasMoved)
            followNinja();
        
        if (healthBar != null)
            healthBar.setLocation(getX() - 5, getY() - 40);
        
        if (bossTitleLabel != null)
            bossTitleLabel.setLocation(getX(), getY() - 90);
    }

    /**
     * Checks if the ninja has started moving.
     */
    public void checkIfNinjaMoved()
    {
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("a") ||
            Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("d") ||
            Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down") ||
            Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right"))
        {
            ninjaHasMoved = true;
        }
    }

    /**
     * Moves the enemy toward the ninja.
     */
    public void followNinja()
    {
        if(getWorld().getObjects(Ninja.class).size() > 0)
        {
            Ninja ninja = getWorld().getObjects(Ninja.class).get(0);
            
            int dx = 0;
            int dy = 0;
            
            if(Math.abs(getX() - ninja.getX()) <= 300 &&
               Math.abs(getY() - ninja.getY()) <= 300)
            {
                if(getX() < ninja.getX()) dx = speed;
                else if (getX() > ninja.getX()) dx = -speed;
                
                if(getY() < ninja.getY()) dy = speed;
                else if (getY() > ninja.getY()) dy = -speed;
                
                setLocation(getX() + dx, getY() + dy);
            }
        }
    }

    /**
     * Reduces enemy health when damaged.
     *
     * @param damage the amount of damage taken
     */
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
        
        if (healthBar != null)
            healthBar.update(health);
    }

    // Handles enemy death
    private void die()
    {  
        deathSound.stop();
        deathSound.play();
        
        MyWorld.addKill();
        
        if (isMiniBoss)
            MyWorld.increaseEnemyHealthBoost(); 
        
        if (healthBar != null)
            getWorld().removeObject(healthBar);
        
        if (bossTitleLabel != null)
            getWorld().removeObject(bossTitleLabel);
        
        getWorld().removeObject(this);
    }

    /**
     * Returns the enemy's current health.
     *
     * @return the current health
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * Returns the enemy's maximum health.
     *
     * @return the maximum health
     */
    public int getMaxHealth()
    {
        return maxHealth;
    }
    
    /**
     * Sets the enemy as a mini boss.
     *
     * @param health the mini boss health
     * @param level the mini boss level
     */
    public void setAsMiniBoss(int health, int level)
    {
        this.isMiniBoss = true;
        this.bossLevel = level;
        this.maxHealth = health;
        this.health = health;
        
        GreenfootImage img = getImage();
        img.scale(80, 105); 
        
        if (getWorld() != null)
        {
            bossTitleLabel = new Label("Lvl " + level + " Mini Boss", 20);
            bossTitleLabel.setFillColor(Color.RED);
            getWorld().addObject(bossTitleLabel, getX(), getY() - 90);
        }
    }
}
