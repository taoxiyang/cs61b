import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int length = 0;
        for(String s : asciis){
            if(s.length() > length){
                length = s.length();
            }
        }
        int index = 0;
        String[] sorted = sortHelperLSD(asciis,index++);
        while (index < length){
            sorted = sortHelperLSD(sorted,index++);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] chars = new int[256];
        int[] startPostions = new int[256];
        for(String s : asciis){
            char c = charAtLSD(s,index);
            chars[c]++;
        }

        for(int i = 1; i < 256; i++){
            startPostions[i] = startPostions[i-1] + chars[i-1];
        }

        String[] sorted = new String[asciis.length];

        for(String s : asciis){
            char c = charAtLSD(s,index);
            sorted[startPostions[c]++] = s;
        }

        return sorted;
    }

    private static char charAtLSD(String ascii, int index){
        if(index >= ascii.length()){
            return 0;
        }
        return ascii.charAt(ascii.length() - 1 - index);
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] asciis = {"xa","yb","ta","fc","fx","uvc","b","uz","a","c"};
        System.out.println(Arrays.toString(asciis));
        System.out.println(Arrays.toString(sort(asciis)));
//        System.out.println(Arrays.toString(sortHelperLSD(asciis,0)));
//        System.out.println(Arrays.toString(sortHelperLSD(asciis,1)));

    }
}
