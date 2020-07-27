package de.nordlbit.prost.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import de.nordlbit.prost.bd.Aufgabe;
import de.nordlbit.prost.bd.Ausloesemuster;
import de.nordlbit.prost.bd.AusloesemusterHandler;
import de.nordlbit.prost.bd.Ereignis;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.Vorgang;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author frankew
 */
public class AusloesemusterTreeModel implements TreeModel {

    DefaultTreeModel innerModel;
    DefaultMutableTreeNode ivRootNode = new DefaultMutableTreeNode("Auslösemuster", true);
    DefaultMutableTreeNode ivEmptyMuster;
    Mandant ivMandant;
    Vorgang ivVorgang;

    public AusloesemusterTreeModel(Mandant pvMandant) {
        super();
        this.ivMandant = pvMandant;
        innerModel = new DefaultTreeModel(ivRootNode);
        if (this.ivMandant != null) {
            this.fillTree();
        }
    }

    @Override
    public Object getRoot() {
        return innerModel.getRoot();
    }

    @Override
    public Object getChild(Object parm1, int parm2) {
        return innerModel.getChild(parm1, parm2);
    }

    @Override
    public int getChildCount(Object parm1) {
        return innerModel.getChildCount(parm1);
    }

    @Override
    public boolean isLeaf(Object parm1) {
        return innerModel.isLeaf(parm1);
    }

    @Override
    public void valueForPathChanged(TreePath parm1, Object parm2) {
        innerModel.valueForPathChanged(parm1, parm2);
    }

    @Override
    public int getIndexOfChild(Object parm1, Object parm2) {
        return innerModel.getIndexOfChild(parm1, parm2);
    }

    @Override
    public void addTreeModelListener(TreeModelListener parm1) {
        innerModel.addTreeModelListener(parm1);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener parm1) {
        innerModel.removeTreeModelListener(parm1);
    }

    private void fillTree() {
        HashMap<String, Ausloesemuster> lvAusloesemuster = (HashMap<String, Ausloesemuster>) AusloesemusterHandler.getMuster(this.ivMandant);

        ArrayList<Ausloesemuster> lvList = new ArrayList<Ausloesemuster>();
        lvList.addAll(lvAusloesemuster.values());
        
        Collections.sort(lvList);
        
        for (Ausloesemuster pvMuster : lvList) {
            this.addAusloesemuster(pvMuster);
        }

    }

    public DefaultMutableTreeNode addAusloesemuster(Ausloesemuster pvMuster) {
        DefaultMutableTreeNode lvMusterNode = new DefaultMutableTreeNode(pvMuster, true);
        this.ivRootNode.add(lvMusterNode);
        this.refreshAusloesemusterTreeNodeInhalte(pvMuster, lvMusterNode);
        if(pvMuster.isEmpty()){
            this.setEmptyMuster(lvMusterNode);
        }
        return lvMusterNode;
    }
    
    public DefaultMutableTreeNode refreshAusloesemusterTreeNodeInhalte(Ausloesemuster pvMuster,  DefaultMutableTreeNode pvMusterNode) {
        pvMusterNode.removeAllChildren();
        for (Ereignis pvEreignis : pvMuster.getEreignisse()) {
            DefaultMutableTreeNode lvEreignisNode = new DefaultMutableTreeNode(pvEreignis, false);
            pvMusterNode.add(lvEreignisNode);
        }
        DefaultMutableTreeNode lvAufgabenNode = new DefaultMutableTreeNode("Aufgaben", true);
        pvMusterNode.add(lvAufgabenNode);
        for (Aufgabe pvAufgabe : pvMuster.getMassnahmen()) {
            DefaultMutableTreeNode lvAufgabeNode = new DefaultMutableTreeNode(pvAufgabe, false);
            lvAufgabenNode.add(lvAufgabeNode);
        }
        
        return pvMusterNode;
        
    }
    
    public TreeNode[] getPathToRoot(TreeNode pvNode)  {
        return this.innerModel.getPathToRoot(pvNode);
    }  
    
    public void setEmptyMuster(DefaultMutableTreeNode pvNode){
        this.ivEmptyMuster = pvNode;
    }
    
    public DefaultMutableTreeNode getEmptyMuster(){
        return this.ivEmptyMuster;
    }
    
}
