package org.example.backend.MapGenerator;

public class KruskalMST {
    private int[] parent;

    public KruskalMST(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }
    //parent = [0,0,1,2]
    //find(3)
    //find(3)
    //parent[3] = 2
    //find(2)
    //
    //find(2)
    //parent[2] = 1
    //find(1)
    //
    //find(1)
    //parent[1] = 0
    //find(0)
    //
    //find(0)
    //parent[0] = 0

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }


    //parent = [0,1,2,3,4]
    //union(1,3)
    //parent[3] = 1
    //parent = [0,1,2,1,4]
    //parent[1] = 0
    //parent = [0,0,0,2]
    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            parent[rootB] = rootA;
        }
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }
}
