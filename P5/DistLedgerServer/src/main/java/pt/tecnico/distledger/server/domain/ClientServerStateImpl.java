package pt.tecnico.distledger.server.domain;

import io.grpc.stub.StreamObserver;
import static io.grpc.Status.*;

import pt.ulisboa.tecnico.distledger.contract.user.UserDistLedger.*;
import pt.ulisboa.tecnico.distledger.contract.user.UserServiceGrpc;
import static pt.tecnico.distledger.server.domain.ServerState.*;
import static pt.tecnico.distledger.server.ServerMain.serverState;
import pt.tecnico.distledger.server.Debug;

public class ClientServerStateImpl extends UserServiceGrpc.UserServiceImplBase {

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

    @Override
    public void balance(BalanceRequest request, StreamObserver<BalanceResponse> responseObserver) {
        BalanceResponse response = BalanceResponse.newBuilder().setValue(serverState.balance(request.getUserId())).build();
        debug("Checking if server is active");

        if (!serverState.isActive()) {
            debugError("Server is deactivated");
            responseObserver.onError(
                    UNAVAILABLE.withDescription("Server is currently inactive.").asRuntimeException());

        } else {
            debug("Server is active");
            if (response.getValue() == ERR_NO_USR) {
                debugError("User not found with id: " + request.getUserId());
                responseObserver.onError(
                        INTERNAL.withDescription("No user was found with the ID: " +
                                request.getUserId()).asRuntimeException());
            }
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        debug("Balance request handled successfully");
    }

    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<CreateAccountResponse> responseObserver) {
        CreateAccountResponse response = CreateAccountResponse.newBuilder().build();
        debug("Checking if server is active");

        if (!serverState.isActive()) {
            debugError("Server is deactivated");
            responseObserver.onError(
                    UNAVAILABLE.withDescription("Server is currently inactive.").asRuntimeException());

        } else {
            debug("Server is active");
            Integer returnCode = serverState.createAccount(request.getUserId());

            if (returnCode.equals(ERR_DUP_USR))
                responseObserver.onError(ABORTED.withDescription("User already exists with ID: " +
                        request.getUserId()).asRuntimeException());
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        debug("Account creation request handled successfully");
    }

    @Override
    public void deleteAccount(DeleteAccountRequest request, StreamObserver<DeleteAccountResponse> responseObserver) {
        DeleteAccountResponse response = DeleteAccountResponse.newBuilder().build();
        debug("Checking if server is active");

        if (!serverState.isActive()) {
            debugError("Server is deactivated");
            responseObserver.onError(
                    UNAVAILABLE.withDescription("Server is currently inactive.").asRuntimeException());
        } else {
            debug("Server is active");
            Integer returnCode = serverState.deleteAccount(request.getUserId());

            if (returnCode.equals(ERR_NO_USR)) {
                debugError("User not found with id: " + request.getUserId());
                responseObserver.onError(ABORTED.withDescription("User does not exist with ID: " +
                        request.getUserId()).asRuntimeException());

            } else if (returnCode.equals(ERR_HAS_BAL)) {
                debugError("Origin user has non-zero balance with id: " + request.getUserId());
                responseObserver.onError(ABORTED.withDescription("User still has balance on with ID: " +
                        request.getUserId()).asRuntimeException());
            }
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        debug("Account deletion request handled successfully");
    }

    @Override
    public void transferTo(TransferToRequest request, StreamObserver<TransferToResponse> responseObserver) {
        TransferToResponse response = TransferToResponse.newBuilder().build();
        debug("Checking if server is active");

        if (!serverState.isActive()) {
            debugError("Server is deactivated");
            responseObserver.onError(
                    UNAVAILABLE.withDescription("Server is currently inactive.").asRuntimeException());

        } else {
            debug("Server is active");
            Integer returnCode =
                    serverState.transferTo(request.getAccountFrom(), request.getAccountTo(), request.getAmount());

            if (returnCode.equals(ERR_NO_USR)) {
                debugError("Origin user not found with id: " + request.getAccountFrom());
                responseObserver.onError(ABORTED.withDescription("Sending user does not exist with ID: " +
                        request.getAccountFrom()).asRuntimeException());

            } else if (returnCode.equals(ERR_NO_DEST)) {
                debugError("Destination user not found with id: " + request.getAccountTo());
                responseObserver.onError(ABORTED.withDescription("Destination user does not exist with ID: " +
                        request.getAccountTo()).asRuntimeException());

            } else if (returnCode.equals(ERR_NO_BAL))
                debugError("Origin user has insuficient balance with id: " + request.getAccountFrom());
                responseObserver.onError((ABORTED.withDescription("Insuficient funds in sending user account with ID: " +
                        request.getAccountFrom()).asRuntimeException()));
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        debug("Transfer request handled successfully");
    }

}
