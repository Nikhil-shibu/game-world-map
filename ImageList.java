package file;
import java.awt.Component;
import java.awt.Image;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

class ImageListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(
        JList<?> list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
        
        // Get the default renderer component
        JLabel label = (JLabel) super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof ImageIcon) {
            // If the value is an ImageIcon, set it as the icon
            label.setIcon((ImageIcon) value);
            label.setText(""); // Clear any text
        }
        
        return label;
    }
}
public class ImageList {

    JPanel frame;
    JList<ImageIcon> list ;
    public ImageList(JPanel jk){
        this.frame=jk;
        imageSelect();
    }
    public void imageSelect(){
        
            // Create image icons (replace with your image paths)
                ImageIcon[] icons = {
                    new ImageIcon("image\\grass.png"),
                    new ImageIcon("image\\player.png")
                };
                
                // Scale images if needed
                for (ImageIcon icon : icons) {
                    Image img = icon.getImage();
                    Image scaledImg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    icon.setImage(scaledImg);
                }
                
                // Create JList with images
                list = new JList<>(icons);
                list.setCellRenderer(new ImageListRenderer());
                
                // Configure list appearance
                list.setLayoutOrientation(JList.VERTICAL_WRAP);
                list.setVisibleRowCount(-1);
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                // Add to frame
                frame.add(new JScrollPane(list));
                
    }
    public int selectedImage() {
    return list != null ? list.getSelectedIndex() : -1;
}
}