package com.github.ikhideifidon.Graph_Edge_List_Representation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Graph<T extends Object & Comparable<T>> {
    // Instance Variables: Graph Definition
    private final List<Vertex<T>> allVertices = new ArrayList<>();
    private final List<Edge<T>> allEdges = new ArrayList<>();

    public enum TYPE {
        DIRECTED, UNDIRECTED
    }

    /** Default type is undirected **/
    private TYPE type = TYPE.UNDIRECTED;

    // Constructors
    public Graph() { }

    public Graph(TYPE type) {
        this();
        this.type = type;
    }

    /** Creates a Graph from the vertices and edges. This defaults to an undirected Graph **/
    public Graph(Collection<Vertex<T>> vertices, Collection<Edge<T>> edges) {
        this(TYPE.UNDIRECTED, vertices, edges);
    }

    /** Creates a Graph from the vertices and edges. **/
    public Graph(TYPE type, Collection<Vertex<T>> vertices, Collection<Edge<T>> edges) {
        this.type = type;

        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Edge<T> edge : edges) {
            final Vertex<T> from = edge.from;
            final Vertex<T> to = edge.to;

            if (!this.allVertices.contains(from) || this.allVertices.contains(to))
                continue;

            from.addEdge(edge);
            if (this.type == TYPE.UNDIRECTED) {
                Edge<T> reciprocal = new Edge<>(from, to, edge.weight);
                to.addEdge(reciprocal);
                this.allEdges.add(reciprocal);
            }
        }
    }

    /** Deep Copies **/
    public Graph(Graph<T> graph) {
        type = graph.type;

        // Copy the vertices which also copies the edges
        for (Vertex<T> vertex : graph.getAllVertices())
            this.allVertices.add(new Vertex<>(vertex));

        for (Vertex<T> v : this.getAllVertices()) {
            for (Edge<T> e : v.getEdges()) {
                this.allEdges.add(new Edge<>(e));
            }
        }
    }

    // Setters and Getters
    public List<Vertex<T>> getAllVertices() {
        return allVertices;
    }

    public List<Edge<T>> getAllEdges() {
        return allEdges;
    }

    public TYPE getType() {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Vertex<T> v : allVertices)
            builder.append(v.toString());
        return builder.toString();
    }

    public static class Vertex<T extends Object & Comparable<T>> implements Comparable<Vertex<T>> {
        // Instance Variables
        private final T value;
        private final List<Edge<T>> edges;


        // Constructor
        public Vertex(T value) {
            this.value = value;
            edges = new ArrayList<>();
        }

        // deep copy the vertex
        public Vertex(Vertex<T> vertex) {
            this(vertex.value);

            this.edges.addAll(vertex.edges);
        }

        public T getValue() {
            return value;
        }

        public List<Edge<T>> getEdges() {
            return edges;
        }

        public void addEdge(Edge<T> edge) {
            edges.add(edge);
        }

        public boolean adjacentVertex(Vertex<T> vertex) {
            for (Edge<T> edge : edges) {
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
            if (this.edges.size() > vertex.edges.size())
                return 1;

            if (this.edges.size() < vertex.edges.size())
                return -1;

            final Iterator<Edge<T>> iter1 = this.edges.iterator();
            final Iterator<Edge<T>> iter2 = vertex.edges.iterator();

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

            final boolean equalSize = this.edges.size() == vertex.edges.size();
            if (!equalSize)
                return false;

            final Iterator<Edge<T>> iter1 = this.edges.iterator();
            //noinspection unchecked
            final Iterator<Edge<T>> iter2 = vertex.edges.iterator();

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
            result = 31 * result + Integer.hashCode(this.edges.size());
            for (Edge<T> edge : edges)
                result = 31 * result + Integer.hashCode(edge.weight);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Value=").append(value).append("\n");
            for (Edge<T> e : edges)
                builder.append("\t").append(e.toString());
            return builder.toString();
        }
    }

    public static class Edge<T extends Object & Comparable<T>> implements Comparable<Edge<T>> {
        // Instance Variables
        private Vertex<T> from = null;
        private Vertex<T> to = null;
        private int weight = 0;

        // Constructor
        // Unweighted
        public Edge(Vertex<T> from, Vertex<T> to) {
            if (from == null || to == null)
                throw new NullPointerException("Both 'to' and 'from' vertices cannot be null.");
            this.from = from;
            this.to = to;
        }

        // weighted
        public Edge(Vertex<T> from, Vertex<T> to, int weight) {
            this(from, to);
            this.weight = weight;
        }

        // Deep Copy
        public Edge(Edge<T> edge) {
            this(edge.from, edge.to, edge.weight);
        }

        public Vertex<T> getFrom() {
            return this.from;
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

            // If weight are equal, try 'from' vertex
            final int from = this.from.compareTo(edge.from);
            if (from != 0)
                return from;

            // if from are equal, try 'to' vertex
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

            final boolean equalFrom = this.from == edge.from;
            if (!equalFrom)
                return false;

            return this.to == edge.to;
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(this.weight);
            result = 31 * result + this.from.hashCode();
            result = 31 * result + this.to.hashCode();
            return result;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[ ").append(from.value).append("(").append("]").append(" -> ")
                    .append("[ ").append(to.value).append("]").append(" = ").append(weight).append("\n");
            return builder.toString();
        }
    }
}
