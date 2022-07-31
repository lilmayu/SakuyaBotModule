package dev.mayuna.sakuya.network.client;

import com.esotericsoftware.kryonet.Client;
import dev.mayuna.sakuya.network.client.listeners.ClientNetworkListener;
import dev.mayuna.sakuya.network.RegisterUtils;
import dev.mayuna.sakuya.utils.logging.LoggerFacade;
import lombok.Getter;

import java.io.IOException;

public class ClientNetworkManager {

    private final @Getter Client client;
    private final @Getter int timeout;

    private final LoggerFacade loggerFacade;

    public ClientNetworkManager(LoggerFacade loggerFacade, int timeout) {
        this.loggerFacade = loggerFacade;
        this.timeout = timeout;
        this.client = new Client(65536, 65536); // 65kb

        prepareClient();
    }

    public ClientNetworkManager(LoggerFacade loggerFacade) {
        this(loggerFacade, 60000);
    }

    public void startAndConnect(String hostname, int port) throws IOException {
        client.start();
        client.connect(timeout, hostname, port);
    }

    private void prepareClient() {
        RegisterUtils.registerClasses(client.getKryo());
        client.addListener(new ClientNetworkListener(loggerFacade));
    }
}
