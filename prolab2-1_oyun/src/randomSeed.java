import java.util.Random;

public class randomSeed {
    int randomSeedGenerator() {
        Random random = new Random();
        int randomSeedNumber = 0;
        int limit = 50000;

        for (int i = 1; i <= 10; i++) {
            randomSeedNumber += (int) ((100 + random.nextInt(1001)) + Math.pow(3, ((10 + random.nextInt(101)) % 15)));
        }

        return randomSeedNumber % limit;
    }

}

