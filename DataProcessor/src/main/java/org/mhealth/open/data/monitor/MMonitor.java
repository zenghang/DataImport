package org.mhealth.open.data.monitor;

import org.mhealth.open.data.reader.MDataReader;
import org.mhealth.open.data.reader.MFileReader;

import java.util.concurrent.CountDownLatch;

/**
 * Created by dujijun on 2017/10/5.
 */
public class MMonitor extends Monitor {

    @Override
    public void startMonitor(MDataReader reader){
        CountDownLatch startupLatch = new CountDownLatch(1);

        setStartupLatch(startupLatch);
        Thread monitor = new Thread(new MMonitorThread((MFileReader) reader, startupLatch));
        monitor.start();
    }
}
