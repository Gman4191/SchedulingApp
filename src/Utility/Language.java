package Utility;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private Locale assignedLanguage;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("Utility/Translation_en");

    public Language(String languageSuffix)
    {
        assignedLanguage = new Locale(languageSuffix);
    }
    public static ResourceBundle getLanguage()
    {
        return resourceBundle;
    }
    public void setLanguage()
    {
        resourceBundle = ResourceBundle.getBundle("Utility/Translation", assignedLanguage);
    }

    @Override
    public String toString()
    {
        return resourceBundle.getString(assignedLanguage.getDisplayLanguage());
    }
}
