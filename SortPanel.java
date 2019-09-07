/************************************************************
 *                                                          *
 *  CSCI 470-2/502-2       Assignment 5        Summer 2019  *
 *                                                          *
 *                                                          *
 *  Class Name:  SortPanel.                                  *
 *                                                          *
 *  Programmer: Sheethal Yellisetty(Z1853460)               *
 *                                                          *                                                         *
 *  Purpose: The purpose of class is to choose type of sorting 
 *           from JCombobox and adds buttons that control the 
 *           sorting to the panel. Adds SortAnimationPanel
 *           object to the sortpanel to display animation.  *
 ************************************************************/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SortPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SORT = "Sort";
	private static final String PAUSE = "Pause";
	private static final String RESUME = "Resume";

	private JComboBox<String> sortinglist;               //JComboBox for sorting list from which user needs to choose
	private JComboBox<String> speedlist;                 //JComboBox for speed to be chosen at which sorting takes place
	
	private JButton populate;                            //JButton for populate
	private JButton sort;                                //JBUtton for sort
	
	private SortAnimationPanel data;                    //instantiating sortAnimationPanel object
	
	Thread t1;                                          //Instantiating thread
	int[] randomIntsArray = null;                       //initializing random integer array
	
	/************************************************************
	 *                                                          *
	 *  Method Name:  SortPanel.                                *
	 *                                                          *
	 *  Purpose:  The method SortPanel is the constructor of    *
	 *            the class, and sets GridBagLayout to the panel
	 *            and adds SortAnimationPanel object, sorting list 
	 *            button and controlling buttons to the panel   *
	 ************************************************************/
	
	public SortPanel() {
		super();

		JPanel panel = new JPanel();
		setLayout(new GridBagLayout());                              //setting layout manager to GridbagLayout
		GridBagConstraints constraints = new GridBagConstraints();   //Instantiating gridbagConstraints
		constraints.fill = GridBagConstraints.BOTH;                  // setting general constraints
		constraints.anchor = GridBagConstraints.CENTER;

		constraints.gridx = 0;                                       // sets constraints for animation panel
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;

		data = new SortAnimationPanel();                             //adding sortingAnimationPanel object to the panel 
		add(data, constraints);
		
		String[] sortings = { "Selection", "Insertion", "Quick" };
		sortinglist = new JComboBox<String>(sortings);              //JComboBox for sorting list
		sortinglist.setEditable(false);                             
		sortinglist.setMaximumRowCount(3);

		String[] speed = { "Slow", "Medium", "Fast" };
		speedlist = new JComboBox<String>(speed);                   //JCombobox for speedlist

		sort = new JButton("Sort");
		sort.setEnabled(false);

		populate = new JButton("Populate");

		
		populate.addActionListener(this);                          //adding action Listener to the populate button
		sort.addActionListener(this);                              //adding action Listener to the sort button
		
		panel.add(sortinglist);                                    //adding sorting list to JPanel 

		constraints.fill = GridBagConstraints.HORIZONTAL; // bottom panel constraints
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;

		add(panel, constraints);                         //adding constraints properties to the panel components

		JPanel panel1 = new JPanel();

		panel1.add(populate);                            // adding buttons to the panel
		panel1.add(sort);
		panel1.add(speedlist);
		add(panel1);

		constraints.gridy = 2;

		add(panel1, constraints);                        // adding constraint properties to the panel components

	}

	/************************************************************
	 *                                                          *
	 *  Method Name:  actionPerformed.                          *
	 *                                                          *
	 *  Purpose:  The purpose of the method is to take appropriate
	 *            action when appropriate button is clicked.    *
	 ************************************************************/
	@SuppressWarnings("deprecation")
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == populate) {              //gets executed when populate button is clicked                     
			Dimension dim = data.getPreferredSize();      //setting the dimension

			int highestvalue = dim.height;                
			int totalvalues = dim.width;
			randomIntsArray = new int[totalvalues];       //random integers array length of dimension width
			try {
			randomIntsArray = IntStream.generate(() -> new Random().nextInt(highestvalue)).limit(totalvalues).toArray(); //Generates random Integer values in the range of Dimension height
			}catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println(e);
			}
			populate.setEnabled(false);
			sort.setText(SORT);
			sort.setEnabled(true);
			repaint();
		} else if (event.getSource() == sort) {            //Gets executed when sort button is clicked
			populate.setEnabled(true);
			sort.setEnabled(true);
			System.out.println(sort.getText());
			if (sort.getText().equals(SORT)) {
				t1 = new Thread(data);
				t1.start();
				sort.setText(PAUSE);

			} else if (sort.getText().equals(PAUSE)) {    //gets executed when pause is clicked
				sort.setText(RESUME);
				t1.suspend();                             //suspends thread

			} else if (sort.getText().equals(RESUME)) {   //gets executed when resume is clicked
				sort.setText(PAUSE);
				t1.resume();                              //resumes the suspended thread
				repaint();
			}

		} 
	}
	/************************************************************
	 *                                                          *
	 *  Class Name:  SortAnimationPanel.                        *
	 *                                                          *
	 *  Purpose:  The purpose of the inner class that implements 
	 *            Runnable interface and run method in it performs 
	 *            type of sort with the specified chosen speed and 
	 *            implements the paintComponent method             *
	 ************************************************************/
	class SortAnimationPanel extends JPanel implements Runnable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static final int FAST = 25;
		public static final int MEDIUM = 50;
		public static final int SLOW = 100;

		public static final int INSERTION_SORT = 0;
		public static final int SELECTION_SORT = 1;
		public static final int QUICK_SORT = 2;

		
		public SortAnimationPanel() {
			setPreferredSize(new Dimension(400,400));
		}
		/************************************************************
		 *                                                          *
		 *  Method Name:  paintComponent.                           *
		 *                                                          *
		 *  Purpose:  The purpose of the method is to represent the 
		 *            sort graphically                              *
		 ************************************************************/
		@Override
		public void paintComponent(Graphics g) {       
			super.paintComponent(g);
			int height = this.getHeight();
			setBackground(Color.WHITE);                   //setting the background color to white
			setPreferredSize(new Dimension(730, 600));    //dimenstion of the graphic component 
			g.setColor(Color.BLUE);
			if (randomIntsArray != null) {
				for (int i = 0; i < randomIntsArray.length; i++) {
					g.drawLine(i, height, i, height - randomIntsArray[i]);
				}
			}
		}

		@Override
		public void run() {

			String speedchoice = String.valueOf(speedlist.getSelectedItem());

			int speedo = 0;
			if (speedchoice.equalsIgnoreCase("FAST")) {
				speedo = FAST;

			} else if (speedchoice.equalsIgnoreCase("MEDIUM")) {
				speedo = MEDIUM;

			} else if (speedchoice.equalsIgnoreCase("SLOW")) {
				speedo = SLOW;

			}

			if (sortinglist.getSelectedIndex() == INSERTION_SORT) {
				insertionsort(randomIntsArray, speedo);

			} else if (sortinglist.getSelectedIndex() == SELECTION_SORT) {
				selectionsort(randomIntsArray, speedo);

			} else if (sortinglist.getSelectedIndex() == QUICK_SORT) {
				quicksort(randomIntsArray, speedo);

			}

			populate.setEnabled(true);
			sort.setText(SORT);
			sort.setEnabled(false);
		}
		/************************************************************
		 *                                                          *
		 *  Method Name: Selectionsort.                             *
		 *                                                          *
		 *  Purpose:  The method is used to sort the array using the*
		 *            sorting technique Selection sort and uses the *
		 *            function partition for the Selection sort.    *
		 ************************************************************/ 
		void selectionsort(int arr[], int THREAD_SLEEP) {
			int n = arr.length;

			// One by one move boundary of unsorted subarray
			for (int i = 0; i < n - 1; i++) {
				// Find the minimum element in unsorted array
				int min_idx = i;
				for (int j = i + 1; j < n; j++)
					if (arr[j] < arr[min_idx])
						min_idx = j;
				// Swap the found minimum element with the first
				// element
				int temp = arr[min_idx];
				arr[min_idx] = arr[i];
				arr[i] = temp;
				repaint();
				try {
					Thread.sleep(THREAD_SLEEP);
				} catch (Exception e) {
				}
			}
		}
		/************************************************************
		 *                                                          *
		 *  Method Name: Insertionsort.                             *
		 *                                                          *
		 *  Purpose:  The method is used to sort the array using the*
		 *            sorting technique Insertion sort and uses the *
		 *            function partition for the quick sort.        *
		 ************************************************************/ 
		void insertionsort(int arr[], int THREAD_SLEEP) {
			int n = arr.length;
			for (int i = 1; i < n; ++i) {
				int key = arr[i];
				int j = i - 1;

				/*
				 * Move elements of arr[0..i-1], that are greater than key, to one position
				 * ahead of their current position
				 */
				while (j >= 0 && arr[j] > key) {
					// if (keepRunning()) {
					arr[j + 1] = arr[j];
					j = j - 1;
					repaint();
					try {
						Thread.sleep(THREAD_SLEEP);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
					arr[j + 1] = key;
				}
			}
		}

		/************************************************************
		 *                                                          *
		 *  Method Name:  partition.                                *
		 *                                                          *
		 *  Purpose:  The method is used to partiton the array into *
		 *            a order so that the method can be used by the *
		 *            functions for quick,selection and insertion sort.*
		 ************************************************************/ 
		private int partition(int arr[], int low, int high, int THREAD_SLEEP) {
			int pivot = arr[high];
			int i = (low - 1); // index of smaller element

			for (int j = low; j < high; j++) {
				// If current element is smaller than or
				// equal to pivot
				
				if (arr[j] <= pivot) {
					i++;
					// swap arr[i] and arr[j]
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					repaint();
				}
			}
			// swap arr[i+1] and arr[high] (or pivot)
			int temp = arr[i + 1];
			arr[i + 1] = arr[high];
			arr[high] = temp;
			repaint();
			// pause the thread to slow down the animation
			try {
				Thread.sleep(THREAD_SLEEP);
			} catch (Exception e) {
			}
			return i + 1;
		}

		/************************************************************
		 *                                                          *
		 *  Method Name:  quicksort.                                *
		 *                                                          *
		 *  Purpose:  The method is used to sort the array using the*
		 *            sorting technique quick sort and uses the function*
		 *            partition for the quick sort.                 *
		 ************************************************************/
		private void quicksort(int arr[], int low, int high, int THREAD_SLEEP) {
			if (low < high) {
				int pi = partition(arr, low, high, THREAD_SLEEP);  //pi is partitioning index, arr[pi] is now at right place
				if (pi == -1)
					return;
				// Recursively sort elements before
				// partition and after partition
				quicksort(arr, low, pi - 1, THREAD_SLEEP);
				quicksort(arr, pi + 1, high, THREAD_SLEEP);
			}
		}
		public void quicksort(int arr[], int THREAD_SLEEP) {
			int n = arr.length;    // calculate the array length
			quicksort(arr, 0, n - 1, THREAD_SLEEP);// call quicksort
		}
	}

}