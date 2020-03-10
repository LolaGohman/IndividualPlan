package vita.syrytsia.individual.plan;

import java.util.Objects;

class Node<T extends Comparable<T>> {
    private T element;
    private Node<T> leftNode;
    private Node<T> rightNode;

    public Node(T element) {
        this.element = element;
        this.leftNode = null;
        this.rightNode = null;
    }

    public T getElement() {
        return element;
    }

    void setElement(T element) {
        this.element = element;
    }

    public Node<T> getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node<T> leftNode) {
        this.leftNode = leftNode;
    }

    public Node<T> getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node<T> rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public String toString() {
        return "Node{" +
                "element=" + element +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(element, node.element) &&
                Objects.equals(leftNode, node.leftNode) &&
                Objects.equals(rightNode, node.rightNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, leftNode, rightNode);
    }
}
