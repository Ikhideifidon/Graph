package com.github.ikhideifidon.Graph_Edge_List_Representation;

import java.util.*;

/** This Graph implementation conforms to the following:
 * 1. It is Undirected.
 * 2. It is either weighted or unweighted.
 * 3. It allows self-loop.
 **/
public class Graph<T extends Object & Comparable<T>> {
    // Instance Variables: Graph Definition
    // Each Vertex is mapped to its destination vertex.
    private Set<Vertex<T>> vertices = null;
    private int v = 0;              // Number of vertices
    private int e = 0;              // Number of edges
    private final List<Vertex<T>> listVertices = new ArrayList<>();           // To keep track of all vertices

    public enum WEIGHT {
        WEIGHTED, UNWEIGHTED
    }

    /** Defaulted to unweighted*/
    private WEIGHT weight = WEIGHT.UNWEIGHTED;



    // Constructors
    public Graph() {
        vertices = new HashSet<>();
    }

    public Graph(WEIGHT weight) {
        this.weight = weight;
    }

    public Graph(WEIGHT weight, Collection<Vertex<T>> collectionVertices) {
        this(weight);
        vertices.addAll(collectionVertices);
        v += collectionVertices.size();
        for (Vertex<T> vertex : collectionVertices)
            e += vertex.getEdgeList().size();
    }

    // Setters and Getters
    public Set<Vertex<T>> getAllVertices() {
        return vertices;
    }

    public List<Vertex<T>> getListVertices() { return listVertices; }

    public WEIGHT getWeight() {
        return weight;
    }

    public int v() {
        return v;
    }

    public int e() {
        return e;
    }

    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
        listVertices.add(vertex);
        v++;
    }

    public void addEdge(Vertex<T> to, Vertex<T> from, int weight) {
        if (to == null || from == null)
            throw new NullPointerException("Both 'to' and 'from' vertices cannot be null");
        to.getEdgeList().add(new Edge<>(from, weight));
        if (to.compareTo(from) != 0)            // Test for a self-loop
            from.getEdgeList().add(new Edge<T>(to, weight));
        e++;
    }

    public List<Edge<T>> adjacentVertices(Vertex<T> vertex) {
        for (Vertex<T> v : vertices) {
            if (vertex.compareTo(v) == 0)
                return vertex.getEdgeList();
        }
        return null;
    }

    public int degree(Vertex<T> vertex) {
        int degree = 0;
        for (Vertex<T> v : vertices) {
            if (vertex.compareTo(v) == 0)
                degree = v.getEdgeList().size();
        }
        return degree;
    }

    public int maximumDegree() {
        int maximumDegree = 0;
        for (Vertex<T> vertex : vertices) {
            if (vertex.edgeList.size() > maximumDegree)
                maximumDegree = vertex.edgeList.size();
        }
        return maximumDegree;
    }

    public int countSelfLoop() {
        int countSelfLoop = 0;
        for (Vertex<T> vertex : vertices) {
            for (Edge<T> edge : vertex.edgeList) {
                if (vertex.compareTo(edge.getTo()) == 0)
                    countSelfLoop++;
            }
        }
        return countSelfLoop / 2;         // Each edge is counted twice.
    }

    public int averageDegree() {
        // This is a measure of density/sparsity
        return (2 * e()) / v();
    }

    public List<Vertex<T>> depthFirstSearch() {
        Vertex<T> startVertex = listVertices.get(0);
        Deque<Vertex<T>> stack = new LinkedList<>();
        List<Vertex<T>> result = new ArrayList<>();
        stack.push(startVertex);

        while(!stack.isEmpty()) {
            Vertex<T> current = stack.pop();

            if (!current.isVisited()) {
                current.setVisited(true);
                result.add(current);

                current.getEdgeList().forEach(stack::push);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Vertex<T> v : vertices)
            builder.append(v.toString()).append("\n");
        return builder.toString();
    }

    public static class Vertex<T extends Object & Comparable<T>> implements Comparable<Vertex<T>> {
        // Instance Variables
        private final T value;
        private boolean visited;
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

        public boolean isVisited() { return false; }

        public void setVisited(boolean value) { visited = value; }

        public List<Edge<T>> getEdgeList() {
            return edgeList;
        }

        public boolean isAdjacentVertex(Vertex<T> vertex) {
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
            builder.append("Vertex = ").append(value).append("\n\t\t");
            Iterator<Edge<T>> iter = edgeList.iterator();

            while (iter.hasNext()) {
                builder.append(iter.next().toString());
                if (iter.hasNext())
                    builder.append("--->");
            }
            return builder.toString();
        }
    }

    public static class Edge<T extends Object & Comparable<T>> implements Comparable<Edge<T>> {
        // Instance Variables
        private final Vertex<T> to;
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

        @Override
        public String toString() {

            return "[" + this.to.value +
                    "][" +
                    this.weight + "]";
        }
    }
}
