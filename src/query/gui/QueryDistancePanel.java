/**
 * 
 */
package query.gui;

import query.DistanceFilterEnum;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import query.DistanceFilter;
import query.DistanceFilterFactory;

/**
 * @author Miriam Martin
 *
 */
public class QueryDistancePanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4949836528879791783L;
	private QueryBuilderFrame frame;
	private JComboBox<DistanceFilterEnum> combo;
	private JTextField lower;
	private JTextField higher;

	QueryDistancePanel(QueryBuilderFrame qbf){
		//setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder("distance");
		
		setBorder(title);
		
		this.frame = qbf;
		
		combo = getComboBox();
		
		add(combo);
		
		lower = new JTextField(2);
		lower.setEnabled(false);
		higher = new JTextField(2);
		higher.setEnabled(false);
		
		add(lower);
		add(higher);
		
	}

	private JComboBox<DistanceFilterEnum> getComboBox() {
		/**
		 * Could this be the distance filter enums
		 */
		ComboBoxModel cbModel = new DefaultComboBoxModel(DistanceFilterEnum.values());
		
		//String[] operatorStrings = {"all","=<", ">=", "==", "between"};
		JComboBox<DistanceFilterEnum> box = new JComboBox<>(cbModel);
        box.setSelectedIndex(0);
        box.addActionListener(this);
        
        return box;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("qp.ap:--" + e.getActionCommand() + " source " + e.getSource());
		@SuppressWarnings("unchecked")
		JComboBox<String> b = (JComboBox<String>)e.getSource();
		int index = b.getSelectedIndex();
		System.out.println("qp.ap:--" + b.getSelectedIndex() + " source " + index);
		
		switch (index){
			case 0: 
				lower.setEnabled(false);
				higher.setEnabled(false);
				break;
			case 1: 
				lower.setEnabled(true);
				higher.setEnabled(false);
				break;
			case 2: 
				lower.setEnabled(true);
				higher.setEnabled(false);
				break;
			case 3: 
				lower.setEnabled(true);
				higher.setEnabled(false);
				break;
			case 4: 
				lower.setEnabled(true);
				higher.setEnabled(true);
				break;
		}
		
		
	}

	public DistanceFilter getSelectedCDistance() {
		
		System.out.println("combo: " + combo.getSelectedItem() + " lower " + lower.getText() + " higher " + (higher.getText().equals("")));	
		return DistanceFilterFactory.createDistanceFilter((DistanceFilterEnum)combo.getSelectedItem(), lower.getText(), higher.getText());
			
	}
		
		
	

}
