package org.hhz.nbareasoner.util.extern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 18.03.2015.
 */
public class DoubleHelper {

    //returns the specified value
    public static Double valueOf(String s, double returnValueIfException)
    {

            try {
                Double dd = Double.valueOf(s);
                return dd;
            }
            catch (Exception e)
            {

                return returnValueIfException;
            }


    }

    public static Double valueOf(String s, boolean returnNullIfException)
    {
        if(returnNullIfException)
        {
            try {
                Double dd = Double.valueOf(s);
                return dd;
            }
            catch (Exception e)
            {

                return null;
            }
        }
        else{
            return Double.valueOf(s);
        }

    }

    //Returns null if it cannot be parsed to Double
    public static Double valueOf(double d, boolean returnNullIfException)
    {
        if(returnNullIfException)
        {
            try {
                return Double.valueOf(d);
            }
            catch (Exception e)
            {
                return null;
            }
        }
        else{
            return Double.valueOf(d);
        }

    }





    public static boolean isValueBetween(Double value, Double valueStart, Double valueEnd)
    {
        if(value > valueStart && value < valueEnd)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean isValueBetweenOrSimilar(Double value, Double valueStart, Double valueEnd)
    {
        if(value >= valueStart && value <= valueEnd)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public static boolean isValueIn(Double comparisonValue, Double... values)
    {
       return ObjectHelper.isValueIn(comparisonValue,values);
    }

    public static boolean isNullOrEmpty(@NotNull Double value, @Nullable Double specificEmptyValue)
    {
        return ObjectHelper.isNullOrEmpty(value,specificEmptyValue);
    }

    public static boolean isNull(@NotNull Double value)
    {
        return ObjectHelper.isNull(value);
    }

}
