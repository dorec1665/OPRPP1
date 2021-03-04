package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

/**
 * Predstavlja sve akcije koje {@link JNotepadPP} podržava.
 * @author dario
 *
 */
public class Actions {
	
	private MultipleDocumentModel model;
	private JNotepadPP notepad;
	private String textToPaste;
	private Timer t;
	private FormLocalizationProvider flp;
	
	/**
	 * Akcija za snimanje dokumenta.
	 */
	public final LocalizableAction SAVE_DOCUMENT;
	/**
	 * Akcija za stvaranje novog praznog dokumenta.
	 */
	public final LocalizableAction CREATE_DOCUMENT;
	/**
	 * Akcija za snimanje dokumenta na putanji koju korisnik odabere.
	 */
	public final LocalizableAction SAVE_AS;
	/**
	 * Akcija za bezuvjetno zatvaranje dokumenta, tj. pozivom
	 * ove akcije korisnik neće moći snimiti dokument.
	 */
	public final LocalizableAction CLOSE_DOCUMENT;
	/**
	 * Akcija za učitavanje postojećeg dokumenta.
	 */
	public final LocalizableAction LOAD_DOCUMENT;
	/**
	 * Akcija za "rezanje" dijela teksta(kojeg se poslje
	 * može zalijepiti).
	 */
	public final LocalizableAction CUT;
	/**
	 * Akcija za zalijepiti kopirani tekst.
	 */
	public final LocalizableAction PASTE;
	/**
	 * Akcija za kopiranje teskta.
	 */
	public final LocalizableAction COPY;
	/**
	 * Akcija koja prikazuje statistiku dokumenta.
	 */
	public final LocalizableAction STATISTICS;
	/**
	 * Akcija za izlazak iz programa {@link JNotepadPP}.
	 */
	public final LocalizableAction EXIT;
	/**
	 * Akcija koja selektiranom tekstu svako slovo
	 * prebaciva u veliko slovo.
	 */
	public final LocalizableAction TOUPPER;
	/**
	 * Akcija koja selektiranom tekstu svako slovo
	 * prebaciva u malo slovo.
	 */
	public final LocalizableAction TOLOWER;
	/**
	 * Akcija koja selektiranom tekstu svako slovo
	 * invertira, tj. svako malo slovo prebcai u veliko i
	 * obratno.
	 */
	public final LocalizableAction INVERT;
	/**
	 * Akcija za uzlazno sortiranje.
	 */
	public final LocalizableAction SORTASC;
	/**
	 * Akcija za silazno sortiranje.
	 */
	public final LocalizableAction SORTDESC;
	/**
	 * Akcija koja izbaciva duplikatne linije.
	 */
	public final LocalizableAction UNIQUE;
	
	/**
	 * Konstrukto.
	 * @param model model
	 * @param notepad program
	 * @param t timer
	 * @param flp lokalizator
	 */
	public Actions(MultipleDocumentModel model, JNotepadPP notepad, Timer t, FormLocalizationProvider flp) {
		this.model = model;
		this.notepad = notepad;
		this.t = t;
		this.flp = flp;
		
		SAVE_DOCUMENT = new LocalizableAction("save", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();	
			}
		};
		
		CREATE_DOCUMENT = new LocalizableAction("create", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				createAction();
			}
		};
		
		SAVE_AS = new LocalizableAction("save_as", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsAction();
			}
		};
		
		CLOSE_DOCUMENT = new LocalizableAction("close", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				closeAction();
			}
		};
		
		LOAD_DOCUMENT = new LocalizableAction("load", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				loadAction();
			}
		};
		
		CUT = new LocalizableAction("cut", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				cutAction();
			}
		};
		
		PASTE = new LocalizableAction("paste", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				pasteAction();
			}
		};
		
		COPY = new LocalizableAction("copy", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				copyAction();
			}
		};
		
		EXIT = new LocalizableAction("exit", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exitAction();
			}
		};
		
		STATISTICS = new LocalizableAction("statistics", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				statisticsAction();
			}
		};
		
		TOUPPER = new LocalizableAction("toupper", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase(0);
			}
		};
		
		TOLOWER = new LocalizableAction("tolower", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase(1);
			}
		};
		
		INVERT = new LocalizableAction("invert", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase(2);
			}
		};
		
		SORTASC = new LocalizableAction("asc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sortAction(true);
			}
		};
		
		SORTDESC = new LocalizableAction("desc", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sortAction(false);
			}
		};
		
		UNIQUE = new LocalizableAction("unique", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				uniqueAction();
			}
		};
	}	
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>CREATE_DOCUMENT</code>.
	 */
	private void createAction() {
		model.createNewDocument();
	}
	
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>SAVE_DOCUMENT</code>.
	 */
	private void saveAction() {
		if(!model.getCurrentDocument().isModified()) {
			JOptionPane.showMessageDialog(notepad, flp.getString("no_changes"));
			return;
		}
		if(model.getCurrentDocument().getFilePath() == null) {
			saveAs(flp.getString("override"), model.getCurrentDocument());
		} else {
			model.saveDocument(model.getCurrentDocument(), null);
		}
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>SAVEAS</code>.
	 */
	private void saveAsAction() {
		saveAs(flp.getString("override"), model.getCurrentDocument());
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>CLOSE_DOCUMENT</code>.
	 */
	private void closeAction() {
		model.closeDocument(model.getCurrentDocument());
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>LOAD_DOCUMENT</code>.
	 */
	private void loadAction() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if(fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) return;
		
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		model.loadDocument(filePath);
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>CUT</code>.
	 */
	private void cutAction() {
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
		
		int len = Math.abs(caret.getDot()-caret.getMark());
		if(len == 0) {
			JOptionPane.showMessageDialog(notepad, flp.getString("no_selected"));
		}
		
		int offset = Math.min(caret.getDot(), caret.getMark());
		
		try {
			textToPaste = doc.getText(offset, len);
			doc.remove(offset, len);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>PASTE</code>.
	 */
	private void pasteAction() {
		if(textToPaste == "" || textToPaste == null) {
			JOptionPane.showMessageDialog(notepad, flp.getString("no_copied"));
			return;
		}
		
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
		
		int len = Math.abs(caret.getDot()-caret.getMark());
		if(len == 0) {
			try {
				doc.insertString(caret.getDot(), textToPaste, null);
				return;
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		if(len != 0) {
			int offset = Math.min(caret.getDot(), caret.getMark());
			try {
				doc.remove(offset, len);
				doc.insertString(caret.getDot(), textToPaste, null);
				return;
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>COPY</code>.
	 */
	private void copyAction() {
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
		
		int len = Math.abs(caret.getDot()-caret.getMark());
		if(len == 0) {
			JOptionPane.showMessageDialog(notepad, flp.getString("no_selected"));
		}
		
		int offset = Math.min(caret.getDot(), caret.getMark());
		
		try {
			textToPaste = doc.getText(offset, len);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>EXIT</code>.
	 */
	private void exitAction() {
		for(int i = 0; i < model.getNumberOfDocuments(); i++) {
			SingleDocumentModel document = model.getDocument(i);
			
			if(document.isModified()) {
				if(document.getFilePath() == null) {
					String options[] = new String[] {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
					int rezultat = JOptionPane.showOptionDialog(notepad, flp.getString("override_unnamed"), flp.getString("warning")
							, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					
					switch(rezultat) {
					case JOptionPane.CLOSED_OPTION:
						return;
					case 0:
						saveAs(flp.getString("override"), document);
						continue;
					case 1:
						continue;
					case 2:
						return;
					}
					
					continue;
				}
				int rezultat = checkOption(flp.getString("not_saved").formatted(document.getFilePath().getFileName()), document.getFilePath(), document);
				if(rezultat == 2 || rezultat == JOptionPane.CLOSED_OPTION) return;
			}
		}
		
		t.stop();
		notepad.dispose();
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>STATISTICS</code>.
	 */
	private void statisticsAction() {
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		
		int numOfCharacters = doc.getLength();
		String text = "";
		try {
			text = doc.getText(0, numOfCharacters);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		String textWithoutBlanks = getTextWithoutBlankCharacters(text);
		int numOfCharactersWithoutBlanks = textWithoutBlanks.length();
		
		int numOfLines = getNumOfLines(text);
		

		
		JOptionPane.showMessageDialog(notepad, flp.getString("stats_info").formatted(numOfCharacters, numOfCharactersWithoutBlanks, numOfLines));
	}
	
	/**
	 * Metoda koja se poziva pozivom akcije <code>UNIQUE</code>.
	 */
	private void uniqueAction() {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Caret caret = editor.getCaret();
		Document doc = editor.getDocument();
		
		int lineStart = 0;
		int lineEnd = 0;
		String text = "";
		int beginLine = 0, endLine = 0;

		try {
			if(caret.getDot()-caret.getMark() < 0) {
				beginLine = editor.getLineOfOffset(caret.getDot());
				endLine = editor.getLineOfOffset(caret.getMark());
			} else {
				beginLine = editor.getLineOfOffset(caret.getMark());
				endLine = editor.getLineOfOffset(caret.getDot());
			}
			lineStart = editor.getLineStartOffset(beginLine);
			lineEnd = editor.getLineEndOffset(endLine);
			text = doc.getText(lineStart, lineEnd-lineStart);
			doc.remove(lineStart, lineEnd-lineStart);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		String lines[] = text.split("\n");
		Set<String> unique = new TreeSet<>();
		for(int i = 0; i < lines.length; i++) {
			unique.add(lines[i]);
		}
		text = "";
		for(String line : unique) {
			text += line + "\n";
		}
		
		try {
			doc.insertString(lineStart, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metoda koja se pozove za akcije <code>SORTASC</code> ili <code>SORTDESC</code>.
	 * @param ascending zastavica za odabir vrste sortiranja
	 */
	private void sortAction(boolean ascending) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Caret caret = editor.getCaret();
		Document doc = editor.getDocument();
		
		int lineStart = 0;
		int lineEnd = 0;
		String text = "";
		int beginLine = 0, endLine = 0;

		try {
			if(caret.getDot()-caret.getMark() < 0) {
				beginLine = editor.getLineOfOffset(caret.getDot());
				endLine = editor.getLineOfOffset(caret.getMark());
			} else {
				beginLine = editor.getLineOfOffset(caret.getMark());
				endLine = editor.getLineOfOffset(caret.getDot());
			}
			lineStart = editor.getLineStartOffset(beginLine);
			lineEnd = editor.getLineEndOffset(endLine);
			text = doc.getText(lineStart, lineEnd-lineStart);
			doc.remove(lineStart, lineEnd-lineStart);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		String[] lines = text.split("\\n");
		
		lines = sort(lines, ascending);
		text = "";
		for(int i = 0; i < lines.length; i++) {
			text += lines[i] + "\n";
		}
		
		try {
			doc.insertString(lineStart, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Pomoćna metoda za sortiranje teksta.
	 * @param lines tekst koji treba sortirati
	 * @param ascending zastavica za odabir vrste sortiranja
	 * @return sortirani tekst
	 */
	private String[] sort(String[] lines, boolean ascending) {
		Locale locale = new Locale(flp.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);
		String tmp;
		
		for(int i = 0; i < lines.length; i++) {
			for(int j = i; j < lines.length; j++) {
				if(ascending) {
					if(collator.compare(lines[i], lines[j]) > 0) {
						tmp = lines[i];
						lines[i] = lines[j];
						lines[j] = tmp;
					}
				} else {
					if(collator.compare(lines[i], lines[j]) < 0) {
						tmp = lines[i];
						lines[i] = lines[j];
						lines[j] = tmp;
					}
				}
			}
		}
		
		return lines;
	}

	/**
	 * Metoda koja se poziva pozivom <code>TOUPPER</code>, <code>TOLOWER</code> ili
	 * <code>INVERT</code> akcije.
	 * @param change zastavica za odabir akcije.
	 * 0 za <code>TOUPPER</code>, 1 za <code>TOLOWER</code> i 2 za <code>INVERT</code>
	 */
	private void changeCase(int change) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Caret caret = editor.getCaret();
		Document document = editor.getDocument();
		String selectedText = null;
		
		int selected = Math.abs(caret.getDot()-caret.getMark());
		if(selected > 0) {
			selectedText = editor.getSelectedText();
			int offset = Math.min(caret.getDot(), caret.getMark());
			try {
				document.remove(offset, selected);
				switch(change) {
				case 0:
					selectedText = selectedText.toUpperCase();
					break;
				case 1:
					selectedText = selectedText.toLowerCase();
					break;
				case 2:
					selectedText = invertCase(selectedText);
					break;
				}
				document.insertString(caret.getDot(), selectedText, null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		
	}


	/**
	 * Mjenja slovo. Malo u veliko i veliko u malo.
	 * @param selectedText tekst nad kojim se metoda primjenjuje
	 * @return invertirani tekst
	 */
	private String invertCase(String selectedText) {
		char[] arr = selectedText.toCharArray();
		String tmp = "";
		for(int i = 0; i < arr.length; i++) {
			if(Character.isLetter(arr[i])) {
				if(Character.isLowerCase(arr[i])) {
					tmp += Character.toUpperCase(arr[i]);
				} else {
					tmp += Character.toLowerCase(arr[i]);
				}
			} else {
				tmp += arr[i];
			}
		}
		selectedText = tmp;
		return selectedText;
	}


	/**
	 * Vraća tekst u dokumentu bez praznina.
	 * @param text tekst koji se nalazi u dokumentu
	 * @return tekst u dokumentu bez praznina
	 */
	private String getTextWithoutBlankCharacters(String text) {
		String textWithoutBlanks = "";
		char[] arr = text.toCharArray();
		
		for(int i = 0; i < arr.length; i++) {
			if(Character.isWhitespace(arr[i])) continue;
			textWithoutBlanks += arr[i];
		}
		
		return textWithoutBlanks;
	}
	
	/**
	 * Vraća broj linija u dokumentu.
	 * @param text tekst koji se nalazi u dokumentu.
	 * @return broj linija u dokumentu
	 */
	private int getNumOfLines(String text) {
		int numOfLines = 1;
		char[] arr = text.toCharArray();
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == '\n') {
				numOfLines++;
			}
		}
		
		return numOfLines;
	}
	
	/**
	 * Pomoćna metoda za snimanje dokumenta u memoriju.
	 * @param text tekst koji se prikaziva korisniku kada želi snimiti dokument.
	 * @param document dokument koji se snima
	 */
	private void saveAs(String text, SingleDocumentModel document) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save document as");
		if(fc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) return;
		
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		if(fileName.exists()) {
			checkOption(text, filePath, document);
			return;
		}
		
		model.saveDocument(document, filePath);
	}
	
	
	/**
	 * Pomoćna metoda za odabir opcije kod snimanja dokumenta.
	 * @param text tekst koji se prikaziva korisniku kada želi snimiti dokument.
	 * @param filePath putanja dokumenta
	 * @param document dokument
	 * @return odabir korisnika kao integer
	 */
	private int checkOption(String text, Path filePath, SingleDocumentModel document) {
		String[] options = new String[] {flp.getString("yes"), flp.getString("no"), flp.getString("cancel")};
		int rezultat = JOptionPane.showOptionDialog(notepad, text, flp.getString("warning")
				, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		
		switch(rezultat) {
		case JOptionPane.CLOSED_OPTION:
			return rezultat;
		case 0:
			model.saveDocument(document, filePath);
			return rezultat;
		case 1:
			return rezultat;
		case 2:
			return rezultat;
		}
		
		return rezultat;
	}
	
}