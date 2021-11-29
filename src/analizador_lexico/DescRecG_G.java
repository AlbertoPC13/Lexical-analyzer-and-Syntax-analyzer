/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador_lexico;

import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Betuc
 */
public class DescRecG_G extends javax.swing.JInternalFrame {

    /**
     * Creates new form DescRecG_G
     */
    DescRecGram_Gram GG;
    Lista first;
    String follow;
    
    public DescRecG_G() {
        initComponents();
        first = new Lista();
        AFD.ConjAFDs.forEach((a) -> {
            AFD1.addItem(String.valueOf(a.IdAFD));
        });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        AFD1 = new javax.swing.JComboBox<>();
        Cancelar = new javax.swing.JButton();
        Aceptar = new javax.swing.JButton();
        Cadena = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Epsilon = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaFirst = new javax.swing.JTable();
        ListaFirst = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        First = new javax.swing.JButton();
        Limpiar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaNoT = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaFollow = new javax.swing.JTable();
        SimbFollow = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Follow = new javax.swing.JButton();
        Limpiar1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Descenso Recursivo: Gramática de gramáticas");

        jLabel1.setText("Selecciona el AFD");

        Cancelar.setText("Cancelar");
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarActionPerformed(evt);
            }
        });

        Aceptar.setText("Aceptar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingresa la cadena a analizar sintácticamente");

        Epsilon.setText("Epsilon");
        Epsilon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EpsilonActionPerformed(evt);
            }
        });

        jLabel3.setText("Simbolos especiales");

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Simbolo", "Terminal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla);
        if (Tabla.getColumnModel().getColumnCount() > 0) {
            Tabla.getColumnModel().getColumn(1).setHeaderValue("Terminal");
        }

        TablaFirst.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Simbolo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaFirst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaFirstMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaFirst);

        ListaFirst.setEditable(false);

        jLabel4.setText("Lista de simbolos para calculo de FIRST");

        First.setText("FIRST");
        First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstActionPerformed(evt);
            }
        });

        Limpiar.setText("Limpiar");
        Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LimpiarActionPerformed(evt);
            }
        });

        TablaNoT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Simbolo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaNoT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaNoTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaNoT);

        jLabel5.setText("Simbolos no terminales");

        TablaFollow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Simbolo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaFollow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaFollowMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TablaFollow);

        SimbFollow.setEditable(false);

        jLabel6.setText("Simbolo no terminal para calculo de FOLLOW");

        Follow.setText("FOLLOW");
        Follow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FollowActionPerformed(evt);
            }
        });

        Limpiar1.setText("Limpiar");
        Limpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Limpiar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(Cadena, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(Epsilon))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1)
                                        .addComponent(AFD1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(79, 79, 79))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(72, 72, 72))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addComponent(jLabel5)))
                                .addGap(103, 103, 103)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Follow, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Limpiar1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(SimbFollow, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(First, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(ListaFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(114, 114, 114))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(125, 125, 125))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AFD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(Cadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Epsilon)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Aceptar)
                            .addComponent(Cancelar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ListaFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(First)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Limpiar)))
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SimbFollow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Follow)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Limpiar1)))))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
        // TODO add your handling code here:
        super.dispose();
    }//GEN-LAST:event_CancelarActionPerformed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        // TODO add your handling code here:
        int a1 = Integer.parseInt((String) AFD1.getSelectedItem());
        String cadena = Cadena.getText();

        AFD aux = new AFD();

        for (AFD a : AFD.ConjAFDs) {
            if (a.IdAFD == a1) {
                aux = a;
            }
        }

        GG = new DescRecGram_Gram(cadena, aux);

        if (GG.AnalizarGramatica()) {
            JOptionPane.showMessageDialog(null, "La cadena es sintácticamente correcta");
            
            GG.IdentificarTerminales();
            DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
            Object rows[] = new Object[2];
            DefaultTableModel model2 = (DefaultTableModel) TablaNoT.getModel();
            Object rows2[] = new Object[1];
            
            GG.Vn.forEach((String s) -> {
                rows[0] = rows2[0] = s;
                rows[1] = "No terminal";
                model.addRow(rows); 
                model2.addRow(rows2);
            });
            
            GG.Vt.forEach((String s) -> {
                rows[0] = s;
                rows[1] = "Terminal";
                model.addRow(rows);
            });
            
            ListaFirst.setText("");
            SimbFollow.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "La cadena es sintácticamente incorrecta");
        }

    }//GEN-LAST:event_AceptarActionPerformed

    private void EpsilonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EpsilonActionPerformed
        Cadena.setText(Cadena.getText() + '£');
    }//GEN-LAST:event_EpsilonActionPerformed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        if(evt.getClickCount() == 2)
        {
            int indice = Tabla.getSelectedRow();
            DefaultTableModel tm = (DefaultTableModel) Tabla.getModel();
            String simbolo = String.valueOf(tm.getValueAt(indice, 0));
            Boolean terminal = simbolo.contentEquals("Terminal");
            ListaFirst.setText(ListaFirst.getText() + simbolo + " ");
            Nodo dato = new Nodo(simbolo,terminal);
            first.lista.add(dato);
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaFirstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaFirstMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaFirstMouseClicked

    private void FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstActionPerformed
        HashSet<String> Simbolos = GG.First(first);
        DefaultTableModel tm = (DefaultTableModel) TablaFirst.getModel();
        Object rows[] = new Object[1];
        Simbolos.forEach((String s) ->{
            rows[0] = s;
            tm.addRow(rows);
        });
    }//GEN-LAST:event_FirstActionPerformed

    private void LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpiarActionPerformed
        ListaFirst.setText("");
        first.lista.clear();
        DefaultTableModel tm = (DefaultTableModel) TablaFirst.getModel();
        tm.setRowCount(0);
    }//GEN-LAST:event_LimpiarActionPerformed

    private void TablaNoTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaNoTMouseClicked
        if(evt.getClickCount() == 2)
        {
            SimbFollow.setText("");
            int indice = TablaNoT.getSelectedRow();
            DefaultTableModel tm = (DefaultTableModel) TablaNoT.getModel();
            follow = String.valueOf(tm.getValueAt(indice, 0));
            SimbFollow.setText(SimbFollow.getText() + follow + " ");
        }
    }//GEN-LAST:event_TablaNoTMouseClicked

    private void TablaFollowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaFollowMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaFollowMouseClicked

    private void FollowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FollowActionPerformed
        HashSet<String> Simbolos = GG.Follow(follow);
        DefaultTableModel tm = (DefaultTableModel) TablaFollow.getModel();
        Object rows[] = new Object[1];
        Simbolos.forEach((String s) ->{
            rows[0] = s;
            tm.addRow(rows);
        });
    }//GEN-LAST:event_FollowActionPerformed

    private void Limpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Limpiar1ActionPerformed
        SimbFollow.setText("");
        follow = "";
        DefaultTableModel tm = (DefaultTableModel) TablaFollow.getModel();
        tm.setRowCount(0);
    }//GEN-LAST:event_Limpiar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AFD1;
    private javax.swing.JButton Aceptar;
    private javax.swing.JTextField Cadena;
    private javax.swing.JButton Cancelar;
    private javax.swing.JButton Epsilon;
    private javax.swing.JButton First;
    private javax.swing.JButton Follow;
    private javax.swing.JButton Limpiar;
    private javax.swing.JButton Limpiar1;
    private javax.swing.JTextField ListaFirst;
    private javax.swing.JTextField SimbFollow;
    private javax.swing.JTable Tabla;
    private javax.swing.JTable TablaFirst;
    private javax.swing.JTable TablaFollow;
    private javax.swing.JTable TablaNoT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
