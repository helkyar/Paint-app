/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *  https://docs.oracle.com/javase/tutorial/uiswing/painting/refining.html
 *  https://www.oracle.com/java/technologies/painting.html
 *  Paleta de colores para elegir
 */
package paint;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Javier Palacios Botejara
 */
public class PaintApp extends JFrame{
    private JPanel draw;
    private JPanel btnBar;
    
    private Dimension size;
    private Graphics g;

    private int prevY = 0;
    private int prevX = 0;
    
    private PaintApp(){
        setLayout(new BorderLayout());

        btnBar = new JPanel(new GridLayout(1,6));
        initbutton("Circulo");
        initbutton("Rect.");
        initbutton("Text");
        initbutton("Limpiar");

        draw = new JPanel(new FlowLayout());
        // Only one is executed
        draw.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                moveSquare(e.getX(),e.getY());
            }
            @Override
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });
        
        // FRAME STRUCTURE ====================================
        add("North", btnBar);
        add("Center", draw);
        setTitle("RAM"); 
        setSize(500,500);
        setVisible(true);
        setResizable(false);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        size = draw.getSize();
        g = draw.getGraphics();
    }
    
    private void initbutton(String name) {
        JButton btn = new JButton(name);
        btn.addActionListener((ActionEvent e) -> 
        {eventHandler(e.getActionCommand());});
        
        btnBar.add("North", btn);
    }
    
    //////////////////////////////////////////////////////////////
    // EVENTS
    //////////////////////////////////////////////////////////////
    private void eventHandler(String ac) {
       if(ac.equals("Circulo")){circle();}
       else if(ac.equals("Text")){paintText();}
       else if(ac.equals("Rect.")){paintRect();}
       else{clear();}
       
    }
    
    private void circle() {
        Graphics g = draw.getGraphics();
        // Dynamically calculate size information 
        // diameter    
        int d = Math.min(size.width, size.height);    
        int x = (size.width - d)/2;    
        int y = (size.height - d)/2;       
        // draw circle (color already set to foreground) 
        g.setColor(Color.yellow);
        g.fillOval(x, y, d, d);     
        g.setColor(Color.green);   
        g.drawOval(x, y, d, d); 
    } 

    private void moveSquare(int x, int y) {

        if ((prevX!=x) || (prevY!=y)) {

            // The square is moving, repaint background 
            // over the old square location. 
            g.clearRect(prevX,prevY,55,55);

            // Update coordinates.
            prevX = x;
            prevY = y;

            g.setColor(Color.RED);
            g.fillRect(x,y,50,50);
            g.setColor(Color.BLACK);
            g.drawRect(x,y,50,50);
        }
    }    
    
    private void paintText(){
        g.setColor(Color.blue);
        g.setFont(new Font("arial", 3, 24));
        g.drawString("Yeah, just text. Bit disapointing isn't it?",20,40);
    }
    
    private void paintRect() {
        g.setColor(Color.RED);
        g.fillRect(30,50,300,300);
        g.setColor(Color.BLACK);
        g.drawRect(30,50,300,300);
    }
  
    private void clear() {
        draw.repaint();
        // g.dispose();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PaintApp();
        
    }   
}