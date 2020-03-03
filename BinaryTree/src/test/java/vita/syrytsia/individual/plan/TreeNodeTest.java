package vita.syrytsia.individual.plan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//* You can use BinaryTreePrinter class to make some tests more demonstrative and easy to understand
class TreeNodeTest {

    @Test
    void valuePassedInConstructorShouldBeRootValue() {
        TreeNode<Integer> node = new TreeNode<>(6);

        assertEquals(6, node.getRootNode().getElement());
    }

    @Test
    void valuePassedInConstructorShouldStayRootValue() {
        TreeNode<Integer> node = new TreeNode<>(6);

        node.insert(4);
        node.insert(3);

        assertEquals(6, node.getRootNode().getElement());
        assertEquals(3, node.size());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNullValuePassedThroughConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new TreeNode<>(null));
    }

    @Test
    void shouldCalculateBinaryTreeSize() {
        TreeNode<Integer> node = new TreeNode<>(2);

        node.insert(5);
        node.insert(6);
        node.insert(1);

        assertEquals(4, node.size());
    }

    @Test
    void testInsertionInNaturalOrder() {
        TreeNode<Integer> actualResult = new TreeNode<>(6);
        TreeNode<Integer> expectedResult = createNodeForInsertionTest();

        actualResult.insert(8);
        actualResult.insert(9);
        actualResult.insert(2);
        actualResult.insert(1);
        actualResult.insert(7);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldNotInsertDuplicatedValues() {
        TreeNode<Integer> actualResult = new TreeNode<>(6);
        TreeNode<Integer> expectedResult = createNodeForInsertionTest();

        actualResult.insert(8);
        actualResult.insert(9);
        actualResult.insert(2);
        actualResult.insert(1);
        actualResult.insert(7);
        actualResult.insert(7);

        assertEquals(expectedResult, actualResult);
        assertEquals(6, actualResult.size());
    }

    @Test
    void shouldNotInsertDuplicatedValues2() {
        TreeNode<Integer> actualResult = new TreeNode<>(1);

        actualResult.insert(1);
        actualResult.insert(1);
        actualResult.insert(1);

        assertEquals(1, actualResult.getRootNode().getElement());
        assertEquals(1, actualResult.size());
    }

    @Test
    void shouldThrowExceptionWhenTryToInsertNullValue() {
        TreeNode<Integer> actualResult = new TreeNode<>(1);

        actualResult.insert(2);

        assertThrows(IllegalArgumentException.class, () -> actualResult.insert(null));
    }

    @Test
    void shouldThrowExceptionWhenTryToRemoveNullValue() {
        TreeNode<Integer> actualResult = new TreeNode<>(1);

        actualResult.insert(2);

        assertThrows(IllegalArgumentException.class, () -> actualResult.remove(null));
    }

    @Test
    void shouldNotThrowExceptionWhenTryToRemoveValueThatDoesNotExist() {
        TreeNode<Integer> actualResult = new TreeNode<>(1);

        actualResult.insert(2);

        assertDoesNotThrow(() -> actualResult.remove(16));
    }

    @Test
    void shouldReturnTrueWhenCheckIfExistingValueExists() {
        TreeNode<Integer> actualResult = new TreeNode<>(1);

        actualResult.insert(2);

        assertTrue(actualResult.contains(1) && actualResult.contains(2));
    }

    @Test
    void shouldReturnFalseWhenCheckIfNonExistingValueExists() {
        TreeNode<Integer> actualResult = new TreeNode<>(1);

        actualResult.insert(2);

        assertFalse(actualResult.contains(12));
        assertFalse(actualResult.contains(null));
    }

    @Test
    void shouldBalanceTreeWhenRemovedElementHasTwoChildrenAndSuccessorHasNoRightElement() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(13);
        treeNode.insert(9);
        treeNode.insert(11);
        treeNode.insert(19);
        treeNode.insert(20);
        treeNode.insert(15);
        treeNode.insert(16);
        treeNode.insert(7);
        treeNode.insert(14);

        treeNode.remove(13);

        assertEquals(createNodeForDeletionTest(), treeNode);
    }

    @Test
    void shouldBalanceTreeWhenRemovedElementHasTwoChildrenAndSuccessorHasRightElement() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(13);
        treeNode.insert(9);
        treeNode.insert(11);
        treeNode.insert(19);
        treeNode.insert(20);
        treeNode.insert(18);
        treeNode.insert(16);
        treeNode.insert(7);
        treeNode.insert(14);
        treeNode.insert(15);

        treeNode.remove(13);

        assertEquals(createNodeForDeletionTest2(), treeNode);
    }

    @Test
    void shouldDeleteLeafNode() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(13);
        treeNode.insert(9);

        treeNode.remove(13);

        TreeNode<Integer> expectedResult = new TreeNode<>(10);
        expectedResult.insert(9);

        assertEquals(expectedResult, treeNode);
    }

    @Test
    void shouldDeleteNodeThatHaveOneChild() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(13);
        treeNode.insert(9);
        treeNode.insert(19);

        treeNode.remove(13);

        TreeNode<Integer> expectedResult = new TreeNode<>(10);
        expectedResult.insert(9);
        expectedResult.insert(19);

        assertEquals(expectedResult, treeNode);
    }

    @Test
    void shouldBeAbleToRemoveRootValue() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(13);
        treeNode.insert(9);
        treeNode.insert(19);

        treeNode.remove(10);

        TreeNode<Integer> expectedResult = new TreeNode<>(13);
        expectedResult.insert(9);
        expectedResult.insert(19);

        assertEquals(expectedResult, treeNode);
        assertEquals(3, treeNode.size());
        assertEquals(13, treeNode.getRootNode().getElement());
    }

    @Test
    void shouldBeAbleToRemoveSingleRootValue() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);

        treeNode.remove(10);

        assertEquals(0, treeNode.size());
    }

    @Test
    void shouldBeAbleToRemoveAllValuesFromTreeNode() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);

        treeNode.insert(15);
        treeNode.insert(2);
        treeNode.insert(-2);
        treeNode.insert(-5);
        treeNode.remove(15);
        treeNode.remove(2);
        treeNode.remove(-2);
        treeNode.remove(-5);
        treeNode.remove(10);

        assertEquals(0, treeNode.size());
        assertNull(treeNode.getRootNode());
    }

    @Test
    void shouldRemoveWhenDeleteElementHasTwoChildrenAndSuccessorIsRightAfterDeleteElement() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);
        treeNode.insert(15);
        treeNode.insert(3);
        treeNode.remove(10);

        TreeNode<Integer> expectedResult = new TreeNode<>(15);
        expectedResult.insert(3);

        assertEquals(expectedResult, treeNode);
        assertEquals(2, treeNode.size());
    }

    @Test
    void firstAddedValueToTreeWith0SizeShouldBeRootValue() {
        TreeNode<Integer> treeNode = new TreeNode<>(10);

        treeNode.insert(15);
        treeNode.remove(10);
        treeNode.remove(15);
        treeNode.insert(3);

        assertEquals(3, treeNode.getRootNode().getElement());
        assertEquals(1, treeNode.size());
    }

    private TreeNode<Integer> createNodeForInsertionTest() {
        TreeNode<Integer> expectedResult = new TreeNode<>(6);
        Node<Integer> leftNode = new Node<>(2);
        Node<Integer> rightNode = new Node<>(8);
        expectedResult.getRootNode().setRightNode(rightNode);
        expectedResult.getRootNode().setLeftNode(leftNode);
        rightNode.setLeftNode(new Node<>(7));
        rightNode.setRightNode(new Node<>(9));
        leftNode.setLeftNode(new Node<>(1));
        return expectedResult;
    }

    private TreeNode<Integer> createNodeForDeletionTest() {
        TreeNode<Integer> expectedResult = new TreeNode<>(10);
        Node<Integer> rightNode = new Node<>(14);
        Node<Integer> leftNode = new Node<>(9);
        expectedResult.getRootNode().setRightNode(rightNode);
        expectedResult.getRootNode().setLeftNode(leftNode);
        rightNode.setLeftNode(new Node<>(11));
        rightNode.setRightNode(new Node<>(19));
        rightNode.getRightNode().setRightNode(new Node<>(20));
        rightNode.getRightNode().setLeftNode(new Node<>(15));
        rightNode.getRightNode().getLeftNode().setRightNode(new Node<>(16));
        leftNode.setLeftNode(new Node<>(7));

        return expectedResult;
    }

    private TreeNode<Integer> createNodeForDeletionTest2() {
        TreeNode<Integer> expectedResult = new TreeNode<>(10);
        Node<Integer> rightNode = new Node<>(14);
        Node<Integer> leftNode = new Node<>(9);
        expectedResult.getRootNode().setRightNode(rightNode);
        expectedResult.getRootNode().setLeftNode(leftNode);
        rightNode.setLeftNode(new Node<>(11));
        rightNode.setRightNode(new Node<>(19));
        rightNode.getRightNode().setRightNode(new Node<>(20));
        rightNode.getRightNode().setLeftNode(new Node<>(18));
        rightNode.getRightNode().getLeftNode().setLeftNode(new Node<>(16));
        rightNode.getRightNode().getLeftNode().getLeftNode().setLeftNode(new Node<>(15));
        leftNode.setLeftNode(new Node<>(7));

        return expectedResult;
    }
}
