import java.awt.*; // 스윙은 awt의 컨테이너를 상속받는 클래스니깐 불러옴
import javax.swing.*;
import java.util.Stack; //이벤트처리할 때 스택사용
import java.awt.event.ActionListener; //이벤트
import java.awt.event.ActionEvent; //이벤트


public class GUI extends JFrame { // JFrame 상속받아옴

    GridBagLayout grid = new GridBagLayout(); // 레이아웃을 위함
    JPanel bt_panel = new JPanel(); // 복잡한 배치를 위한 JPanel 생성
    JPanel label_panel = new JPanel();
    //계산 레이블로 출력할 것
    JTextField inputspace = new JTextField(" "); // 텍스트를 입력받기 위함
    // #3. 버튼 이벤트 처리 때 사용
    Stack<String> number_stack = new Stack<>(); // 숫자만 저장함
    Stack<String> operator_stack = new Stack<>(); // 연산자만 저장함
    String currentNumInput = "";
    //Calculate.java에서 사용

    public GUI() {
        setLayout(null);

//#1. 화면 프레임에 대한 것
        setSize(400, 550);
        setTitle("Calculator - 기본 계산기"); // 제목설정
        setFont(new Font("Arial", Font.PLAIN, 40));
        getContentPane().setBackground(Color.WHITE); // 화면 배경색 설정
        setResizable(false); // 화면크기 조절 불가하게 만들기
        setLocationRelativeTo(null); // 화면 가운데 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫으면 프로그램도 종료시키기

//#2.버튼

        // 버튼정렬
        bt_panel.setLayout(new GridLayout(5, 5, 2, 2)); // 행과 열, 버튼사이 간격 정하기
        bt_panel.setBounds(25, 150, 350, 350); // 좌표로 직접 정렬, 크기조정하기

        String bt_names[] = { // 버튼이름
                "CE", "%", "√", "←",
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "X",
                ".", "0", "=", "/"
        }; // 버튼이 너무 많아서 panel과 배열로 만들기

        JButton bt[] = new JButton[bt_names.length]; // 버튼의 길이 -> 반복문을 통해 이름을 입력하기 위함

        for (int i = 0; i < bt.length; i++) {
            bt[i] = new JButton(bt_names[i]); // 배열 길이만큼 버튼을 만들고 차례대로 이름 넣어주기

            bt[i].setFont(new Font("Arial", Font.PLAIN, 17));

            // 고냥 이쁘게 만들려고 색상 정해주기
            if ((i >= 4) && (i <= 6))
                bt[i].setBackground(new Color(246, 234, 200));
            else if ((i >= 8) && (i <= 10))
                bt[i].setBackground(new Color(246, 234, 200));
            else if ((i >= 12) && (i <= 14))
                bt[i].setBackground(new Color(246, 234, 200));
            else if (i == 17)
                bt[i].setBackground(new Color(246, 234, 200));
            else
                bt[i].setBackground(new Color(236, 213, 227));

            bt[i].setBorderPainted(false); // 버튼 테두리 없애기
            bt_panel.add(bt[i]); // 다음 내용들을 bt_panel에 추가하기
            bt[i].setOpaque(true); // 맥에서 색이 안먹는 것 해결

            if (((i >= 4) && (i <= 6)) || ((i >= 8) && (i <= 10)) || ((i >= 12) && (i <= 14)) || (i == 17))
                bt[i].addActionListener(new Number()); // 숫자버튼을 누르면
            else
                bt[i].addActionListener(new Operator());
        }
        add(bt_panel); // 화면에 띄우기


//#3. 입출력 레이블
        // 레이아웃
        label_panel.setLayout(null);
        label_panel.setBounds(23, 30, 350, 100);
        // 레이블 표시하기
        inputspace.setBounds(0, 0, 350, 100); // JTextField 위치와 크기 설정
        label_panel.add(inputspace);

        add(label_panel);

        // 프레임이 보이도록 활성화
        setVisible(true); // 맨 뒤에 와야한다고 함 (근데 맨 뒤가 아니어도 실행됐음)
    }

    // 숫자만 받아오기
    class Number implements ActionListener { //이벤트 실행을 위한 리스너
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton number_button = (JButton) e.getSource(); //버튼에서 이벤트가 있을 때 소스를 받아오기
            currentNumInput += number_button.getText(); // 버튼에서 텍스트를 받아와서 현재 입력상태에 추가
            inputspace.setText(currentNumInput); //*** 텍스트 필드에 현재 버튼눌린 상태 표시
            System.out.println("현재 입력: " + currentNumInput); //(출력확인용)터미널에 현재입력 표시해보기
        }
    }

    // 연산자만 받아오기
    class Operator implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton operator_button = (JButton) e.getSource();
            String operator = operator_button.getText();
            //초기화 먼저 처리
            if (operator.equals("CE")) { //받아온 택스트가 CE라면
                // 초기화 작업
                currentNumInput = ""; //현재입력 초기화
                number_stack.clear(); //숫자스택비우기
                operator_stack.clear(); //연산자스택 비우기
                inputspace.setText(currentNumInput); //빈 현재상태 레이블에 띄우기

            //등호 입력됐을 때
            } else if (operator.equals("=")) { //등호입력

                if (!currentNumInput.isEmpty()) {
                    number_stack.push(currentNumInput);
                }
                calculate();
                currentNumInput = ""; //다시 초기화
            } else {
                // Handle other operators
                if (!currentNumInput.isEmpty()) {
                    number_stack.push(currentNumInput);
                    currentNumInput = "";
                }
                operator_stack.push(operator);
                inputspace.setText(operator);
            }
            System.out.println("연산자: " + operator);
            System.out.println("숫자 스택: " + number_stack);
            System.out.println("연산자 스택: " + operator_stack);
        }
    }
//#4.계산 메소드
    private void calculate() {
        while (!operator_stack.isEmpty()) {
            String operator = operator_stack.pop();
            double num2 = Double.parseDouble(number_stack.pop());
            double num1 = Double.parseDouble(number_stack.pop());
            double result = 0;

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "X":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
            }
            number_stack.push(String.valueOf(result));
        }
        String current = number_stack.pop();
        inputspace.setText(current); //레이블에 결과 출력
        System.out.println("계산 결과: " + current); // (출력확인용) 터미널에도 출력
    }


    public static void main(String[] args) {
        GUI cal = new GUI();
    }
}
