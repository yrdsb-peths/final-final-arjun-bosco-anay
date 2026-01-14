import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HitboxVisualizer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HitboxVisualizer extends Actor
{
    private int width;
    private int height;
    
    public HitboxVisualizer(int width, int height)
    {
        this.width = width;
        this.height = height;
        updateImage();
    }
    
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(new Color(255, 0, 0, 100)); // Semi-transparent red
        img.fillRect(0, 0, width, height);
        img.setColor(Color.RED);
        img.drawRect(0, 0, width-1, height-1);
        setImage(img);
    }
    
    public void updateSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        updateImage();
    }
}
