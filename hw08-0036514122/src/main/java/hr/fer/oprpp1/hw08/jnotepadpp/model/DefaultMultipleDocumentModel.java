package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Implementacija sučelja {@link MultipleDocumentModel}.
 * @author dario
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	
	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> singleDocuments;
	private SingleDocumentModel currentSingleDocument;
	private List<MultipleDocumentListener> listeners;
	private ImageIcon crvena;
	private ImageIcon zelena;
	
	/**
	 * Konstruktor.
	 * @param crvena ikona za nespremljeni dokument
	 * @param zelena ikona za spremljeni dokument
	 */
	public DefaultMultipleDocumentModel(ImageIcon crvena, ImageIcon zelena) {
		singleDocuments = new ArrayList<>();
		listeners = new ArrayList<>();
		this.crvena = crvena;
		this.zelena = zelena;
		
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("Trenutno sam u tabu broj " + getSelectedIndex());
				SingleDocumentModel old = currentSingleDocument;
				if(singleDocuments.isEmpty()) {
					currentSingleDocument = null;
				} else {
					currentSingleDocument = singleDocuments.get(getSelectedIndex());
				}
				notifyDocumentChanged(old, currentSingleDocument);
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return singleDocuments.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = new DefaultSingleDocumentModel("", null);
		
		model.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int index = singleDocuments.indexOf(model);
				if(model.isModified()) {
					setIconAt(index, crvena);
				} else {
					setIconAt(index, zelena);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = singleDocuments.indexOf(model);
				setTitleAt(index, model.getFilePath().getFileName().toString());
				setToolTipText(model.getFilePath().toString());
			}
		});
		
		singleDocuments.add(model);
		insertTab("(unnamed)", null, new JScrollPane(model.getTextComponent()), "(unnamed)", getTabCount());
		newDocumentAdded(model);
		setSelectedIndex(getTabCount()-1);
		model.setModified(true);
		
		return model;
		
		
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentSingleDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(path == null) {
			throw new NullPointerException("Path must not be null!");
		}
		
		for(SingleDocumentModel model : singleDocuments) {
			if(path.equals(model.getFilePath())) {
				int index = singleDocuments.indexOf(model);
				setSelectedIndex(index);
				return model;
			}
		}
		
		String text = "";
		
		try {
			text = new String(Files.readAllBytes(Paths.get(path.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SingleDocumentModel model = new DefaultSingleDocumentModel(text, path);
		
		model.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int index = singleDocuments.indexOf(model);
				if(model.isModified()) {
					setIconAt(index, crvena);
				} else {
					setIconAt(index, zelena);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = singleDocuments.indexOf(model);
				setTitleAt(index, model.getFilePath().getFileName().toString());
				setToolTipText(model.getFilePath().toString());
			}
		});
		
		singleDocuments.add(model);
		insertTab(path.getFileName().toString(), null, 
				new JScrollPane(model.getTextComponent()), path.toString(), getTabCount());
		newDocumentAdded(model);
		setSelectedIndex(getTabCount()-1);
		
		return model;
		
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path path) {
		if(model.isModified() && path == null) {
			writeToFile(model, model.getFilePath());
		} else if(model.isModified() && path != null) {
			writeToFile(model, path);
			SingleDocumentModel old = currentSingleDocument;
			model.setFilePath(path);
			currentSingleDocument = model;
			notifyDocumentChanged(old, model);
		}
	}
	
	
	/**
	 * Pomoćna metoda koja zapisiva promjene u dokument.
	 * @param model dokument
	 * @param path putanja
	 */
	private void writeToFile(SingleDocumentModel model, Path path) {
		String text = model.getTextComponent().getText();
		File f = new File(path.toString());
		if(f.delete()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f);
			byte[] strToBytes = text.getBytes();
			fos.write(strToBytes);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = singleDocuments.indexOf(model);
		singleDocuments.remove(index);
		removeTabAt(index);
		notifyDocumentRemoval(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return singleDocuments.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return singleDocuments.get(index);
	}
	
	/**
	 * Pomoćna metoda za obaještavanje svih slušača o 
	 * dodavanju novog dokumenta.
	 */
	private void newDocumentAdded(SingleDocumentModel model) {
		for(MultipleDocumentListener listener : listeners) {
			listener.documentAdded(model);
		}
	}
	
	
	/**
	 * Pomoćna metoda za obaještavanje svih slušača o 
	 * uklanjanju postojećeg dokumenta.
	 */
	private void notifyDocumentRemoval(SingleDocumentModel model) {
		for(MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(model);
		}
	}
	
	
	/**
	 * Pomoćna metoda za obaještavanje svih slušača o 
	 * promjeni prikazivanja dokumenta.
	 */
	private void notifyDocumentChanged(SingleDocumentModel previous, SingleDocumentModel current) {
		for(MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previous, current);
		}
	}

}
