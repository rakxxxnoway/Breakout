package engine;

import java.util.ArrayList;

public final class Collision
{
	private static ArrayList<Box> boxArray = new ArrayList<Box>();
	
    private Collision() {}

    public static boolean aabb(
    		int ax, int ay, int aw, int ah,
            int bx, int by, int bw, int bh )
    {
        return ax < bx + bw && ax + aw > bx &&
               ay < by + bh && ay + ah > by;
    }

    public static int overlapX(int ax, int aw, int bx, int bw)
    {
        int left = Math.max(ax, bx);
        int right = Math.min(ax + aw, bx + bw);
        
        return right - left;
    }

    public static int overlapY(int ay, int ah, int by, int bh)
    {
        int top = Math.max(ay, by);
        int bottom = Math.min(ay + ah, by + bh);
        
        return bottom - top;
    }
    
    // setter
    public static void registerObject(Box b) { boxArray.add(b); }
    
    
    public static void unregisterObject(Box b) { boxArray.remove(b); }
    
    // getter
    public static ArrayList<Box> getRegisteredObjects() { return boxArray; }
}