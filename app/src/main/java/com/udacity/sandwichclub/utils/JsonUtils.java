package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich ret = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);

            JSONObject name = sandwichJson.getJSONObject(KEY_NAME);
            ret.setMainName(name.getString(KEY_MAIN_NAME));

            JSONArray alsoKnownListJson = name.getJSONArray(KEY_ALSO_KNOWN);
            List<String> alsoKnownList = new ArrayList<>(alsoKnownListJson.length());
            for(int i = 0; i < alsoKnownListJson.length(); i++) {
                alsoKnownList.add(alsoKnownListJson.getString(i));
            }
            ret.setAlsoKnownAs(alsoKnownList);

            ret.setPlaceOfOrigin(sandwichJson.getString(KEY_PLACE_OF_ORIGIN));
            ret.setDescription(sandwichJson.getString(KEY_DESCRIPTION));
            ret.setImage(sandwichJson.getString(KEY_IMAGE));

            JSONArray ingredientsListJson = sandwichJson.getJSONArray(KEY_INGREDIENTS);
            List<String> ingredientsList = new ArrayList<>(ingredientsListJson.length());
            for(int i = 0; i < ingredientsListJson.length(); i++){
                ingredientsList.add(ingredientsListJson.getString(i));
            }
            ret.setIngredients(ingredientsList);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        Log.d(JsonUtils.class.getSimpleName(), json);
        return ret;
    }
}
