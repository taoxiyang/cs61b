package lab14;

import lab14lib.Generator;

/**
 * @author qiushui
 * @Date 2023/9/21
 */
public class StrangeBitwiseGenerator implements Generator {

    private int period;

    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
    }

    @Override
    public double next() {
        state = state + 1;
        int weirdState = state & (state >>> 3) % period;
//        int weirdState = state & (state >> 7) % period;
        return -1 + 2 * ((double) (weirdState) % period) / period;
    }
}
