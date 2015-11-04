package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition;

import org.hhz.nbareasoner.cbml.model.base.CException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mbehr on 11.05.2015.
 *
 * Collect general converters
 */
public class JavaTypeConverter {

    //String as long value
    public static Timestamp timestampFromString(String time)
    {

        return new Timestamp(Long.valueOf(time));
        //2015-05-08 13:16:28.771
    }








}
