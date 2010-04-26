package org.geolatte.graph;


/**
 * <code>Nodal</code> captures minimum requirements on the myNodes
 * in a <code>Graph</code>.
 * <p/>
 * <p><em>Implementations should be thread-safe</em>. The <code>getData()</code>-method
 * is intended to be used concurrently by threads executing some graph algorithm.</p>
 *
 * @author Karel Maesen, Geovise BVBA
 */
public interface Nodal<D> {

    /**
     * Returns The X-coordinate of the internalNode
     *
     * @return X-coordinate
     */
    public int getX();

    /**
     * Returns the Y-coordinate of the internalNode
     *
     * @return Y-coordinate
     */
    public int getY();


}
