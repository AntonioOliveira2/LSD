package pt.tecnico.distledger.namingserver;

import io.grpc.stub.StreamObserver;
import static io.grpc.Status.*;
import pt.ulisboa.tecnico.distledger.contract.namingservice.NamingServer.*;
import pt.ulisboa.tecnico.distledger.contract.namingservice.NamingServerServiceGrpc.NamingServerServiceImplBase;
import static pt.tecnico.distledger.namingserver.NamingServerState.*;

public class NamingServerServiceImpl extends NamingServerServiceImplBase {

    private NamingServerState namingServer = new NamingServerState();

    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        // TODO: debug register operation flow
        RegisterResponse response = RegisterResponse.newBuilder().build();
        Integer result = namingServer.register(request.getService(), request.getHostPort(), request.getQualifier());

        if (result.equals(ERR_DUP_REG))
            responseObserver.onError(
                    ABORTED.withDescription("Not possible to  register the server").asRuntimeException());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void lookup(LookupRequest request, StreamObserver<LookupResponse> responseObserver) {
        // TODO: debug lookup operation flow
        LookupResponse response = LookupResponse.newBuilder().addAllServerList(
                namingServer.lookup(request.getService(), request.getQualifier())).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        DeleteResponse response = DeleteResponse.newBuilder().build();

        Integer result = namingServer.delete(request.getHostPort(), request.getService());

        if (result.equals(ERR_NO_REG) || result.equals(ERR_NO_SERV))
                responseObserver.onError(
                        ABORTED.withDescription("Not possible to remove the server").asRuntimeException());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}
