package GUISimple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class GUISimple extends JFrame{

   // defaultTextView 메서드에 필요한 컴포넌트들 
   JPanel panelTotalView = new JPanel(); // 전체를 담은 패널  
   static JLabel labelCommandView = new JLabel(); // 내가 선택한 명령부분을 보여주는 레이블
   static JLabel labelCounterView = new JLabel(); // 지금이 몇 번째 메모리에 있는지를 보여주는 레이블
   static JLabel labelInput = new JLabel(); // 입력하는 레이블
   
   // defaultCommandBtn 메서드에 필요한 컴포넌트들
   JPanel panelTotalCommBtn = new JPanel(); // 커맨드 버튼을 담은 전체 패널
   String[] commandString = {"READ", "WRITE", "LOAD", "STORE", "ADD", "SUB", "DIVIDE", "MUL", "BRAN", "B.NEG", "B.ZERO", "HALT"}; // 버튼 배열에 담기 위한 명령들
   JButton[] commandBtn = new JButton[commandString.length]; // commandString의 문자열을 하나씩 담을 버튼 배열
   
   // defaultNumberBtn 메서드에 필요한 컴포넌트들
   JPanel panelTotalNumBtn = new JPanel(); // 숫자 버튼들을 담을 전체 패널
   String[] numberString = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "+/-", "0"," ", "Prev", "Next", "Save", "Start", "Debug", "Reset"}; // 버튼 배열에 담기 위한 명령들
   JButton[] numberBtn = new JButton[numberString.length]; // numberString의 문자열을 하나씩 담을 버튼 배열
   
   // defaultMemory 메서드에 필요한 컴포넌트들
   JPanel panelTotalMemory = new JPanel(); // 아래에 있는거 다 담을 전체 패널
   JPanel panelRegister = new JPanel(); // 그 다섯개를 넣는 패널
   JPanel panelLine = new JPanel(); // labelLine을 넣는 패널
   JPanel panelRow = new JPanel(); // labelRow를 넣는 패널
   JPanel panelMemory = new JPanel(); // laelMemory를 넣을 패널
   static JLabel labelAccumulator = new JLabel(); // Accumulator를 보여줄 레이블
   static JLabel labelInstructionCounter = new JLabel(); // instructionCounter를 보여줄 레이블
   static JLabel labelInstructionRegister = new JLabel(); // instructionRegister를 보여줄 레이블
   static JLabel labelOperationCode = new JLabel(); // operationCode를 보여줄 레이블
   static JLabel labelOperand = new JLabel(); // operand를 보여줄 레이블
   JLabel[] labelLine = new JLabel[10]; // 0~9가 들어갈 Line
   JLabel[] labelRow = new JLabel[10]; // 0, 10, 20, 30, ..., 90이 들어갈 Row
   static JLabel[] labelMemory = new JLabel[100]; // 메모리들이 들어갈 label;
   
   // defaultSpeed 메서드에 필요한 컴포넌트들
   JPanel panelSpeed = new JPanel(); // 재생속도 전체 패널
   JLabel LabelSpeedTitle = new JLabel("재생 속도"); // 그냥 재생 속도 이름 집어넣을려고 만든 거
   JSlider speedSlider = new JSlider(JSlider.HORIZONTAL , -10, 10, 0); // 슬라이더가 생기는데 최소 -10 ~ 최대 10의 값 중 0을 기본 값으로 지정
   
   // defaultSaveOrLoad
   JPanel panelTotalSave = new JPanel(); // 파일 및 DB 저장의 전체 패널
   JTextField filePathText = new JTextField("C:\\");
   static JTextField databasePathText = new JTextField("");
   JButton fileSaveBtn = new JButton("파일 저장");
   JButton fileLoadBtn = new JButton("파일 불러오기");
   JButton databaseSaveBtn = new JButton("DB 저장");
   JButton databaseLoadBtn = new JButton("DB 불러오기");
   
   
   PageEvent pe = new PageEvent(this);
   
   public GUISimple() {
      setExtendedState(JFrame.MAXIMIZED_BOTH); // 프레임의 사이즈를 (740, 960) 지정
      setLocationRelativeTo(null); // 프레임 가운데 정렬
      setLayout(null); // 프레임 레이아웃 적용 안함
      getContentPane().setBackground(Color.WHITE); // 프레임의 배경색을 흰색으로 설정
      
      defaultInput(); // defaultInput 메서드 실행
      defaultCommandBtn(); //defaultCommandBtn 메서드 실행
      defaultNumberBtn(); // defaultNumberBtn 메서드 실행
      defaultMemory(); //defaultMemory 메서드 실행
      // defaultSpeed();
      defaultSaveOrLoad();
      addKeyListener(pe);
      
      setMinimumSize(new Dimension(600, 500));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼 누르면 종료되게
      setVisible(true); // 프레임 보이기
   }
   
   // 명령부분, 카운터, 입력이 보이는 GUI 세팅 메서드
   public void defaultInput() {
      Font font = new Font("굴림", 0, 55);
      
      // panelTotal의 여러가지 옵션
      panelTotalView.setBounds(0, 0, 724, 100); 
      panelTotalView.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      panelTotalView.setLayout(null); // panelTotal의 레이아웃을 null로 지정해줘야, 그 안에 있는 컴포넌트들의 setBounds도 적용됨
      
      // panelView의 여러가지 옵션
      labelCommandView.setBounds(0, 0, 410, 100); // labelCommandView의 크기를 (0,0) 위치에 500*100 크기로 설정. 참고로 labelCommandView는 panelTotal 안에 위치하니까, (0,0) 좌표는 panelTotal 안의 좌표에서 (0,0) 위치를 말한다.
      labelCommandView.setBorder(BorderFactory.createLineBorder(Color.red)); // panelView의 테두리를 빨간색으로 설정
      labelCommandView.setText("???"); // labelCommandView가 첫 실행되면 명령어는 입력되지 않았으니 "???"를 세팅
      labelCommandView.setFont(font); //labelCommandView에 font를 지정 
      labelCommandView.setHorizontalAlignment(JLabel.CENTER); // labelCommandView의 텍스트 부분을 가운데 정렬함 
      labelCommandView.setOpaque(true); // 불투명도를 참으로 하여, 배경색을 보이도록 설정함
      labelCommandView.setBackground(Color.WHITE); // labelCommandView의 배경색을 하얀색으로 지정
      
      // labelCounterView의 여러가지 옵션
      labelCounterView.setBounds(410, 0, 120, 100); // labelCounterView의 크기를 (410,0) 위치에 120*100 크기로 설정. 참고로 labelCounterView는 panelTotal 안에 위치하므로 (410,0) 좌표는 panelTotal 안의 좌표에서 (410,0) 위치를 말한다.
      labelCounterView.setBorder(BorderFactory.createLineBorder(Color.green)); // labelCounterView의 테두리를 녹색으로 설정
      labelCounterView.setText("00?"); // labelCounterView가 첫 실행되면 메모리는 0을 가리키니 기본값으로 "00?"를 세팅함
      labelCounterView.setFont(font); // labelCounterView에 font를 지정
      labelCounterView.setHorizontalAlignment(JLabel.CENTER); // labelCounterView의 글씨를 가운데 정렬함.
      labelCounterView.setOpaque(true); // 불투명도를 참으로 하여, 배경색을 보이도록 설정함
      labelCounterView.setBackground(Color.white); // labelCounterView의 배경색을 하얀색으로 지정
      
      // labelInput의 여러가지 옵션
      labelInput.setBounds(530, 0, 194, 100); // 패널 크기를 (500,0) 위치에 224*100 크기로 설정, 참고로 labelInput은 PanelTotal 안에 위치하니까, (500, 0) 좌표는 panelTotal 안의 좌표에서 (500,0) 위치를 말한다.
      labelInput.setBorder(BorderFactory.createLineBorder(Color.blue)); // labelInput의 테두리를 파란색으로 설정
      labelInput.setText("+0000"); // labelInput이 첫 실행되면 메모리에는 +0000이 들어있으니, 기본값으로 "+0000"을 세팅함
      labelInput.setFont(font); // labelInput에 font를 지정
      labelInput.setHorizontalAlignment(JLabel.RIGHT); // labelInput의 글씨를 오른쪽 정렬함.
      labelInput.setOpaque(true); // 불투명도를 참으로 하여, 배경색을 보이도록 설정함
      labelInput.setBackground(Color.WHITE); // labelInput의 배경색을 하얀색으로 지정
      
      // add하는 부분
      add(panelTotalView);
      panelTotalView.add(labelCommandView);
      panelTotalView.add(labelCounterView);
      panelTotalView.add(labelInput);
      
   }
   
   // command 버튼들에 대한 메서드
   public void defaultCommandBtn() {
      Font font = new Font("굴림", Font.BOLD, 25); // font 변수에 "굴림", "25"의 크기를 "볼드"체로 생성
      
      // panelTotalCommBtn의 여라가지 옵션
      panelTotalCommBtn.setBounds(0, 120, 400, 340); // panelTotalCommBtn의 패널을 (0,120) 위치에 400*340 크기로 지정함.
      panelTotalCommBtn.setBorder(BorderFactory.createLineBorder(Color.black)); // panelTotalCommBtn의 테두리를 검은색으로 설정함
      panelTotalCommBtn.setLayout(new GridLayout(4, 3)); // panleTotalCommBtn의 레이아웃을 3행 4열로 구성
      
      // panelTotalCommBtn의 여러가지 옵션
      for (int i=0; i<commandString.length; i++) { // commandBtn을 commandString.length(12개)만큼 만들기 위해 반복문 사용
         commandBtn[i] = new JButton(commandString[i]); // commandBtn을 commandString[i]의 이름을 가져와서 생성
         commandBtn[i].setFont(font); // commandBtn의 폰트 지정
         commandBtn[i].addActionListener(pe); //이벤트 연결
         commandBtn[i].addKeyListener(pe); //이벤트 연결
         panelTotalCommBtn.add(commandBtn[i]); // 만들면서 해당 panelTotalCommBtn[i]를 panelTotalCommBtn에 넣기
      }
      
//      commandBtn[0].setText("<html><font color='red'>R</font>EAD</html>");
//      commandBtn[1].setText("<html><font color='red'>W</font>RITE</html>");
//      commandBtn[2].setText("<html><font color='red'>L</font>OAD</html>");
//      commandBtn[3].setText("<html><font color='red'>ST</font>ORE</html>");
//      commandBtn[4].setText("<html><font color='red'>A</font>DD</html>");
//      commandBtn[5].setText("<html><font color='red'>SU</font>B</html>");
//      commandBtn[6].setText("<html><font color='red'>D</font>IVIDE</html>");
//      commandBtn[7].setText("<html><font color='red'>M</font>UL</html>");
//      commandBtn[8].setText("<html><font color='red'>B</font>RAN</html>");
//      commandBtn[9].setText("<html><font color='red'>B</font>.<font color='red'>N</font>EG</html>");
//      commandBtn[10].setText("<html><font color='red'>B</font>.<font color='red'>Z</font>ERO</html>");
//      commandBtn[11].setText("<html><font color='red'>H</font>ALT</html>");

      // add
      add(panelTotalCommBtn);
   }
   
   // number 버튼들에 대한 메서드
   public void defaultNumberBtn() {
      Font font = new Font("굴림", Font.BOLD, 30); // font 변수에 "굴림", "30"의 크기를 "볼드"체로 생성
      
      // panelTotalNumBtn의 여러가지 옵션
      panelTotalNumBtn.setBounds(420, 120, 400, 340); // panelTotalNumBtn의 패널을 (420,120) 위치에 400*340 크기로 지정함
      panelTotalNumBtn.setBorder(BorderFactory.createLineBorder(Color.RED)); // panelTotalNumBtn의 테두리를 빨간색으로 설정함
      panelTotalNumBtn.setLayout(new GridLayout(0, 3)); // panelTotalNumBtn의 레이아웃을 3행으로 구성
      
      // numberBtn의 여러가지 옵션
      for (int i=0; i<numberString.length; i++) { // numberBtn을 numberString.length(17개)만큼 만들기 위해 반복문 사용
         numberBtn[i] = new JButton(numberString[i]); // numberBtn을 numberString[i]의 이름을 가져와서 생성
         numberBtn[i].setFont(font); // numberBtn의 폰트 지정
         numberBtn[i].addActionListener(pe);
         numberBtn[i].addKeyListener(pe);
         panelTotalNumBtn.add(numberBtn[i]); // numberBtn을 panelTotalNumBtn에 넣기
      }
      
      // add
      add(panelTotalNumBtn);
   }
   
   // 메모리에 대한 메서드
   public void defaultMemory() {
      Font font1 = new Font("굴림", Font.BOLD, 40);
      Font font2 = new Font("굴림", Font.BOLD, 20);
      
      // panelTotalMemory의 여러가지 옵션
      panelTotalMemory.setBounds(900, 0, 1000, 1000); // panelTotalMemory의 패널을 (900,0) 위치에 1000*1000 크기로 지정
      panelTotalMemory.setLayout(null); // panelTotalMemory의 레이아웃을 null로 설정해야, panelTotalMemory의 안에 있는 컴포넌트들이 setBounds로 위치 지정과 크기 조절을 할 수 있음
      panelTotalMemory.setOpaque(true); // 불투명도를 참으로 하여, 배경색을 보이도록 설정함
      panelTotalMemory.setBackground(Color.white); // panelTotalMemory의 배경색을 하얀색으로 설정 
      panelTotalMemory.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // panelTotal의 테두리를 검은색으로 설정
      
      
      // panelRegister의 여러가지 옵션
      panelRegister.setBounds(0, 0, 1000, 400); // panelRegister의 패널을 (0, 0) 위치에 1000*400 크기로 지정. 참고로 panelTotalMemory 안의 (0,0) 위치임
      panelRegister.setLayout(null); // panelRegister의 레이아웃을 null로 설정해야, panelRegister의 안에 있는 컴포넌트들이 setBounds로 위치 지정과 크기 조절을 할 수 있음
      panelRegister.setOpaque(true); // 불투명도를 참으로 하여. 배경색을 보이도록 설정
      panelRegister.setBackground(Color.WHITE); // panelRegister의 배경색을 하얀색으로 지정
      panelRegister.setBorder(BorderFactory.createLineBorder(Color.yellow)); // panelRegister의 테두리를 노란색으로 설정
      
      // panelLine의 여러가지 옵션
      panelLine.setBounds(50, 410, 950, 30); // panelLine의 패널을 (50, 410) 위치에 950*30 크기로 지정. 참고로 panelTotalMemory 안의 (50, 410) 위치
      panelLine.setLayout(new GridLayout(1, 0)); 
      panelLine.setOpaque(true);
      panelLine.setBackground(Color.white);
      panelLine.setBorder(BorderFactory.createLineBorder(Color.green));
      
      // panelRow의 여러가지 옵션
      panelRow.setBounds(20, 440, 30, 560);
      panelRow.setLayout(new GridLayout(0, 1));
      panelRow.setOpaque(true);
      panelRow.setBackground(Color.white);
      panelRow.setBorder(BorderFactory.createLineBorder(Color.blue));
      
      // panelMemory의 여러가지 옵션
      panelMemory.setBounds(50, 440, 950, 560);
      panelMemory.setLayout(new GridLayout(10, 10));
      panelMemory.setOpaque(true);
      panelMemory.setBackground(Color.white);
      panelMemory.setBorder(BorderFactory.createLineBorder(Color.cyan));
      
      // labelAccumulator의 여러가지 옵션
      labelAccumulator.setBounds(0, 0, 1000, 80);
      labelAccumulator.setText("accumulator: +0000");
      labelAccumulator.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      labelAccumulator.setFont(font1);
      
      // labelInstructionCounter의 여러가지 옵션
      labelInstructionCounter.setBounds(0, 80, 1000, 80);
      labelInstructionCounter.setText("instrcutionCounter: 00");
      labelInstructionCounter.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
      labelInstructionCounter.setFont(font1);
      
      // labelInstructionRegister의 여러가지 옵션
      labelInstructionRegister.setBounds(0, 160, 1000, 80);
      labelInstructionRegister.setText("instrcutionRegister: +0000");
      labelInstructionRegister.setBorder(BorderFactory.createLineBorder(Color.magenta));
      labelInstructionRegister.setFont(font1);
      
      // labelOperationCode의 여러가지 옵션
      labelOperationCode.setBounds(0, 240, 1000, 80);
      labelOperationCode.setText("operationCode: 00");
      labelOperationCode.setBorder(BorderFactory.createLineBorder(Color.pink));
      labelOperationCode.setFont(font1);
      
      // labelOperand의 여러가지 옵션
      labelOperand.setBounds(0, 320, 1000, 80);
      labelOperand.setText("operand: 00");
      labelOperand.setBorder(BorderFactory.createLineBorder(Color.orange));
      labelOperand.setFont(font1);
      
      // labelLine 배열의 여러가지 옵션
      for (int i=0; i<10; i++) {
         Color color = new Color(i*20, i*20, i*20);
         labelLine[i] = new JLabel("0"+i);
         labelLine[i].setBorder(BorderFactory.createLineBorder(color));
         labelLine[i].setHorizontalAlignment(JLabel.RIGHT);
         labelLine[i].setFont(font2);
         panelLine.add(labelLine[i]);
      }
      
      // labelRow 배열의 여러가지 옵션
      for (int i=0; i<10; i++) {
         Color color = new Color(i*20, i*20, i*20);
         labelRow[i] = new JLabel(i*10+"");
         labelRow[i].setBorder(BorderFactory.createLineBorder(color));
         labelRow[i].setFont(font2);
         panelRow.add(labelRow[i]);
      }
      
      // labelMemory 배열의 여러가지 옵션
      for (int i=0; i<100; i++) {
         Color color = new Color(i+2, i+2, i+2);
         labelMemory[i] = new JLabel("+0000");
         labelMemory[i].setBorder(BorderFactory.createLineBorder(color));
         labelMemory[i].setHorizontalAlignment(JLabel.RIGHT);
         labelMemory[i].setFont(font2);
         labelMemory[i].setOpaque(true);//색상사용
         labelMemory[i].setBackground(Color.white);
         labelMemory[i].addMouseListener(pe);//이벤트 연결
         panelMemory.add(labelMemory[i]);
      }
      labelMemory[0].setBackground(Color.LIGHT_GRAY);//맨 첫 주소 포커싱
      
      add(panelTotalMemory);
      panelTotalMemory.add(panelRegister);
      panelTotalMemory.add(panelLine);
      panelTotalMemory.add(panelRow);
      panelTotalMemory.add(panelMemory);
      panelRegister.add(labelAccumulator);
      panelRegister.add(labelInstructionCounter);
      panelRegister.add(labelInstructionRegister);
      panelRegister.add(labelOperationCode);
      panelRegister.add(labelOperand);
   }
   
   // public void defaultSpeed() {
   //    // panelSpeed 여러가지 옵션
   //    panelSpeed.setBounds(100, 500, 600, 200);
   //    panelSpeed.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
   //    panelSpeed.setOpaque(true);
   //    panelSpeed.setBackground(Color.white);
   //    panelSpeed.setLayout(null);
      
   //    // labelSpeedTilte 여러가지 옵션
   //    LabelSpeedTitle.setBounds(55, 20, 70, 40);
      
   //    // speedSlider 여러가지 옵션
   //    speedSlider.setMinorTickSpacing(1); // 1개씩
   //    speedSlider.setMajorTickSpacing(20); // 10개
   //    speedSlider.setPaintTicks(true); // 틱 보여줌
   //    speedSlider.setPaintLabels(true); // -10이랑 0 보여줌
   //    speedSlider.setBounds(50, 50, 500, 100);
   //    speedSlider.setOpaque(true);
   //    speedSlider.setBackground(Color.white);
      
   //    add(panelSpeed);
   //    panelSpeed.add(speedSlider);
   //    panelSpeed.add(LabelSpeedTitle);
   // }
   
   public void defaultSaveOrLoad() {
      // panelTotalSave 여러가지 옵션
      panelTotalSave.setBounds(50, 750, 800, 200);
      panelTotalSave.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
      panelTotalSave.setOpaque(true);
      panelTotalSave.setBackground(Color.white);
      panelTotalSave.setLayout(null);
      
      // filePathText 여러가지 옵션
      filePathText.setBounds(50, 50, 400, 30);
      
      // databasePathText 여러가지 옵션
      databasePathText.setBounds(50, 120, 400, 30);
      
      // fileSaveBtn 여러가지 옵션
      fileSaveBtn.setBounds(620, 50, 120, 30);
      
      // fileLoadBtn 여러가지 옵션
      fileLoadBtn.setBounds(480, 50, 120, 30);
      
      // databaseSaveBtn 여러가지 옵션
      databaseSaveBtn.setBounds(480, 120, 120, 30);
      
      // databaseLoadBtn 여러가지 옵션
      databaseLoadBtn.setBounds(620, 120, 120, 30);
      
      
      add(panelTotalSave);
      panelTotalSave.add(filePathText);
      panelTotalSave.add(databasePathText);
      panelTotalSave.add(fileSaveBtn);
      panelTotalSave.add(fileLoadBtn);
      panelTotalSave.add(databaseSaveBtn);
      panelTotalSave.add(databaseLoadBtn);
      
      SLSelect f = new SLSelect(this,filePathText);
      fileSaveBtn.addActionListener(f);
      fileLoadBtn.addActionListener(f);
      databaseSaveBtn.addActionListener(f);
      databaseLoadBtn.addActionListener(f);
   }
}