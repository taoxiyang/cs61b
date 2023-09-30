import org.junit.Test;

/**
 * @author qiushui
 * @Date 2023/9/30
 */
public class TestTrie {

    @Test
    public void test(){
        String[] strings = {"tao","xi","yang","hello","taq","yes","yaa","ya","陶","陶希","陶希阳"};
        double[] doubles = {0.1, 0.3, 0.4, 0.2, 0.3, 0.5, 0.9, 0.8, 0.3, 0.2, 0.5};
        Trie trie = new Trie(strings,doubles);
        System.out.println(trie.prefix("t"));
        System.out.println(trie.prefix("ya"));
        System.out.println(trie.prefix("z"));
        System.out.println(trie.prefix("陶"));
        System.out.println(trie.prefix("陶希"));
        System.out.println(trie.prefix("陶陶"));
    }

}
