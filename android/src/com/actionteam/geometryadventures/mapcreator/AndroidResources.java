package com.actionteam.geometryadventures.mapcreator;

import android.content.Context;

import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by theartful on 4/2/18.
 */

public class AndroidResources extends Resources {

    private Context context;

    public AndroidResources(Context context) {
        this.context = context;
    }

    @Override
    protected boolean loadTileTypes() throws  Exception {
        TileType[] tileTypes;
        Gson gson = new Gson();
        InputStream fis = context.getAssets().open("cat.json");
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while((line = br.readLine()) != null){
            sb.append(line);
            sb.append('\n');
        }
        tileTypes = gson.fromJson(sb.toString(), TileType[].class);
        for(TileType type : tileTypes){
            if(type.type.equals(TileType.FLOOR)) floors.add(type);
            else if(type.type.equals(TileType.WALL)) walls.add(type);
        }
        return true;
    }
}
