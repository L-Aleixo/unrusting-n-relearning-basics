package com.studying;

import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class multilingual{
    // Scanninc the lang folder
    public static List<String> getLanguages(String langDir){
        List<String> languages = new ArrayList<>();
        File folder = new File(langDir);

        if (!folder.exists() || !folder.isDirectory()){
            return languages;
        }

        File[] files = folder.listFiles();
        if (files != null){
            for (File file : files){
                if (file.isFile() && file.getName().endsWith(".json")){
                    // Stripping ".json"
                    String name = file.getName();
                    languages.add(name.substring(0, name.length() -5));
                }
            }
        }
        return languages;
    }
    // Loading and returning the JSONObject
    public static JSONObject loadLanguage(String langDir, String langCode) throws IOException{
        File file = new File(langDir, langCode + ".json");

        // Parsing file into JSONObject
        try (FileReader reader = new FileReader(file)){
            JSONTokener tokener = new JSONTokener(reader);
            return new JSONObject(tokener);
        }
    }
}
