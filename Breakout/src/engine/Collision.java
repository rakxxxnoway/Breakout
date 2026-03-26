package engine;

import java.util.ArrayList;
import java.util.List;

public final class Collision
{
	private static List<Box> boxArray = new ArrayList<>();
	
	
    private Collision() {}

    
    public static boolean isObjectsCollide(int firstX, int firstY, int firstW, int firstH, int secX, int secY, int secW, int secH )
    {
        return ( firstX < secX + secW ) && ( firstX + firstW > secX )
        	   &&
        	   ( firstY < secY + secH ) && ( firstY + firstH > secY );
    }

    
    public static int overlapX(int firstX, int firstW, int secX, int secW)
    {
        int left = Math.max(firstX, secX);
        int right = Math.min(firstX + firstW, secX + secW);
        
        return right - left;
    }

    
    public static int overlapY(int firstY, int firstH, int secY, int secH)
    {
        int top = Math.max(firstY, secY);
        int bottom = Math.min(firstY + firstH, secY + secH);
        
        return bottom - top;
    }
    
    
    // setter
    public static void registerObject(Box b) 	{ boxArray.add(b); }
    public static void resetObjects()			{ boxArray.clear(); }
    
    
    public static void unregisterObject(Box b) { boxArray.remove(b); }
    
    
    // getter
    public static List<Box> getRegisteredObjects() { return boxArray; }
}