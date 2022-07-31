package dev.mayuna.sakuya.network.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import dev.mayuna.sakuya.utils.logging.LoggerFacade;

public class ServerNetworkListener extends Listener {

    private final LoggerFacade loggerFacade;

    public ServerNetworkListener(LoggerFacade loggerFacade) {
        this.loggerFacade = loggerFacade;
    }

    @Override
    public void connected(Connection connection) {
        connection.setName("Client " + connection.getID() + " (" + connection.getRemoteAddressTCP() + ")");

        loggerFacade.info("[KRYO] Successfully connected to " + connection.toString());
    }

    @Override
    public void disconnected(Connection connection) {
        loggerFacade.info("[KRYO] Disconnected from " + connection.toString());
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof FrameworkMessage) {
            return;
        }

        loggerFacade.info("[KRYO] Received object " + object.getClass().getSimpleName() + " from " + connection.toString());

        loggerFacade.info("Value: " + object);
    }
}
