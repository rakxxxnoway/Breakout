package engine;

import java.util.ArrayList;

//import java.awt.Graphics2D;

public class Box {
    private final Sprite master;

    private boolean collidable = false;
    
    private final int startX;
    private final int startY;

    // speed for only falling obejcts
    private int vx = 0;
    private int vy = 0;
    
    
    private boolean outOfBorder;


    // constructor
    public Box(Sprite master)
    {
        this.master = master;
        this.startX = master.getX();
        this.startY = master.getY();
        
        outOfBorder = false;
    }
    
    
    // setter
    public void setVelocity(int vx, int vy) { this.vx = vx; this.vy = vy; }
    public void backOn() { this.outOfBorder = false; }
    
    
    // getters
    public int getX() { return master.getX(); }
    public int getY() { return master.getY(); }
    public int getWidth() { return master.getWidth(); }
    public int getHeight() { return master.getHeight(); }
    public Sprite getOwner() { return master; }
    public boolean didFall() { return outOfBorder; }

    
    // methods
    public void enableCollision(boolean on) { this.collidable = on; }
    public boolean isCollisionEnabled() { return collidable; }
  
    
    public void lockToWorld()
    {
        if (!collidable)
        	return;

        if (getX() < 0)
            master.setX(0);

        if (getX() + getWidth() > Settings.WINDOW_WIDTH)
            master.setX(Settings.WINDOW_WIDTH - getWidth());

        if (getY() < 0)
            master.setY(0);

        if (getY() + getHeight() > Settings.WINDOW_HEIGHT)
            master.setY(Settings.WINDOW_HEIGHT - getHeight());
    }
    
    
    public ArrayList<Box> physics(ArrayList<Box> slaves)
    {
        ArrayList<Box> hits = new ArrayList<>();
        
        master.setX(master.getX() + vx);
        master.setY(master.getY() + vy);

        if (!collidable) return  hits;

        if (getX() < 0)
        {
            master.setX(0);
            vx = -vx;
        }

        if (getX() + getWidth() > Settings.WINDOW_WIDTH)
        {
            master.setX(Settings.WINDOW_WIDTH - getWidth());
            vx = -vx;
        }

        if (getY() < 0)
        {
            master.setY(0);
            vy = -vy;
        }

        if (getY() + getHeight() > Settings.WINDOW_HEIGHT)
        {
            master.setX(startX);
            master.setY(startY);

            vy = Math.abs(vy);
            
            outOfBorder = true;

            return hits;
        }
        

        if ( slaves.isEmpty())
        	return hits;
        
        for (Box slave : slaves)
        {
        	if (slave == this)
        	    continue;
        	
            if (!slave.isCollisionEnabled())
                continue;

            if (!Collision.aabb(
                    getX(),
                    getY(),
                    getWidth(),
                    getHeight(),
                    //
                    slave.getX(),
                    slave.getY(),
                    slave.getWidth(),
                    slave.getHeight()
            ))
                continue;
           
            hits.add(slave);

            int ox = Collision.overlapX(
                    getX(),
                    getWidth(),
                    //
                    slave.getX(),
                    slave.getWidth()
            );

            int oy = Collision.overlapY(
                    getY(),
                    getHeight(),
                    slave.getY(),
                    slave.getHeight()
            );

            if (ox < oy)
            {
                if (getX() < slave.getX())
                    master.setX(master.getX() - ox);
                else
                    master.setX(master.getX() + ox);

                vx = -vx;
            }
            else
            {
                if (getY() < slave.getY())
                    master.setY(master.getY() - oy);
                else
                    master.setY(master.getY() + oy);

                vy = -vy;
            }
        }
        
        return hits;
        
    }
}
