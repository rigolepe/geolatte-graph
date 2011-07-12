/*
 * This file is part of the GeoLatte project.
 *
 *     GeoLatte is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     GeoLatte is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with GeoLatte.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2010 - 2011 and Ownership of code is shared by:
 * Qmino bvba - Esperantolaan 4 - 3001 Heverlee  (http://www.qmino.com)
 * Geovise bvba - Generaal Eisenhowerlei 9 - 2140 Antwerpen (http://www.geovise.com)
 */

package org.geolatte.graph.algorithms;

import org.geolatte.data.PairNode;
import org.geolatte.data.PairingHeap;
import org.geolatte.graph.InternalNode;
import org.geolatte.graph.PredGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements a Min-PriorityQueue for PredGraphs in terms of a Pairing Heap.
 *
 * @param <V>
 * @author Karel Maesen, Geovise BVBA
 */
public class PMinQueue<V> {

    private final PairingHeap<Element<V>> heap = new PairingHeap<Element<V>>();
    private final Map<InternalNode<V>, PairNode<Element<V>>> index = new HashMap<InternalNode<V>, PairNode<Element<V>>>();

    /**
     * Creates an instance of PMinQueue.
     */
    protected PMinQueue() {
    }

    /**
     * Adds a the given value with the given priority (key).
     *
     * @param value The value to add.
     * @param key   The priority.
     */
    public void add(PredGraph<V> value, float key) {
        PairNode<Element<V>> pn = heap.insert(new Element<V>(value, key));
        this.index.put(value.getInternalNode(), pn);
    }

    /**
     * Removes and returns the element from the queue with the smallest key (lowest priority).
     *
     * @return The element with the smallest key.
     */
    public PredGraph<V> extractMin() {
        PredGraph<V> val = heap.deleteMin().value;
        this.index.remove(val.getInternalNode());
        return val;
    }

    /**
     * Gets the predecessor graph associated with the given node.
     *
     * @param node The node.
     * @return A predecessor graph.
     */
    public PredGraph<V> get(InternalNode<V> node) {
        PairNode<Element<V>> pNode = this.index.get(node);
        if (pNode == null) {
            return null;
        }
        return pNode.getElement().value;
    }

    /**
     * Gets a value indicating whether the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    public void update(PredGraph<V> value, float r) {
        PairNode<Element<V>> node = this.index.get(value.getInternalNode());
        if (node == null) {
            throw new RuntimeException("Node not in Pairing Heap.");
        }
        Element<V> newElement = new Element<V>(value, r);
        this.heap.decreaseKey(node, newElement);
    }

    static class Element<V> implements Comparable<Element<V>> {
        private final Float key;
        private final PredGraph<V> value;

        Element(PredGraph<V> value, Float key) {
            this.key = key;
            this.value = value;
        }


        public int compareTo(Element<V> o) {
            return this.key.compareTo(o.key);
        }
    }

}

