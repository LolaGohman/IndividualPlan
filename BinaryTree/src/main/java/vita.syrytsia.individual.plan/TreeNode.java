package vita.syrytsia.individual.plan;

import java.util.Objects;

class TreeNode<T extends Comparable<T>> {

    private Node<T> rootNode;

    TreeNode(T rootValue) {
        if (rootValue == null) {
            throw new IllegalArgumentException("Binary tree cannot contains null values!");
        }
        rootNode = new Node<>(rootValue);
    }

    TreeNode() {

    }

    int size() {
        return size(rootNode);
    }

    void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Binary tree cannot contains null values!");
        }
        rootNode = insert(value, rootNode);
    }

    void remove(T value) {
        rootNode = remove(rootNode, value);
    }

    boolean contains(T value) {
        return contains(rootNode, value);
    }


    Node<T> getRootNode() {
        return rootNode;
    }

    private int size(Node<T> currentNode) {
        if (currentNode == null) {
            return 0;
        } else {
            return (size(currentNode.getLeftNode()) + 1 + size(currentNode.getRightNode()));
        }
    }

    private Node<T> remove(Node<T> currentNode, T value) {
        if (currentNode == null) return null;
        int compareResult = value.compareTo(currentNode.getElement());
        if (compareResult < 0) currentNode.setLeftNode(remove(currentNode.getLeftNode(), value));
        else if (compareResult > 0) currentNode.setRightNode(remove(currentNode.getRightNode(), value));
        else {
            if (currentNode.getRightNode() == null) return currentNode.getLeftNode();
            if (currentNode.getLeftNode() == null) return currentNode.getRightNode();
            Node<T> copy = currentNode;
            currentNode = min(copy.getRightNode());
            currentNode.setRightNode(removeMin(copy.getRightNode()));
            currentNode.setLeftNode(copy.getLeftNode());
        }
        return currentNode;
    }

    private Node<T> min(Node<T> currentNode) {
        if (currentNode.getLeftNode() == null) return currentNode;
        return min(currentNode.getLeftNode());
    }

    private Node<T> removeMin(Node<T> currentNode) {
        if (currentNode.getLeftNode() == null) return currentNode.getRightNode();
        currentNode.setLeftNode(removeMin(currentNode.getLeftNode()));
        return currentNode;
    }

    private Node<T> insert(T element, Node<T> currentNode) {
        if (currentNode == null) return new Node<>(element);
        int compareResult = element.compareTo(currentNode.getElement());
        if (compareResult < 0)
            currentNode.setLeftNode(insert(element, currentNode.getLeftNode()));
        else if (compareResult > 0)
            currentNode.setRightNode(insert(element, currentNode.getRightNode()));
        return currentNode;
    }

    private boolean contains(Node<T> current, T value) {
        if (current == null || value == null) {
            return false;
        } else if (equalsByComparator(value, current.getElement())) {
            return true;
        } else {
            return value.compareTo(current.getElement()) < 0
                    ? contains(current.getLeftNode(), value)
                    : contains(current.getRightNode(), value);
        }
    }

    private boolean equalsByComparator(T elementFirst, T elementSecond) {
        return elementFirst.compareTo(elementSecond) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode<?> node = (TreeNode<?>) o;
        return Objects.equals(rootNode, node.rootNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootNode);
    }
}
