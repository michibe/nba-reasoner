package org.hhz.nbareasoner.util.extern;

import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 18.03.2015.
 */
public class BooleanHelper {

    public static Boolean valueOf(@Nullable String s, boolean returnNullIfException)
    {

        if(returnNullIfException)
        {   try {
                 Boolean bb =Boolean.valueOf(s);
                return bb;
            }
            catch (Exception e)
            {

                return null;
            }
        }
        else{
            return Boolean.valueOf(s);
        }

    }

    //Returns null if it cannot be parsed to Double
    public static Boolean valueOf(boolean b, boolean returnNullIfException)
    {
        if(returnNullIfException)
        {
            try {
                return Boolean.valueOf(b);
            }
            catch (Exception e)
            {
                return null;
            }
        }
        else{
            return Boolean.valueOf(b);
        }

    }





}
