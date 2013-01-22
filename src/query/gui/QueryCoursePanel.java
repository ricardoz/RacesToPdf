/**
 * 
 */
package query.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import course.Course;

/**
 * @author Miriam Martin
 *
 */
public class QueryCoursePanel extends JPanel implements ActionListener {

	JComboBox<String> box;
	private QueryBuilderFrame frame;
	/**
	 * 
	 */
	private static final long serialVersionUID = -18889053314804576L;

	public QueryCoursePanel(QueryBuilderFrame queryBuilderFrame) {
		//setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

		TitledBorder title;
		title = BorderFactory.createTitledBorder("course");

		setBorder(title);

		this.frame = queryBuilderFrame;

		box = createComboBox();
		
		add(box);
	}

	private JComboBox<String> createComboBox() {
		JComboBox<String> jcb = new JComboBox<String>(Course.getCourseNames().toArray(new String[61]));
		jcb.setSelectedIndex(0);
		jcb.addActionListener(this);

		return jcb;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("qp.ap:--" + e.getActionCommand() + " source " + e.getSource());
		JComboBox<String> b = (JComboBox<String>)e.getSource();
		String s = (String) b.getSelectedItem();
		System.out.println("qp.ap:--" + b.getSelectedIndex() + " source " + s);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Course getSelectedCourse() {
		// TODO Auto-generated method stub
		return new Course((String) box.getSelectedItem());
	}

}
