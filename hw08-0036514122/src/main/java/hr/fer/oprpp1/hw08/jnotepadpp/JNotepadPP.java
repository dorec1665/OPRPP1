package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.Actions;
import hr.fer.oprpp1.hw08.jnotepadpp.custom.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentAdapter;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;


/**
 * V1.0. JNotepadpp.
 * @author dario
 *
 */
public class JNotepadPP extends JFrame{


	private static final long serialVersionUID = 1L;
	private MultipleDocumentModel model;
	private ImageIcon crvena;
	private ImageIcon zelena;
	private Actions actions;
	private ChangeListener caretListener;
	private Timer t;
	private FormLocalizationProvider flp;

	/**
	 * Konstruktor.
	 */
	public JNotepadPP() {
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0,0);
		setSize(800, 800);
		setTitle("JNotepad++");
		crvena = loadIcon(true);
		zelena = loadIcon(false);
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		WindowListener wl = new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				actions.EXIT.actionPerformed(null);
			}
		};
		
		this.addWindowListener(wl);
		initGUI();
	}

	/**
	 * Pomoćna metoda za postavljanje komponenti po prozoru.
	 */
	private void initGUI() {
		
		model = new DefaultMultipleDocumentModel(crvena, zelena);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add((JTabbedPane) model, BorderLayout.CENTER);

		createStatusbar();
		createActions();
		createMenus();
		createToolbars();
		
		freezeUnfreezeListener();
		changeTitleListener();
		
	}
	

	/**
	 * Pomoćna metoda za dinamičko mijenjanje naslova prozora.
	 */
	private void changeTitleListener() {
		model.addMultipleDocumentListener(new MultipleDocumentAdapter() {
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(currentModel != null && currentModel.getFilePath() != null) {
					String title = "JNotepad++ - " + currentModel.getFilePath().toString();
					setTitle(title);
				} else if(currentModel != null && currentModel.getFilePath() == null) {
					String title = "JNotepad++ - unnamed";
					setTitle(title);
				}
			}
		});
	}

	/**
	 * Pomoćna metoda za zamrzavanje i odmrzavanje gumbova i izbornika.
	 */
	private void freezeUnfreezeListener() {
		model.addMultipleDocumentListener(new MultipleDocumentAdapter() {
			@Override
			public void documentRemoved(SingleDocumentModel document) {
				if(model.getNumberOfDocuments() == 0) {
					actions.CLOSE_DOCUMENT.setEnabled(false);
					actions.SAVE_DOCUMENT.setEnabled(false);
					actions.SAVE_AS.setEnabled(false);				
					actions.PASTE.setEnabled(false);
					actions.STATISTICS.setEnabled(false);

					setTitle("JNotepad++");
				}
			}
			
			@Override
			public void documentAdded(SingleDocumentModel document) {
				actions.CLOSE_DOCUMENT.setEnabled(true);
				actions.SAVE_DOCUMENT.setEnabled(true);
				actions.PASTE.setEnabled(true);
				actions.SAVE_AS.setEnabled(true);
				actions.STATISTICS.setEnabled(true);
			}

		});
	}

	/**
	 * Stvaranje akcija.
	 */
	private void createActions() {
		actions = new Actions(model, this, t, flp);
				
		
		actions.CLOSE_DOCUMENT.setEnabled(false);
		actions.CLOSE_DOCUMENT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		
		actions.CREATE_DOCUMENT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		
		actions.SAVE_DOCUMENT.setEnabled(false);
		actions.SAVE_DOCUMENT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		
		actions.SAVE_AS.setEnabled(false);
		actions.SAVE_AS.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		
		actions.LOAD_DOCUMENT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		
		actions.CUT.setEnabled(false);
		actions.CUT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		
		actions.PASTE.setEnabled(false);
		actions.PASTE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		
		actions.COPY.setEnabled(false);
		actions.COPY.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		
		actions.STATISTICS.setEnabled(false);
		actions.STATISTICS.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 1"));
		
		actions.TOUPPER.setEnabled(false);
		actions.TOUPPER.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 2"));

		actions.TOLOWER.setEnabled(false);
		actions.TOLOWER.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 3"));
		
		actions.INVERT.setEnabled(false);
		actions.INVERT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 4"));
		
		actions.SORTASC.setEnabled(false);
		actions.SORTASC.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 5"));
		
		actions.SORTDESC.setEnabled(false);
		actions.SORTDESC.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 6"));
		
		actions.UNIQUE.setEnabled(false);
		actions.UNIQUE.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 7"));
		
		actions.EXIT.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		
		
		
	}

	
	/**
	 * Stvaranje alatne trake.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(actions.CREATE_DOCUMENT));
		toolBar.add(new JButton(actions.LOAD_DOCUMENT));
		toolBar.add(new JButton(actions.CLOSE_DOCUMENT));
		toolBar.add(new JButton(actions.SAVE_DOCUMENT));
		toolBar.add(new JButton(actions.SAVE_AS));
		toolBar.add(new JButton(actions.CUT));
		toolBar.add(new JButton(actions.PASTE));
		toolBar.add(new JButton(actions.COPY));
		toolBar.add(new JButton(actions.STATISTICS));
		toolBar.add(new JButton(actions.EXIT));


		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	
	/**
	 * Stvaranje izbornika.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		LJMenu fileMenu = new LJMenu("file", flp);
		fileMenu.setText("File");
		
		fileMenu.add(new JMenuItem(actions.CREATE_DOCUMENT));
		fileMenu.add(new JMenuItem(actions.LOAD_DOCUMENT));
		fileMenu.add(new JMenuItem(actions.CLOSE_DOCUMENT));
		fileMenu.add(new JMenuItem(actions.SAVE_DOCUMENT));
		fileMenu.add(new JMenuItem(actions.SAVE_AS));
		fileMenu.add(new JMenuItem(actions.STATISTICS));
		fileMenu.add(new JMenuItem(actions.EXIT));
		
		LJMenu editMenu = new LJMenu("edit", flp);
		editMenu.setText("Edit");
		
		editMenu.add(new JMenuItem(actions.CUT));
		editMenu.add(new JMenuItem(actions.PASTE));
		editMenu.add(new JMenuItem(actions.COPY));
		
		LJMenu languageMenu = new LJMenu("languages", flp);
		languageMenu.setText("Language");
		
		JMenuItem hr = new JMenuItem("hr");
		JMenuItem en = new JMenuItem("en");
		JMenuItem de = new JMenuItem("de");
		hr.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("hr"));
		en.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("en"));
		de.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("de"));
		
		languageMenu.add(hr);
		languageMenu.add(en);
		languageMenu.add(de);
		
		LJMenu toolsMenu = new LJMenu("tools", flp);
		toolsMenu.setText("Tools");
		
		LJMenu changeCaseMenu = new LJMenu("change_case", flp);
		changeCaseMenu.setText("Change case");
		changeCaseMenu.add(actions.TOUPPER);
		changeCaseMenu.add(actions.TOLOWER);
		changeCaseMenu.add(actions.INVERT);
		toolsMenu.add(changeCaseMenu);
		
		LJMenu sortMenu = new LJMenu("sort", flp);
		sortMenu.setText("Sort");
		sortMenu.add(actions.SORTASC);
		sortMenu.add(actions.SORTDESC);
		toolsMenu.add(sortMenu);
		
		JMenuItem uniqueItem = new JMenuItem(actions.UNIQUE);
		toolsMenu.add(uniqueItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(languageMenu);
		menuBar.add(toolsMenu);

		this.setJMenuBar(menuBar);
	}
	
	
	/**
	 * Stvaranje status trake.
	 */
	private void createStatusbar() {
		JPanel statusbar = new JPanel();
		statusbar.setLayout(new GridLayout(1, 3));
		statusbar.setBackground(Color.LIGHT_GRAY);
		statusbar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		
		
		JLabel left = new JLabel(flp.getString("length") + ":");
		JLabel right = createDateAndTime();
		JPanel middle = new JPanel();
		JLabel ln = new JLabel("Ln:");
		JLabel col = new JLabel("Col:");
		JLabel sel = new JLabel("Sel:");
		
		middle.setBackground(Color.LIGHT_GRAY);
		middle.add(ln);
		middle.add(col);
		middle.add(sel);
		
		statusbar.add(left);
		statusbar.add(middle);
		statusbar.add(right);
		
		statusbarListener(left, ln, col, sel);
		
		getContentPane().add(statusbar, BorderLayout.PAGE_END);
	}
	
	
	/**
	 * Pomoćna metoda za stvaranje komponente koja prikaziva trenutni
	 * datum i vrijeme.
	 * @return Komponenta koja prikaziva trenutni datum i vrijeme
	 */
	private JLabel createDateAndTime() {
		JLabel labelDateAndTime = new JLabel("", SwingConstants.RIGHT);
		ActionListener updateClock = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				labelDateAndTime.setText(dtf.format(now));
			}
		};
		
		
		
		t = new Timer(1000, updateClock);
		t.start();
		
		
		return labelDateAndTime;
		
	}

	
	/**
	 * Pomoćna metoda koja služi za dinamičko ažuriranje statusne trake.
	 * @param left Lijevi dio statusne trake(prikazuje duljinu trenutnog dokumenta).
	 * @param ln Linija u kojem se nalazi znak za umetanje(eng. caret)
	 * @param col Stupac u kojem se nalazi znak za umetanje(eng. caret)
	 * @param sel Broj selektiranih znakova
	 */
	private void statusbarListener(JLabel left, JLabel ln, JLabel col, JLabel sel) {
		model.addMultipleDocumentListener(new MultipleDocumentAdapter() {
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(previousModel == null) {
					currentModel.getTextComponent().getCaret()
						.addChangeListener(addCaretListener(left, ln, col, sel));
				} else if(previousModel != null && currentModel != null) {
					previousModel.getTextComponent().getCaret().removeChangeListener(caretListener);
					currentModel.getTextComponent().getCaret()
					.addChangeListener(addCaretListener(left, ln, col, sel));
					caretListener.stateChanged(null);
				}
			}
		});
	}
	
	/**
	 * Pomoćna metoda koja postavlja slušača(eng. listener) na znak za
	 * umetanje(eng. caret).
	 * @param left Lijevi dio statusne trake(prikazuje duljinu trenutnog dokumenta).
	 * @param ln Linija u kojem se nalazi znak za umetanje(eng. caret)
	 * @param col Stupac u kojem se nalazi znak za umetanje(eng. caret)
	 * @param sel Broj selektiranih znakova
	 * @return Postavljeni slušač
	 */
	private ChangeListener addCaretListener(JLabel left, JLabel ln, JLabel col, JLabel sel) {		
		caretListener = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				Caret caret = editor.getCaret();
					
				int selected = Math.abs(caret.getDot()-caret.getMark());
				actions.TOUPPER.setEnabled(selected != 0);
				actions.TOLOWER.setEnabled(selected != 0);
				actions.INVERT.setEnabled(selected != 0);
				actions.CUT.setEnabled(selected != 0);
				actions.COPY.setEnabled(selected != 0);
				actions.SORTASC.setEnabled(selected != 0);
				actions.SORTDESC.setEnabled(selected != 0);
				actions.UNIQUE.setEnabled(selected != 0);
				
				int len = editor.getDocument().getLength();
				int caretPosition = editor.getCaretPosition();
				int line = 0;
				
				int column = 0;
				try {
					line = editor.getLineOfOffset(caretPosition);
					column = caretPosition - editor.getLineStartOffset(line);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				line++;
				left.setText(flp.getString("length") + ":" + len);
				ln.setText("Ln:" + line);
				col.setText("Col:" + column);
				sel.setText("Sel:" + selected);
				}
			};
		return caretListener;	
	}

	/**
	 * Pomoćna metoda za učitavanje ikona za snimanje iz memorije.
	 * @param whichToLoad zastavica za koju ikone treba učitat
	 * @return učitanu ikonu
	 */
	private ImageIcon loadIcon(boolean whichToLoad) {
		InputStream is = whichToLoad ? this.getClass().getResourceAsStream("icons/crvena.png") : this.getClass().getResourceAsStream("icons/zelena.png");
		if(is == null) {
			System.out.println("Greška pri čitanju ikona");
			System.exit(0);
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(bytes);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
