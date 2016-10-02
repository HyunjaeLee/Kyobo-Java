import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String OS = System.getProperty("os.name").toUpperCase();
    private static final String ARCH = System.getProperty("os.arch").toUpperCase();

    public static final String PATH = path();
    public static final String ACCOUNTPATH = PATH
            + File.separator
            + "account.txt";
    public static final String GECKOPATH = PATH
            + File.separator
            + "gecko"
            + File.separator
            + "geckodriver-"
            + platform();
    public static final String[] ACCOUNT = account(); // 0: ID 1: PW

    private static String path(){
        return new File(System.getProperty("java.class.path")).getAbsoluteFile().getParentFile().toString();
    }

    private static String platform() {

        if(OS.contains("MAC") && ARCH.contains("64")){
            return "macos";
        } else if (OS.contains("LINUX") && ARCH.contains("64")) {
            return "linux";
        } else if (OS.contains("LINUX") && ARCH.contains("ARM")) {
            return "arm7hf";
        } else if (OS.contains("WINDOWS") && ARCH.contains("64")) {
            return "win64";
        } else {
            return null;
        }

    }

    private static String[] account(){

        String account[] = new String[2];

        try(Stream<String> stream = Files.lines(Paths.get(Main.ACCOUNTPATH))){
            stream
                    .limit(2)
                    .collect(Collectors.toList()).toArray(account);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            return account;
        }

    }

    public static void main(String[] args) throws Exception {

        JobDetail job = JobBuilder
                .newJob(Selenium.class)
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(
                        //CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                        CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }

}
