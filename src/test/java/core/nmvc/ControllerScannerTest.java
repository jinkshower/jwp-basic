package core.nmvc;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScannerTest {
    private static final Logger log = LoggerFactory.getLogger(ControllerScannerTest.class);
    private ControllerScanner cf;

    @Before
    public void setup() {
        cf = new ControllerScanner("core.nmvc");
    }

    @Test
    public void getControllers() {
        Map<Class<?>, Object> controllers = cf.getControllers();
        for (Class<?> clazz : controllers.keySet()) {
            log.debug("class : {}", clazz);
        }
    }
}
