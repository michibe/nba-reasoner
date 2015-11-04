package org.hhz.nbareasoner.util.extern;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 17.03.2015.
 */
public class ObjectHelper {

    public static <T>  T defaultOnNull(T value, T defaultValue )
    {

        if(value==null)
        {

            return defaultValue;
        }
        else
        {
            return value;
        }
    }

    public static <T>  T defaultOnNullOrEmpty(T value, T defaultValue, T specificEmptyValue )
    {

        if(isNullOrEmpty(value,specificEmptyValue))
        {

            return defaultValue;
        }
        else
        {
            return value;
        }
    }


    //Es können keine primitiven werte kommen
    public static <T> boolean isValueIn(T comparisonValue, T... values)
    {

            for(int i=0; i<values.length;i++)
            {
                if(values[i].equals(comparisonValue))
                {
                    return true;
                }
            }
            return false;

    }


//Eigentlich können nur Objekte rein also keine sorge wegen equals
    public static <T> boolean isNullOrEmpty(@NotNull T value, @Nullable T specificEmptyValue)
    {
        if(specificEmptyValue==null)
        {
            return isNull(value);
        }
        else
        {

            if(isNull(value) | specificEmptyValue.equals(value) )
            {
                return true;
            }
          return false;
        }


    }

    public static <T> boolean isNull(@NotNull T value)
    {
        if(value==null)
        {
            return true;
        }
        else
        {
            return false;
        }

    }






}
