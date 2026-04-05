package org.example.backend.MapGenerator;
import org.example.backend.Tile;


import java.util.Comparator;
import java.util.ArrayList;

public class CorridorGenerator {

    public static Tile[][] generateCorridors(Tile[][] map, ArrayList<Room> rooms) {
        ArrayList<Edge> graph = makeGraph(rooms);
        graph.sort(Comparator.comparingDouble(Edge::getDistance));      //сорт ребер по расстоянию
        ArrayList<Edge> mstEdges = findMSTEdges(graph, rooms);
        for (Edge edge : mstEdges) {
            generateCorridor(map, edge.getRoomA(), edge.getRoomB());
        }
        return map;
    }

    private static ArrayList<Edge> findMSTEdges(ArrayList<Edge> graph, ArrayList<Room> rooms) {     //поиск MST по Краскала
        ArrayList<Edge> mstEdges = new ArrayList<>();                        //MST - минимальное остовное дерево, соединяющее все вершины графа с минимальной суммарной длиной рёбер
        KruskalMST mst = new KruskalMST(rooms.size());
        for (Edge edge : graph) {
            int a = rooms.indexOf(edge.getRoomA());
            int b = rooms.indexOf(edge.getRoomB());     //индексы комнат
            if (!mst.connected(a, b)) {                 //если не соеденены то соединяем
                mst.union(a, b);
                mstEdges.add(edge);
            }
        }
        return mstEdges;
    }


    private static Tile[][] generateCorridor(Tile[][] map, Room a, Room b) {
        int[] centerA = a.getCenter();
        int[] centerB = b.getCenter();
        int x1 = centerA[0];
        int y1 = centerA[1];
        int x2 = centerB[0];
        int y2 = centerB[1];
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {        //горизонтальный коридор
            map[y1][x] = Tile.FLOOR;
        }
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {        //вертикальный коридор
            map[y][x2] = Tile.FLOOR;
        }
        return map;
    }

    private static ArrayList<Edge> makeGraph(ArrayList<Room> rooms) {       //создание графа все комнаты свящаны
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                Room room1 = rooms.get(i);
                Room room2 = rooms.get(j);
                edges.add(new Edge(room1, room2));
            }
        }
        return edges;
    }
}
