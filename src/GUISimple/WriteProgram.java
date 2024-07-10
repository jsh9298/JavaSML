package GUISimple;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class WriteProgram {
	public WriteProgram(String Filename) {
		// TODO Auto-generated constructor stub
		File file = new File(Filename+".txt"); // 파일
		String[] a = PageEvent.str;
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < a.length; i++) {
				writer.write(String.format("%02d?", i)+a[i]);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {

		} finally {

		}
	}
}
