package com.mrcrayfish.furniture.api;

import java.util.HashMap;

public class Parser
{

    private static Parser instance = new Parser();

    public static Parser getInstance()
    {
        return instance;
    }

    private HashMap<String, String> values = new HashMap<>();

    public void parseLine(String line, boolean removeSpaces)
    {
        values.clear();

        if(removeSpaces) line = line.replace(" ", "");

        String[] keys = line.split(",");
        for(String key : keys)
        {
            String[] keyData = key.split("=");
            String key_id = keyData[0];
            String key_value = keyData[1];
            values.put(key_id, key_value);
        }
    }

    public String getValue(String key_id, String d3fault)
    {
        if(values.get(key_id) != null)
        {
            return values.get(key_id);
        }
        return d3fault;
    }

    public HashMap<String, String> getMap()
    {
        return values;
    }
}