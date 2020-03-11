package vita.syrytsia.individual.plan;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

class TreeNode<T extends Comparable<T>> {

    private Node<T> rootNode;

    TreeNode(T rootValue) {
        if (rootValue == null) {
            throw new IllegalArgumentException("Binary tree cannot contains null values!");
        }
        rootNode = new Node<>(rootValue);
    }

    int size() {
        return size(rootNode);
    }

    void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Binary tree cannot contains null values!");
        } else if (rootNode == null) {
            rootNode = new Node<>(value);
        } else {
            insert(value, rootNode);
        }
    }

    boolean contains(T value) {
        return contains(rootNode, value);
    }

    void remove(T value) {
        if (value == null) {
            throw new IllegalArgumentException("You cannot remove null value from binary tree!");
        }
        remove(rootNode, null, value);
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

    private void remove(Node<T> currentNode, Node<T> currentNodeParent, T value) {
        if (!contains(value)) return;
        if (equalsByComparator(currentNode.getElement(), value)) {
            if (hasBothChildren(currentNode)) {
                removeNodeThatHasBothChildren(currentNode);
            } else if (hasOnlyOneChildren(currentNode)) {
                removeNodeThatHasOneChildren(currentNode);
            } else if (isLeaf(currentNode)) {
                removeLeafNode(currentNodeParent, value);
            }
        } else if (value.compareTo(currentNode.getElement()) < 0) {
            remove(currentNode.getLeftNode(), currentNode, value);
        } else {
            remove(currentNode.getRightNode(), currentNode, value);
        }
    }

    private void removeLeafNode(Node<T> deleteNodeParent, T deleteValue) {
        if (deleteNodeParent == null) {
            rootNode = null;
        } else if (deleteNodeParent.getLeftNode() != null &&
                equalsByComparator(deleteNodeParent.getLeftNode().getElement(), deleteValue)) {
            deleteNodeParent.setLeftNode(null);
        } else {
            deleteNodeParent.setRightNode(null);
        }
    }

    private void removeNodeThatHasOneChildren(Node<T> currentNode) {
        Node<T> children = currentNode.getRightNode() != null ? currentNode.getRightNode() : currentNode.getLeftNode();
        currentNode.setElement(children.getElement());
        currentNode.setLeftNode(children.getLeftNode());
        currentNode.setRightNode(children.getRightNode());
    }

    private void removeNodeThatHasBothChildren(final Node<T> deleteNode) {
        Node<T> successorParent = deleteNode;
        final Map.Entry<Node<T>, Node<T>> successorEntry = getSuccessor(deleteNode.getRightNode(), successorParent);
        Node<T> successor = successorEntry.getKey();
        successorParent = successorEntry.getValue();
        deleteNode.setElement(successor.getElement());
        if (successor.getRightNode() != null) {
            successor.setElement(successor.getRightNode().getElement());
            successor.setLeftNode(successor.getRightNode().getLeftNode());
            successor.setRightNode(successor.getRightNode().getRightNode());
        } else if (!successor.equals(deleteNode.getRightNode())) {
            successorParent.setLeftNode(null);
        } else {
            successorParent.setRightNode(null);
        }
    }

    private Map.Entry<Node<T>, Node<T>> getSuccessor(Node<T> rightNode, Node<T> successorParent) {
        if (rightNode.getLeftNode() == null) {
            return new AbstractMap.SimpleEntry<>(rightNode, successorParent);
        } else {
            return getSuccessor(rightNode.getLeftNode(), rightNode);
        }
    }

    private boolean hasBothChildren(Node<T> node) {
        return node != null &&
                node.getLeftNode() != null &&
                node.getRightNode() != null;
    }

    private boolean hasOnlyOneChildren(Node<T> node) {
        return node != null &&
                (node.getLeftNode() != null ||
                        node.getRightNode() != null);
    }

    private boolean isLeaf(Node<T> node) {
        return node != null &&
                node.getLeftNode() == null &&
                node.getRightNode() == null;
    }


    private boolean equalsByComparator(T elementFirst, T elementSecond) {
        return elementFirst.compareTo(elementSecond) == 0;
    }

    private void insert(T value, Node<T> currentNode) {
        if (currentNode == null || value == null)
            return;
        if (value.compareTo(currentNode.getElement()) < 0) {
            if (currentNode.getLeftNode() == null) {
                currentNode.setLeftNode(new Node<>(value));
            } else {
                insert(value, currentNode.getLeftNode());
            }
        } else if (value.compareTo(currentNode.getElement()) > 0) {
            if (currentNode.getRightNode() == null) {
                currentNode.setRightNode(new Node<>(value));
            } else {
                insert(value, currentNode.getRightNode());
            }
        }
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
