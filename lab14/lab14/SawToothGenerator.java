package lab14;

import lab14lib.Generator;

/**
 * @author qiushui
 * @Date 2023/9/20
 */
public class SawToothGenerator implements Generator {

    private int period;

    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
    }

    @Override
    public double next() {
        return -1 + 2 * ((double) (state++) % period) / period;
    }
}
