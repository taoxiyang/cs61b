import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author qiushui
 * @Date 2023/9/29
 */
public class Trie {

    private Node root;

    public Trie(String[] strings, double[] values) {
        this.root = new Node((char)0,false);
        if(strings.length != values.length){
            throw new IllegalArgumentException("not same length");
        }
        for(int i = 0; i < strings.length; i++){
            insert(strings[i],values[i],root,0);
        }
    }

    public Trie(String[] strings) {
        this(strings,new double[strings.length]);
    }

    private void insert(String str, double value, Node node, int idx){
        if(idx >= str.length()){
            return;
        }
        char c = str.charAt(idx);
        boolean isKey = idx == str.length() - 1;
        if(!node.map.containsKey(c)){
            node.map.put(c,new Node(c,isKey));
        }

        node = node.map.get(c);
        if(value > node.best){
            node.best = value;
        }

        if(isKey){
            node.isKey = true;
            node.value = value;
        }else{
            insert(str, value, node, idx + 1);
        }
    }

    public boolean contains(String str){
        int idx = 0;
        Node p = root;
        while (idx < str.length()){
            p = p.map.get(str.charAt(idx++));
            if(p == null)
                return false;
        }
        return p != null && p.isKey;
    }

    public List<String> prefix(String prefix){
        List<String> list = new ArrayList<>();
        prefix(prefix, root, list);
        return list;
    }

    private void prefix(String prefix, Node node, List<String> list){
        int idx = 0;
        while (idx < prefix.length()){
            node = node.map.get(prefix.charAt(idx++));
            if(node == null){
                break;
            }
        }
        if(node == null){
            return;
        }
        collect(prefix, node, list);
    }

    private void collect(String str, Node node, List<String> list){
        if(node.isKey){
            list.add(str);
        }
        for(Character c : node.map.keySet()){
            collect(str + c.toString(), node.map.get(c), list);
        }
    }

    private class Node{

        final char c;

        boolean isKey;

        double value;

        double best;

        Map<Character, Node> map;

        public Node(char c, boolean isKey) {
            this.c = c;
            this.isKey = isKey;
            map = new TreeMap<>();
            value = Double.MIN_VALUE;
            best = Double.MIN_VALUE;
        }
    }
}
