/*
 * Copyright 2008-2009 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/*
 * @test %I% %E%
 * @bug 6655001 6670649 6687141
 * @summary Tests that hw acceleration doesn't affect translucent/shaped windows
 * @author Dmitri.Trembovetski@sun.com: area=Graphics
 * @compile -XDignore.symbol.file=true TranslucentShapedFrameTest.java
 * @compile -XDignore.symbol.file=true TSFrame.java
 * @run main/manual/othervm TranslucentShapedFrameTest
 * @run main/manual/othervm -Dsun.java2d.noddraw=true TranslucentShapedFrameTest
 * @run main/manual/othervm -Dsun.java2d.opengl=True TranslucentShapedFrameTest
 */
import com.sun.awt.AWTUtilities;
import static com.sun.awt.AWTUtilities.Translucency.*;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.CountDownLatch;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TranslucentShapedFrameTest extends javax.swing.JFrame {
    Frame testFrame;
    static CountDownLatch done;
    static volatile boolean failed = false;
    GraphicsConfiguration gcToUse = null;

    /**
     * Creates new form TranslucentShapedFrameTest
     */
    public TranslucentShapedFrameTest() {
        // not necessary, but we just look nicer
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        initComponents();
        checkEffects();

        SwingUtilities.updateComponentTreeUI(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        createDisposeGrp = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        transparencySld = new javax.swing.JSlider();
        shapedCb = new javax.swing.JCheckBox();
        nonOpaqueChb = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        passedBtn = new javax.swing.JButton();
        failedBtn = new javax.swing.JButton();
        createFrameBtn = new javax.swing.JToggleButton();
        disposeFrameBtn = new javax.swing.JToggleButton();
        useSwingCb = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TranslucentShapedFrameTest");
        jLabel1.setText("Frame Opacity:");

        transparencySld.setMajorTickSpacing(10);
        transparencySld.setMinorTickSpacing(5);
        transparencySld.setPaintLabels(true);
        transparencySld.setPaintTicks(true);
        transparencySld.setValue(100);
        transparencySld.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                transparencySldStateChanged(evt);
            }
        });

        shapedCb.setText("Shaped Frame");
        shapedCb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        shapedCb.setMargin(new java.awt.Insets(0, 0, 0, 0));
        shapedCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapedCbActionPerformed(evt);
            }
        });

        nonOpaqueChb.setText("Non Opaque Frame");
        nonOpaqueChb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        nonOpaqueChb.setMargin(new java.awt.Insets(0, 0, 0, 0));
        nonOpaqueChb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonOpaqueChbActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Create translucent and/or shaped, or\nnon-opaque frame. Make sure it behaves\ncorrectly (no artifacts left on the screen\nwhen dragging - if dragging is possible).\nClick \"Passed\" if the test behaves correctly,\n\"Falied\" otherwise.");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("Instructions:");

        passedBtn.setBackground(new java.awt.Color(129, 255, 100));
        passedBtn.setText("Passed");
        passedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passedBtnActionPerformed(evt);
            }
        });

        failedBtn.setBackground(java.awt.Color.red);
        failedBtn.setText("Failed");
        failedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                failedBtnActionPerformed(evt);
            }
        });

        createDisposeGrp.add(createFrameBtn);
        createFrameBtn.setText("Create Frame");
        createFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createFrameBtnActionPerformed(evt);
            }
        });

        createDisposeGrp.add(disposeFrameBtn);
        disposeFrameBtn.setSelected(true);
        disposeFrameBtn.setText("Dispose Frame");
        disposeFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disposeFrameBtnActionPerformed(evt);
            }
        });

        useSwingCb.setText("Use JFrame");
        useSwingCb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        useSwingCb.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(transparencySld, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(shapedCb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nonOpaqueChb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(useSwingCb)
                        .addContainerGap(102, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(passedBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(failedBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(createFrameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(disposeFrameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transparencySld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shapedCb)
                    .addComponent(nonOpaqueChb)
                    .addComponent(useSwingCb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(disposeFrameBtn)
                    .addComponent(createFrameBtn))
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passedBtn)
                    .addComponent(failedBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nonOpaqueChbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonOpaqueChbActionPerformed
        if (testFrame != null) {
            // REMIND: this path in the test doesn't work well (test bug)
//            AWTUtilities.setWindowOpaque(testFrame, !nonOpaqueChb.isSelected());
        }
    }//GEN-LAST:event_nonOpaqueChbActionPerformed

    private void shapedCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapedCbActionPerformed
        if (testFrame != null) {
            Shape s = null;
            if (shapedCb.isSelected()) {
                s = new Ellipse2D.Double(0, 0,
                                         testFrame.getWidth(),
                                         testFrame.getHeight());
            }
            AWTUtilities.setWindowShape(testFrame, s);
        }
    }//GEN-LAST:event_shapedCbActionPerformed

    private void transparencySldStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_transparencySldStateChanged
        JSlider source = (JSlider)evt.getSource();
            int transl = transparencySld.getValue();
            if (testFrame != null) {
                AWTUtilities.setWindowOpacity(testFrame, (float)transl/100f);
            }
    }//GEN-LAST:event_transparencySldStateChanged

    private void failedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_failedBtnActionPerformed
        disposeFrameBtnActionPerformed(evt);
        dispose();
        failed = true;
        done.countDown();
    }//GEN-LAST:event_failedBtnActionPerformed

    private void disposeFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disposeFrameBtnActionPerformed
        TSFrame.stopThreads();
        if (testFrame != null) {
            testFrame.dispose();
            testFrame = null;
        }
    }//GEN-LAST:event_disposeFrameBtnActionPerformed

    private void createFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createFrameBtnActionPerformed
        disposeFrameBtnActionPerformed(evt);
        int transl = transparencySld.getValue();
        testFrame = TSFrame.createGui(gcToUse,
                useSwingCb.isSelected(), shapedCb.isSelected(),
                (transl < 100), nonOpaqueChb.isSelected(),
                (float)transl/100f);
    }//GEN-LAST:event_createFrameBtnActionPerformed

    private void passedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passedBtnActionPerformed
        disposeFrameBtnActionPerformed(evt);
        dispose();
        done.countDown();
    }//GEN-LAST:event_passedBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        done = new CountDownLatch(1);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TranslucentShapedFrameTest().setVisible(true);
            }
        });
        try {
            done.await();
        } catch (InterruptedException ex) {}
        if (failed) {
            throw new RuntimeException("Test FAILED");
        }
        System.out.println("Test PASSED");
    }

    private void checkEffects() {
        if (!AWTUtilities.isTranslucencySupported(PERPIXEL_TRANSPARENT)) {
            shapedCb.setEnabled(false);
        }

        if (!AWTUtilities.isTranslucencySupported(TRANSLUCENT)) {
            transparencySld.setEnabled(false);
        }

        GraphicsConfiguration gc = null;
        if (AWTUtilities.isTranslucencySupported(PERPIXEL_TRANSLUCENT)) {
            gc = findGraphicsConfig();
            if (gc == null) {
                nonOpaqueChb.setEnabled(false);
            }
        }

        gcToUse = gc;
    }

    private GraphicsConfiguration findGraphicsConfig() {
        GraphicsDevice gd =
            GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice();
        GraphicsConfiguration gcs[] = gd.getConfigurations();
        for (GraphicsConfiguration gc : gcs) {
            if (AWTUtilities.isTranslucencyCapable(gc)) {
                return gc;
            }
        }
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup createDisposeGrp;
    private javax.swing.JToggleButton createFrameBtn;
    private javax.swing.JToggleButton disposeFrameBtn;
    private javax.swing.JButton failedBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JCheckBox nonOpaqueChb;
    private javax.swing.JButton passedBtn;
    private javax.swing.JCheckBox shapedCb;
    private javax.swing.JSlider transparencySld;
    private javax.swing.JCheckBox useSwingCb;
    // End of variables declaration//GEN-END:variables

}
