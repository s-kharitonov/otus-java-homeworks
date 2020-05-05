package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import ru.otus.benchmarks.OOMBenchmark;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

/*
-Xms2048m
-Xmx2048m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseParallelGC

-Xms2048m
-Xmx2048m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

public class Main {
	public static void main(String[] args) throws Exception {
		switchOnMonitoring();
		new OOMBenchmark(100_000).run();
	}

	private static void switchOnMonitoring() {
		List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean gcbean : gcbeans) {
			System.out.println("GC name:" + gcbean.getName());
			NotificationEmitter emitter = (NotificationEmitter) gcbean;
			NotificationListener listener = (notification, handback) -> {
				if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
					GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
					String gcName = info.getGcName();
					String gcAction = info.getGcAction();
					String gcCause = info.getGcCause();

					long startTime = info.getGcInfo().getStartTime();
					long duration = info.getGcInfo().getDuration();

					System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
				}
			};
			emitter.addNotificationListener(listener, null, null);
		}
	}
}
