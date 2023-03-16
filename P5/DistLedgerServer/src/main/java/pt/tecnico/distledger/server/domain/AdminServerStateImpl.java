package pt.tecnico.distledger.server.domain;

import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.distledger.contract.admin.AdminDistLedger.*;
import pt.ulisboa.tecnico.distledger.contract.admin.AdminServiceGrpc;
import pt.tecnico.distledger.server.Debug;
import static pt.tecnico.distledger.server.ServerMain.serverState;

public class AdminServerStateImpl extends AdminServiceGrpc.AdminServiceImplBase {

    private void debug(String debugMessage) {
        if (Debug.getDebug()) {
            System.out.println(debugMessage);
        }
    }

    public void activate(ActivateRequest request, StreamObserver<ActivateResponse> responseObserver) {
        ActivateResponse response = ActivateResponse.newBuilder().build();
        debug("Server handling activation request");
        serverState.activate();
        debug("Activation request successful");

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void deactivate(DeactivateRequest request, StreamObserver<DeactivateResponse> responseObserver) {
        DeactivateResponse response = DeactivateResponse.newBuilder().build();
        debug("Server handling deactivation request");
        serverState.deactivate();
        debug("Deactivation request successful");

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getLedgerState(getLedgerStateRequest request, StreamObserver<getLedgerStateResponse> responseObserver) {
        debug("Server handling LedgerState request");
        getLedgerStateResponse response = getLedgerStateResponse.newBuilder().setLedgerState(
                serverState.getLedgerState()).build();
        debug("LedgerState request successful");

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
