///**
// * Copyright 2011-2012 www.wanlitong.com
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.paic.jrkj.tk.samples.mq;
//
//import com.paic.jrkj.tk.mq.DBMessageBean;
//import com.paic.jrkj.tk.mq.DBStore;
//import com.paic.jrkj.tk.mq.H2DBStore;
//import com.paic.jrkj.tk.mq.MessageBean;
//import com.paic.jrkj.tk.util.RandomUtil;
//import com.paic.jrkj.tk.util.ByteUtil;
//import com.paic.jrkj.tk.tools.core.SystemProperty;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
///**
// * <p/>
// *
// * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
// * @version $Revision: 1.0 $ $Date: 2016-1-8 17:06:19 $
// * @serial 1.0
// * @since 2016-1-8 17:06:19
// */
//public class MQTest {
//
//    private static Log logger = LogFactory.getLog(MQTest.class);
//
//    public static void main(String[] args) {
//        final DBStore dbstore = new H2DBStore("mq");
//
//        testConcurrency(dbstore);
//    }
//
//    private static void testOnce(final DBStore mq){
//        byte[] key = RandomUtil.getRandomASCII(25);
//        byte[] data = RandomUtil.getRandomASCII(2000);
//        mq.push(key, data);
//        MessageBean bean = mq.getMessageBean();
//        System.out.println("bean="+bean);
//        System.out.println(ByteUtil.isEquals(bean.getMsgId(), key));
//        System.out.println(ByteUtil.isEquals(bean.getMessage(), data));
//        mq.successBack(bean.getId());
//
//        bean = mq.getMessageBean();
//        System.out.println("bean2="+bean); //null
//
//        key = RandomUtil.getRandomASCII(25);
//        data = RandomUtil.getRandomASCII(2000);
//        mq.push(key, data);
//        bean = mq.getMessageBean();
//        System.out.println("bean3="+bean);
//        System.out.println(ByteUtil.isEquals(bean.getMsgId(), key));
//        System.out.println(ByteUtil.isEquals(bean.getMessage(), data));
//        mq.failBack(bean.getId());
//        bean = mq.getMessageBean();
//        System.out.println("bean4="+bean);
//        System.out.println("bean.getFailCount1="+((DBMessageBean)bean).getFailCount()); //1
//        System.out.println(ByteUtil.isEquals(bean.getMsgId(), key));
//        System.out.println(ByteUtil.isEquals(bean.getMessage(), data));
//        mq.failBack(bean.getId());
//        bean = mq.getMessageBean();
//        System.out.println("bean5="+bean);
//        System.out.println("bean.getFailCount2="+((DBMessageBean)bean).getFailCount());//2
//        System.out.println(ByteUtil.isEquals(bean.getMsgId(), key));
//        System.out.println(ByteUtil.isEquals(bean.getMessage(), data));
//        mq.successBack(bean.getId());
//        bean = mq.getMessageBean();
//        System.out.println("bean6="+bean); //null
//        mq.shutdown();
//    }
//
//    private static void testProducter(final DBStore mq){
//        new Thread(new Runnable() {
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e) {
//                        logger.fatal("", e);
//                    }
//                    System.out.println(mq.getStoreInfo());
//                }
//            }
//        }).start();
//        for (int i = 0; i < 12; i++) {
//            new Thread(new ProductThread(mq, i, 1)).start();
//        }
//    }
//
//    private static void testCustomer(final DBStore mq){
//        new Thread(new Runnable() {
//            public void run() {
//                while (true) {
//                    System.out.println(mq.getStoreInfo());
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e) {
//                        logger.fatal("", e);
//                    }
//
//                }
//            }
//        }).start();
//        for (int i = 0; i < 8; i++) {
//            new Thread(new CustomThread(mq, 1)).start();
//        }
//    }
//
//    private static void testConcurrency(final DBStore mq){
//        new Thread(new Runnable() {
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e) {
//                        logger.fatal("", e);
//                    }
//                    System.out.println(mq.getStoreInfo());
//                }
//            }
//        }).start();
//        logger.warn("Product 1 start.................");
//        for (int i = 0; i < 8; i++) {
//            new Thread(new ProductThread(mq, i, 3)).start();
//        }
//
//        try {
//            Thread.sleep(30000);
//        } catch (Exception e) {
//            logger.fatal("", e);
//        }
//        logger.warn("Custom 1 start.................");
//        for (int i = 0; i < 4; i++) {
//            new Thread(new CustomThread(mq, 2)).start();
//        }
//        try {
//            Thread.sleep(15000);
//        } catch (Exception e) {
//            logger.fatal("", e);
//        }
//        logger.warn("Product 2 start.................");
//        for (int i = 8; i < 16; i++) {
//            new Thread(new ProductThread(mq, i, 3)).start();
//        }
//        try {
//            Thread.sleep(120000);
//        } catch (Exception e) {
//            logger.fatal("", e);
//        }
//        logger.warn("Custom 2 start.................");
//        for (int i = 0; i < 6; i++) {
//            new Thread(new CustomThread(mq, 0)).start();
//        }
//        try {
//            Thread.sleep(150000);
//        } catch (Exception e) {
//            logger.fatal("", e);
//        }
//        logger.warn("Product 3 start.................");
//        for (int i = 16; i < 40; i++) {
//            new Thread(new ProductThread(mq, i, 0)).start();
//        }
//    }
//
//    private static class ProductThread implements Runnable {
//
//        private DBStore mq;
//        private int idx;
//        private long time;
//
//        public ProductThread(DBStore mq, int idx, long time) {
//            this.mq = mq;
//            this.idx = idx;
//            this.time = time;
//        }
//
//        public void run() {
//            int i = 0;
//            try {
//                byte[] data = RandomUtil.getRandomASCII(2000);
//                while (true) {
//                    i++;
//                    mq.push((idx + "_" + i + "_asdfghjklqwertyuiop1").getBytes(), data);
//                    if(time>0){
//                        Thread.sleep(time);
//                    }
//                }
//            } catch (Exception e) {
//                logger.fatal("", e);
//                System.exit(0);
//            }
//        }
//    }
//
//    private static class CustomThread implements Runnable {
//
//        private DBStore mq;
//        private int time;
//
//        public CustomThread(DBStore mq, int time) {
//            this.mq = mq;
//            this.time = time;
//        }
//
//        public void run() {
//            try {
//                while (true) {
//                    MessageBean bean = mq.getMessageBean();
//                    if (bean != null) {
////                        if (bean.getMsgId() == null) {
////                            logger.error("bean=" + bean);
////                            System.exit(0);
////                        }
//                        //send
////                        mq.successBack(bean.getMsgId());
//                        mq.successBack(bean.getId());
////                        mq.failBack(bean.getId());
//                    }
//                    if (time > 0) {
//                        Thread.sleep(time);
//                    }
//                }
//            } catch (Exception e) {
//                logger.fatal("", e);
//                System.exit(0);
//            }
//        }
//    }
//}
