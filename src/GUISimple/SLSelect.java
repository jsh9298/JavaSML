package GUISimple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SLSelect implements ActionListener {
	private JFileChooser jFC = new JFileChooser("C:\\");
	protected static JFrame field;
	private JTextField filePathText;

	FileNameExtensionFilter fn = new FileNameExtensionFilter("txt","txt");
	public SLSelect(JFrame field, JTextField filePathText) {
		SLSelect.field = field;
		this.filePathText = filePathText;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jFC.setMultiSelectionEnabled(false);
		jFC.setFileFilter(fn);
		if (e.getActionCommand().equals("파일 불러오기")) {
			if (jFC.showOpenDialog(field) == JFileChooser.APPROVE_OPTION) {
				filePathText.setText(jFC.getSelectedFile().toString());
				ReadProgram rp = new ReadProgram(filePathText.getText());
				if(rp.isRight) {
					PageEvent.rePa();
				}
			}
		}
		if (e.getActionCommand().equals("파일 저장")) {
			if (jFC.showSaveDialog(field) == JFileChooser.APPROVE_OPTION) {
				filePathText.setText(jFC.getSelectedFile().toString());
				new WriteProgram(filePathText.getText());
			}
		}
		if (e.getActionCommand().equals("DB 저장")||e.getActionCommand().equals("DB 불러오기")) {
			new PopUpDBList(e.getActionCommand());
		}
	}
}
