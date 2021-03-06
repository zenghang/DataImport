package org.mhealth.open.data.configuration;

import org.apache.log4j.Logger;
import org.mhealth.open.data.reader.MDataReader;
import org.mhealth.open.data.reader.MRecord;
import org.mhealth.open.data.record.SRecord;
import org.mhealth.open.data.util.ClockService;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dujijun on 2017/10/5.
 * <p>
 * 这个类之后会被改装成通过配置文件读入
 */
public class ConfigurationSetting {
    private static Logger logger = Logger.getLogger(ConfigurationSetting.class);

    // 数据导入的路径
    public static final String DATA_ROOT_PATH;

    public static final String SDATA_ROOT_PATH;

    // 判断线程是否应该阻塞的时间间隔
    public static final long BLOCK_WAIT_TIME;

    // 队列最大长度
    public static final int MAX_QUEUE_SIZE;

    // MHealth数据读取器的类
    public static final Class<? extends MDataReader> MHEALTH_READER_CLASS;

    // synthea数据读取器的类
    public static final Class<? extends MDataReader> SYNTHEA_READER_CLASS;

    // 包装所有和度量相关配置项
    public static final Map<String, MeasureConfiguration> measures = new HashMap<>();
    public static final Set<String> symeasures = new HashSet<>();
    // 时钟
    public static final ClockService CLOCK;
    public static final ClockService SYNTHEA_CLOCK;

    // @Deprecated 终止时间->毒丸
    // public static final String END_TIME;
    public static final String SYNTHEA_END_TIME;

    public static final Long DURATION;

    // 用于记录reader的个数
    public static final AtomicInteger READER_COUNT = new AtomicInteger(0);

    // 时钟每秒tick次数
    public static final int TICK_PER_SECOND;
    public static final int CUSHION_TIME;

    public static final int SYNTHEA_TICK_PER_SECOND;

    // 数据重用次数,用来计算时间偏移量,初始化为0
    public static int DATA_REPEAT_TIME = 0;
    // 截断后的当前时间与数据时间的差,与repeat_time合起来为总偏移量,
    // 为了方便新人部署该项目,不用再每次生成新数据,增设该字段.
    public static long TRUNCATE_OFFSET_TIME;

    // 经纬度信息数据根路径
    public static final String LATILONG_DATA_PATH;

    static {
        // 读入properties
        ClassLoader classLoader = ConfigurationSetting.class.getClassLoader();
        InputStream resource_in = classLoader.getResourceAsStream("conf.properties");
        Properties prop = new Properties();


        // 读入相应的数据
        String tmpDataRootPath = null;
        long tmpReadingIntervalMillis = 0l;
        int tmpMaxQueueSize = 0;
        Class tmpMHealthReaderClass = null;
        //Class tmpSyntheaClass = null;
        String tmpStartTime = null, duration = null;
        int tmpTickTime = 1;

        String tmpSyntheaDataRootPath = null;
        Class tmpSyntheaClass = null;
        String tmpSyntheaStartTime = null, tmpSyntheaEndTime = null;
        int tmpSyntheaTickTime = 1;
        int tmpCushionTime = 0;
        String tmpLatilongDataPath = null;
        try {
            prop.load(resource_in);
            tmpDataRootPath = prop.getProperty("DATA_ROOT_PATH");
            tmpReadingIntervalMillis = Long.valueOf(prop.getProperty("BLOCK_WAIT_TIME"));
            tmpMaxQueueSize = Integer.valueOf(prop.getProperty("MAX_QUEUE_SIZE"));
            tmpMHealthReaderClass = Class.forName(prop.getProperty("MHEALTH_READER_CLASS_NAME"));
            tmpStartTime = prop.getProperty("startTime");
            duration = prop.getProperty("duration");

            tmpTickTime = Integer.valueOf(prop.getProperty("tickPerSecond"));
            tmpCushionTime = Integer.valueOf(prop.getProperty("cushionTime"));
            // 这里开始读入measure相关配置项
            String[] measureNames = prop.getProperty("measureNames").split(",");
            for (String name : measureNames) {
                int readingFrequency = Integer.valueOf(prop.getProperty(name + ".readingFrequency"));
                float queueImportThreshold = Float.valueOf(prop.getProperty(name + ".queueImportThreshold"));
                int producerNums = Integer.valueOf(prop.getProperty(name + ".producerNums"));
                measures.put(name, new MeasureConfiguration(name, readingFrequency, queueImportThreshold, producerNums));
            }
            tmpSyntheaDataRootPath = prop.getProperty("SDATA_ROOT_PATH");
            tmpSyntheaClass = Class.forName(prop.getProperty("SYNTHRA_READER_CLASS_NAME"));
            tmpSyntheaStartTime = prop.getProperty("SyntheastartTime");
            tmpSyntheaEndTime = prop.getProperty("SyntheaendTime");
            tmpSyntheaTickTime = Integer.valueOf(prop.getProperty("SyntheatickPerSecond"));
            tmpLatilongDataPath = prop.getProperty("LATILONG_DATA_PATH");


            String[] SymeasureNames = prop.getProperty("SyntheaNames").split(",");
            for (String name : SymeasureNames) {
                symeasures.add(name);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        DATA_ROOT_PATH = tmpDataRootPath;
        BLOCK_WAIT_TIME = tmpReadingIntervalMillis;
        MAX_QUEUE_SIZE = tmpMaxQueueSize;
        MHEALTH_READER_CLASS = tmpMHealthReaderClass;
        SYNTHEA_READER_CLASS = tmpSyntheaClass;

        TICK_PER_SECOND = tmpTickTime;
        CUSHION_TIME = tmpCushionTime;
        CLOCK = new ClockService(Instant.parse(tmpStartTime), tmpTickTime, CUSHION_TIME);

        TRUNCATE_OFFSET_TIME = Instant.now().minusMillis(Instant.parse(tmpStartTime).toEpochMilli())
                .truncatedTo(ChronoUnit.MINUTES).toEpochMilli() + 60_000 * 2;
        logger.info("initial clock: " + CLOCK.instant());

        SDATA_ROOT_PATH = tmpSyntheaDataRootPath;
        SYNTHEA_TICK_PER_SECOND = tmpSyntheaTickTime;
        SYNTHEA_CLOCK = new ClockService(Instant.parse(tmpSyntheaStartTime), tmpSyntheaTickTime);
        logger.info(SYNTHEA_CLOCK.instant());
        SYNTHEA_END_TIME = tmpSyntheaEndTime;
        DURATION = Duration.parse(duration).toMillis();
        LATILONG_DATA_PATH = tmpLatilongDataPath;
    }

    public static Map<String, BlockingQueue> getSimpleContainer() {
        Map<String, BlockingQueue> queueMaps = new HashMap<>();
        Set<String> measureNames = measures.keySet();
        measureNames.forEach(name ->
                queueMaps.put(name,
                        new DelayQueue<MRecord>())
        );
        return queueMaps;
    }

    //初始化Synthea容器
    public static Map<String, BlockingQueue> getSyntheaContainer() {

        Map<String, BlockingQueue> syntheaqueueMaps = new HashMap<>();
        symeasures.forEach(name ->
                syntheaqueueMaps.put(name,
                        new DelayQueue<SRecord>())
        );
        return syntheaqueueMaps;
    }


}
