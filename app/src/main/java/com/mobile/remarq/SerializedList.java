package com.mobile.remarq;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aman Chauhan on 25-04-2016.
 */
public class SerializedList implements Serializable
{
    private List<Serializable> list;

    public List getList()
    {
        return list;
    }

    public void setList(List list)
    {
        this.list = list;
    }
}
