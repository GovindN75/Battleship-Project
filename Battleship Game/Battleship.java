import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Battleships are one of the main elements of this game. A battleship is an actor that is on the BattleWorld grid during the game.
 * Each ship is associated with a player and has a certain amount of hits it can endure before going down. There are 3 subclasses of battleship, Zoomboat, Submarine, and Cruiser.
 * Battleships can be selected one at a time by each player during their turn.
 * 
 * @author Daniel Wei
 * @version June 14, 2020
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
    
    // Check if the ship is already being dragged
    protected boolean beingDragged = false;
    
    // Check if a key has been pressed
    protected boolean keyPressed = false;
    
    // Selected/unselected image
    protected GreenfootImage unselectedImg;
    protected GreenfootImage selectedImg;
    
    // BattleWorld
    protected BattleWorld bw;
    
    // Submerged boolean for submarines
    protected boolean submerged = false;
    
    /**
     * Make the Battleship take damage
     * 
     * @param damage The amount of damage to be taken
     */
    protected void takeDamage(int damage) {
        hp -= damage;
    }
    
    /**
     * Toggle this ship to be selected
     */
    protected void select() {
        selected = true;
    }
    
    /**
     * Toggle this ship to be unselected
     */
    protected void unselect() {
        selected = false;
    }
    
    /**
     * Check submerged
     * 
     * @return boolean True if submerged, false if not
     */
    protected boolean isSubmerged() {
        return submerged;
    }
    
    /**
     * Check for border collision based on the ship's rotation
     */
    protected void borderCheck() {
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

    /**
     * Check if the ship is being dragged around to reposition
     */
    protected void dragCheck() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int preX = getX();
        int preY = getY();
        if (mouse == null) {return;}
        if (Greenfoot.mousePressed(this) && !beingDragged) {
            beingDragged = true;
            return;
        }
        
        if (beingDragged && Greenfoot.mouseDragged(this)) {
            setLocation(mouse.getX(), mouse.getY());
            return;
        }
        
        if (Greenfoot.mouseDragEnded(this) && beingDragged) {
            beingDragged = false;
            return;
        }
    }

    /**
     * Method that is constantly called prior to game starting
     */
    protected void preGame() {
        BattleWorld bw = (BattleWorld) getWorld();
        if (!selected) {return;}
        dragCheck();
        collisionHandler();
        if (bw.keyPress() == "space") {
            turn(90);
        }
    }
    
    /**
     * Collision handler
     */
    protected void collisionHandler() {
        if (isLeft) {
            if (getOneIntersectingObject(Battleship.class) != null) {
                if (getY() >= BattleWorld.CELL_SIZE * 9) {setLocation(getX(), getY() - BattleWorld.CELL_SIZE);}
                else {setLocation(getX(), getY() + BattleWorld.CELL_SIZE);}
            }
            
            if (getX() + getImage().getWidth() / 2 >= BattleWorld.CELL_SIZE * 10 + BattleWorld.CELL_SIZE / 2) {setLocation(getX() - BattleWorld.CELL_SIZE, getY());}
            
            if (getOneIntersectingObject(Battleship.class) != null) {
                if (getX() - BattleWorld.CELL_SIZE >= BattleWorld.CELL_SIZE / 2) {
                    setLocation(getX() - BattleWorld.CELL_SIZE, getY());
                } else {
                    setLocation(getX() + BattleWorld.CELL_SIZE, getY());
                }
            }
        } else {
            if (getOneIntersectingObject(Battleship.class) != null) {
                if (getY() >= BattleWorld.CELL_SIZE * 9) {setLocation(getX(), getY() - BattleWorld.CELL_SIZE);}
                else {setLocation(getX(), getY() + BattleWorld.CELL_SIZE);}
            }
            
            if (getX() - getImage().getWidth() / 2 <= BattleWorld.CELL_SIZE * 9 + BattleWorld.CELL_SIZE / 2) {setLocation(getX() + BattleWorld.CELL_SIZE, getY());}
            
            if (getOneIntersectingObject(Battleship.class) != null) {
                if (getX() + BattleWorld.CELL_SIZE <= BattleWorld.CELL_SIZE * 19 + BattleWorld.CELL_SIZE / 2) {
                    setLocation(getX() + BattleWorld.CELL_SIZE, getY());
                } else {
                    setLocation(getX() - BattleWorld.CELL_SIZE, getY());
                }
            }
        }
    }
    
    /**
     * Conceal this ship
     */
    protected void conceal() {
        unselectedImg.setTransparency(1);
        selectedImg.setTransparency(1);
        img.setTransparency(1);
        setImage(img);
    }
    
    /**
     * Reveal this ship
     */
    protected void reveal() {
        unselectedImg.setTransparency(255);
        selectedImg.setTransparency(255);
        img.setTransparency(255);
        setImage(img);
    }
    
    /**
     * Get team side of this ship
     * 
     * @return boolean True if left side team , false if right side team
     */
    protected boolean getSide() {return isLeft;}
    
    /**
     * Get HP of ship
     * 
     * @return int HP of ship
     */
    protected int getHP() {return hp;}
}
