
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.net.URI;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fysh
 */
public class OpenPortChecker extends javax.swing.JFrame {

    /**
     * Creates new form OpenPortChecker
     */

    public void progressBar(){
        //This section will be used to create a progress bar when scanning ports
    
    }
    
    public void checkLocalIP(){
        
        try{
            //This section gets the local IP address and subnet mask
            String address = new String (Inet4Address.getLocalHost().getHostAddress());
            String hostname = new String (Inet4Address.getLocalHost().getHostName());
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
            System.out.println(Inet4Address.getLocalHost().getHostName());
            ovdcheckIPTextArea.setText("Your local network information is below: \n");
            ovdcheckIPTextArea.append("IP address = " + address +"\n");
            ovdcheckIPTextArea.append("Hostname = " + hostname +"\n");
            xpncheckIPTextArea.setText("Your local network information is below: \n");
            xpncheckIPTextArea.append("IP address = " + address +"\n");
            xpncheckIPTextArea.append("Hostname = " + hostname +"\n");
            InetAddress localHost = Inet4Address.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
            ovdcheckIPTextArea.append("Subnet Mask = /"+(networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength()) + "\n");
            xpncheckIPTextArea.append("Subnet Mask = /"+(networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength()) + "\n");                               
        }
        catch (UnknownHostException|SocketException e){
            System.out.println(e);
        }              
    }
    
    public void internetAccess(){
        //See if the loacl PC can access the Internet
        //Maybe make this a button instead as it slows the opening of the application
        Socket google;
        JFrame frame = new JFrame("test");
        frame.setAlwaysOnTop(true);
        try {
            google = new Socket("google.com", 80);
            google.close();
            System.out.println("successfully connected to the outside world");
            JOptionPane.showMessageDialog(frame, "This system can successfully connect to the Internet");
        }
        catch (IOException e1){
            System.out.println(e1);
            System.out.println("Google.com");
            JOptionPane.showMessageDialog(frame, "This system does not have acess to the Internet");
            //ovdcheckIPTextArea.append("Internet Access = Not Accessible");
            //xpncheckIPTextArea.append("Internet Access = Not Accessible");
        }          
    }
    
    public void groupButtons(){        
            ButtonGroup xpngroup = new ButtonGroup();
            xpngroup.add(xpnstudioRadioButton);
            xpngroup.add(xpngatewayRadioButton);
            xpngroup.add(xpnincoderRadioButton);
            ButtonGroup ovdgroup = new ButtonGroup();
            ovdgroup.add(priovdRadioButton);
            ovdgroup.add(redovdRadioButton);
    }
    
    public void testSequenceOVD(){
        Socket socket;
        //ovdProgressBar.setValue(0);
        //ovdProgressBar.setStringPainted(true); 
        int[] ovdPorts = {80, 10540, 10541, 18008};
        JFrame frame = new JFrame("test");
        frame.setAlwaysOnTop(true);
        if (priovdRadioButton.isSelected()){
            int count = 0; 
            ovdresultsTextArea.append("Checking for open ports on " +priovdIPTextField.getText() +", the primary OverDrive server... \n");
            while (count < ovdPorts.length){
                    if (priovdIPTextField.getText().isEmpty()){
                       JOptionPane.showMessageDialog(frame, "IP address cannot be blank");
                       break;
                    }                   
                try {
                    socket = new Socket(priovdIPTextField.getText(), ovdPorts[count]);
                    socket.close();
                    System.out.println("success!");
                    ovdresultsTextArea.append("Success: The port, "+(ovdPorts[count]) +", is open and the service is listening \n");
                    //ovdProgressBar.getPercentComplete((ovdPorts.length));
                }
                catch (IOException e1){
                    System.out.println(e1);
                    System.out.println(ovdPorts[count]);
                    ovdresultsTextArea.append("Failure: The port, " +(ovdPorts[count]) + ", is either closed or the service is not listening \n");
                }                     
                count++;
                
                
                
            }
            ovdresultsTextArea.append("Test Complete \n");
        }
        if (redovdRadioButton.isSelected()){
            int count = 0; 
            ovdresultsTextArea.append("Checking for open ports on " +redovdIPTextField.getText() +", the primary OverDrive server... \n");
            while (count < ovdPorts.length){
                    if (redovdIPTextField.getText().isEmpty()){
                       JOptionPane.showMessageDialog(frame, "IP address cannot be blank");
                       break;
                    }                   
                try {
                    socket = new Socket(redovdIPTextField.getText(), ovdPorts[count]);
                    socket.close();
                    System.out.println("success!");
                    ovdresultsTextArea.append("Success: The port, "+(ovdPorts[count]) +", is open and the service is listening \n");
                }
                catch (IOException e1){
                    System.out.println(e1);
                    System.out.println(ovdPorts[count]);
                    ovdresultsTextArea.append("Failure: The port, " +(ovdPorts[count]) + ", is either closed or the service is not listening \n");
                }                     
                count++;
            }
                ovdresultsTextArea.append("Test Complete \n");
        }        
    }

    public void testSequenceXPN() { 
        Socket socket;
        int[] xpnstudioPorts = {80, 7788, 8001, 8181, 8888, 9001, 9090};
        int[] xpngatewayPorts = {10540, 10541, 10550};
        int[] xpnincoderPorts = {9595, 9797};
        //Use this frame for warnings - always on top
        JFrame frame = new JFrame("test");
        frame.setAlwaysOnTop(true);
        if (xpnstudioRadioButton.isSelected()){
            int count = 0; 
            xpnresultsTextArea.append("Checking for open ports on " +studioIPTextField.getText() +", the XPN Studio PC... \n");
            while (count < xpnstudioPorts.length){
                    if (studioIPTextField.getText().isEmpty()){
                       JOptionPane.showMessageDialog(frame, "IP address cannot be blank");
                       break;
                    }                   
                try {
                    socket = new Socket(studioIPTextField.getText(), xpnstudioPorts[count]);
                    socket.close();
                    System.out.println("success!");
                    xpnresultsTextArea.append("Success: The port, "+(xpnstudioPorts[count]) +", is open and the service is listening \n");

                }
                catch (IOException e1){
                    System.out.println(e1);
                    System.out.println(xpnstudioPorts[count]);
                    xpnresultsTextArea.append("Failure: The port, " +(xpnstudioPorts[count]) + ", is either closed or the service is not listening \n");
                }                     

                count++;
            }
                xpnresultsTextArea.append("Test Complete \n");

        }
        else if (xpngatewayRadioButton.isSelected()){
            int count = 0; 
            xpnresultsTextArea.setText("Checking for open ports on " +xpngatewayIPTextField.getText() +", the XPN Gateway PC... \n");
            while (count < xpngatewayPorts.length){
                    if (xpngatewayIPTextField.getText().isEmpty()){
                       JOptionPane.showMessageDialog(frame, "IP address cannot be blank");
                       break;
                    }                 
                try {
                    socket = new Socket(xpngatewayIPTextField.getText(), xpngatewayPorts[count]);
                    socket.close();
                    System.out.println("success!");
                    xpnresultsTextArea.append("Success: The port, "+(xpngatewayPorts[count]) +", is open and the service is listening \n");

                }
                catch (IOException e1){
                    System.out.println(e1);
                    System.out.println(xpngatewayPorts[count]);
                    xpnresultsTextArea.append("Failure: The port, " +(xpngatewayPorts[count]) + ", is either closed or the service is not listening \n");
                }                     

                count++;
            }
                xpnresultsTextArea.append("Test Complete \n");
        }
        else if (xpnincoderRadioButton.isSelected()){
            int count = 0; 
            xpnresultsTextArea.setText("Checking for open ports on " +incoderIPTextField.getText() +", the XPN Incoder PC... \n");
              
            while (count < xpnincoderPorts.length){
                    if (incoderIPTextField.getText().isEmpty()){
                       JOptionPane.showMessageDialog(frame, "IP address cannot be blank");
                       break;
                    }              
                try {
                    socket = new Socket(incoderIPTextField.getText(), xpnincoderPorts[count]);
                    socket.close();
                    System.out.println("success!");
                    xpnresultsTextArea.append("Success: The port, "+(xpnincoderPorts[count]) +", is open and the service is listening \n");

                }
                catch (IOException e1){
                    System.out.println(e1);
                    System.out.println(xpnincoderPorts[count]);
                    xpnresultsTextArea.append("Failure: The port, " +(xpnincoderPorts[count]) + ", is either closed or the service is not listening \n");
                }                     

                count++;
            }
            xpnresultsTextArea.append("Test Complete \n");
        }
    }
    
    public void defaultPorts(){
        ovddefportsTextArea.setText("Default OverDrive ports:\n10540 \n10541 \n18008 \n");
        xpndefportsTextArea.setText("Default XPression ports:\n7788\n8001\n8181\n8888\n9001\n9090\n");
        xpndefportsTextArea.append("Default XPression Gateway ports:\n10540\n10541\n10550\n");
        xpndefportsTextArea.append("Default XPression Incoder ports:\n9595\n9797");
    }
    
    public OpenPortChecker() {
        initComponents();
        //Use this frame for warnings - always on top
        JFrame frame = new JFrame("test");
        frame.setAlwaysOnTop(true);
        //group the radio buttons so only 1 is selected at a time
        groupButtons();
        //get Local IP address information
        checkLocalIP();
        //Show the default ports in each text area before testing the ports
        defaultPorts();
        
        //Setup the EXIT button to close the app
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }            
        });   
        
        //This code is for the submit button actions
        internetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                internetAccess();
            }                
        });
        testButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                if (toolsPane.getSelectedComponent().equals(overdrivePanel)){
                    
                    testSequenceOVD();
                }
                    else { 
                        if (toolsPane.getSelectedComponent().equals(xpressionPanel)){
                        testSequenceXPN();                      
                        }                        
                }   
            }                
        });
        
        priodUIButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                try {
                    String line = (priovdIPTextField.getText());
                    if (priovdIPTextField.getText().isEmpty()){
                       //System.out.println("IP address cannot be empty");
                       JOptionPane.showMessageDialog(frame, "IP address cannot be blank");
                    }                    
                    String ipAddress = line.substring(line.indexOf(":") + 1).trim(),
                    routerURL = String.format("http://%s", ipAddress);
                    // opening router setup in browser
                    Desktop.getDesktop().browse(new URI(routerURL));
                    System.out.println(line);
                }
                catch (Exception e1) {
                    System.out.println(e1);                   
                }   
            }                
        });
        
        redodUIButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                try {
                    String line = (redovdIPTextField.getText());
                    if (redovdIPTextField.getText().isEmpty()){
                       //System.out.println("IP address cannot be empty");
                       JOptionPane.showMessageDialog(frame, "IP address cannot be blank");
                    }
                    String ipAddress = line.substring(line.indexOf(":") + 1).trim(),
                    routerURL = String.format("http://%s", ipAddress);
                    // opening router setup in browser
                    Desktop.getDesktop().browse(new URI(routerURL));
                    System.out.println(line);
                }
                catch (Exception e1){
                    System.out.println(e1);
                }
            }                
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane8 = new javax.swing.JScrollPane();
        ovddefportsTextArea1 = new javax.swing.JTextArea();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        testButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        internetButton = new javax.swing.JButton();
        toolsPane = new javax.swing.JTabbedPane();
        overdrivePanel = new javax.swing.JPanel();
        priodsrvLabel = new javax.swing.JLabel();
        priovdIPTextField = new javax.swing.JTextField();
        priovdRadioButton = new javax.swing.JRadioButton();
        priodsrvLabel1 = new javax.swing.JLabel();
        redovdIPTextField = new javax.swing.JTextField();
        redovdRadioButton = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ovdresultsTextArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        ovdcheckIPTextArea = new javax.swing.JTextArea();
        priodUIButton = new javax.swing.JButton();
        redodUIButton = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        ovddefportsTextArea = new javax.swing.JTextArea();
        ovdProgressBar = new javax.swing.JProgressBar();
        xpressionPanel = new javax.swing.JPanel();
        studioipLabel = new javax.swing.JLabel();
        studioIPTextField = new javax.swing.JTextField();
        xpnstudioRadioButton = new javax.swing.JRadioButton();
        xpngatewayipLabel = new javax.swing.JLabel();
        xpngatewayIPTextField = new javax.swing.JTextField();
        xpngatewayRadioButton = new javax.swing.JRadioButton();
        incoderipLabel = new javax.swing.JLabel();
        incoderIPTextField = new javax.swing.JTextField();
        xpnincoderRadioButton = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        xpncheckIPTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        xpnresultsTextArea = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        xpndefportsTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();

        ovddefportsTextArea1.setColumns(20);
        ovddefportsTextArea1.setRows(5);
        jScrollPane8.setViewportView(ovddefportsTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Open Port Checker Utility");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        testButton.setText("TEST FOR OPEN PORTS");

        exitButton.setText("EXIT");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        internetButton.setText("TEST FOR INTERNET ACCESS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(testButton)
                .addGap(18, 18, 18)
                .addComponent(internetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testButton)
                    .addComponent(exitButton)
                    .addComponent(internetButton))
                .addGap(183, 183, 183))
        );

        toolsPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        priodsrvLabel.setText("Primary OverDrive Server IP:");

        priovdRadioButton.setText("Check");
        priovdRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priovdRadioButtonActionPerformed(evt);
            }
        });

        priodsrvLabel1.setText("Redundant OverDrive Server IP:");

        redovdRadioButton.setText("Check");
        redovdRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redovdRadioButtonActionPerformed(evt);
            }
        });

        ovdresultsTextArea.setEditable(false);
        ovdresultsTextArea.setColumns(20);
        ovdresultsTextArea.setRows(5);
        ovdresultsTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane3.setViewportView(ovdresultsTextArea);

        ovdcheckIPTextArea.setEditable(false);
        ovdcheckIPTextArea.setColumns(20);
        ovdcheckIPTextArea.setRows(5);
        ovdcheckIPTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane4.setViewportView(ovdcheckIPTextArea);

        priodUIButton.setText("Check Primary OD WebUI ");

        redodUIButton.setText("Check Redundant OD WebUI ");

        ovddefportsTextArea.setEditable(false);
        ovddefportsTextArea.setColumns(20);
        ovddefportsTextArea.setRows(5);
        ovddefportsTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane7.setViewportView(ovddefportsTextArea);

        javax.swing.GroupLayout overdrivePanelLayout = new javax.swing.GroupLayout(overdrivePanel);
        overdrivePanel.setLayout(overdrivePanelLayout);
        overdrivePanelLayout.setHorizontalGroup(
            overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overdrivePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(overdrivePanelLayout.createSequentialGroup()
                        .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(overdrivePanelLayout.createSequentialGroup()
                                .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(priodsrvLabel1)
                                    .addComponent(priodsrvLabel))
                                .addGap(18, 18, 18)
                                .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(redovdIPTextField)
                                    .addComponent(priovdIPTextField))
                                .addGap(18, 18, 18)
                                .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(redovdRadioButton)
                                    .addComponent(priovdRadioButton)))
                            .addGroup(overdrivePanelLayout.createSequentialGroup()
                                .addComponent(priodUIButton)
                                .addGap(18, 18, 18)
                                .addComponent(redodUIButton)
                                .addGap(0, 29, Short.MAX_VALUE)))
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(overdrivePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7)))
                .addContainerGap())
            .addGroup(overdrivePanelLayout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(ovdProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        overdrivePanelLayout.setVerticalGroup(
            overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overdrivePanelLayout.createSequentialGroup()
                .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(overdrivePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priodsrvLabel)
                            .addComponent(priovdIPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(priovdRadioButton))
                        .addGap(18, 18, 18)
                        .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priodsrvLabel1)
                            .addComponent(redovdIPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(redovdRadioButton))
                        .addGap(30, 30, 30)
                        .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priodUIButton)
                            .addComponent(redodUIButton)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(overdrivePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(ovdProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        redovdIPTextField.getAccessibleContext().setAccessibleName("");

        toolsPane.addTab("OVERDRIVE", overdrivePanel);

        studioipLabel.setText("XPression Studio PC IP:");

        xpnstudioRadioButton.setText("Check");

        xpngatewayipLabel.setText("XPression Gateway IP:");

        xpngatewayRadioButton.setText("Check");
        xpngatewayRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xpngatewayRadioButtonActionPerformed(evt);
            }
        });

        incoderipLabel.setText("XPression Incoder IP:");

        xpnincoderRadioButton.setText("Check");
        xpnincoderRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xpnincoderRadioButtonActionPerformed(evt);
            }
        });

        xpncheckIPTextArea.setEditable(false);
        xpncheckIPTextArea.setColumns(20);
        xpncheckIPTextArea.setRows(5);
        xpncheckIPTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setViewportView(xpncheckIPTextArea);

        xpnresultsTextArea.setEditable(false);
        xpnresultsTextArea.setColumns(20);
        xpnresultsTextArea.setRows(5);
        xpnresultsTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane2.setViewportView(xpnresultsTextArea);

        xpndefportsTextArea.setEditable(false);
        xpndefportsTextArea.setColumns(20);
        xpndefportsTextArea.setRows(5);
        xpndefportsTextArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane6.setViewportView(xpndefportsTextArea);

        javax.swing.GroupLayout xpressionPanelLayout = new javax.swing.GroupLayout(xpressionPanel);
        xpressionPanel.setLayout(xpressionPanelLayout);
        xpressionPanelLayout.setHorizontalGroup(
            xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xpressionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(xpressionPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6))
                    .addGroup(xpressionPanelLayout.createSequentialGroup()
                        .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(incoderipLabel)
                            .addComponent(xpngatewayipLabel)
                            .addComponent(studioipLabel))
                        .addGap(18, 18, 18)
                        .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(xpngatewayIPTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(incoderIPTextField)
                            .addComponent(studioIPTextField))
                        .addGap(18, 18, 18)
                        .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(xpngatewayRadioButton)
                            .addComponent(xpnstudioRadioButton)
                            .addComponent(xpnincoderRadioButton))
                        .addGap(88, 88, 88)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        xpressionPanelLayout.setVerticalGroup(
            xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xpressionPanelLayout.createSequentialGroup()
                .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(xpressionPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(studioipLabel)
                            .addComponent(studioIPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xpnstudioRadioButton))
                        .addGap(18, 18, 18)
                        .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xpngatewayipLabel)
                            .addComponent(xpngatewayIPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xpngatewayRadioButton))
                        .addGap(18, 18, 18)
                        .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(incoderipLabel)
                            .addComponent(incoderIPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xpnincoderRadioButton)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(xpressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)))
        );

        studioIPTextField.getAccessibleContext().setAccessibleName("");

        toolsPane.addTab("XPRESSION", xpressionPanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
        );

        toolsPane.addTab("TOOLS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolsPane)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolsPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        toolsPane.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitButtonActionPerformed

    private void priovdRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priovdRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priovdRadioButtonActionPerformed

    private void redovdRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redovdRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_redovdRadioButtonActionPerformed

    private void xpnincoderRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xpnincoderRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xpnincoderRadioButtonActionPerformed

    private void xpngatewayRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xpngatewayRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xpngatewayRadioButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        

        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpenPortChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpenPortChecker().setVisible(true);   
                
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton exitButton;
    private javax.swing.JTextField incoderIPTextField;
    private javax.swing.JLabel incoderipLabel;
    private javax.swing.JButton internetButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JProgressBar ovdProgressBar;
    private javax.swing.JTextArea ovdcheckIPTextArea;
    private javax.swing.JTextArea ovddefportsTextArea;
    private javax.swing.JTextArea ovddefportsTextArea1;
    private javax.swing.JTextArea ovdresultsTextArea;
    private javax.swing.JPanel overdrivePanel;
    private javax.swing.JButton priodUIButton;
    private javax.swing.JLabel priodsrvLabel;
    private javax.swing.JLabel priodsrvLabel1;
    private javax.swing.JTextField priovdIPTextField;
    private javax.swing.JRadioButton priovdRadioButton;
    private javax.swing.JButton redodUIButton;
    private javax.swing.JTextField redovdIPTextField;
    private javax.swing.JRadioButton redovdRadioButton;
    private javax.swing.JTextField studioIPTextField;
    private javax.swing.JLabel studioipLabel;
    private javax.swing.JButton testButton;
    private javax.swing.JTabbedPane toolsPane;
    private javax.swing.JTextArea xpncheckIPTextArea;
    private javax.swing.JTextArea xpndefportsTextArea;
    private javax.swing.JTextField xpngatewayIPTextField;
    private javax.swing.JRadioButton xpngatewayRadioButton;
    private javax.swing.JLabel xpngatewayipLabel;
    private javax.swing.JRadioButton xpnincoderRadioButton;
    private javax.swing.JTextArea xpnresultsTextArea;
    private javax.swing.JRadioButton xpnstudioRadioButton;
    private javax.swing.JPanel xpressionPanel;
    // End of variables declaration//GEN-END:variables
}
