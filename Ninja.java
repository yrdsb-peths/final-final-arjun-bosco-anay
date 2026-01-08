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
    HealthBar healthBar;
    
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
    
    public void addedToWorld(World w)
    {
        healthBar = new HealthBar(maxHealth);
        w.addObject(healthBar, getX(), getY() - 40);
    }
    
    public void checkEnemyTouch()
    {
        if(isTouching(Enemy.class))
        {
            health -= 1;
            if(health < 0)
            {
                health = 0;
            }
            healthBar.update(health);
        }
    }
    
    public void act()
    {
        // Add your action code here.
        getKeyboardInputs();
        slash();
        playerMovement();
        
        if (!isSlashing)
        {   
            animateNinja();
        }
        
        if(healthBar != null)
        {
            healthBar.setLocation(getX(), getY() - 40);
        }
        checkEnemyTouch();
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
        }
    
        // If currently slashing, play animation
        if (isSlashing)
        {
            if (slashAnimationTimer.millisElapsed() < 50)
            {
                return;
            }
            slashAnimationTimer.mark();
    
              
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
            }
        }

    }
}
