package com.studying;

import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Defining the language directory
    private static final String LANG_DIR = "java/multi-lingual-selection/src/lang";

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        // Scanning the languages
        List<String> currentLangs = multilingual.getLanguages(LANG_DIR);
        if (currentLangs.isEmpty()){
            System.out.println("Java is looking for files in: " + new java.io.File(".").getAbsolutePath());
            System.out.println("ERROR: No language files found!");
            scanner.close();
            return;
        }

        // Creating the language list
        String langOptions = String.join(", ", currentLangs);
        String langCode = "none";

        // Validating user's selection
        while (!currentLangs.contains(langCode)){
            System.out.print("\n| Select a language (" + langOptions + "): ");
            langCode = scanner.nextLine().trim().toLowerCase();

            if (!currentLangs.contains(langCode)){
                System.out.print("ERROR: [" + langCode + "] not found. Default to english? (y/n): ");
                String defaultLang = scanner.nextLine().trim().toLowerCase();

                if (defaultLang.equals("y")){
                    if (currentLangs.contains("en")){
                        langCode = "en";
                    }else{
                        System.out.println("ERROR: Default language not found!");
                        scanner.close();
                        return;
                    }
                }
            }
        }

        JSONObject translations = null;

        // Attempting to load the language
        try{
            translations = multilingual.loadLanguage(LANG_DIR, langCode);
        }catch (IOException | JSONException e){
            System.out.println("ERROR: [" + langCode + ".json] is corrupted or missing. Defaulting to English.");
            try{
                translations = multilingual.loadLanguage(LANG_DIR, "en");
            }catch (IOException | JSONException ex){
                System.out.println("CRITICAL ERROR: Could not load any language files!");
                scanner.close();
                return;
            }
        }

        //
        String msg = translations.optString("hello", "[Translation Missing: hello]");
        System.out.println("\n" + msg);

        scanner.close();
    }
}
