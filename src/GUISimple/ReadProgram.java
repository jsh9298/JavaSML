package GUISimple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

public class ReadProgram {
	boolean isRight = false;

	public ReadProgram(String Filename) {
		// TODO Auto-generated constructor stub
		File reside = new File(Filename); // 파일
		try {
			BufferedReader reader = new BufferedReader(new FileReader(reside));
			String[] array = new String[100];
			String temp;
			for (int i = 0; i < array.length; i++) {
				temp = reader.readLine();
				if (temp.matches("(\\d{2}\\D+\\d{4})") == false) {
					JOptionPane.showMessageDialog(SLSelect.field, "포맷에 안맞는 문자열.", "오류", JOptionPane.INFORMATION_MESSAGE);
					isRight = false;
					break;
				} else {
					array[i] = temp.replaceAll("(\\d{2}\\D)", "");
					isRight = true;
				}
			}
			if (isRight) {
				PageEvent.str = array;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

}