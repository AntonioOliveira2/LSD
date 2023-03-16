package pt.tecnico.distledger.namingserver.grpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServiceEntry {

    private final String serviceName;
    private List<ServerEntry> serverEntries = new ArrayList<ServerEntry>();
    public final static int OP_SUCCESS = 1;
    public final static int ERR_NO_SV = -1;


    public ServiceEntry(String service, String hostPort, String associatedQualifier) {
        serverEntries.add(new ServerEntry(hostPort, associatedQualifier));
        serviceName = service;
    }

    // Register operation related
    public void addServerEntry(ServerEntry entry) {
        serverEntries.add(entry);
    }

    // Lookup operation related
    public List<String> searchServerEntry(String qualifier) {
        // Check if qualifier is valid
        if (qualifier.compareTo("") != 0 || qualifier.compareTo("primary") != 0 || qualifier.compareTo("secondary") == 0)
            return Collections.<String>emptyList();

        ArrayList<String> hosts = new ArrayList<>();

        for (ServerEntry entry : serverEntries) {
            if (qualifier.compareTo("") == 0) {
                hosts.add(entry.getHostPort());
            } else if (entry.getHostPortQual(qualifier) != null)
                hosts.add(entry.getHostPortQual(qualifier));
        }
        return hosts;
    }

    public Integer removeServer(String hostPort) {
        for (ServerEntry entry : serverEntries) {
            if (entry.getHostPort().compareTo(hostPort) == 0) {
                serverEntries.remove(entry);
                return OP_SUCCESS;
            }
        }
        return ERR_NO_SV;
    }
}
