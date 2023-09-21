package lab14;

import lab14lib.Generator;

/**
 * @author qiushui
 * @Date 2023/9/21
 */
public class AcceleratingSawToothGenerator implements Generator {

    private int period;

    private double factor;

    private int state;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
    }

    @Override
    public double next() {
        double value = -1 + 2 * ((double) (state++) % period) / period;
        if(state % period == 0){
            state = 0;
            period = (int)(period * factor);
            if(period == 0){
                period = 1;
            }
        }
        return value;
    }
}
