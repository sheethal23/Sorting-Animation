/************************************************************
 *                                                          *
 *  CSCI 470-2/502-2       Assignment 5        Summer 2019  *
 *                                                          *
 *                                                          *
 *  Class Name:  SortAnimationApp.                          *
 *                                                          *
 *  Programmer: Sheethal Yellisetty(Z1853460)               *
 *                                                          *
 *                                                          *
 *                                                          *
 *  Purpose: The purpose of class is to represent the sorting 
 *           animation application that inherits from JFrame.It 
 *           has a pair of SortPanel objects as data members.
 *           The class has a custom constructor that        *
 *           passes a title bar string to the superclass    *
 *           constructor and adds the two SortPanel objects *
 *           to the application’s layout.                   *
 ************************************************************/
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class SortAnimationApp extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws InterruptedException {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() 
			{
				SortAnimationApp sortGUI = new SortAnimationApp();       // makes new GUI
				sortGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set default close
				sortGUI.setSize(710,450);                                // set Size
				sortGUI.setResizable(false);                             // make not resizeable
				sortGUI.setVisible(true);                                // shows GUI
			}});
	}


	/************************************************************
	 *                                                          *
	 *  Method Name:  SortAnimationApp.                         *
	 *                                                          *
	 *  Purpose:  The method SortAnimationApp is the constructor of*
	 *            the class, it does create 2 panels for the sorting 
	 *            panels and sets the layout and add sort panel *
	 *            objects to layout.                            *
	 ************************************************************/
	public SortAnimationApp() {
		super("Sorting Animation");                  // title
        
		
		setLayout(new GridLayout(1,2,10,0));         //set layout with 10px vertical gap
		SortPanel leftPanel = new SortPanel();       // instantiate panels
		SortPanel rightPanel = new SortPanel();

		add(leftPanel);                               // add to frames
		add(rightPanel);
		
		pack();
	}
}