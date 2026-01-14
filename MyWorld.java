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
    
    public MyWorld() {
        super(600, 600, 1);
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
        
        if (getObjects(Enemy.class).isEmpty() && door == null && currentFloor < maxFloors)
        {
            // Create door at top center
            door = new Door();
            addObject(door, getWidth() / 2, 10);
            levelComplete = true;
        }
    }
    public void nextFloor()
    {
       if (currentFloor >= maxFloors)
        {
            // Game completed
            showText("You Win! All " + maxFloors + " floors cleared!", getWidth() / 2, getHeight() / 2);
            Greenfoot.stop();
            return;
        }
        
        // Remove door
        removeObject(door);
        door = null;
        levelComplete = false;
        
        // Increase floor number
        currentFloor++;
        floorLabel.setValue("Floor: " + currentFloor);
        
         // HEAL PLAYER BASED ON FLOOR
        // Get player reference
        Ninja player = getObjects(Ninja.class).get(0);
         int healAmount = 0;
        
        if (currentFloor <= 4) {
            // Floors 1-4: Heal 25 health
            healAmount = 25;
        } else if (currentFloor <= 9) {
            // Floors 5-9: Heal 50 health
            healAmount = 50;
        }
        // Floor 10 doesn't heal (no next floor)
        
        // Apply healing to player
        player.heal(healAmount);
        
        // Move player to bottom center
        player.setLocation(getWidth() / 2, getHeight() - 60);
        
        // Spawn enemies equal to floor number
        // Spawn enemies equal to floor number
        for (int i = 0; i < currentFloor; i++)
        {
            Enemy enemy = new Enemy();
            
            // Spawn enemies away from player at bottom
            int spawnX;
            int spawnY;
            
            // Generate position
            spawnX = Greenfoot.getRandomNumber(getWidth() - 100) + 50;
            spawnY = Greenfoot.getRandomNumber(getHeight() / 3) + 50; // Spawn in top 2/3 of screen
            
            // If too close to player, regenerate X position
            while (Math.abs(spawnX - player.getX()) < 100)
            {
                spawnX = Greenfoot.getRandomNumber(getWidth() - 100) + 50;
            }
            
            addObject(enemy, spawnX, spawnY);
        }
        
        
        
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

