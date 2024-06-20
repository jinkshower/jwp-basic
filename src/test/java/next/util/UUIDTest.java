package next.util;

import java.util.UUID;
import org.junit.Test;

public class UUIDTest {

    @Test
    public void uuid() {
        String uuid = UUID.randomUUID().toString();
        //숫자만 남기는 정규식

        System.out.println(uuid);
    }
}
