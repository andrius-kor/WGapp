package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import client.WorkGroupClient;

public class ChatTextPane extends JFrame implements MessageListener, Runnable{

  /**
   * 
   */
  private static final long serialVersionUID = 2905994669319142344L;
  private JPanel contentPane;
  private JTextArea textPane = new JTextArea();
  private JTextField textField;
  private WorkGroupClient client;
  
  public ChatTextPane(WorkGroupClient client) {
    this.client = client;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    GridBagLayout gbl_contentPane = new GridBagLayout();
    gbl_contentPane.columnWidths = new int[]{0, 0};
    gbl_contentPane.rowHeights = new int[]{0, 0, 0};
    gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
    contentPane.setLayout(gbl_contentPane);
    
    
    GridBagConstraints gbc_textPane = new GridBagConstraints();
    gbc_textPane.insets = new Insets(0, 0, 5, 0);
    gbc_textPane.fill = GridBagConstraints.BOTH;
    gbc_textPane.gridx = 0;
    gbc_textPane.gridy = 0;
    contentPane.add(textPane, gbc_textPane);
    
    textField = new JTextField();
    GridBagConstraints gbc_textField = new GridBagConstraints();
    gbc_textField.fill = GridBagConstraints.HORIZONTAL;
    gbc_textField.gridx = 0;
    gbc_textField.gridy = 1;
    contentPane.add(textField, gbc_textField);
    textField.setColumns(10);
    
    textField.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent arg0) {
        client.sendMessage(textField.getText());
        
      }
    });
  }

  @Override
  public void displayMesssage(String msg) {
   textPane.append(msg + "\n");
    
  }

  @Override
  public void run() {
    try {
      this.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
