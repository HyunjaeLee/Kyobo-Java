import org.openqa.selenium.TimeoutException;

public abstract class Operation{

    abstract public void run();

    public void exception(TimeoutException e){
        e.printStackTrace();
    }

}