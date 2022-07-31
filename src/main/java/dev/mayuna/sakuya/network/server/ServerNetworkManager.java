package dev.mayuna.sakuya.network.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import dev.mayuna.sakuya.network.server.listeners.ServerNetworkListener;
import dev.mayuna.sakuya.network.RegisterUtils;
import dev.mayuna.sakuya.utils.logging.BotLogger;
import dev.mayuna.sakuya.utils.logging.LoggerFacade;
import lombok.Getter;

import java.io.IOException;

public class ServerNetworkManager {

    private final @Getter Server server;
    private final @Getter int port;

    private final LoggerFacade loggerFacade;

    public ServerNetworkManager(LoggerFacade loggerFacade, int port) {
        this.loggerFacade = loggerFacade;
        this.port = port;
        this.server = new Server(65536, 65536); // 65kb

        prepareServer();
    }

    public void start() throws IOException {
        this.server.bind(port);
        server.start();
    }

    private void prepareServer() {
        Log.setLogger(new BotLogger());
        RegisterUtils.registerClasses(server.getKryo());
        server.addListener(new ServerNetworkListener(loggerFacade));
    }
}
