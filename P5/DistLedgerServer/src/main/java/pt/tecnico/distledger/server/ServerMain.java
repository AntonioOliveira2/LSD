package pt.tecnico.distledger.server;

import pt.tecnico.distledger.server.domain.AdminServerStateImpl;
import pt.tecnico.distledger.server.domain.ClientServerStateImpl;
import pt.tecnico.distledger.server.domain.ServerState;
import pt.tecnico.distledger.server.Debug;
import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.Server;

import java.io.IOException;

public class ServerMain {

    private static final String debugFlag = "debug";

    public static ServerState serverState = new ServerState();

    public static void main(String[] args) throws IOException, InterruptedException {

        // check debug
        if (debugFlag.equals(System.getProperty("debug"))) {
            Debug.setDebug(true);
        }

        if (args.length != 1) {
            System.out.println("Argument usage: java ServerMain <port>");
            return;
        }

        final int port = Integer.parseInt(args[0]);

        final BindableService clientService = new ClientServerStateImpl();
        final BindableService adminService = new AdminServerStateImpl();

        Server server = ServerBuilder.forPort(port).addService(clientService).addService(adminService).build();

        server.start();

        server.awaitTermination();

    }
}

