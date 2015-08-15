import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.SduniRandom;

public class Test {

	public static void main(String[] args) {
		Test l = new Test();
		ArrayList<Long> buffer = new ArrayList<>();
		SduniRandom rand = new SduniRandom();
		for(int i = 0; i < 100000; i++){
			buffer.add(rand.next(5,100));
			System.out.println(buffer.get(i));
		}
		JFrame main = new JFrame("Verteilung!");
        main.setSize(400,400);
        main.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        main.setLocation((int)(width/2)-200,(int) (height/2)-200);
        main.setVisible(true);
        GPanel panel =  l.new GPanel();
        panel.points = buffer;
        main.add(panel);
        main.repaint();

	}
	
	class GPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected List<Long> points = new ArrayList<>(); 
        public GPanel() {
            setPreferredSize(new Dimension(400, 400));
            setBackground(Color.BLACK);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Map<Long,Long> counts = new HashMap<>();
            for(Long p : points){
            	if(counts.get(p) == null){
            		counts.put(p,0L);
            	}
            	counts.put(p, counts.get(p)+1);
            }
            long highest = 0;
            for (Entry<Long, Long> entry : counts.entrySet())
            {
            	if(entry.getValue() > highest){
            		highest = entry.getValue();
            	}
            }
            Map<Long,Double> countsPercent = new HashMap<>();
            for (Entry<Long, Long> entry : counts.entrySet())
            {
            	countsPercent.put(entry.getKey(), entry.getValue().doubleValue()/highest);
            }
            int weightOfOneRectangle = this.getWidth()/counts.size()-2;
            g.setColor(Color.RED);
            int where = 0;
            for (Entry<Long, Double> entry : countsPercent.entrySet())
            {
            	JLabel label = new JLabel(entry.getKey().toString());
            	int heightOfTheReacangle = (int) (Math.round(entry.getValue()*this.getHeight())-5);
            	g.fillRect((where*weightOfOneRectangle)+(where*2)+5, this.getHeight()-heightOfTheReacangle, weightOfOneRectangle, heightOfTheReacangle);
            	label.setBounds((where*weightOfOneRectangle)+(where*2)+5, this.getHeight()-20, weightOfOneRectangle, 20);
            	label.setHorizontalAlignment(JLabel.CENTER);
            	label.setVerticalAlignment(JLabel.CENTER);
            	this.add(label);
            	this.repaint();
            	where++;
            }   
        }
    }

}
