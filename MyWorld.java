import greenfoot.*;

public class MyWorld extends World {
    GreenfootSound bgMusic = new GreenfootSound("bgmusic.mp3");
    
    private Label floorLabel;
    private Door door;
    private int currentFloor = 1;
    private int maxFloors = 10;
    private boolean levelComplete = false;
    
    public MyWorld() {
        super(600, 600, 1);
        Ninja ninja = new Ninja();
        addObject(ninja, 300, 500);
        
        Enemy enemy1 = new Enemy();
        addObject(enemy1, 100, 100);
        
        bgMusic.playLoop();
        
        // Add floor label at top of screen
        floorLabel = new Label("Floor: " + currentFloor, 30);
        addObject(floorLabel, 80, 45);
        
    }
    public void act()
    {
        // Check if all enemies are dead and door doesn't exist yet
        // In MyWorld.java act() method, change the door position:
        if (getObjects(Enemy.class).isEmpty() && door == null && currentFloor < maxFloors)
        {
            // Create door at top center - adjust for larger size
            door = new Door();
            addObject(door, getWidth() / 2, 10); // 175 = half of 350 (image height)
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
        
        // Get player reference
        Ninja player = getObjects(Ninja.class).get(0);
        
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
        
        // Optional: Add some visual feedback
    }
}
