package dev.mayuna.sakuya.utils;

import com.google.gson.JsonPrimitive;
import dev.mayuna.modularbot.objects.ModuleConfig;
import dev.mayuna.sakuya.SakuyaModule;

public class Config {

    public static void load() {
        getModuleConfig().copyDefaultsIfEmpty();
    }

    public static int getKryoServerPort() {
        return getModuleConfig().getMayuJson().getOrCreate("kryoServerPort", new JsonPrimitive(65000)).getAsInt();
    }

    private static ModuleConfig getModuleConfig() {
        return SakuyaModule.getInstance().getModuleConfig();
    }
}
