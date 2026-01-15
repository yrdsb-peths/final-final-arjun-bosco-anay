import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ninja here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ninja extends Actor
{
    /**
     * Act - do whatever the Ninja wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    int dx = 0;
    int dy = 0;
    int speed = 3;
    
    String facing = "south";
    GreenfootImage[] idleNorth = new GreenfootImage[2];
    GreenfootImage[] idleSouth = new GreenfootImage[2];
    GreenfootImage[] idleEast = new GreenfootImage[2];
    GreenfootImage[] idleWest = new GreenfootImage[2];
    
    
    GreenfootImage[] slashNorth = new GreenfootImage[4];
    GreenfootImage[] slashSouth = new GreenfootImage[4];
    GreenfootImage[] slashEast = new GreenfootImage[4];
    GreenfootImage[] slashWest = new GreenfootImage[4];
    
    SimpleTimer idleAnimationTimer = new SimpleTimer();
    SimpleTimer slashAnimationTimer = new SimpleTimer();
    
    int idleIndex = 0;
    int slashIndex = 0;
    
    boolean up;
    boolean down;
    boolean left;
    boolean right;
    boolean space;

    
    boolean isSlashing = false;
    SimpleTimer slashCooldownTimer = new SimpleTimer();
    int slash_cooldown = 100;
    
    int maxHealth = 100;
    int health = 100;
    Healthbar healthBar;
    
    SimpleTimer damageTimer = new SimpleTimer();
    int damageCooldown = 500; // Slower health decline rate
    
    private boolean canSlashDamage = false;
    private int slashDamageFrame = 1; // Frame when damage should be dealt
    int damage_amount = 50;
    private boolean hasDied = false;
    
    private static GreenfootSound hitSound = new GreenfootSound("hurt_sound.mp3");
    private static GreenfootSound deathSound = new GreenfootSound("Death.mp3");
    
    
    private boolean debugMode = false;
    private HitboxVisualizer debugHitbox = null;
    private boolean f2WasPressed = false; // Track if F2 was pressed last frame
    
    /**
     * Constructs a Ninja object.
     * Initializes animations and sets the default idle image.
     */
    
    public Ninja()
    {
        
        for (int i = 0; i < idleNorth.length; i++)
        {
            idleNorth[i] = new GreenfootImage("images/ninja_idle_back/idle_back" + i + ".png");
            idleNorth[i].scale(45, 60);
        }
            
        for (int i = 0; i < idleSouth.length; i++)
        {
            idleSouth[i] = new GreenfootImage("images/ninja_idle/idle" + i + ".png");
            idleSouth[i].scale(45, 60);
        }    
         
        for (int i = 0; i < idleEast.length; i++)
        {
            idleEast[i] = new GreenfootImage("images/ninja_idle_side/idle_side" + i + ".png");
            idleEast[i].scale(45, 60);
        }   
        
        for (int i = 0; i < idleWest.length; i++)
        {
            idleWest[i] = new GreenfootImage(idleEast[i]);
            idleWest[i].mirrorHorizontally();
        }
        
        
        for (int i = 0; i < slashNorth.length; i++)
        {
            slashNorth[i] = new GreenfootImage("images/ninja_slash_back/slash_back" + i + ".png");
            slashNorth[i].scale(75, 80);
        } 
        
        for (int i = 0; i < slashSouth.length; i++)
        {
            slashSouth[i] = new GreenfootImage("images/ninja_slash_forward/slash_forward" + i + ".png");
            slashSouth[i].scale(75, 80);
        } 
        
        for (int i = 0; i < slashSouth.length; i++)
        {
            slashEast[i] = new GreenfootImage("images/ninja_slash_side/slash_side" + i + ".png");
            slashEast[i].scale(75, 55);
        } 
        
        for (int i = 0; i < slashSouth.length; i++)
        {
            slashWest[i] = new GreenfootImage(slashEast[i]);
            slashWest[i].mirrorHorizontally();
        } 
        
        
        setImage(idleSouth[1]);  
    }
    
   
    public void animateNinja()
    {
        if (idleAnimationTimer.millisElapsed() < 200)
        {
            return;
        }
        idleAnimationTimer.mark();
        if(facing.equals("north"))
        {
            setImage(idleNorth[idleIndex]);
        } 
        else if(facing.equals("south"))
        {
            setImage(idleSouth[idleIndex]);
        }
        else if(facing.equals("east"))
        {
            setImage(idleEast[idleIndex]);
        }
        else if(facing.equals("west"))
        {
            setImage(idleWest[idleIndex]);
        }
        
        
        
        
        idleIndex = (idleIndex + 1) % idleNorth.length;
    }
    
    /**
     * Called automatically when the Ninja is added to the world.
     * Creates and positions the health bar above the Ninja.
     *
     * @param w the world the Ninja was added to
     */
    
    public void addedToWorld(World w)
    {
        healthBar = new Healthbar(maxHealth);
        w.addObject(healthBar, getX(), getY() - 40);
    }
    
    public void checkEnemyTouch()
    {
        if(isTouching(Enemy.class) && damageTimer.millisElapsed() >= damageCooldown)
        {
            health -= 5;
            if(health < 0)
            {
                health = 0;
            }
            
            hitSound.stop();
            hitSound.play();
            
            healthBar.update(health);
            damageTimer.mark();
        }
    }
    
    public void act()
    {
        // Add your action code here.
        getKeyboardInputs();
        slash();
        playerMovement();
        checkDoor();
        
        // TOGGLE DEBUG MODE WITH F2
        boolean f2IsPressed = Greenfoot.isKeyDown("F2");
        if (f2IsPressed && !f2WasPressed) {
            // F2 was just pressed (not held)
            debugMode = !debugMode; // Toggle debug mode
            
            // Show/hide debug text when toggling
            if (debugMode && getWorld() != null) {
                getWorld().showText("DEBUG MODE", getWorld().getWidth() / 2, 20);
            } else if (getWorld() != null) {
                getWorld().showText("", getWorld().getWidth() / 2, 20);
            }
        }
        f2WasPressed = f2IsPressed; // Remember for next frame
        
        if (!isSlashing)
        {   
            animateNinja();
        }
        
        if(healthBar != null)
        {
            healthBar.setLocation(getX(), getY() - 40);
        }
        checkEnemyTouch();
        
        if (health <= 0 && !hasDied)
        {
            hasDied = true;
            deathSound.stop();
            deathSound.play();
            Greenfoot.delay(30); // let sound play briefly
            MyWorld.resetCurrentKills();
            Greenfoot.setWorld(new GameOverScreen());
        }
        
        // Clean up debug hitbox if debug mode is off and we're not slashing
        if (!debugMode && debugHitbox != null && !isSlashing)
        {
            getWorld().removeObject(debugHitbox);
            debugHitbox = null;
            getWorld().showText("", getWorld().getWidth() / 2, 20); // Clear text
        }
    }
    
    public void getKeyboardInputs()
    {
        up = Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("up");
        down = Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("down");
        left = Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("left");
        right = Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D") || Greenfoot.isKeyDown("right");
        space = Greenfoot.isKeyDown("space");
    }
    
    public void playerMovement()
    {
        dx = 0;
        dy = 0;
        
        if (up)
        {
            dy = -speed;
            
            facing = "north";
        }
        if (down)
        {
            dy = speed;
            
            facing = "south";
        }
        if (left)
        {
            dx = -speed;
            
            facing = "west";
        }
        if (right)
        {
            dx = speed;
            
            facing = "east";
        }
        
        
        
        
        setLocation(getX() + dx, getY() + dy);
    }
    
    public void slash()
    {
        // Start slash ONLY if:
        // - space is pressed
        // - not already slashing
        // - cooldown finished
        if (space && !isSlashing && slashCooldownTimer.millisElapsed() >= slash_cooldown)
        {
            isSlashing = true;
            slashIndex = 0;
            slashAnimationTimer.mark();
            canSlashDamage = true; // Enable damage checking
        }
    
        // If currently slashing, play animation
        if (isSlashing)
        {
            if (slashAnimationTimer.millisElapsed() < 50)
            {
                return;
            }
            slashAnimationTimer.mark();
            
            if (canSlashDamage && slashIndex == slashDamageFrame)
            {
                checkSlashDamage();
                canSlashDamage = false; // Only deal damage once per slash
            }
            
            if (facing.equals("north"))
            {
                setImage(slashNorth[slashIndex]);
            }
            
            if (facing.equals("south"))
            {
                setImage(slashSouth[slashIndex]);
            }
            
            if (facing.equals("east"))
            {
                setImage(slashEast[slashIndex]);
            }
            
            if (facing.equals("west"))
            {
                setImage(slashWest[slashIndex]);
            }
    
            slashIndex++;
    
            // Animation finished
            if (slashIndex >= slashSouth.length)
            {
                isSlashing = false;
                slashCooldownTimer.mark(); // start cooldown AFTER slash ends
                
                // Clean up debug hitbox after slash animation ends
                if (debugHitbox != null && getWorld() != null)
                {
                    getWorld().removeObject(debugHitbox);
                    debugHitbox = null;
                }
            }
        }
        else if (!debugMode && debugHitbox != null && getWorld() != null)
        {
            // Clean up debug hitbox when not slashing and debug mode is off
            getWorld().removeObject(debugHitbox);
            debugHitbox = null;
            
            // Clear debug text when debug mode is off
            getWorld().showText("", getWorld().getWidth() / 2, 20);
        }
    }
    private void checkSlashDamage()
    {
        // Define slash hitbox dimensions
        int slashRange = 60;  // How far the slash reaches
        int slashWidth =  60;  // Width of the slash area
        
        // Get all enemies in the world as an array
        java.util.List enemiesList = getWorld().getObjects(Enemy.class);
        Enemy[] enemies = new Enemy[enemiesList.size()];
        enemiesList.toArray(enemies);
        
        // Create a rectangular hitbox based on facing direction
        int slashX = getX();
        int slashY = getY();
        int hitboxX, hitboxY, hitboxWidth, hitboxHeight;
        
        // Adjust hitbox based on direction
        if (facing.equals("north"))
        {
            slashRange += 20;
            
            hitboxX = slashX - slashWidth/2;
            hitboxY = slashY - slashRange; 
            hitboxWidth = slashWidth;
            hitboxHeight = slashRange;
        }
        else if (facing.equals("south"))
        {
            hitboxX = slashX - slashWidth/2;
            hitboxY = slashY; 
            hitboxWidth = slashWidth;
            hitboxHeight = slashRange;
        }
        else if (facing.equals("east"))
        {
            hitboxX = slashX; 
            hitboxY = slashY - slashWidth/2;
            hitboxWidth = slashRange;
            hitboxHeight = slashWidth;
        }
        else // west
        {
            hitboxX = slashX - slashRange; 
            hitboxY = slashY - slashWidth/2;
            hitboxWidth = slashRange;
            hitboxHeight = slashWidth;
        }
        
        // DEBUG: Show hitbox visualization
        if (debugMode)
        {
            if (debugHitbox == null)
            {
                debugHitbox = new HitboxVisualizer(hitboxWidth, hitboxHeight);
                getWorld().addObject(debugHitbox, hitboxX + hitboxWidth/2, hitboxY + hitboxHeight/2);
            }
            else
            {
                debugHitbox.updateSize(hitboxWidth, hitboxHeight);
                debugHitbox.setLocation(hitboxX + hitboxWidth/2, hitboxY + hitboxHeight/2);
            }
            
            // Show "DEBUG MODE" text
            getWorld().showText("DEBUG MODE", getWorld().getWidth() / 2, 20);
        }
        else if (debugHitbox != null)
        {
            // Remove debug hitbox when debug mode is off
            getWorld().removeObject(debugHitbox);
            debugHitbox = null;
            
            // Clear debug text
            getWorld().showText("", getWorld().getWidth() / 2, 20);
        }
        
        // Check each enemy if they're in the hitbox
        for (int i = 0; i < enemies.length; i++)
        {
            Enemy enemy = enemies[i];
            if (isEnemyInHitbox(enemy, hitboxX, hitboxY, hitboxWidth, hitboxHeight))
            {
                // Deal damage to enemy
                enemy.takeDamage(damage_amount);
            }
        }
    }
    
    private boolean isEnemyInHitbox(Enemy enemy, int hitboxX, int hitboxY, int hitboxWidth, int hitboxHeight)
    {
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();
        
        // Check if enemy is within the rectangular hitbox
        return enemyX >= hitboxX && enemyX <= hitboxX + hitboxWidth && enemyY >= hitboxY && enemyY <= hitboxY + hitboxHeight;
    }
    
    
    public void checkDoor()
    {
        Door door = (Door) getOneIntersectingObject(Door.class);
        if (door != null)
        {
            // Tell the world to go to next floor
            ((MyWorld) getWorld()).nextFloor();
        }
    }
    
     /**
     * Heals the Ninja by a specified amount without exceeding max health.
     *
     * @param amount the amount of health to restore
     */
    
    public void heal(int amount)
    {
        health += amount;
        
        // Don't exceed max health
        if (health > maxHealth)
        {
            health = maxHealth;
        }
        
        // Update health bar if it exists
        if (healthBar != null)
        {
            healthBar.update(health);
        }
    }
    
    public void healToFull()
    {
        health = maxHealth; // Set to 100 (or whatever maxHealth is)
        
        // Update health bar if it exists
        if (healthBar != null)
        {
            healthBar.update(health);
        }
    }
}

