package com.sterilized.staffManagement;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

public interface Page extends ActionListener{
    
    public void enterPage(JFrame frame);
    public void exitPage();
}
