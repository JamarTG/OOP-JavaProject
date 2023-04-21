import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ShowcasePanel extends JPanel {
	private static Font FONT = new Font("SimSun", Font.BOLD, 65);
	private static Font SECONDARY_FONT = new Font("SimSun", Font.BOLD, 20);
	
	public ShowcasePanel() {        
		super(new BorderLayout());

        ImageIcon logoIcon = new ImageIcon(System.getProperty("user.dir") + "\\resources\\logo\\real-estate.jpg");
        int maxWidth = 800; 
        int maxHeight = 350; 
        Image image = logoIcon.getImage();
        
        double aspectRatio = (double) image.getWidth(null) / (double) image.getHeight(null);
        if (image.getWidth(null) > maxWidth || image.getHeight(null) > maxHeight) {
            if (aspectRatio > 1.0) {
                image = image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH);
            } else {
                image = image.getScaledInstance(-1, maxHeight, Image.SCALE_SMOOTH);
            }
        }
        
        ImageIcon scaledLogoIcon = new ImageIcon(image);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        JLabel showcaseLabel = new JLabel("Java Estate Limited");
        JLabel subShowcaseLabel = new JLabel(System.getProperty("user.dir") + "\\resources\\logo\\real-estate.jpg");
        
        showcaseLabel.setPreferredSize(new Dimension(600, 80));
        showcaseLabel.setFont(FONT);
        showcaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
        subShowcaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subShowcaseLabel.setFont(SECONDARY_FONT);
       

        
        setBackground(Color.white);
        add(subShowcaseLabel, BorderLayout.CENTER);
        add(showcaseLabel, BorderLayout.NORTH);
        add(logoLabel, BorderLayout.SOUTH);    
	}
}
