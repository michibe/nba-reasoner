package org.hhz.nbareasoner.config.impl.base;

import org.hhz.nbareasoner.cbr.model.phase.reuse.CbrReuseAgent;
import org.hhz.nbareasoner.util.extern.StringHelper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

/**
 * Created by mbehr on 18.03.2015.
 *
 * Die Validation für den filter soll schon beim einlesen stattfinden, wenn die config-xml validiert wird
 */
public class FunctionResultFilter
 {

     private final static org.slf4j.Logger logger = LoggerFactory.getLogger(FunctionResultFilter.class);



     // =1 alles gleioch 1 soll durchkommen
        //<1 alles kleiner eins soll durchkommen
        //>1 alles größer 1 soll durchkommen
//Die frage ist was kommt durch den filter durch
        //Wenn filterString =0, dann wird nichts gefiltert
        public static boolean isPassingFilter(@NotNull String filterString, @NotNull double filterValue)
        {

            logger.debug("isPassingFilter called with filterString:'"+filterString +"' and filterValue:'"+filterValue+"'");


           if(filterString.startsWith(">=") || filterString.startsWith("&gt;=") )
           {

               if(filterValue>=Double.valueOf(StringHelper.splitStringOnNumberOccurrence(filterString)[1]))
               {
                   return true;
               }
               else
               {
                   return false;
               }
           }
           else if(filterString.startsWith("<=") || filterString.startsWith("&lt;="))
           {
               if(filterValue<=Double.valueOf(StringHelper.splitStringOnNumberOccurrence(filterString)[1]))
               {
                   return true;
               }
               else
               {
                   return false;
               }
           }
           else  if(filterString.startsWith("<")|| filterString.startsWith("&lt;"))
           {
               if(filterValue<Double.valueOf(StringHelper.splitStringOnNumberOccurrence(filterString)[1]))
               {
                   return true;
               }
               else
               {
                   return false;
               }
           }
           else if(filterString.startsWith(">") || filterString.startsWith("&gt;"))
           {
               if(filterValue>Double.valueOf(StringHelper.splitStringOnNumberOccurrence(filterString)[1]))
               {
                   return true;
               }
               else
               {
                   return false;
               }
           }
           else if(filterString.startsWith("="))
           {
               if(Double.valueOf(StringHelper.splitStringOnNumberOccurrence(filterString)[1])==filterValue)
               {
                   return true;
               }
               else
               {
                   return false;
               }
           }
           else if(filterString.equals("0"))
           {

                   return true;

           }
            else
           {
               return false;
           }



        }






    }

