import greenfoot.*;

public class MyWorld extends World {
    GreenfootSound bgMusic = new GreenfootSound("bgmusic.mp3");
    
    private Label floorLabel;
    private Label currentKillsLabel;  // Rename from killLabel
    private Label mostKillsLabel;     // New label for most kills
    private static int currentKills = 0;
    private static int mostKills = 0;      
    private Door door;
    private int currentFloor = 1;
    private int maxFloors = 10;
    private boolean levelComplete = false;
    
    private static int bossLevel = 0; // Tracks which mini boss level we're on (1, 2, 3...)
    private static int enemyHealthBoost = 0; // Tracks health boost for regular enemies
    
    public MyWorld() {
        super(600, 600, 1);
        GreenfootImage bg = new GreenfootImage("dungeon_background.png");
        setBackground(bg);
        Greenfoot.setWorld(new StartScreen());
        Ninja ninja = new Ninja();
        addObject(ninja, 300, 500);
        
        Enemy enemy1 = new Enemy();
        addObject(enemy1, 100, 100);
        
        bgMusic.playLoop();
        
        // Add floor label at top left
        floorLabel = new Label("Floor: " + currentFloor, 30);
        addObject(floorLabel, 80, 45);
        
        // Add current kills label at top right
        currentKillsLabel = new Label("Kills: " + currentKills, 30);  
        addObject(currentKillsLabel, getWidth() - 80, 45);
        
        // Add most kills label below current kills
        mostKillsLabel = new Label("Most Kills: " + mostKills, 30);
        addObject(mostKillsLabel, getWidth() - 110, 75);
    }
    public void act()
    {
        // Update both kill labels
        currentKillsLabel.setValue("Kills: " + currentKills);
        mostKillsLabel.setValue("Most Kills: " + mostKills);
        
        if (getObjects(Enemy.class).isEmpty() && door == null)
        {
            // Create door at top center
            door = new Door();
            addObject(door, getWidth() / 2, 10);
            levelComplete = true;
        }
    }
    public void nextFloor()
    {
        // Remove door
        removeObject(door);
        door = null;
        levelComplete = false;
        
        // Increase floor number
        currentFloor++;
        floorLabel.setValue("Floor: " + currentFloor);
        
        // Get player reference
        Ninja player = getObjects(Ninja.class).get(0);
        
        // HEAL PLAYER BASED ON FLOOR
        int healAmount = 0;
        
        if (currentFloor <= 4) {
            healAmount = 25;
        } else if (currentFloor <= 9) {
            healAmount = 50;
        }
        
         boolean previousWasBossFloor = ((currentFloor - 1) % 10 == 0);
    
        if (previousWasBossFloor) {
            // Player just completed a mini boss floor, heal to full
            player.healToFull();
        } else {
            if (healAmount > 0) {
                player.heal(healAmount);
            }
        }
        
        // Apply healing to player
        player.heal(healAmount);
        
        // Move player to bottom center
        player.setLocation(getWidth() / 2, getHeight() - 60);
        
        // Check if this is a mini boss floor (every 10 floors)
        boolean isBossFloor = (currentFloor % 10 == 0);
        
        if (isBossFloor)
        {
            // This is a mini boss floor - spawn only the mini boss
            bossLevel = currentFloor / 10;
            
            int bossHealth = 200 + (bossLevel - 1) * 100;
            
            // Create and spawn mini boss
            Enemy miniBoss = createMiniBoss(bossHealth, bossLevel);  // This calls setAsMiniBoss()
            addObject(miniBoss, getWidth() / 2, 100); // Spawn at top center
            
            // Show boss announcement
            showText("MINI BOSS LEVEL " + bossLevel + "!", getWidth() / 2, getHeight() / 2);
            Greenfoot.delay(60);
            showText("", getWidth() / 2, getHeight() / 2); // Clear text
        }else
        {
            // Normal floor - spawn regular enemies
            for (int i = 0; i < currentFloor; i++)
            {
                Enemy enemy = new Enemy();  // Just create regular enemy - constructor handles health boost
                
                // Spawn enemies away from player at bottom
                int spawnX;
                int spawnY;
                
                // Generate position
                spawnX = Greenfoot.getRandomNumber(getWidth() - 100) + 50;
                spawnY = Greenfoot.getRandomNumber(getHeight() / 3) + 50;
                
                // If too close to player, regenerate X position
                while (Math.abs(spawnX - player.getX()) < 100)
                {
                    spawnX = Greenfoot.getRandomNumber(getWidth() - 100) + 50;
                }
                
                addObject(enemy, spawnX, spawnY);
            }
        }
    }

    // Method to create a mini boss
    private Enemy createMiniBoss(int health, int level)
    {
        Enemy miniBoss = new Enemy();
        miniBoss.setAsMiniBoss(health, level);
        return miniBoss;
    }
    public static void increaseEnemyHealthBoost()
    {
        enemyHealthBoost += 50;
    }
    
    public static int getEnemyHealthBoost()
    {
        return enemyHealthBoost;
    }
    
    public static void addKill()
    {
        currentKills++;
        // Update most kills if current kills is higher
        if (currentKills > mostKills) {
            mostKills = currentKills;
        }
    }
    
    public static void resetCurrentKills()
    {
        currentKills = 0;
    }
    
    public int getFloorNumber()
    {
        return currentFloor;
    }
}

