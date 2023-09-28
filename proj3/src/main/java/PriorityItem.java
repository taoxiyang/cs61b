/**
 * @author qiushui
 * @Date 2023/9/27
 */
public interface PriorityItem extends Comparable<PriorityItem> {

    double getPriority();

    default int compareTo(PriorityItem o){
        double cmp = getPriority() - o.getPriority();
        if(cmp < 0){
            return -1;
        }else if(cmp > 0){
            return 1;
        }else {
            return 0;
        }
    }
}
