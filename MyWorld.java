import greenfoot.*;

public class MyWorld extends World {
    public MyWorld() {
        super(600, 600, 1);
        Ninja ninja = new Ninja();
        addObject(ninja, 300, 500);
        
        Enemy enemy1 = new Enemy();
        addObject(enemy1, 100, 100);
        
    }
}
