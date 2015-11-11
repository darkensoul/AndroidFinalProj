package com.example.brittany.hcd;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Temp_Table")
public class Temp_Table extends ParseObject {

    public String getName(){
        return getString("Name");
    }

    public void setName(String name){
        put("Name", name);
    }
    public String getName2(){
        return getString("Name2");
    }

    public void setName2(String name){
        put("Name2", name);
    }
    public String getType(){
        return getString("Type");
    }

    public void setType(String type){
        put("Type", type);
    }

    public ParseFile getPhoto(){
        return getParseFile("FoodPhoto");
    }

    public void setPhoto(ParseFile photo){
        put("FoodPhoto", photo);
    }
    public String getUsername(){
        return getString("username");
    }

    public void setUsername(String name){
        put("username", name);
    }
    public static ParseQuery<Temp_Table> getQuery(){
        return ParseQuery.getQuery(Temp_Table.class);
    }
}

