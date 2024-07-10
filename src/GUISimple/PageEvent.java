package GUISimple;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PageEvent extends KeyAdapter implements ActionListener, MouseListener {
	JFrame feild;
	JLabel labelCommandView, labelCounterView, labelInput, labelAccumulator, labelInstructionCounter,
			labelInstructionRegister, labelOperationCode, labelOperand;
	static JLabel[] labelMemory;
	int index;
	static String[] str;
	String command; // 입력시 들어오는 값 저장
	boolean isRead;
	boolean isExecute;
	int readcount;
	ArrayList<Integer> read;
	Queue<Character> a; // 입력표현 큐

	int accumulatorDebug; // 디버그모드에서 누산기 값에 변경이 일어날시 저장
	ArrayList<String> debug; // 심플트론이 while문 반복마다 accumulator를 add함. accumulator가 변경이 일어나면 다음 변경이 일어나기 전까진 쭉
	// 그시점의 값을 추가.
	boolean isDebug;// 디버그 모드 구분용
	ArrayList<Integer> debugNode;
	int debugindex;
	int temp;

	// 이벤트를 따로 빼려고 GUI에서 멀 많이 받아옴
	public PageEvent(JFrame field) {
		this.feild = field;
		this.labelCommandView = GUISimple.labelCommandView;
		this.labelCounterView = GUISimple.labelCounterView;
		this.labelInput = GUISimple.labelInput;
		this.labelAccumulator = GUISimple.labelAccumulator;
		this.labelInstructionCounter = GUISimple.labelInstructionCounter;
		this.labelInstructionRegister = GUISimple.labelInstructionRegister;
		this.labelOperationCode = GUISimple.labelOperationCode;
		this.labelOperand = GUISimple.labelOperand;
		PageEvent.labelMemory = GUISimple.labelMemory;
		str = new String[100];
		a = new LinkedList<Character>();
		debug = new ArrayList<String>();
		debugNode = new ArrayList<Integer>();
		read = new ArrayList<Integer>();
		isDebug = false;
		isRead = false;
		isExecute = false;
		accumulatorDebug = 0;
		index = 0;
		debugindex = 0;
		defaultStr();
		resetQ();
	}

	static void rePa() {
		for (int i = 0; i < str.length; i++) {
			labelMemory[i].setText(str[i]);
		}
	}

	// 입력받는 큐 초기화
	void resetQ() {
		a.clear();
		a.add('0');
		a.add('0');
		a.add('0');
		a.add('0');
	}

	// 보이는 화면 리셋
	void clearView() {
		labelAccumulator.setText("accumulator: +0000");
		labelInstructionCounter.setText("instructionCounter: 00");
		labelInstructionRegister.setText("instructionRegister: +0000");
		labelOperationCode.setText("operationCode: 00");
		labelOperand.setText("operand: 00");
		for (int i = 0; i < labelMemory.length; i++) {
			labelMemory[i].setText("+0000");
			labelMemory[i].setBackground(Color.WHITE);
		}
		labelMemory[0].setBackground(Color.LIGHT_GRAY);
	}

	void debugview() {
		for (int i = 0; i < str.length; i++) {
			labelMemory[i].setText(str[i]);
			labelMemory[i].setBackground(Color.WHITE);
		}
	}

	void reset() {
		labelCommandView.setText("???");
		labelCounterView.setText("00?");
		labelInput.setText("+0000");
		a.clear();
		debug.clear();
		debugNode.clear();
		read.clear();
		isDebug = false;
		isRead = false;
		isExecute = false;
		accumulatorDebug = 0;
		index = 0;
		debugindex = 0;
		readcount = 0;
		clearView();
		defaultStr();
		resetQ();
	}

	// 저장할배열 초기화
	void defaultStr() {
		for (int i = 0; i < str.length; i++) {
			str[i] = "+0000";
		}
	}

	public void actionPerformed(ActionEvent e) {
		command = e.getActionCommand();
		commonMethod(command);
	}

	void setCommandViewName() {
		switch (str[index].substring(1, 3)) {
			case "10":
				labelCommandView.setText("READ");
				break;
			case "11":
				labelCommandView.setText("WRITE");
				break;
			case "20":
				labelCommandView.setText("LOAD");
				break;
			case "21":
				labelCommandView.setText("STORE");
				break;
			case "30":
				labelCommandView.setText("ADD");
				break;
			case "31":
				labelCommandView.setText("SUB");
				break;
			case "32":
				labelCommandView.setText("DIVIDE");
				break;
			case "33":
				labelCommandView.setText("MUL");
				break;
			case "40":
				labelCommandView.setText("BRAN");
				break;
			case "41":
				labelCommandView.setText("B.NEG");
				break;
			case "42":
				labelCommandView.setText("B.ZERO");
				break;
			case "43":
				labelCommandView.setText("HALT");
				break;
			default:
				labelCommandView.setText("???");
		}
	}

	// 심-쁠 도론
	public void simpleTronexcutation() {
		final int READ = 10;
		final int WRITE = 11;
		final int LOAD = 20;
		final int STORE = 21;
		final int ADD = 30;
		final int SUBTRACT = 31;
		final int DIVIDE = 32;
		final int MULTIPLY = 33;
		final int BRANCH = 40;
		final int BRANCHNEG = 41;
		final int BRANCHZERO = 42;
		final int HALT = 43;
		int accumulator = 0;
		int instructionCounter = 0;
		int instructionRegister = 0;
		int operationCode = 0;
		int operand = 0;
		accumulatorDebug = 0;
		debug.clear();
		int[] Memory = new int[100];
		for (int i = 0; i < Memory.length; i++) {
			Memory[i] = Integer.parseInt(str[i]);
		}
		while (true) {
			instructionRegister = Memory[instructionCounter];
			operationCode = instructionRegister / 100;
			operand = instructionRegister % 100;
			if (isExecute) {
				debug.add(String.valueOf(accumulatorDebug));
			}
			switch (operationCode) {
				case READ:
					if (isExecute == false) {
						isRead = true;
					} else {
						if (!debugNode.contains(instructionCounter)) {
							debugNode.add(instructionCounter);
						}
					}
					read.add(operand);
				case WRITE:
					labelCommandView.setText(String.valueOf(Memory[operand]));
					debugNode.add(instructionCounter);
					break;
				case LOAD:
					accumulator = Memory[operand];
					accumulatorDebug = accumulator;
					debugNode.add(instructionCounter);
					break;
				case STORE:
					Memory[operand] = accumulator;
					str[operand] = String.format("%+05d", Memory[operand]);
					debugNode.add(instructionCounter);
					break;
				case ADD:
					accumulator += Memory[operand];
					accumulatorDebug = accumulator;
					debugNode.add(instructionCounter);
					break;
				case SUBTRACT:
					accumulator -= Memory[operand];
					accumulatorDebug = accumulator;
					debugNode.add(instructionCounter);
					break;
				case DIVIDE:
					if (Memory[operand] == 0 || accumulator == 0) {
						JOptionPane.showMessageDialog(feild, "0으로 나누기", "오류", JOptionPane.INFORMATION_MESSAGE);
						operationCode = HALT;
						dump(accumulator, instructionCounter, instructionRegister, operationCode, operand, labelMemory,
								Memory);
						break;
					} else {
						accumulator /= Memory[operand];
						accumulatorDebug = accumulator;
					}
					debugNode.add(instructionCounter);
					break;
				case MULTIPLY:
					accumulator *= Memory[operand];
					accumulatorDebug = accumulator;
					debugNode.add(instructionCounter);
					break;
				case BRANCH:
					debugNode.add(instructionCounter);
					instructionCounter = operand - 1;
					debug.add(String.valueOf(accumulatorDebug));
					break;
				case BRANCHNEG:
					if (accumulator < 0) {
						debugNode.add(instructionCounter);
						instructionCounter = operand - 1;
						if (Memory[instructionCounter] % 100 == 10) {
							isExecute= false;
						}
						debug.add(String.valueOf(accumulatorDebug));
					}
					break;
				case BRANCHZERO:
					if (accumulator == 0) {
						debugNode.add(instructionCounter);
						instructionCounter = operand - 1;
						if (Memory[instructionCounter] % 100 == 10) {
							isExecute= false;
						}
						debug.add(String.valueOf(accumulatorDebug));
					}
					break;
				case HALT:
					if (!isExecute) {
						JOptionPane.showMessageDialog(feild, "실행", "알림", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(feild, "실행 완료", "알림", JOptionPane.INFORMATION_MESSAGE);
					}
					dump(accumulator, instructionCounter, instructionRegister, operationCode, operand, labelMemory,
							Memory);
					debugNode.add(instructionCounter);
					isExecute = true;
					break;
			}
			if (operationCode == HALT) {
				break;
			}
			if (accumulator < -9999 || accumulator > 9999) {
				JOptionPane.showMessageDialog(feild, "누산기공간 부족", "오류", JOptionPane.INFORMATION_MESSAGE);
				dump(accumulator, instructionCounter, instructionRegister, operationCode, operand, labelMemory, Memory);
				break;
			}

			instructionCounter++;
		}

	}

	// 땀프
	void dump(int accumulator, int instructionCounter, int instructionRegister, int operationCode, int operand,
			JLabel[] labelMemory, int[] Memory) {
		labelAccumulator.setText(String.format("accumulator: %+05d", accumulator));
		labelInstructionCounter.setText(String.format("instrcutionCounter: %02d", instructionCounter));
		labelInstructionRegister.setText(String.format("instrcutionRegister: %+05d", instructionRegister));
		labelOperationCode.setText(String.format("operationCode: %02d", operationCode));
		labelOperand.setText(String.format("operand: %02d", operand));
		for (int i = 0; i < Memory.length; i++) {
			labelMemory[i].setText(String.format("%+05d", Memory[i]));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			JLabel eJL = (JLabel) e.getComponent();
			eJL.setName("temp");
			if (isDebug) {
				int tmp = debugNode.get(debugindex);
				labelMemory[tmp].setBackground(Color.WHITE);
				for (int i = 0; i < labelMemory.length; i++) {
					if (labelMemory[i].getName() == eJL.getName()) {
						if (i >= debugNode.size()) {
							JOptionPane.showMessageDialog(feild, "실행 범위 밖", "알림", JOptionPane.INFORMATION_MESSAGE);
							break;
						} else {
							tmp = i;
						}
					}
				}
				for (int i = 0; i < debugNode.size(); i++) {
					if (debugNode.get(i) == tmp) {
						debugindex = i;
						break;
					}
				}
				eJL.setName("");
				labelMemory[tmp].setBackground(Color.LIGHT_GRAY);
				labelMemory[tmp].setText(str[tmp]);
				labelInput.setText(str[tmp]);
				labelCounterView.setText(String.format("%02d?", tmp));
			} else {
				labelMemory[index].setBackground(Color.WHITE);
				for (int i = 0; i < labelMemory.length; i++) {
					if (labelMemory[i].getName() == eJL.getName()) {
						index = i;
					}
				}
				eJL.setName("");
				labelMemory[index].setBackground(Color.LIGHT_GRAY);
				labelMemory[index].setText(str[index]);
				labelInput.setText(str[index]);
				labelCounterView.setText(String.format("%02d?", index));
			}
			switch (isDebug ? str[debugindex].substring(1, 3) : str[index].substring(1, 3)) {
				case "10":
					labelCommandView.setText("READ");
					break;
				case "11":
					labelCommandView.setText("WRITE");
					break;
				case "20":
					labelCommandView.setText("LOAD");
					break;
				case "21":
					labelCommandView.setText("STORE");
					break;
				case "30":
					labelCommandView.setText("ADD");
					break;
				case "31":
					labelCommandView.setText("SUB");
					break;
				case "32":
					labelCommandView.setText("DIVIDE");
					break;
				case "33":
					labelCommandView.setText("MUL");
					break;
				case "40":
					labelCommandView.setText("BRAN");
					break;
				case "41":
					labelCommandView.setText("B.NEG");
					break;
				case "42":
					labelCommandView.setText("B.ZERO");
					break;
				case "43":
					labelCommandView.setText("HALT");
					break;
				default:
					labelCommandView.setText("???");
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// ============================================================ 키보드 입력
	// ===================================================================
	String commandName = "";
	boolean keyInputStart = true;
	boolean isShift = false;
	boolean isS = false;
	boolean isBran = false;
	boolean isMemoryOverwrite = true;
	boolean isWrite = false;
	int writeCount = 0;
	boolean isLabel = false;
	boolean isMinus = false;
	ArrayList<String> readIndex = new ArrayList<>();

	public void keyPressed(KeyEvent e) {
		String setCommand = "";
		isLabel = false;
		System.out.println("KeyEvent: " + e);
		if (e.getKeyCode() != 10)
			if (isShift)
				isShift = false;
		if (e.getKeyCode() != 90 && e.getKeyCode() != 78)
			if (isBran)
				isBran = false;
		if (e.getKeyCode() != 84 && e.getKeyCode() != 85)
			if (isS)
				isS = false;

		switch (e.getKeyCode()) { // 키 입력 Start 지점
			case 8: // Backspace
				setCommand = "Reset";
				break;
			case 10: // enter
				if (isShift) {
					setCommand = "Start";
				} else {
					setCommand = "Save";
				}
				break;
			case 16: // shitf
				isShift = true;
				setCommand = "shift";
				break;
			// sinkHole(setCommand);
			case 37: // <-
				setCommand = "Prev";
				break;
			case 38: // ↑
				setCommand = "Prev";
				break;
			case 39: // ->
				setCommand = "Next";
				break;
			case 40:
				setCommand = "Next";
				break;
			case 65: // A
				setCommand = "ADD";
				break;
			case 66: // B
				setCommand = "BRAN";
				isBran = true;
				break;
			case 68: // D
				setCommand = "DIVIDE";
				break;
			case 72: // H
				setCommand = "HALT";
				break;
			case 76: // L
				setCommand = "LOAD";
				break;
			case 77: // M
				setCommand = "MUL";
				break;
			case 78: // N
				if (isBran) {
					setCommand = "B.NEG";
				}
				break;
			case 82: // R
				setCommand = "READ";
				break;
			case 83: // S
				setCommand = "S";
				isS = true;
				break;
			case 84: // T
				if (isS) {
					setCommand += "STORE";
				}
				break;
			case 85: // U
				if (isS) {
					setCommand += "SUB";
				}
				break;
			case 87: // W
				setCommand = "WRITE";
				break;
			case 90: // Z
				if (isBran) {
					setCommand = "B.ZERO";
				}
				break;
			default:
				// sinkHole(setCommand);
				setCommand += e.getKeyChar();
				break;
		}
		if (setCommand.equals("")) {
			// System.out.println("setCommand is null?" + setCommand);
		}
		// System.out.println("setCommand is " + setCommand);
		commonMethod(setCommand);
	}

	public void commonMethod(String value) {
		command = value;
		if (command.equals("Reset")) {
			reset();
		}
		if (isRead == false) {
			if (isDebug == false) {
				switch (command) {
					case "READ":
						a.poll();
						a.poll();
						a.add('1');
						a.add('0');
						labelCommandView.setText(command);
						break;
					case "WRITE":
						a.poll();
						a.poll();
						a.add('1');
						a.add('1');
						labelCommandView.setText(command);
						break;
					case "LOAD":
						a.poll();
						a.poll();
						a.add('2');
						a.add('0');
						labelCommandView.setText(command);
						break;
					case "STORE":
						a.poll();
						a.poll();
						a.add('2');
						a.add('1');
						labelCommandView.setText(command);
						break;
					case "ADD":
						a.poll();
						a.poll();
						a.add('3');
						a.add('0');
						labelCommandView.setText(command);
						break;
					case "SUB":
						a.poll();
						a.poll();
						a.add('3');
						a.add('1');
						labelCommandView.setText(command);
						break;
					case "DIVIDE":
						a.poll();
						a.poll();
						a.add('3');
						a.add('2');
						labelCommandView.setText(command);
						break;
					case "MUL":
						a.poll();
						a.poll();
						a.add('3');
						a.add('3');
						labelCommandView.setText(command);
						break;
					case "BRAN":
						a.poll();
						a.poll();
						a.add('4');
						a.add('0');
						labelCommandView.setText(command);
						break;
					case "B.NEG":
						a.poll();
						a.poll();
						a.add('4');
						a.add('1');
						labelCommandView.setText(command);
						break;
					case "B.ZERO":
						a.poll();
						a.poll();
						a.add('4');
						a.add('2');
						labelCommandView.setText(command);
						break;
					case "HALT":
						a.clear();
						a.add('4');
						a.add('3');
						a.add('0');
						a.add('0');
						labelCommandView.setText(command);
						break;
					default:
						if ((48 <= command.charAt(0) && command.charAt(0) <= 57)) {
							a.add(command.charAt(0));
						}
				}
			}
			if (a.size() > 4) {
				a.remove();
			}
			// a.stream().map(String::valueOf).collect(Collectors.joining()) -> 큐입력을 문자열로
			labelInput.setText(String.format("%+05d",
					Integer.parseInt(a.stream().map(String::valueOf).collect(Collectors.joining()))));
			labelMemory[index].setText(String.format("%+05d",
					Integer.parseInt(a.stream().map(String::valueOf).collect(Collectors.joining()))));
			if (command.equals("Save") && isDebug == false) {
				str[index] = labelMemory[index].getText();
				labelMemory[index].setBackground(Color.GREEN); // 해당위치의 작성값이 저장됬다고 표시, next나 prev누르면 white로 돌아감
			}
			if (command.equals("Next")) {
				if (index < 99) {
					if (isDebug) {
						try {
							labelMemory[debugNode.get(debugindex)].setText(str[debugNode.get(debugindex)]);
							temp = debugindex;
							debugindex++;
							labelMemory[debugNode.get(temp)].setBackground(Color.WHITE);
							labelMemory[debugNode.get(debugindex)].setBackground(Color.LIGHT_GRAY); // 현재 접근 위치 표현
							labelMemory[debugNode.get(debugindex)].setText(str[debugNode.get(debugindex)]);
							labelInput.setText(str[debugNode.get(debugindex)]);
							labelCounterView.setText(String.format("%02d?", debugNode.get(debugindex)));
							labelAccumulator.setText("accumulator: " + debug.get(debugNode.get(debugindex)));
						} catch (IndexOutOfBoundsException arle) {
							JOptionPane.showMessageDialog(feild, "마지막입니다.", "알림", JOptionPane.INFORMATION_MESSAGE);// 알림창
							debugindex--;
							temp = debugindex - 1;
							labelMemory[debugNode.get(temp)].setBackground(Color.WHITE);
							labelMemory[debugNode.get(debugindex)].setBackground(Color.LIGHT_GRAY);
						}
						labelInstructionCounter
								.setText(String.format("instrcutionCounter: %02d", debugNode.get(debugindex)));
						labelInstructionRegister.setText("instrcutionRegister: " + str[debugNode.get(debugindex)]);
						labelOperationCode.setText("operationCode: " + str[debugNode.get(debugindex)].substring(1, 3));
						labelOperand.setText("operand: " + str[debugNode.get(debugindex)].substring(3, 5));
					} else {
						labelMemory[index].setText(str[index]);
						index++;
						labelMemory[index - 1].setBackground(Color.WHITE);
						labelMemory[index].setBackground(Color.LIGHT_GRAY); // 현재 접근 위치 표현
						labelMemory[index].setText(str[index]);
						labelInput.setText(str[index]);
						labelCounterView.setText(String.format("%02d?", index));
					}
					switch (isDebug ? str[debugNode.get(debugindex)].substring(1, 3) : str[index].substring(1, 3)) {
						case "10":
							labelCommandView.setText("READ");
							break;
						case "11":
							labelCommandView.setText("WRITE");
							break;
						case "20":
							labelCommandView.setText("LOAD");
							break;
						case "21":
							labelCommandView.setText("STORE");
							break;
						case "30":
							labelCommandView.setText("ADD");
							break;
						case "31":
							labelCommandView.setText("SUB");
							break;
						case "32":
							labelCommandView.setText("DIVIDE");
							break;
						case "33":
							labelCommandView.setText("MUL");
							break;
						case "40":
							labelCommandView.setText("BRAN");
							break;
						case "41":
							labelCommandView.setText("B.NEG");
							break;
						case "42":
							labelCommandView.setText("B.ZERO");
							break;
						case "43":
							labelCommandView.setText("HALT");
							break;
						default:
							labelCommandView.setText("???");
					}
					resetQ();
				} else {
					JOptionPane.showMessageDialog(feild, "메모리 공간이 부족합니다.", "오류", JOptionPane.INFORMATION_MESSAGE);
					index = 99;
				}
			}
			if (command.equals("Prev")) {
				if (index > 0) {
					if (isDebug) {
						try {
							labelMemory[debugNode.get(debugindex)].setText(str[debugNode.get(debugindex)]);
							temp = debugindex;
							debugindex--;
							labelMemory[debugNode.get(temp)].setBackground(Color.WHITE);
							labelMemory[debugNode.get(debugindex)].setBackground(Color.LIGHT_GRAY); // 현재 접근 위치 표현
							labelMemory[debugNode.get(debugindex)].setText(str[debugNode.get(debugindex)]);
							labelInput.setText(str[debugNode.get(debugindex)]);
							labelCounterView.setText(String.format("%02d?", debugNode.get(debugindex)));
							labelAccumulator.setText("accumulator: " + debug.get(debugNode.get(debugindex)));
						} catch (IndexOutOfBoundsException arle) {
							JOptionPane.showMessageDialog(feild, "잘못된 접근.", "오류", JOptionPane.INFORMATION_MESSAGE);
							debugindex = 0;
						}
						labelInstructionCounter
								.setText(String.format("instrcutionCounter: %02d", debugNode.get(debugindex)));
						labelInstructionRegister.setText("instrcutionRegister: " + str[debugNode.get(debugindex)]);
						labelOperationCode.setText("operationCode: " + str[debugNode.get(debugindex)].substring(1, 3));
						labelOperand.setText("operand: " + str[debugNode.get(debugindex)].substring(3, 5));
					} else {
						labelMemory[index].setText(str[index]);
						index--;
						labelMemory[index + 1].setBackground(Color.WHITE);
						labelMemory[index].setBackground(Color.LIGHT_GRAY);
						labelMemory[index].setText(str[index]);
						labelInput.setText(str[index]);
						labelCounterView.setText(String.format("%02d?", index));
					}
					switch (isDebug ? str[debugNode.get(debugindex)].substring(1, 3) : str[index].substring(1, 3)) {
						case "10":
							labelCommandView.setText("READ");
							break;
						case "11":
							labelCommandView.setText("WRITE");
							break;
						case "20":
							labelCommandView.setText("LOAD");
							break;
						case "21":
							labelCommandView.setText("STORE");
							break;
						case "30":
							labelCommandView.setText("ADD");
							break;
						case "31":
							labelCommandView.setText("SUB");
							break;
						case "32":
							labelCommandView.setText("DIVIDE");
							break;
						case "33":
							labelCommandView.setText("MUL");
							break;
						case "40":
							labelCommandView.setText("BRAN");
							break;
						case "41":
							labelCommandView.setText("B.NEG");
							break;
						case "42":
							labelCommandView.setText("B.ZERO");
							break;
						case "43":
							labelCommandView.setText("HALT");
							break;
						default:
							labelCommandView.setText("???");
					}
					resetQ();
				} else {
					JOptionPane.showMessageDialog(feild, "잘못된 접근.", "오류", JOptionPane.INFORMATION_MESSAGE);
					index = 0;
				}
			}
			if (command.equals("Start") || command.equals("Debug")) { // Start 원본 지점
				try {
					if (readcount != 0) {
						readcount = 0;
						read.clear();
					}
					if (debug.size() != 0) {
						debug.clear();
						debugNode.clear();
						accumulatorDebug = 0;
					}
					if (command.equals("Debug")) {
						isDebug = true;
						isExecute = false;
						simpleTronexcutation();
						debugview();
						index = 0;
					} else {
						isDebug = false;
						isExecute = false;
						simpleTronexcutation();
						index++;
					}

				} catch (ArrayIndexOutOfBoundsException dqrqr) {// 4300을 작성하지 않은채로 실행하면 발생
					JOptionPane.showMessageDialog(feild, "종료 지점을 찾기 못했습니다.", "오류", JOptionPane.INFORMATION_MESSAGE);
					isExecute = false;
					isRead = false;
					readcount = 0;
					read.clear();
				}
			}
		}
		if (isRead == true && isExecute == true) {
			index = read.get(readcount);
			labelCounterView.setText(String.format("%02d?", index));
			if ((48 <= command.charAt(0) && command.charAt(0) <= 57) || command.equals("+/-")
					|| command.equals("Save")) {
				if (48 <= command.charAt(0) && command.charAt(0) <= 57) {
					a.add(command.charAt(0));
				}
				if (a.size() > 4) {
					a.remove();
				}
				str[index] = String.valueOf(labelMemory[index].getText().charAt(0))
						+ a.stream().map(String::valueOf).collect(Collectors.joining());
				labelInput.setText(str[index]);
				labelMemory[index].setText(str[index]);
				if (command.equals("+/-")) {
					int temp2 = Integer.parseInt(str[index]);
					temp2 *= -1;
					str[index] = String.format("%+05d", temp2);
					labelInput.setText(str[index]);
					labelMemory[index].setText(str[index]);
				}

				if (command.equals("Save")) {
					labelMemory[index].setBackground(Color.GREEN);
					index++;
					readcount++;
					resetQ();
				}

			}
			if (readcount == read.size()) {
				simpleTronexcutation();
				int count = 0;
				Iterator<Integer> iterator = debugNode.iterator();
				while (iterator.hasNext()) {
					int element = iterator.next();
					if (element == 0) {
						count++;
						// 두 번째 0을 찾으면 그 이전의 모든 요소를 제거
						if (count == 2) {
							iterator.remove();
							break;
						}
					} else {
						// 0이 아닌 경우에는 계속 진행
						iterator.remove();
					}
				}
				isRead = false;
			}
		}
	}
}