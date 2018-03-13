package com.example.demo.multi.springBoot;

import java.util.PriorityQueue;

/**
 * 类比ObjectThread1Test
 *
 * @author: LiHongxing
 * @date: Create in 2018-03-13 14:29
 * @modefied:
 */
public class ObjectThread2Test {

    private PriorityQueue<Integer> queue=new PriorityQueue<>(10);
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ObjectThread2Test object=new ObjectThread2Test();
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
                    while(queue.size() != 0)
                    {
                        queue.poll();
                        System.out.println("取走一个元素,还有："+queue.size());
                    }
                    System.out.println("队列为空");
                    try {
                        queue.notifyAll();
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                    while(queue.size() < 10)
                    {
                        queue.offer(1);
                        System.out.println("向队列中插入一个元素，长度为"+queue.size());
                    }
                    try {
                        System.out.println("队列已经满了");
                        queue.notifyAll();
                        queue.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
