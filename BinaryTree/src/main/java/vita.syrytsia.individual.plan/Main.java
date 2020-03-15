package vita.syrytsia.individual.plan;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        treePerformance(3200_000);
        //        printTree();
    }

    private static void treePerformance(int nodesCount) {
        final Random random = new Random(159);
        final int randomDistribution = nodesCount * 20;
        long start = System.nanoTime();
        final TreeNode<Integer> tree = new TreeNode<>(random.nextInt(randomDistribution));
        for (int i = 0; i < nodesCount - 1; i += 1) {
            int value = random.nextInt(randomDistribution);
//            while (tree.contains(value))
//                value = random.nextInt(randomDistribution);

            tree.insert(value);
        }
        System.out.println("tree size = " + tree.size());
        System.out.println("execution time = " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
    }

    private static void printTree() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(13);
        treeNode.insert(9);
        treeNode.insert(19);
        treeNode.insert(25);
        treeNode.insert(16);
        treeNode.insert(66);
        treeNode.insert(11);
        treeNode.insert(8);

//        treeNode.remove(10);

        BinaryTreePrinter.print(treeNode.getRootNode());
    }

}
