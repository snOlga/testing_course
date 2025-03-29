package com.example.demo;

public class BTree {

    private BTreeNode root;
    private int t;

    public BTree(int t) {
        this.t = t;
        root = null;
    }

    public BTreeNode search(int key) {
        return (root == null) ? null : root.search(key);
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            if (root.n == t) {
                BTreeNode newRoot = new BTreeNode(t, false);
                newRoot.children[0] = root;
                newRoot.splitChild(0, root);

                int i = 0;

                if (newRoot.keys[0] < key)
                    i++;

                newRoot.children[i].insertNonFull(key);
                root = newRoot;
            } else {
                root.insertNonFull(key);
            }
        }
    }

    public String fold() {
        if (root != null)
            return root.fold();
        return "";
    }

    public BTreeNode getRoot() {
        return root;
    }
}
