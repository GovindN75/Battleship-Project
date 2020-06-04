import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Missile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Missile extends Weapons
{
    private int speed; 
    private int damage; 
    private GreenfootImage[] missileExplosion = {new GreenfootImage("mis2.png"), new GreenfootImage("mis3.png"), new GreenfootImage("mis4.png"), new GreenfootImage("mis5.png"), 
                                                 new GreenfootImage("mis6.png"), new GreenfootImage("mis7.png"), new GreenfootImage("mis8.png"), new GreenfootImage("mis9.png"),
                                                 new GreenfootImage("mis10.png"), new GreenfootImage("mis11.png")};
    private int index = 0;
    private boolean exploding = false;
    private GreenfootImage[] blowingUp = {};
    public Missile(int speed, int damage){
       this.speed=speed;
       this.damage = damage;
    }
    public Missile(){
        speed = 3;
        damage = 5;
    }
    public void act() 
    {
        // Add your action code here.
        if(exploding){
            explode();
        }
    }

    public void dropMissile(int x, int y){
        BattleWorld world =(BattleWorld) getWorld();
        Missile m = new Missile();
        int row = world.getRow(y);
        int column = world.getCol(x);
        exploding =true; 
    }

    public void explode(){
        if(index < 40) {     
            GreenfootImage missileExplosions  = missileExplosion[index/4];
            setImage(missileExplosions);
            index++;
            if(index == 4){
                 blowUp();
            }
        }
        else {
            getWorld().removeObject(this);
        }
    }
    public void blowUp(){


    }


}
