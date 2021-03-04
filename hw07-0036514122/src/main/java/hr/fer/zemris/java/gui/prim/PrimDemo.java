package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Razred koji prikazuje prozor u kojem se
 * nalaze dvije liste koje prikazuju primarne brojeve, koje
 * generiramo pritiskom na gumb.
 * @author dario
 *
 */
public class PrimDemo extends JFrame{


	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setSize(750, 400);
		initGUI();
	}

	/**
	 * Pomoćna metoda za iscrtavanje potrebnih komponenti prozora.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel primList = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(primList);
		JList<Integer> list2 = new JList<>(primList);
		list1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list2.setSelectionModel(list1.getSelectionModel());
		JButton button = new JButton("Sljedeci");
		button.addActionListener(e -> {
			primList.next();
		});
		
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JScrollPane(list1));
		p.add(new JScrollPane(list2));
		
		cp.add(p, BorderLayout.CENTER);
		cp.add(button, BorderLayout.PAGE_END);
	}
	
	
	/**
	 * Razred koji model liste primarnih brojeva.
	 * @author dario
	 *
	 */
	private static class PrimListModel implements ListModel<Integer> {

		private List<Integer> primNumbers = new ArrayList<>();
		private List<ListDataListener> listeners = new ArrayList<>();
		private int currentNumber = 2;
		
		/**
		 * Konstruktor.
		 */
		public PrimListModel() {
			primNumbers.add(1);
		}
		
		
		/**
		 * Meotda koja svakim novim pozivom dohvaća novi primarni broj,
		 * počevši od broja 2.
		 * @return novi pronađeni primarni broj
		 */
		public int next() {
			boolean notPrime = false;
			while(true) {
				for(int i = 2; i <= currentNumber/2; i++) {
					if(currentNumber % i == 0) {
						notPrime = true;
						break;
					}
				}
				if(notPrime) {
					currentNumber++;
					notPrime = false;
				} else {
					addPrimNumber(currentNumber);
					currentNumber++;
					return currentNumber-1;
				}
			}
		}
		
		/**
		 * Sprema primarni broj u internu listu podataka.
		 * Dodatno obavještava sve zakvačene promatrače na ovome modelu
		 * o novonastaloj promjeni.
		 * @param primNumber novi dodani primarni broj
		 */
		private void addPrimNumber(int primNumber) {
			primNumbers.add(primNumber);
			
			ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize()-1, getSize()-1);
			for(ListDataListener l : listeners) {
				l.intervalAdded(e);
			}
		}
		
		
		@Override
		public int getSize() {
			return primNumbers.size();
		}

		@Override
		public Integer getElementAt(int index) {
			return primNumbers.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}
		
	}
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

}
