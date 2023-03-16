package pt.tecnico.distledger.namingserver.grpc;

public class ServerEntry {

    private final String hostPort;
    private final String qualifier;

    public ServerEntry (String hostPort, String qualifier) {
        this.hostPort = hostPort;
        this.qualifier = qualifier;
    }

    public String getHostPort() {
        return hostPort;
    }
    public String getHostPortQual(String associatedQualifier) {
        return this.qualifier.compareTo(associatedQualifier) == 0 ? this.hostPort : null;
    }

}
