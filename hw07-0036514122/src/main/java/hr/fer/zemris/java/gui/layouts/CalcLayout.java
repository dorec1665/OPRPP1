package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;



/**
 * Upravljač razmješaja za kalkulator.
 * @author dario
 *
 */
public class CalcLayout implements LayoutManager2{
	
	/**
	 * Maksimalan broj redaka u razmještaju.
	 */
	private final int MAX_ROWS = 5;
	/**
	 * Maksimalan broj stupaca u razmještaju.
	 */
	private final int MAX_COLUMNS = 7;
	/**
	 * Komponente razmještaja.
	 */
	private Map<RCPosition, Component> components;
	
	/**
	 * Razmak među komponentama.
	 */
	private int gap;

	/**
	 * Konstruktor u kojem se razmak među komponentama postavlja na 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Konstruktor.
	 * @param gap razmak među komponentama
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		components = new HashMap<>();
	}
	
	

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		Iterator<Map.Entry<RCPosition, Component>> iterator = components.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<RCPosition, Component> c = iterator.next();
			if(comp.equals(c.getValue())) {
				iterator.remove();
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize((c) -> c.getPreferredSize(), 0);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize((c) -> c.getMaximumSize(), 1);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize((c) -> c.getMinimumSize(), 2);
	}
	
	
	/**
	 * Vraća preferiranu, minimalnu ili maksimalnu veličinu upravljača razmještaja
	 * u ovisnosti tko je pozvao metodu.
	 * @param function računa dimenziju komponente
	 * @param whichLayoutSize pozivatelj funkcije
	 * @return preferiranu, minimalnu ili maksimalnu veličinu upravljača razmještaja
	 * u ovisnosti tko je pozvao metodu
	 */
	private Dimension getLayoutSize(Function<Component, Dimension> function, int whichLayoutSize) {
		int width = 0;
		int height = 0;
		Iterator<Map.Entry<RCPosition, Component>> iterator = components.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<RCPosition, Component> c = iterator.next();
			Component comp = c.getValue();
			RCPosition position = c.getKey();
			Dimension d = function.apply(comp);
			int componentWidth = (int) d.getWidth();
			int componentHeight = (int) d.getHeight();
			if(position.equals(RCPosition.firstPosition)) {
				componentWidth = (int) (d.getWidth() - 4*gap)/5;
			}
			
			if((width < componentWidth && (whichLayoutSize == 0 || whichLayoutSize == 2)) || 
					(width > componentWidth && whichLayoutSize == 1)) { 
				width = componentWidth;
			}
			if((height < componentHeight && (whichLayoutSize == 0 || whichLayoutSize == 2)) ||
					(width > componentWidth && whichLayoutSize == 1)) {
				height = componentHeight;
			}
		}
		
		width = width*MAX_COLUMNS + gap*(MAX_COLUMNS-1);
		height = height*MAX_ROWS + gap*(MAX_ROWS-1);
		
		return new Dimension(width, height);
		
	}

	@Override
	public void layoutContainer(Container parent) {
		double componentHeight = (parent.getHeight() - gap*(MAX_ROWS-1))/MAX_ROWS;
		double componentWidth = (parent.getWidth() - gap*(MAX_COLUMNS-1))/MAX_COLUMNS;
		
		Iterator<Map.Entry<RCPosition, Component>> iterator = components.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<RCPosition, Component> c = iterator.next();
			Component comp = c.getValue();
			RCPosition position = c.getKey();
			if(position.equals(RCPosition.firstPosition)) {
				double firstCompWidth = 5*componentWidth + 4*gap;
				comp.setBounds((int) ((firstCompWidth+gap)*(position.getColumn()-1)),
						(int) ((componentHeight+gap)*(position.getRow()-1)), (int) firstCompWidth, (int) componentHeight);
				continue;
			}
			comp.setBounds((int)((componentWidth+gap)*(position.getColumn()-1)),
					(int) ((componentHeight+gap)*(position.getRow()-1)), (int) componentWidth, (int) componentHeight);
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) {
			throw new NullPointerException();
		}
		
		RCPosition position = null;
		if(constraints instanceof String) {
			position = RCPosition.parse((String) constraints);
		} else if(constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Invalid argument.");
		}
		int r = position.getRow();
		int c = position.getColumn();
		if(components.containsKey(position) || r < 1 || r > MAX_ROWS || c < 1 || c > MAX_COLUMNS) {
			throw new CalcLayoutException("Cannot add this component!");
		}
		if((r == 1 && (c >= 2 && c <= 5))) {
			throw new CalcLayoutException("Cannot add this component!");
		}
		
		components.put(position, comp);
		
	}


	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub	
	}

}
