package com.personal.gallery.kafkainternals.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


public class ZookeeperExperiment implements Watcher {

    private Logger logger = LoggerFactory.getLogger(ZookeeperExperiment.class);
    private static final String zNodePath = "/MyZNode";
    ZooKeeper zk;
    private static ZookeeperExperiment experiment;

    public static void main(String[] args) throws Exception {

        experiment = new ZookeeperExperiment();
        experiment.zk = new ZooKeeper("localhost:2181", 3000, experiment);

        experiment.zk.addWatch(zNodePath, AddWatchMode.PERSISTENT);

        Thread mThread = new Thread(experiment::modifier);

        mThread.start();
        mThread.join();
        experiment.zk.close();
    }


    public void modifier() {

        try {
            zk.create(zNodePath, "first node".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                zk.setData(zNodePath, ("first node " + (i + 1)).getBytes(), -1);
            }

            zk.delete(zNodePath, -1);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public void process(WatchedEvent event) {

        logger.info("notification for changes in znode {} {}", event.getPath(), event.getType());

        Stat stat = new Stat();
        try {
            byte[] data = zk.getData(zNodePath, false, stat);
            String updatedVal = new String(data, StandardCharsets.UTF_8);
            logger.info("updated values {}", updatedVal);

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
