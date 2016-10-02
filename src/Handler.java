import org.openqa.selenium.TimeoutException;

/**
 * Created by Hyunjae on 9/16/16.
 */

public class Handler {
    public Handler handle(int max, Operation o) {
        for(int i = 0; i<max; i++) {
            try {
                o.run();
                i = max;
            } catch (TimeoutException e) {
                o.exception(e);
            }
        }
        return this;
    }
}
