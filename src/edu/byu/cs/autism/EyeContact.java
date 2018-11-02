package edu.byu.cs.autism;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EyeContact {

    public  static  final double THRESHHOLD = 0.98;


    public static Vector eyeToeye(Player start, Player end){

        start.getEyeLocation();
        end.getEyeLocation();
        Vector s = start.getEyeLocation().toVector();
        Vector e = end.getEyeLocation().toVector();
        return e.add(s.multiply(-1));

    }


    public static Vector direction(double pitch, double yaw){
        //convert from degrees to radians
        double pitch2 = Math.PI * 2 *(pitch / 360);
        double yaw2 = Math.PI * 2 *(yaw / 360);

        double x = -Math.sin(yaw2) * Math.cos(pitch2);
        double y = -Math.sin(pitch2);
        double z = Math.cos(yaw2) * Math.cos(pitch2);

        return  new Vector(x, y, z);
    }

    public static double cossime(Vector u, Vector v){
        return u.dot(v)/(u.length() * v.length());
    }


    public static boolean eyeContact(Player one, Player two){
        if (cossime(direction(one.getEyeLocation().getPitch(), one.getEyeLocation().getYaw()), eyeToeye(one, two)) > THRESHHOLD){
            //ensure other player is also looking at them
            return cossime(direction(two.getEyeLocation().getPitch(), two.getEyeLocation().getYaw()), eyeToeye(two, one)) > THRESHHOLD;

        }
        return  false;

    }




}
