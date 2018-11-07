package lab.utils;

import lab.objects.Lang;

/**
 * Created by Alexandra on 07.11.2018.
 */
public class GroupsManager {
    private static Lang currentLang;
    private static Lang currentLangs;

    public static Lang getCurrentLang() {
        return currentLang;
    }

    public static void setCurrentLang(Lang currentLang) {
        GroupsManager.currentLang = currentLang;
    }

    public static Lang getCurrentLangs() {
        return currentLangs;
    }

    public static void setCurrentLangs(Lang currentLangs) {
        GroupsManager.currentLangs = currentLangs;
    }
}
