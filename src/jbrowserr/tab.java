package jbrowserr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class tab extends JPanel {
    Icon go = new ImageIcon(getClass().getResource("go.png"));
    JButton goButton = new JButton("GO",go );
    JButton newtab = new JButton("new tab");
    JTextField locationTextField = new JTextField(36);
    JEditorPane displayEditorPane = new JEditorPane();
    JPanel buttonPanel = new JPanel();
    JPanel displayEditorPanel = new JPanel(new BorderLayout());
    JTextField h1;
    JTextArea h2;
    
    
    public tab() {
        setLayout(new BorderLayout());
        setDoubleBuffered(true);
        updateUI();
        
        buttonPanel.add(goButton);
        goButton.setToolTipText("Go to the address");
        buttonPanel.add(locationTextField);
        displayEditorPane.setContentType("text/html");
        displayEditorPane.setEditable(false);
        displayEditorPanel.add(displayEditorPane);
        h1 = new JTextField();
        this.add(h1);
        h1.setVisible(true);
        h1.setLocation(100, 100);
        h1.setSize(800, 30);
        h1.setBackground(Color.gray);
        h2 = new JTextArea("Printing Response Header...\n");
        h2.setVisible(true);
        h2.setBackground(Color.white);
        JScrollPane scroll = new JScrollPane(h2);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setVisible(true);
        scroll.setLocation(100, 130);
        scroll.setSize(800, 250);
        scroll.setBackground(Color.yellow);
        this.add(scroll);

        // Agar Enter zade shod
        locationTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doGo();
                }
            }
        });

        // Agar go zade shod
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doGo();
            }
        });
        addToTabPanel();
    }

    private void addToTabPanel() {
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayEditorPanel), BorderLayout.CENTER);
    }
    
    private void doGo() {
            String str = locationTextField.getText();
            try {
            URL url = new URL(str);
            h1.setText("Address is: "+ url);
            //h2.append("Address is: "+ url);
            URLConnection F1 = url.openConnection();
            Map<String, List<String>> map = F1.getHeaderFields();
           
            //h2.append("Printing Response Header...\n");
            
            for (Map.Entry<String, List<String>> entry : map.entrySet()) 
		h2.append("Key : " + entry.getKey() + " ,Value : " + entry.getValue()+"\n");
               
            h2.append("");
            
            //Q1
            String server = F1.getHeaderField("Server");
            if (server == null)
                h2.append("Server is not found!"+"\n");
            else 
                h2.append("Server: " + server+"\n");
            
            
            //Q2
            
            HttpURLConnection F2 = (HttpURLConnection) url.openConnection();
            F2.setRequestMethod("OPTIONS");
            if (F2.getHeaderField("Allow") == null) 
                h2.append("Methods are not found!"+"\n");
            else 
                h2.append("Methods: "+ F2.getHeaderField("Allow")+"\n");
            
            
            //Q3
            Set<String> headerFieldsSet = map.keySet();
		Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
                while (hearerFieldsIter.hasNext()) {
                    String headerFieldKey = hearerFieldsIter.next();
                    if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
			List<String> headerFieldValue = map.get(headerFieldKey);
			for (String headerValue : headerFieldValue) {
                            h2.append("Cookie Found..." +"\n");
                            String[] fields = headerValue.split(" ");
                            String cookieValue = fields[0];
                            String expires = null;
                            String path = null;
                            String domain = null;
                            boolean secure = false;
                            for (int j = 1; j < fields.length; j++) {
                                if ("secure".equalsIgnoreCase(fields[j])) 
                                    secure = true;
				else if (fields[j].indexOf('=') > 0) {
                                    String[] f = fields[j].split("=");
                                    if ("expires".equalsIgnoreCase(f[0])) 
					expires = f[1];
                                    
                                    else if ("domain".equalsIgnoreCase(f[0])) 
					domain = f[1];
                                    
                                    else if ("path".equalsIgnoreCase(f[0])) 
					path = f[1];
				
				}
						
                            }
                            h2.append("cookieValue:" + cookieValue +"\n");
                            h2.append("expires:" + expires +"\n");
                            h2.append("path:" + path +"\n");
                            h2.append("domain:" + domain +"\n");
                            h2.append("secure:" + secure +"\n");
                        }
				 
                    }
		}
            //Q4
            String expire = F1.getHeaderField("Expires");
            if (expire == null) 
                h2.append("expire is not found!" +"\n");
            
            else 
                h2.append("expire: " + expire +"\n");
            
            String lastModified = F1.getHeaderField("Last-Modified");
            if (lastModified == null) 
                h2.append("last-modified is not found!" +"\n");
            
            else 
                h2.append("last-modified: " + lastModified +"\n");
            
            
            String CacheControl = F1.getHeaderField("Cache-Control");
            if (CacheControl == null) 
                h2.append("cache control is not found!" +"\n");
            else 
                h2.append("cache control: " + CacheControl +"\n");
            
            //Q5
           
            
            //Q6
            //int statusCode = F2.getResponseCode();
            F2.connect();
            int code = F2.getResponseCode();
            h2.append("Response code: " + code +"\n");
            
            //Q7
            String persistent = F1.getHeaderField("connection");
            if (persistent == null) 
                h2.append("connection is not found!" +"\n");
            else 
                h2.append("Connection: " + persistent +"\n");
            
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
