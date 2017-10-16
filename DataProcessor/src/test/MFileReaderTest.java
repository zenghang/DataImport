package test;

import com.alibaba.fastjson.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * MFileReader Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 4, 2017</pre>
 */
public class MFileReaderTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: readDataInQueue(Map<String, Queue> queueMaps)
     */
//    @Test
//    public void testReadDataInQueue() throws Exception {
//        String path = "/Users/dujijun/Documents/大数据相关/数据生成/data/UserGroup-0";
//        MDataReader reader = new MFileReadRer(new File(path));
//        Map<String, Queue> queueMap = new HashMap<>();
//        queueMap.put("blood-pressure-output", new LinkedList<>());
//        queueMap.put("body-fat-percentage-output", new LinkedList<>());
//        queueMap.put("body-weight-output", new LinkedList<>());
//        queueMap.put("heart-rate-output", new LinkedList<>());
//        queueMap.put("step-count-output", new LinkedList<>());
//
//        while (!((MFileReader) reader).isEnd())
//            reader.readDataInQueue(queueMap);
//
//        queueMap.forEach((name, queue) -> {
//            System.out.println("==================measure name is " + name);
//            queue.forEach(System.out::println);
//        });
//
//    }

    @Test
    public void testFile() {
        String path = "/Users/dujijun/Documents/大数据相关/数据生成/data/UserGroup-0";
        File file = new File(path);
        System.out.println(file.getName());
        File[] files = file.listFiles(File::isDirectory);

        Arrays.stream(files).forEach(f -> System.out.println(f.getName()));
    }

    @Test
    public void testRandomFileAccess(){
        String path = "/Users/dujijun/Documents/大数据相关/数据生成/data/UserGroup-0/the-user-5/blood-pressure-output.json";
        File file = new File(path);
        long offset = 0;
        try(RandomAccessFile raf = new RandomAccessFile(file, "r")){
            while(true){
                raf.seek(offset);
                String line = raf.readLine();
                if(line == null)
                    break;
                System.out.println(line);
                offset += line.length() + 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetDate(){
        String record = "{\"header\":{\"id\":\"4d8328b8-342b-4fb7-bc48-3b133bbeb7c1\",\"schema_id\":{\"namespace\":\"omh\",\"name\":\"blood-pressure\",\"version\":\"1.0\"},\"user_id\":\"the-user-0\"},\"body\":{\"effective_time_frame\":{\"date_time\":\"2017-01-01T12:01:42Z\"},\"systolic_blood_pressure\":{\"unit\":\"mmHg\",\"value\":111.95501693146828},\"diastolic_blood_pressure\":{\"unit\":\"mmHg\",\"value\":73.34199409000631}}}\n";
        Date date = JSON.parseObject(record)
                .getJSONObject("body")
                .getJSONObject("effective_time_frame")
                .getDate("date_time");
        System.out.println(date);
    }

    @Test
    public void testProperties(){
        ClassLoader classLoader = MFileReaderTest.class.getClassLoader();
        InputStream resource_in = classLoader.getResourceAsStream("conf.properties");
        Properties prop = new Properties();
        try {
            prop.load(resource_in);
            String dataRootPath = prop.getProperty("DATA_ROOT_PATH");
            long readingIntervalMillis = Long.valueOf(prop.getProperty("BLOCK_WAIT_TIME"));
            Class<?> readerClass = Class.forName(prop.getProperty("READER_CLASS_NAME"));
            System.out.println(dataRootPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testThreadShutDown(){
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            es.execute(()->{
                System.out.println(j);
                try {
                    Thread.sleep(1000);
                    System.out.println(j+"over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
        es.shutdown();
        try {
            es.awaitTermination(10L, TimeUnit.SECONDS);
            System.out.println("over!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testClockMethod() throws InterruptedException {
        Clock current = Clock.system(ZoneId.systemDefault());
        Instant date = Instant.parse("2017-01-01T12:00:00Z");
        Clock clock = Clock.offset(current,Duration.between(current.instant(),date));
        System.out.println(current.instant().until(date, SECONDS));
        System.out.println(date.until(current.instant(), SECONDS));
        System.out.println(TimeUnit.NANOSECONDS.convert(current.instant().until(date, SECONDS)/100,TimeUnit.SECONDS));
        System.out.println(TimeUnit.NANOSECONDS.convert(current.instant().until(date, SECONDS),TimeUnit.SECONDS));
        System.out.println(LocalDateTime.now());
        System.out.println(clock.instant());
        Thread.sleep(1000);
        System.out.println(clock.instant());

        System.out.println(current.instant());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(current.instant());
        Clock self = Clock.offset(current, Duration.ofDays(-1));
        System.out.println(self.instant());
    }

} 
