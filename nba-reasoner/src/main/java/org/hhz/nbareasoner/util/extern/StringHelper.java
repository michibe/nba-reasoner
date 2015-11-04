package org.hhz.nbareasoner.util.extern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbehr on 09.03.2015.
 */
public class StringHelper {

    public static List<String> splitStringBetween(String text, String startSequence,String endSequence)
    {

        List<String> results = new ArrayList<String>();

        String stringTmp = text;


        while(stringTmp.contains(startSequence)) {
           // System.out.println("stringTmp substring " + stringTmp.substring(stringTmp.indexOf(startSequence)+startSequence.length(), stringTmp.indexOf(endSequence)));

            results.add(stringTmp.substring(stringTmp.indexOf(startSequence)+startSequence.length(), stringTmp.indexOf(endSequence)));
            stringTmp = stringTmp.substring( stringTmp.indexOf(endSequence)+endSequence.length(),stringTmp.length());

        }


        return results;

    }


    //Splittet einen string ab dem ersten numerischen char
    //Wenn keiner vorhanden sit kommt leerer array zurück
    public static String[] splitStringOnNumberOccurrence(String s)
    {


        for(int i =0; i<s.length();i++)
        {



                if(isNumeric(String.valueOf(s.charAt(i))))
                {

                    return new String[]{s.substring(0,i), s.substring(i)};
                }


        }

        return new String[]{};
    }





    public static boolean isValueIn(String comparisonValue, String... values)
    {
       return ObjectHelper.<String>isValueIn(comparisonValue,values);
    }

    public static boolean isNullOrEmpty(@NotNull String value, @Nullable String specificEmptyValue)
    {
        return ObjectHelper.isNullOrEmpty(value,specificEmptyValue);
    }

    public static boolean isNull(@NotNull String value)
    {
        return ObjectHelper.isNull(value);
    }



    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }


}
