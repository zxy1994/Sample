package geektime.im.lecture.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;
import redis.embedded.exceptions.EmbeddedRedisException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class EmbededRedis {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        try {
            redisServer = new RedisServer(redisPort);

            redisServer = RedisServer.builder()
                    .port(redisPort)
                    // good for local development on Windows to prevent security popups
                    .setting("bind 127.0.0.1")
//                    .slaveOf("locahost", redisPort)
//                    .setting("daemonize no")
//                    .setting("appendonly no")
                    .setting("maxmemory 128M")
                    .build();
            redisServer.start();
        } catch (EmbeddedRedisException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
