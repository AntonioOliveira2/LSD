package pt.tecnico.distledger.adminclient.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import pt.tecnico.distledger.adminclient.Debug;
import pt.ulisboa.tecnico.distledger.contract.admin.AdminServiceGrpc;
import pt.ulisboa.tecnico.distledger.contract.admin.AdminDistLedger.*;
import pt.ulisboa.tecnico.distledger.contract.admin.AdminServiceGrpc.AdminServiceBlockingStub;


public class AdminService {

    /* TODO: The gRPC client-side logic should be here.
        This should include a method that builds a channel and stub,
        as well as individual methods for each remote operation of this service. */

    private final ManagedChannel channel;
    private final AdminServiceBlockingStub stub;

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

    public AdminService(String target) {
        this.channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        this.stub = AdminServiceGrpc.newBlockingStub(this.channel);
    }

    public synchronized void activate() {
        ActivateRequest request = ActivateRequest.newBuilder().build();
        try {
            debug("Client requesting server activation");
            stub.activate(request);
            debug("activation request handled successfully");
            System.out.println("OK\n");
        } catch (StatusRuntimeException exception) {
            debugError(exception.toString());
            System.out.println("activate exception: " + exception.getStatus().getDescription());
        }
    }

    public synchronized void deactivate() {
        DeactivateRequest request = DeactivateRequest.newBuilder().build();
        try {
            debug("Client requesting server deactivation");
            stub.deactivate(request);
            debug("deactivation request handled successfully");
            System.out.println("OK\n");
        } catch (StatusRuntimeException exception) {
            debugError(exception.toString());
            System.out.println("deactivate exception: " + exception.getStatus().getDescription());
        }
    }

    public synchronized void getLedgerState() {
        getLedgerStateRequest request = getLedgerStateRequest.newBuilder().build();
        try {
            debug("Client requesting server operations");
            getLedgerStateResponse response = stub.getLedgerState(request);
            debug("Server operations request handled successfully");
            System.out.println("OK\n" + response.getLedgerState());
        } catch (StatusRuntimeException exception) {
            debugError(exception.toString());
            System.out.println("getLedgerState exception: " + exception.getStatus().getDescription());
        }
    }
}
