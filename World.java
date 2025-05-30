package file;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class World extends JPanel{
    
    Tile t;
    private ImageList L ;
    JPanel rightPanel ;
    BufferedImage canvas;
    
    
    public final int TileSize = 32;
    final int SizeX = 30;
    final int SizeY = 20;
    final int height = SizeY * TileSize;
    final int width = SizeX * TileSize;

    private int x ;
    private int y ;

    private int FileX ;
    private int FileY ;

    public int worldmap[][];
    
    public World() {
        t = new Tile(); 
        worldmap = new int[SizeX][SizeY];
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        rightPanel = createRightPanel();
        this.L = new ImageList(rightPanel);

        addMouseListener(new MouseAdapter() { 
          @Override
          public void mouseClicked(java.awt.event.MouseEvent me) { 

            FileX = me.getX()/TileSize;
            x = FileX*TileSize;
            FileY = me.getY()/TileSize;
            y = FileY*TileSize;

            int selected = L.selectedImage();
            worldmap[FileX][FileY] = selected;
            //System.err.println(selected);
            t.loadImage(selected);
            draw();
            requestFocusInWindow();
          } 
        });
        addKeyListener(new KeyAdapter() {   
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    saveWorldMap();
                }
            }
        });
    }
    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(32, 20*32));
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        return panel;
    }

    public static void main(String[] args) {
    // Must run on the EDT
        JFrame frame = new JFrame();
        World worldPanel = new World(); 

        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(worldPanel,BorderLayout.CENTER); 
        worldPanel.L = new ImageList(worldPanel.rightPanel);
        frame.add(worldPanel.rightPanel, BorderLayout.EAST);
        
        frame.pack();
        frame.setVisible(true); 
        System.out.println("Is focusable: " + worldPanel.isFocusable());
    
}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
        
    }
    public void draw(){
        Graphics2D g2 = canvas.createGraphics();
        if (t.image != null) {
            //System.err.println("hi");
            g2.drawImage(t.image, x, y, 32, 32, null);
            g2.dispose();  
            repaint();
        } else {
            System.err.println("Image is null!");
           // g2.fillRect(0, 0, 32, 32);
        }
    }
    public void saveWorldMap() {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name(without extention):", "Save Map", JOptionPane.PLAIN_MESSAGE);
        
        if (fileName != null && !fileName.trim().isEmpty()) {
            try (FileWriter writer = new FileWriter(fileName + ".txt")) {
                for (int y = 0; y < SizeY; y++) {
                    for (int x = 0; x < SizeX; x++) {
                        writer.write(worldmap[x][y] + " ");
                    }
                    writer.write("\n");
                }
                writer.flush();
                System.out.println("Map saved to " + fileName + ".txt");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Save cancelled.");
        }
    }
}