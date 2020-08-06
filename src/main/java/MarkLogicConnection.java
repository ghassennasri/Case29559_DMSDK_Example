import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MarkLogicConnection {
    private static final Logger logger = LoggerFactory.getLogger(MarkLogicConnection.class);

    private DatabaseClient client = null;

    /**
     *
     * This constructor should be used to create a MarkLogicConnection on the fly
     * e.g. when a writer/admin user logs in.
     *
     *
     * @param user username to use in the connection
     * @param password  password for the above username
     *
     */
    public MarkLogicConnection( String host,String port,String user, String password) {
        createConnection(host,port,user, password);
    }

    public void createConnection(String host,String port, String user, String password) {

        logger.info("Creating Connection to MarkLogic db on " + host + ":" + port);
        int   pt = Integer.parseInt(port);

        try {
            // create the client
            client = DatabaseClientFactory.newClient(host, pt, new DatabaseClientFactory.BasicAuthContext(user,password));
        } catch (Exception e) {
            logger.error("Failed to create db connection " + e.toString() + e.getMessage() );
            throw new RuntimeException(e);
        }

    }

    /**
     * accessor for DatabaseClient
     * @return
     */
    public DatabaseClient getClient() {
        return client;
    }

    /*
     * release the MarkLogic db connection
     *
     */
    public void release() {
        logger.info("releasing ML connection");
        client.release();
    }
}
