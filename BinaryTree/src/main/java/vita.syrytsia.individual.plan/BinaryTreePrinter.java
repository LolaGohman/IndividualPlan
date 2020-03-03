package vita.syrytsia.individual.plan;

class BinaryTreePrinter {

    static void print(Node root) {
        print(root, 0);
    }

    private static void print(Node root, int space) {
        if (root == null)
            return;

        space += 10;

        print(root.getRightNode(), space);

        System.out.print("\n");
        for (int i = 10; i < space; i++)
            System.out.print(" ");
        System.out.print(root.getElement() + "\n");

        print(root.getLeftNode(), space);
    }
}
