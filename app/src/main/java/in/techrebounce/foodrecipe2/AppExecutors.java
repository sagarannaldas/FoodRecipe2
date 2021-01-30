package in.techrebounce.foodrecipe2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors intance;

    public static AppExecutors getInstance() {
        if(intance == null) {
            intance = new AppExecutors();
        }
        return intance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO() {
        return mNetworkIO;
    }
}
