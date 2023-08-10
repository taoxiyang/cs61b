/**
 * @author qiushui
 * @Date 2023/8/10
 */
public class ShowDog extends Dog{

    public void bark(){
        System.out.println("show dog bark");
    }

    public static void main(String[] args) {
        Object dog = new ShowDog();
        ((Dog)dog).bark();
        ((ShowDog)dog).bark();
    }
}
