package com.github.ikhideifidon.Graph_Edge_List_Representation;

import java.util.*;

public class Graph<T extends Object & Comparable<T>> {
    // Instance Variables: Graph Definition
    // Each Vertex is mapped to its destination vertex.
    private Set<Vertex<T>> vertices = null;

    // Constructors
    public Graph() {
        vertices = new TreeSet<>();
    }

    // Setters and Getters
    public Set<Vertex<T>> getAllVertices() {
        return vertices;
    }

    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Vertex<T> to, Vertex<T> from, int weight) {
        if (to == null || from == null)
            throw new NullPointerException("Both 'to' and 'from' vertices cannot be null");
        to.edgeList.add(new Edge<>(from, weight));
    }

    public void printGraph(){
        //I printed it like this. You can print it however you want though
        for(Vertex<T> v : vertices){
            System.out.print("vertex name: "+ v.getValue() + ": ");
            for(Edge<T> edge : v.getEdgeList()){
                System.out.print("destVertex: " + edge.getTo().getValue() + " weight: " + edge.getWeight() + " | ");
            }
            System.out.print("\n");
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Vertex<T> v : vertices)
            builder.append(v.toString());
        return builder.toString();
    }

    public static class Vertex<T extends Object & Comparable<T>> implements Comparable<Vertex<T>> {
        // Instance Variables
        private final T value;
        private final List<Edge<T>> edgeList;


        // Constructor
        public Vertex(T value) {
            this.value = value;
            edgeList = new LinkedList<>();
        }

        // deep copy the vertex
        public Vertex(Vertex<T> vertex) {
            this(vertex.value);

            this.edgeList.addAll(vertex.edgeList);
        }

        public T getValue() {
            return value;
        }

        public List<Edge<T>> getEdgeList() {
            return edgeList;
        }

        public boolean adjacentVertex(Vertex<T> vertex) {
            for (Edge<T> edge : edgeList) {
                if (edge.to.equals(vertex))
                    return true;
            }
            return false;
        }


        @Override
        public int compareTo(Vertex<T> vertex) {
            // Comparison based on values
            final int valueCompare = this.value.compareTo(vertex.value);
            if (valueCompare != 0)
                return valueCompare;

            // Comparison based on edges
            if (this.edgeList.size() > vertex.edgeList.size())
                return 1;

            if (this.edgeList.size() < vertex.edgeList.size())
                return -1;

            final Iterator<Edge<T>> iter1 = this.edgeList.iterator();
            final Iterator<Edge<T>> iter2 = vertex.edgeList.iterator();

            while (iter1.hasNext()) {
                // Compare each edge's weight
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                if (e1.weight > e2.weight)
                    return 1;
                if (e1.weight < e2.weight)
                    return -1;
            }
            return 0;
        }

        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Vertex vertex))
                return false;

            final boolean equalValue = this.value == vertex.value;
            if (!equalValue)
                return false;

            final boolean equalSize = this.edgeList.size() == vertex.edgeList.size();
            if (!equalSize)
                return false;

            final Iterator<Edge<T>> iter1 = this.edgeList.iterator();
            //noinspection unchecked
            final Iterator<Edge<T>> iter2 = vertex.edgeList.iterator();

            while (iter1.hasNext()) {
                // Compare each edge's weight
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                if (e1.weight != e2.weight)
                    return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = this.value.hashCode();
            result = 31 * result + Integer.hashCode(this.edgeList.size());
            for (Edge<T> edge : edgeList)
                result = 31 * result + Integer.hashCode(edge.weight);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Value=").append(value).append("\n");
            for (Edge<T> e : edgeList)
                builder.append("\t").append(e.toString());
            return builder.toString();
        }
    }

    public static class Edge<T extends Object & Comparable<T>> implements Comparable<Edge<T>> {
        // Instance Variables
        private Vertex<T> to = null;
        private int weight = 0;

        // Constructor
        // Unweighted
        public Edge(Vertex<T> to) {
            if (to == null)
                throw new NullPointerException("Destination 'to' and vertex cannot be null.");
            this.to = to;
        }

        // weighted
        public Edge(Vertex<T> to, int weight) {
            this(to);
            this.weight = weight;
        }

        // Deep Copy
        public Edge(Edge<T> edge) {
            this(edge.to, edge.weight);
        }

        public Vertex<T> getTo() {
            return this.to;
        }

        public int getWeight() {
            return this.weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge<T> edge) {
            // Compare all the instances:
            if (this.weight > edge.weight)
                return 1;
            if (this.weight < edge.weight)
                return -1;

            // if weight are equal, try 'to' vertex
            return this.to.compareTo(edge.to);
        }

        @Override
        @SuppressWarnings("rawtypes")
        public boolean equals(Object o) {
            if (!(o instanceof Edge edge))
                return false;

            final boolean equalWeight = this.weight == edge.weight;
            if (!equalWeight)
                return false;

            return this.to == edge.to;
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(this.weight);
            result = 31 * result + this.to.hashCode();
            return result;
        }

//        @Override
//        public String toString() {
//            StringBuilder builder = new StringBuilder();
//            builder.append("[ ").append(from.value).append("(").append("]").append(" -> ")
//                    .append("[ ").append(to.value).append("]").append(" = ").append(weight).append("\n");
//            return builder.toString();
//        }
    }
}
