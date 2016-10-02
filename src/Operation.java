import org.openqa.selenium.TimeoutException;

/**
 * Created by Hyunjae on 9/16/16.
 */

public abstract class Operation{

    abstract public void run();

    public void exception(TimeoutException e){
        e.printStackTrace();
    }

}