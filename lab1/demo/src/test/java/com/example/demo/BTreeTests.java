package com.example.demo;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class BTreeTests {

    private final int T_CONST = 3;

    static Stream<Arguments> insertData() {
        return Stream.of(
                Arguments.of(new int[] {}, ""),
                Arguments.of(new int[] { 1 }, "1 "),
                Arguments.of(new int[] { 1, 2, 3 }, "1 2 3 "),
                Arguments.of(new int[] { 1, 1, 1 }, "1 1 1 "),
                Arguments.of(new int[] { 3, 2, 1 }, "1 2 3 "),
                Arguments.of(new int[] { 3, 3, 1 }, "1 3 3 "),
                Arguments.of(new int[] { 10, 1, 0 }, "0 1 10 "),
                Arguments.of(new int[] { 10, -1, 0 }, "-1 0 10 "),
                Arguments.of(new int[] { 0, 0, 0, 0 }, "0 0 0 0 "),
                Arguments.of(new int[] { 0, 1, 2, 3 }, "0 1 2 3 "));
    }

    @ParameterizedTest
    @MethodSource("insertData")
    void insertTest(int[] input, String expected) {
        BTree tree = new BTree(T_CONST);
        for (int value : input) {
            tree.insert(value);
        }
        String result = tree.fold();
        Assert.isTrue(result.equals(expected), result);
    }

    static Stream<Arguments> searchNotNullData() {
        return Stream.of(
                Arguments.of(new int[] {}),
                Arguments.of(new int[] { 1 }),
                Arguments.of(new int[] { 1, 2, 3 }),
                Arguments.of(new int[] { 1, 1, 1 }),
                Arguments.of(new int[] { 3, 2, 1 }),
                Arguments.of(new int[] { 3, 3, 1 }),
                Arguments.of(new int[] { 10, 1, 0 }),
                Arguments.of(new int[] { 10, -1, 0 }),
                Arguments.of(new int[] { 0, 0, 0, 0 }),
                Arguments.of(new int[] { 0, 1, 2, 3 }));
    }

    @ParameterizedTest
    @MethodSource("searchNotNullData")
    void searchNotNullTest(int[] insertInput) {
        BTree tree = new BTree(T_CONST);
        for (int value : insertInput) {
            tree.insert(value);
        }
        for (int value : insertInput) {
            Assert.notNull(tree.search(value), "");
        }
    }

    static Stream<Arguments> searchNullData() {
        return Stream.of(
                Arguments.of(new int[] {}, 0),
                Arguments.of(new int[] { 1 }, 0),
                Arguments.of(new int[] { 1, 2, 3 }, 0),
                Arguments.of(new int[] { 1, 1, 1 }, 3),
                Arguments.of(new int[] { 3, 2, 1 }, 0),
                Arguments.of(new int[] { 3, 3, 1 }, 0),
                Arguments.of(new int[] { 10, 1, 0 }, 3),
                Arguments.of(new int[] { 10, -1, 0 }, 3),
                Arguments.of(new int[] { 0, 0, 0, 0 }, 1),
                Arguments.of(new int[] { 0, 1, 2, 3 }, -1));
    }

    @ParameterizedTest
    @MethodSource("searchNullData")
    void searchNullTest(int[] insertInput, int searchItem) {
        BTree tree = new BTree(T_CONST);
        for (int value : insertInput) {
            tree.insert(value);
        }
        Assert.isNull(tree.search(searchItem), "");
    }

    @Test
    void creationTest() {
        BTree tree = new BTree(T_CONST);
        Assert.isNull(tree.getRoot(), "");
    }

    @Test
    void testMaxT() {
        BTree tree = new BTree(T_CONST);
        tree.insert(1);
        Assert.isTrue(tree.getRoot().keys.length == T_CONST, "");
        Assert.isTrue(tree.getRoot().children.length == T_CONST, "");
    }

    @Test
    void inOneNodeTest() {
        int value = 1;
        BTree tree = new BTree(T_CONST);

        for (int i = 0; i < 3; i++) {
            tree.insert(value);
        }
        for (int i = 0; i < 3; i++) {
            Assert.isTrue(tree.getRoot().keys[i] == value, "");
            Assert.isTrue(tree.getRoot().children[i] == null, "");
        }
    }

    @Test
    void inTwoNodesTest() {
        int value = 1;
        BTree tree = new BTree(T_CONST);

        for (int i = 0; i < 4; i++) {
            tree.insert(value);
        }

        Assert.isTrue(tree.getRoot().keys[0] == value, "");
        Assert.isTrue(tree.getRoot().keys[1] == 0, "");
        Assert.isTrue(tree.getRoot().keys[2] == 0, "");        
        Assert.isTrue(tree.getRoot().children[0] != null, "");
        Assert.isTrue(tree.getRoot().children[0].keys[0] == value, "");
        Assert.isTrue(tree.getRoot().children[0].keys[1] == value, "");
        Assert.isTrue(tree.getRoot().children[0].children[0] == null, "");
        Assert.isTrue(tree.getRoot().children[1] != null, "");
        Assert.isTrue(tree.getRoot().children[1].keys[0] == value, "");
        Assert.isTrue(tree.getRoot().children[1].children[0] == null, "");
    }

    @Test
    void testSort() {
        BTree tree = new BTree(T_CONST);

        for (int i = 0; i < 4; i++) {
            tree.insert(i);
        }
        Assert.isTrue(tree.getRoot().children[0].keys[0] == 0, "");
        Assert.isTrue(tree.getRoot().keys[0] == 1, "");
        Assert.isTrue(tree.getRoot().children[1].keys[0] == 2, "");
        Assert.isTrue(tree.getRoot().children[1].keys[1] == 3, "");
        
        Assert.isTrue(tree.getRoot().keys[1] == 0, "");
        Assert.isTrue(tree.getRoot().keys[2] == 0, "");        
        Assert.isTrue(tree.getRoot().children[0] != null, "");
        Assert.isTrue(tree.getRoot().children[0].children[0] == null, "");
        Assert.isTrue(tree.getRoot().children[1] != null, "");
        Assert.isTrue(tree.getRoot().children[1].children[0] == null, "");
    }

    @Test
    void testSortSplitToRight() {
        int[] values = {3, 0, 1, 2};
        int[] sortedValues = {0, 1, 2, 3};
        BTree tree = new BTree(T_CONST);

        for (int i = 0; i < 4; i++) {
            tree.insert(values[i]);
        }
        Assert.isTrue(tree.getRoot().children[0].keys[0] == sortedValues[0], "");
        Assert.isTrue(tree.getRoot().keys[0] == sortedValues[1], "");
        Assert.isTrue(tree.getRoot().children[1].keys[0] == sortedValues[2], "");
        Assert.isTrue(tree.getRoot().children[1].keys[1] == sortedValues[3], "");
        
        Assert.isTrue(tree.getRoot().keys[1] == 0, "");
        Assert.isTrue(tree.getRoot().keys[2] == 0, "");        
        Assert.isTrue(tree.getRoot().children[0] != null, "");
        Assert.isTrue(tree.getRoot().children[0].children[0] == null, "");
        Assert.isTrue(tree.getRoot().children[1] != null, "");
        Assert.isTrue(tree.getRoot().children[1].children[0] == null, "");
    }

    @Test
    void testSortSplitToLeft() {
        int[] values = {5, 2, 10, 0};
        int[] sortedValues = {0, 2, 5, 10};
        BTree tree = new BTree(T_CONST);

        for (int i = 0; i < 4; i++) {
            tree.insert(values[i]);
        }
        Assert.isTrue(tree.getRoot().children[0].keys[0] == sortedValues[0], "");
        Assert.isTrue(tree.getRoot().children[0].keys[1] == sortedValues[1], "");
        Assert.isTrue(tree.getRoot().keys[0] == sortedValues[2], "");
        Assert.isTrue(tree.getRoot().children[1].keys[0] == sortedValues[3], "");
        
        Assert.isTrue(tree.getRoot().keys[1] == 0, "");
        Assert.isTrue(tree.getRoot().keys[2] == 0, "");        
        Assert.isTrue(tree.getRoot().children[0] != null, "");
        Assert.isTrue(tree.getRoot().children[0].children[0] == null, "");
        Assert.isTrue(tree.getRoot().children[1] != null, "");
        Assert.isTrue(tree.getRoot().children[1].children[0] == null, "");
    }
}
