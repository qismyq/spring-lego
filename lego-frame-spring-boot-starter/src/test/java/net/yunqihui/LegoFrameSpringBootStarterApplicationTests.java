package net.yunqihui;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LegoFrameSpringBootStarterApplicationTests {


    @Autowired
    private RedissonClient redissonClient;

    public static final String lock = "lock";


    @Test
    void contextLoads() throws Exception{



        new Thread(() -> {
            System.out.println("线程进来了"+ Thread.currentThread().getName());
            RLock name = redissonClient.getLock(lock);
            name.lock();

            try {
                System.out.println("线程拿到了锁："+ Thread.currentThread().getName());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                name.unlock();
                System.out.println("线程释放了锁："+ Thread.currentThread().getName());

            }

        },"11").start();


        new Thread(() -> {
            RLock name = redissonClient.getLock(lock);
            System.out.println("线程进来了"+ Thread.currentThread().getName());
            name.lock();

            try {
                System.out.println("线程拿到了锁："+ Thread.currentThread().getName());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                name.unlock();
                System.out.println("线程释放了锁："+ Thread.currentThread().getName());
            }

        },"22").start();


        Thread.sleep(3000);
    }

}
