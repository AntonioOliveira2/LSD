package pt.tecnico.distledger.namingserver;

import pt.tecnico.distledger.namingserver.grpc.ServiceEntry;
import static pt.tecnico.distledger.namingserver.grpc.ServiceEntry.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamingServerState {
    private Map<String, ServiceEntry> serviceMap = new HashMap<>();

    public final static int OP_SUCCESS = 1;
    public final static int ERR_DUP_REG = -1;
    public final static int ERR_NO_REG = -2;
    public final static int ERR_NO_SERV = -3;


    public int register(String service, String hostPort, String associatedQualifier) {

        if(serviceMap.get(hostPort) != null)
            return ERR_DUP_REG;

        serviceMap.put(hostPort, new ServiceEntry(service, hostPort, associatedQualifier));
        return OP_SUCCESS;
    }

    public List<String> lookup(String service, String qualifier) {

        if (serviceMap.get(service) == null)
            return Collections.emptyList();

        return serviceMap.get(service).searchServerEntry(qualifier);
    }

    public int delete(String hostPort, String service) {

        if (serviceMap.get(service) == null)
            return ERR_NO_SERV;

        ServiceEntry serviceEntry = serviceMap.get(service);

        if (serviceEntry.removeServer(hostPort).equals(ERR_NO_SV))
            return ERR_NO_REG;
        return OP_SUCCESS;
    }
}
