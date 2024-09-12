package boot.spring.config;

import java.util.Random;

/**
 * @author tl
 * @description: TODO
 * @date 2024/9/11
 */
public class RandomUtil {
    static int count = 0;
    public static String getRandomStr() {
        count ++ ;
        return "count" + count ;
    }
}
