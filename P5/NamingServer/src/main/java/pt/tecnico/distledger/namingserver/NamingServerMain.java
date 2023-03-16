package pt.tecnico.distledger.namingserver;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.Server;

import java.io.IOException;

public class NamingServerMain {

    public static void main(String[] args) throws IOException, InterruptedException{

        // receive and print arguments
        System.out.printf("Received %d arguments%n", args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("arg[%d] = %s%n", i, args[i]);
        }

        // check arguments
        if (args.length != 1) {
            System.err.println("Argument(s) missing!");
            System.err.println("Usage: mvn exec:java NamingServerMain <port>");
            return;
        }

        final Integer port = Integer.parseInt(args[0]);
        final String target = port.toString();

        final BindableService namingService = new NamingServerServiceImpl();

        Server server = ServerBuilder.forPort(port).addService(namingService).build();
        server.start();
        server.awaitTermination();
    }

}
