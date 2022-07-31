package dev.mayuna.sakuya;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import dev.mayuna.modularbot.logging.MayuLogger;
import dev.mayuna.modularbot.objects.Module;
import dev.mayuna.sakuya.network.server.ServerNetworkManager;
import dev.mayuna.sakuya.utils.Config;
import dev.mayuna.sakuya.utils.logging.BotLogger;
import lombok.Getter;
import lombok.NonNull;

public class SakuyaModule extends Module {

    private static @Getter MayuLogger log;
    private static @Getter SakuyaModule instance;

    private static @Getter ServerNetworkManager networkManager;

    private static @Getter long start;

    @Override
    public void onLoad() {
        instance = this;

        start = System.currentTimeMillis();
    }

    @Override
    public void onEnable() {
        log = getLogger();

        log.info("> Sakuya Module @ " + getModuleInfo().version());
        log.info("Made by " + getModuleInfo().author());
        log.info("Now loading, please wait...");

        log.info("Loading default config...");
        Config.load();

        log.info("Starting SakuyaServer...");
        startSakuyaServer();

        log.info("Loading done in " + getElapsedTimeSinceStart() + "ms!");
    }

    @Override
    public void onDisable() {
        log.info("Unloading Sakuya...");

        log.info("Stopping SakuyaServer...");
        stopSakuyaServer();

        log.info("o/");
    }

    @Override
    public void onUnload() {
        instance = null;
    }

    @Override
    public void onCommandClientBuilderInitialization(@NonNull CommandClientBuilder commandClientBuilder) {
        log.info("Registering commands...");

        commandClientBuilder.addSlashCommands();
    }

    public static long getElapsedTimeSinceStart() {
        return System.currentTimeMillis() - start;
    }

    private void startSakuyaServer() {
        networkManager = new ServerNetworkManager(new BotLogger(), Config.getKryoServerPort());

        try {
            networkManager.start();
            log.info("Successfully started SakuyaServer on port " + Config.getKryoServerPort());
        } catch (Exception exception) {
            networkManager = null;

            log.error("Exception occurred while starting server!", exception);
        }
    }

    private void stopSakuyaServer() {
        if (networkManager != null) {
            networkManager.getServer().close();
        }
    }
}
