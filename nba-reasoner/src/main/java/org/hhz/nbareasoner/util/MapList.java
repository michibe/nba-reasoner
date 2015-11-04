package org.hhz.nbareasoner.util;


import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by mbehr on 21.05.2015.
 *
 *
 *
 */
public class MapList<KEY,VALUE> {

    private final HashMap<KEY,Integer> map = new HashMap<>();
    private final ArrayList<VALUE> list = new ArrayList<>();


     public synchronized void  put(KEY key, VALUE value)
    {

        if(this.map.get(key)!=null)
        {
            this.remove(key);
        }

        this.list.add(value);
        this.map.put(key,this.list.size()-1);
    }

    @Nullable
    public VALUE getValue(KEY key)
    {
        Integer index = this.map.get(key);
        if(index ==null)
        {
            return null;
        }

        return list.get(index);
    }

    @Nullable
    public VALUE getValue(int index)
    {
        return list.get(index);
    }

    @Nullable
    public VALUE getNextValueFor(KEY key)
    {
        try
        {
           return this.list.get(this.map.get(key)+1);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }

    }

    @Nullable
    public VALUE getPreviousValueFor(KEY key)
    {
        try
        {
            return this.list.get(this.map.get(key)-1);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }





    public List<VALUE> values()
    {

       return this.list;
    }





    public Map<KEY,Integer> keys()
    {
        return Collections.unmodifiableMap(this.map);
    }

    public synchronized void remove(KEY key)
    {
        this.list.remove(this.map.get(key));
        this.map.remove(key);
    }

    public synchronized void sort(Comparator<VALUE> comparator)
    {

        ArrayList<VALUE> listToSort = new ArrayList(this.list);

        Collections.sort(listToSort, comparator);

        //Create new keymap by
        Map<KEY,Integer> newKeyMap = new HashMap();
        for(int i=0; i<listToSort.size();i++)
        {
            //für jeden value die gesamte map durchgehen und schauen ob der vlaue gleich ist, wenn ja dann eintrag
            //übernehmen
                VALUE value = listToSort.get(i);
                    for(Map.Entry<KEY,Integer> entry : this.map.entrySet())
                    {
                        if(this.list.get(entry.getValue()).equals(value))
                        {
                            newKeyMap.put(entry.getKey(),i);
                            break;
                        }
                    }

        }

        this.map.clear();
        this.list.clear();

        this.list.addAll(listToSort);

        this.map.putAll(newKeyMap);


    }

    public void putAll(@Nullable MapList<KEY,VALUE> mapList)
    {
        if(mapList!=null) {
            this.list.addAll(mapList.values());
            this.map.putAll(mapList.keys());
        }

    }


    public void clear()
    {
        this.list.clear();
        this.map.clear();
    }


    public int size()
    {
        return this.list.size();
    }




}
