package io.crative.backend.internationalization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TranslationManager {
    private static TranslationManager instance;
    private final Map<String, Properties> languages = new HashMap<>();
    private String currentLanguage = "en_US";
    private final Properties fallbackLanguage;

    private TranslationManager() {
        loadLanguage("en_US");
        loadLanguage("de_DE");
        fallbackLanguage = languages.get("en_US");
    }

    public static TranslationManager getInstance() {
        if (instance == null) {
            instance = new TranslationManager();
        }
        return instance;
    }

    private void loadLanguage(String code) {
        Properties properties = new Properties();
        String filename = "/lang/"+code+".properties";

        try (InputStream is = getClass().getResourceAsStream(filename)) {
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                properties.load(isr);
                languages.put(code, properties);
                System.out.println("TranslationManager: Loaded language '" + code +"'");
            } else {
                System.err.println("TranslationManager: No language file found for '" + filename +"'");
            }
        } catch (IOException e) {
            System.err.println("TranslationManager: Failed to load language '" + code + "'\n" + e.getMessage());
        }
    }

    public String translate(String key) {
        Properties currentLanguage = languages.get(this.currentLanguage);

        if (currentLanguage != null && currentLanguage.containsKey(key)) {
            return currentLanguage.getProperty(key);
        }

        if (fallbackLanguage != null && fallbackLanguage.containsKey(key)) {
            return fallbackLanguage.getProperty(key);
        }

        return key;
    }

    public static String t(String key) {
        return getInstance().translate(key);
    }

    public String translate(String key, Object... args) {
        String translated = translate(key);

        for (int i = 0; i < args.length; i++) {
            translated = translated.replace("{" + i + "}", String.valueOf(args[i]));
        }

        return translated;
    }

    public static String t(String key, Object... args) {
        return getInstance().translate(key, args);
    }

    public void setCurrentLanguage(String code) {
        if (this.languages.containsKey(code)) {
            this.currentLanguage = code;
            System.out.println("TranslationManager: Switched current language to '"+code+"'");
        } else {
            System.err.println("TranslationManager: Language '"+code+"' not available");
        }
    }

    public String getCurrentLanguage() {
        return this.currentLanguage;
    }

    public String[] getAvailableLanguages() {
        return languages.keySet().toArray(new String[0]);
    }

    public boolean hasTranslation(String key) {
        Properties currentLang = languages.get(currentLanguage);
        return (currentLang != null && currentLang.containsKey(key)) ||
                (fallbackLanguage != null && fallbackLanguage.containsKey(key));
    }

    public void setStartLanguage(String code) {
        if (this.languages.containsKey(code)) {
            this.currentLanguage = code;
            System.out.println("TranslationManager: Set start language to '"+code+"'");
        } else {
            System.err.println("TranslationManager: Language '"+code+"' not available");
        }
    }
}