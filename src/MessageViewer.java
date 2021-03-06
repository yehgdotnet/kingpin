import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Cursor;

public class MessageViewer extends JFrame {
	@SuppressWarnings("unused")
	private static Run thread = null;
	private static final long serialVersionUID = 1L;
	private ScrollPane contentPane;
	static String message;
	static boolean intercepted;

	//static JTextArea textArea = new JTextArea();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DefaultTableModel model = (DefaultTableModel) ProKSy.tblLog.getModel();
		   
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessageViewer frame = new MessageViewer(message,intercepted,thread=null);
					frame.setVisible(true);					
				} catch (Exception e) {
					String current_time_str = time_formatter.format(System.currentTimeMillis());
					model.addRow(new Object[]{"X", e, current_time_str});
				}
			}
		});
	}
	public static String newMessage="";
	/**
	 * Create the frame.
	 * @param message2 
	 */
	public MessageViewer(String message2, boolean intercepted, Run thread) {
		JTextArea textArea = new JTextArea();
		setTitle("Kingpin Traffic Viewer");
		setBounds(100, 100, 380, 240);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.LIGHT_GRAY);
		menuBar.setOpaque(false);
		menuBar.setIgnoreRepaint(true);
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		//menuBar.setToolTipText("Forward/Cancel");
		setJMenuBar(menuBar);
		JMenuItem mnForward = new JMenuItem("Forward");
		mnForward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mnForward.setAlignmentX(Component.LEFT_ALIGNMENT);
		mnForward.setMaximumSize(new Dimension(75, 50));
		mnForward.setHorizontalAlignment(SwingConstants.LEFT);
		mnForward.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				newMessage = textArea.getText();
				thread.newMessage = newMessage;
				setVisible(false);
				thread.res();
		    }
		});
		menuBar.add(mnForward);
		
		JMenuItem mnCancel = new JMenuItem("Cancel");
		mnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mnCancel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mnCancel.setMaximumSize(new Dimension(75, 50));
		mnCancel.setHorizontalAlignment(SwingConstants.LEFT);
		mnCancel.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				newMessage = message2;
				thread.newMessage = newMessage;
				setVisible(false);
				thread.res();
		    }
		});
		menuBar.add(mnCancel);

		if (!intercepted) {
			mnForward.setEnabled(false);
			mnCancel.setEnabled(false);
			menuBar.setVisible(false);
		}
		else {

			mnForward.setEnabled(true);
			mnCancel.setEnabled(true);
		}

		contentPane = new ScrollPane();
		textArea.setLineWrap(true);
		textArea.setBackground(ProKSy.mycolor);
		textArea.setText(message2.replace("\r\n", ""));
		if(!intercepted)
			textArea.setEditable(false);
		else {
			textArea.setEditable(true);
			textArea.setBackground(Color.WHITE);
		}
		contentPane.add(textArea, BorderLayout.NORTH);
		setContentPane(contentPane);
		this.setPreferredSize(textArea.getPreferredSize());
		this.addWindowListener(new WindowListener() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	if (intercepted) {
	        		thread.newMessage = message2;
	        		setVisible(false);
	        		thread.res();
	        	}
	        }
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
	    });
	}
}
