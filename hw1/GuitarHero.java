import synthesizer.GuitarString;

/**
 * @author qiushui
 * @Date 2023/8/12
 */
public class GuitarHero {

    private final static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private final static Double frequency = 440.0;


    public static void main(String[] args) {
        GuitarString[] strings = new GuitarString[keyboard.length()];
        for(int i = 0; i < keyboard.length(); i++){
            strings[i] = new GuitarString(frequency * Math.pow(2, (i - 24) / 12));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) > -1) {
                    strings[keyboard.indexOf(key)].pluck();
                }
            }

        /* compute the superposition of samples */
            double sample = 0;
            for(GuitarString string : strings){
                sample += string.sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            for(GuitarString string : strings){
                string.tic();
            }
        }

    }

}
