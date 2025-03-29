package com.example.demo;

public class BTreeNode {
    // Variables Declared
    int[] keys; // Array to store keys
    int t; // Minimum degree (defines the range for number of keys)
    BTreeNode[] children; // Array to store child pointers
    int n; // Current number of keys
    boolean leaf; // True when node is leaf, else False

    public BTreeNode(int t, boolean leaf) {
        this.t = 2;
        this.leaf = leaf;

        keys = new int[t];
        children = new BTreeNode[t];
        n = 0;
    }

    public BTreeNode search(int key) {
        int i = 0;

        while (i < n && key > keys[i])
            i++;

        if (keys[i] == key)
            return this;

        if (leaf)
            return null;

        return children[i].search(key);
    }

    public void insertNonFull(int key) {
        int i = n - 1;

        if (leaf) {
            while (i >= 0 && key < keys[i]) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            n++;
        } else {
            while (i >= 0 && key < keys[i])
                i--;
            i++;

            if (children[i].n == 2 * t - 1) {
                splitChild(i, children[i]);
                if (key > keys[i])
                    i++;
            }
            children[i].insertNonFull(key);
        }
    }

    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++)
            z.keys[j] = y.keys[j + t];

        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.children[j] = y.children[j + t];
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--)
            children[j + 1] = children[j];

        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--)
            keys[j + 1] = keys[j];

        keys[i] = y.keys[t - 1];
        n++;
    }

    public String fold() {
        int i;
        String result = "";

        for (i = 0; i < n; i++) {
            if (!leaf)
                result += children[i].fold();
            result += (keys[i] + " ");
        }

        if (!leaf)
            result += children[i].fold();
        return result;
    }
}
