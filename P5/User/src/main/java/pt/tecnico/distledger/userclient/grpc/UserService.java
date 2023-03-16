package pt.tecnico.distledger.userclient.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import pt.tecnico.distledger.userclient.Debug;
import pt.ulisboa.tecnico.distledger.contract.user.*;
import pt.ulisboa.tecnico.distledger.contract.user.UserDistLedger.*;

public class UserService {

    /*TODO: The gRPC client-side logic should be here.
        This should include a method that builds a channel and stub,
        as well as individual methods for each remote operation of this service. */
    
        private final ManagedChannel channel;
        private final UserServiceGrpc.UserServiceBlockingStub stub;

        private void debug(String debugMessage) {
            if (Debug.getDebug()) {
                System.out.println(debugMessage);
            }
        }
    
        private void debugError(String debugMessage) {
            if (Debug.getDebug()) {
                System.err.println(debugMessage);
            }
        }
    
        public UserService(String target) {
            this.channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            this.stub = UserServiceGrpc.newBlockingStub(this.channel);
        }

        public synchronized void createAccount(String username) {
            CreateAccountRequest request = CreateAccountRequest.newBuilder()
                .setUserId(username).build();
            try {
                debug("Client requesting account creation with id: " + username);
                stub.createAccount(request);
                debug("Client account creation request handled successfully");
                System.out.println("OK\n");
            } catch (StatusRuntimeException exception){
                debugError(exception.toString());
                System.out.println("createAccount exception: " + exception.getStatus().getDescription());
            }
        }

        public synchronized void deleteAccount(String username) {
            DeleteAccountRequest request = DeleteAccountRequest.newBuilder()
                .setUserId(username).build();
            try {
                debug("Client requesting account deletion with id: " + username);
                stub.deleteAccount(request);
                debug("Client account deletion request handled successfully");
                System.out.println("OK\n");
            } catch (StatusRuntimeException exception){
                debugError(exception.toString());
                System.out.println("deleteAccount exception: " + exception.getStatus().getDescription());
            }
        }

        public synchronized void balance(String username) {
            BalanceRequest request = BalanceRequest.newBuilder()
                .setUserId(username).build();
            try {
                debug("Client requesting account balance with id: " + username);
                BalanceResponse response = stub.balance(request);
                debug("Client balance request handled successfully");
                System.out.println("OK\n" + response.getValue());
            } catch (StatusRuntimeException exception){
                debugError(exception.toString());
                System.out.println("balance exception: " + exception.getStatus().getDescription());
            }
        }
    
        public synchronized void transferTo(String from, String dest, Integer amount) {
            TransferToRequest request = TransferToRequest.newBuilder()
                .setAccountFrom(from).setAccountTo(dest).setAmount(amount).build();
            try {
                debug("Client requesting account transfer with origin id: " + from + ", destination id: " + dest + " and amount: " + amount);
                stub.transferTo(request);
                debug("Client transfer request handled successfully");
                System.out.println("OK\n");
            } catch (StatusRuntimeException exception){
                debugError(exception.toString());
                System.out.println("transferTo exception: " + exception.getStatus().getDescription());
            }
        }

        public synchronized void shutdown() {
            channel.shutdownNow();
        }
}
