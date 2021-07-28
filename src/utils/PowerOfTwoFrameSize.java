package utils;

public class PowerOfTwoFrameSize {
    public static long get(long tagCount) {
        if (tagCount == 0)
            throw new IllegalArgumentException();

        if (tagCount <= 5)
            return 4;
        if (tagCount <= 11)
            return 8;
        if (tagCount <= 22)
            return 16;
        if (tagCount <= 44)
            return 32;
        if (tagCount <= 89)
            return 64;
        if (tagCount <= 177)
            return 128;
        if (tagCount <= 355)
            return 256;
        if (tagCount <= 710)
            return 512;
        if (tagCount <= 1420)
            return 1024;
        if (tagCount <= 2840)
            return 2048;
        if (tagCount <= 5680)
            return 4096;
        if (tagCount <= 11360)
            return 8192;

        throw new ArithmeticException();
    }
}
