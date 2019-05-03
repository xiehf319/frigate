package cn.cici.frigate.gateway.properties;

import java.util.concurrent.TimeUnit;

/**
 * @author xiehf
 * @date 2019/5/3 23:38
 * @concat 370693739@qq.com
 **/
public class GatewayLimitProperties {

    private RedisRate redisRate;

    private Throttle throttle;

    public Throttle getThrottle() {
        return throttle;
    }

    public void setThrottle(Throttle throttle) {
        this.throttle = throttle;
    }

    public RedisRate getRedisRate() {
        return redisRate;
    }

    public void setRedisRate(RedisRate redisRate) {
        this.redisRate = redisRate;
    }

    public static class RedisRate {
        private int replenishRate;

        private int burstCapacity;

        public int getReplenishRate() {
            return replenishRate;
        }

        public void setReplenishRate(int replenishRate) {
            this.replenishRate = replenishRate;
        }

        public int getBurstCapacity() {
            return burstCapacity;
        }

        public void setBurstCapacity(int burstCapacity) {
            this.burstCapacity = burstCapacity;
        }
    }

    public static class Throttle {
        private int capacity;

        private int refillTokens;

        private int refillPeriod;

        TimeUnit refillUnit = TimeUnit.MILLISECONDS;

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getRefillTokens() {
            return refillTokens;
        }

        public void setRefillTokens(int refillTokens) {
            this.refillTokens = refillTokens;
        }

        public int getRefillPeriod() {
            return refillPeriod;
        }

        public void setRefillPeriod(int refillPeriod) {
            this.refillPeriod = refillPeriod;
        }

        public TimeUnit getRefillUnit() {
            return refillUnit;
        }

        public void setRefillUnit(TimeUnit refillUnit) {
            this.refillUnit = refillUnit;
        }
    }
}
