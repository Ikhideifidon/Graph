package com.github.ikhideifidon.Graph_Edge_List_Representation;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("jol")
public class Graphs {
    // Undirected
    final List<Graph.Vertex<Integer>> vertices = new ArrayList<>();
    final Graph.Vertex<Integer> v1 = new Graph.Vertex<>(1);
    final Graph.Vertex<Integer> v2 = new Graph.Vertex<>(2);
    final Graph.Vertex<Integer> v3 = new Graph.Vertex<>(3);
    final Graph.Vertex<Integer> v4 = new Graph.Vertex<>(4);
    final Graph.Vertex<Integer> v5 = new Graph.Vertex<>(5);
    final Graph.Vertex<Integer> v6 = new Graph.Vertex<>(6);
    final Graph.Vertex<Integer> v7 = new Graph.Vertex<>(7);
    final Graph.Vertex<Integer> v8 = new Graph.Vertex<>(8);

    {
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        vertices.add(v5);
        vertices.add(v6);
        vertices.add(v7);
        vertices.add(v8);
    }

    final List<Graph.Edge<Integer>> edges = new ArrayList<>();
    final Graph.Edge<Integer> e1_2 = new Graph.Edge<>(v1, v2, 7);
    final Graph.Edge<Integer> e1_3 = new Graph.Edge<>(v1, v3, 9);
    final Graph.Edge<Integer> e1_6 = new Graph.Edge<>(v1, v6, 14);
    final Graph.Edge<Integer> e2_3 = new Graph.Edge<>(v2, v3, 10);
    final Graph.Edge<Integer> e2_4 = new Graph.Edge<>(v2, v4, 15);
    final Graph.Edge<Integer> e3_4 = new Graph.Edge<>(v3, v4, 11);
    final Graph.Edge<Integer> e3_6 = new Graph.Edge<>(v3, v6, 2);
    final Graph.Edge<Integer> e5_6 = new Graph.Edge<>(v5, v6, 9);
    final Graph.Edge<Integer> e4_5 = new Graph.Edge<>(v4, v5, 6);
    final Graph.Edge<Integer> e1_7 = new Graph.Edge<>(v1, v7, 1);
    final Graph.Edge<Integer> e1_8 = new Graph.Edge<>(v1, v8, 1);
    {
        edges.add(e1_2);
        edges.add(e1_3);
        edges.add(e1_6);
        edges.add(e2_3);
        edges.add(e2_4);
        edges.add(e3_4);
        edges.add(e3_6);
        edges.add(e5_6);
        edges.add(e4_5);
        edges.add(e1_7);
        edges.add(e1_8);
    }

    final Graph<Integer> graph = new Graph<>(vertices, edges);
}