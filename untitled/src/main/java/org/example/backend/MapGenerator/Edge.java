package org.example.backend.MapGenerator;

public class Edge {
    private Room roomA;
    private Room roomB;
    private double distance;

    public Edge(Room roomA, Room roomB) {
        this.roomA = roomA;
        this.roomB = roomB;
        this.distance = calculateDistance();
    }

    private double calculateDistance() {
        int[] centerA = roomA.getCenter();
        int[] centerB = roomB.getCenter();
        return Math.sqrt(Math.pow(centerA[0] - centerB[0], 2) + Math.pow(centerA[1] - centerB[1], 2));
    }

    public Room getRoomA() {
        return roomA;
    }

    public Room getRoomB() {
        return roomB;
    }

    public double getDistance() {
        return distance;
    }

}
