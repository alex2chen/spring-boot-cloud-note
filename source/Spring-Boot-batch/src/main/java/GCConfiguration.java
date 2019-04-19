import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 java -XX:+PrintFlagsFinal -version|find "ExplicitGCInvokesConcurrent"
	
	
	
 * @Author: alex
 * @Description:
 * @Date: created in 14:24 2018/11/21
 */
@Configuration
@EnableScheduling
public class GCConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(GCConfiguration.class);

    @Scheduled(fixedRate = 30000)
    public void checkMemeroyToCollect() {
        long freeMemory = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
        if (freeMemory < 350) {
            LOGGER.info("内存不足{}，开始回收，", freeMemory);
            System.gc();
        }
    }
}
