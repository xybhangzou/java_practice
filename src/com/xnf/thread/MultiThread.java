package com.xnf.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 *  示例：一个Java 程序的运行不仅仅是main()方法的运行，而是main 线程和其他线程的同时运行。
 */
public class MultiThread {

    public static void main(String[] args) {
        // 获取Java 线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo [] threadInfos = threadMXBean.dumpAllThreads(false,false);
        //遍历线程信息，仅打印线程ID 和 线程名称信息
        for (ThreadInfo t :threadInfos){
            System.out.println("[threadId:"+t.getThreadId() +"]" + "---threadName:"+t.getThreadName());
        }
        //---------输出结果（内容可能不同）-----------
        //[threadId:6]---threadName:Monitor Ctrl-Break
        //[threadId:5]---threadName:Attach Listener
        //[threadId:4]---threadName:Signal Dispatcher
        //[threadId:3]---threadName:Finalizer
        //[threadId:2]---threadName:Reference Handler
        //[threadId:1]---threadName:main
    }
}
