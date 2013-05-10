/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sonkd.project2.soict;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Hashtable;
import java.util.Set;
import javax.swing.JApplet;
import javax.swing.JFrame;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;
import sonkd.project2.soict.InputGraph.Node;

/**
 *
 * @author KimDinh
 */
public class GraphDemo extends JApplet {
    //~ Static fields/initializers ---------------------------------------------

    private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    public static int height = 600;
    public static int width = 400;
    private static final Dimension DEFAULT_SIZE = new Dimension(height, width);
    public static UndirectedGraph<Node, DefaultEdge> g1 = new InitGraph().getGraph();
    private mxGraph graph;
    public Object[] V = new Object[1000];
    //~ Instance fields --------------------------------------------------------
    //
    private JGraphModelAdapter<Integer, DefaultEdge> jgAdapter;

    //~ Methods ----------------------------------------------------------------
    /**
     * An alternative starting point for this demo, to also allow running this
     * applet as an application.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
        GraphDemo applet = new GraphDemo();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("Unnion-Closed-Set: Graph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void init() {
        // create a JGraphT graph
        ListenableUndirectedGraph<Integer, DefaultEdge> g =
                new ListenableUndirectedGraph<Integer, DefaultEdge>(
                DefaultEdge.class);

        // create a visualization using JGraph, via an adapter
        jgAdapter = new JGraphModelAdapter<>(g);

        JGraph jgraph = new JGraph(jgAdapter);

        adjustDisplaySettings(jgraph);
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);

        Set<DefaultEdge> e = g1.edgeSet();
        Set<Node> v = g1.vertexSet();

        Integer i = 0;
        for (Node x : v) {
            x.index = i + 1;
            g.addVertex(x.index);
            i++;
        }

        for (Node sv : g1.vertexSet()) {
            for (Node tv : g1.vertexSet()) {
                if (g1.containsEdge(sv, tv)) {
                    g.addEdge(sv.index, tv.index);
                    System.out.print("(" + sv.index + " ; " + tv.index + ")" + ", ");
                }
            }
            System.out.println();
        }

        // position vertices nicely within JGraph component

        // Set position vertices
        setPositionVertices(g);
    }

    public void cellStyle(String s) {
        mxStylesheet stylesheet = graph.getStylesheet();

        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        stylesheet.putCellStyle(s, style);
        graph.setStylesheet(stylesheet);
    }

    public void setPositionVertices(ListenableUndirectedGraph<Integer, DefaultEdge> g) {

        int counter = 0;
        for (Integer vertex : g.vertexSet()) {
            if (counter % 2 == 0) {
                positionVertexAt(vertex, width / 2 + 150, height / 2 - 250 + 30 * counter);
            } else {
                positionVertexAt(vertex, width / 2 - 150, height / 2 - 250 + 30 * counter);
            }
            counter++;
        }
    }

    private void adjustDisplaySettings(JGraph jg) {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter("bgcolor");
        } catch (Exception e) {
        }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }

    @SuppressWarnings("unchecked") // FIXME hb 28-nov-05: See FIXME below
    private void positionVertexAt(Integer vertex, int x, int y) {
        graph.insertVertex(vertex, null, vertex, x, y, 80, 30, "ROUNDED;strokeColor=red;fillColor=green");
        //cellStyle(vertex+"");
//        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
//        AttributeMap attr = cell.getAttributes();
//        Point p = new Point();
//        p.x = x;
//        p.y = y;
//        attr.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        //attr.put(mxConstants, attr);
//        Rectangle2D bounds = GraphConstants.getBounds(attr);
//        Rectangle2D newBounds =
//                new Rectangle2D.Double(
//                x,
//                y,
//                bounds.getWidth(),
//                bounds.getHeight());
//
//        GraphConstants.setBounds(attr, newBounds);
        // TODO: Clean up generics once JGraph goes generic
//        AttributeMap cellAttr = new AttributeMap();
//        cellAttr.put(cell, attr);
//        jgAdapter.edit(cellAttr, null, null, null);
    }
}

// End JGraphAdapterDemo.java
