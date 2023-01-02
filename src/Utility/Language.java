package Utility;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private Locale assignedLanguage;
    private static ResourceBundle resourceBundle;

    public Language(String languageSuffix)
    {
        assignedLanguage = new Locale(languageSuffix);
    }
    public static ResourceBundle getLang()
    {
        return resourceBundle;
    }
    public void setLang()
    {
        resourceBundle = ResourceBundle.getBundle("Translation", assignedLanguage);
    }

    @Override
    public String toString()
    {
        return assignedLanguage.getDisplayLanguage();
    }
}
