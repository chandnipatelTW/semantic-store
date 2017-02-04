package recipestore.db.triplestore;

import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static com.google.common.io.Resources.getResource;

public class StardogTripleStoreDAOTest extends AbstractTripleStoreDAOTest {

    private static final String TEST_FILE = "test_quads.nq";
    private static StardogTripleStoreDAO tripleStoreDAO;

    @Before
    public void beforeMethod() {
        org.junit.Assume.assumeTrue("Stardog server should be running locally", isServerAvailable("localhost", 5821));
    }

    static {
        final InputStream recipeStream;
        try {
            recipeStream = getResource(TEST_FILE).openStream();
            tripleStoreDAO = new StardogTripleStoreDAO("test", new StardogConfiguration());
            tripleStoreDAO.populate(recipeStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed initializing query ", e);
        }
    }

    private static boolean isServerAvailable(String host, int port) {
        try {
            (new Socket(host, port)).close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    @Override
    TripleStoreDAO getTripleStoreDAO() {
        return tripleStoreDAO;
    }

}