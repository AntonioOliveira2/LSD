package pt.tecnico.distledger.server;

public class Debug {
    private static boolean debug = false;

    public static void setDebug(boolean debug) {
        Debug.debug = debug;
    }

    public static boolean getDebug() {
        return debug;
    }
}
