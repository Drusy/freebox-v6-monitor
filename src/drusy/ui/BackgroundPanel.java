package drusy.ui;

import java.awt.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel
{
    private Image img;

    public BackgroundPanel(LayoutManager layout, Image image) {
        super(layout);

        this.img = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();
        int width = img.getWidth( null );
        int height = img.getHeight( null );

        super.paintComponent(g);

        for (int x = 0; x < d.width; x += width)
        {
            for (int y = 0; y < d.height; y += height)
            {
                g.drawImage( img, x, y, null, null );
            }
        }
    }
}