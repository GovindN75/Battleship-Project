import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Battleship here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Battleship extends Actor
{
    // Hits this ship can endure before sinking
    protected int hp;
    protected int width;
    protected int height;
    
    // Boolean to store which player (left or right) the ship belongs to
    protected boolean isLeft;
    
    // Boolean to store whether or not the player has selected this ship
    protected boolean selected;
    
    // Image
    protected GreenfootImage img;
    
    public void act()
    {
        if (hp <= 0) {getWorld().removeObject(this);}
    }
    
    /**
     * Make the Battleship take damage
     * 
     * @param damage The amount of damage to be taken
     */
    protected void takeDamage(int damage)
    {
        hp -= damage;
    }
    
    /**
     * Toggle this ship to be selected
     */
    protected void select()
    {
        selected = true;
    }
    
    /**
     * Toggle this ship to be unselected
     */
    protected void unselect()
    {
        selected = false;
    }
    
    /**
     * Check for border collision based on the ship's rotation
     */
    protected void borderCheck()
    {
        int worldWidth = BattleWorld.CELL_SIZE * 20;
        int worldHeight = BattleWorld.CELL_SIZE * 10;
        if (getRotation() == 180 || getRotation() == 0) // Horizontal orientation
        {
            if (getX() - width / 2 < 0 || getX() + width / 2 < 0) 
            {
                setLocation(getX() + BattleWorld.CELL_SIZE, getY());
            }
            
            if (getX() - width / 2 > worldWidth || getX() + width / 2 > worldWidth) 
            {
                setLocation(getX() - BattleWorld.CELL_SIZE, getY());
            }
            
            if (getY() - height / 2 < 0 || getY() + height / 2 < 0) 
            {
                setLocation(getX(), getY() + BattleWorld.CELL_SIZE);
            }
            
            if (getY() - height / 2 > worldHeight || getY() + height / 2 > worldHeight) 
            {
                setLocation(getX(), getY()  - BattleWorld.CELL_SIZE);
            }
        } 
        else // Vertical orientation
        {
            if (getX() - height / 2 < 0 || getX() + height / 2 < 0) 
            {
                setLocation(getX() + BattleWorld.CELL_SIZE, getY());
            }
            
            if (getX() - height / 2 > worldWidth || getX() + height / 2 > worldWidth) 
            {
                setLocation(getX() + BattleWorld.CELL_SIZE, getY());
            }
            
            if (getY() - width / 2 < 0 || getY() + width / 2 < 0) 
            {
                setLocation(getX(), getY() + BattleWorld.CELL_SIZE);
            }
            
            if (getY() - width / 2 > worldHeight || getY() + width / 2 > worldHeight) 
            {
                setLocation(getX(), getY()  - BattleWorld.CELL_SIZE);
            }
        }
    }
}