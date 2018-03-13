package com.example.demo.multi.springBoot;

import java.util.PriorityQueue;

/**
 * 测试Object类中wait、notify等方法，类比ObjectThread2Test
 * @author: LiHongxing
 * @date: Create in 2018-03-13 14:03
 * @modefied:
 */
public class ObjectThread1Test {



    private PriorityQueue<Integer> queue=new PriorityQueue<>(10);
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ObjectThread1Test object=new ObjectThread1Test();
        Producer producer=object.new Producer();
        Consumer consumer=object.new Consumer();
        producer.start();
        consumer.start();
    }

    class Consumer extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("------------start Consumer-------------");
            consume();
        }
        private void consume()
        {
            while(true)
            {
                synchronized (queue) {
                    while(queue.size()==0)
                    {
                        try {
                            System.out.println("队列为空");
                            queue.wait();

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    queue.poll();
                    queue.notify();
                    System.out.println("取走一个元素,还有："+queue.size());

                }
            }
        }
    }

    class Producer extends Thread
    {
        @Override
        public void run()
        {
            produce();
        }
        private void produce()
        {
            while(true)
            {
                synchronized (queue) {
                    while(queue.size()==10)
                    {

                        try {
                            System.out.println("队列已经满了");
                            queue.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    queue.notify();
                    System.out.println("向队列中插入一个元素，长度为"+queue.size());
                }
            }
        }
    }
}
