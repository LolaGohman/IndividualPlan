package vita.syrytsia.individual.plan;

public class Main {

    public static void main(String[] args) {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(13);
        treeNode.insert(9);
        treeNode.insert(19);
        treeNode.insert(25);
        treeNode.insert(16);
        treeNode.insert(66);
        treeNode.insert(11);

        treeNode.remove(10);

        BinaryTreePrinter.print(treeNode.getRootNode());
    }
    
}
