import java.awt.*; // 스윙은 awt의 컨테이너를 상속받는 클래스니깐 불러옴
import javax.swing.*;
import java.util.Arrays;

import java.util.Stack; //이벤트처리할 때 스택사용

import java.awt.event.ActionListener; //이벤트
import java.awt.event.ActionEvent; //이벤트

public class GUI extends JFrame { // JFrame 상속받아옴

    GridBagLayout grid = new GridBagLayout(); // 레이아웃을 위함
    JPanel bt_panel = new JPanel(); // 복잡한 배치를 위한 JPanel 생성
    JPanel consol_panel = new JPanel();
    //계산 레이블로 출력할 것

    JTextField inputspace = new JTextField(); // 텍스트를 입력받기 위함
    JLabel outputspace =new JLabel();

    // 3. 버튼 이벤트 처리 때 사용
    Stack<String> number_stack = new Stack<>(); // 숫자만 저장함
    Stack<String> operator_stack = new Stack<>(); // 연산자만 저장함
    Stack<String> expression = new Stack<>();
    String currentNumInput = "";
    String str_expression = "";
    String last_expression = "";



    public GUI() {
        setLayout(null);//프레임좌표값

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
                "AC", "\uD83D\uDC40", "♡", "←",
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "x",
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

            if (((i >= 4) && (i <= 6)) || ((i >= 8) && (i <= 10)) || ((i >= 12) && (i <= 14)) || (i == 16)|| (i == 17))
                bt[i].addActionListener(new Number()); // 숫자버튼을 누르면
            else if(i==18){
                bt[i].addActionListener(new equal());
            }
            else
                bt[i].addActionListener(new Operator());
        }
        add(bt_panel); // 화면에 띄우기
        bt[0].setFont(new Font("Arial", Font.PLAIN, 15));
        bt[1].setFont(new Font("Arial", Font.PLAIN, 15));


//#3. 입출력 레이블
        // 레이아웃
        //consol_panel.setLayout(new BorderLayout());
        //consol_panel.setBounds(//, 30, 350, 100); //고정

        // 레이블 표시하기
        inputspace.setEnabled(false);
        inputspace.setBounds(23, 80,350, 50); // JTextField 위치와 크기 설정
        inputspace.setCaretColor(Color.BLACK);
        inputspace.setFont(new Font("Arial", Font.BOLD, 40));
        consol_panel.add(BorderLayout.CENTER,inputspace);
        add(inputspace);

        outputspace.setBounds(30,30,400,30);
        outputspace.setFont(new Font("Arial", Font.BOLD,20 ));
        consol_panel.add(outputspace);
        add(outputspace);


        // 프레임이 보이도록 활성화
        setVisible(true); // 맨 뒤에 와야한다고 함 (근데 맨 뒤가 아니어도 실행됐음)
    }

//#4. 이벤트리스너

    // #4-1. 숫자만 받아오기
    class Number implements ActionListener { //이벤트 실행을 위한 리스너
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton number_button = (JButton) e.getSource(); //버튼에서 이벤트가 있을 때 소스를 받아오기
            currentNumInput += number_button.getText(); // 버튼에서 텍스트를 받아온 것
            inputspace.setText(currentNumInput); //*** 텍스트 필드에 누른 버튼 출력

            str_expression += number_button.getText();
            expression.add(str_expression);

            //(출력확인용)터미널에 표시
            System.out.println("현재 숫자 입력: " + currentNumInput);
            System.out.println("숫자 스택: " + number_stack);
            System.out.println("연산자 스택: " + operator_stack);
            System.out.println("방정식 : " + str_expression);


        }
    }

    // #4-2. 등호(=)만 받아오기
    class equal implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton equal_button = (JButton) e.getSource();
            String equal = equal_button.getText();

            //str_expression += equal_button.getText();
            //expression.add(str_expression);

           if (equal.equals("=")) {  //결과값 출력

                if (!currentNumInput.isEmpty()) { //연산자키를 눌렀는데, 입력받은 숫자가 있을 때
                    number_stack.push(currentNumInput); //넘버스택에 입력받았던 숫자 넣기
                }
                last_expression = expression.peek();
                System.out.println(last_expression);
               System.out.println(str_expression);

                calculate();
                reset(); //수행 후 다시 초기화
            }


            System.out.println("연산자: " + equal);
            System.out.println("숫자 스택: " + number_stack);
            System.out.println("연산자 스택: " + operator_stack);
            System.out.println("방정식 : " + str_expression);

        } // {}action performe
    } // {}operator event listener

    // #4-3. 나머지연산자 받아오기
    class Operator implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton operator_button = (JButton) e.getSource();
            String operator = operator_button.getText();

            if(!Arrays.asList("AC","\uD83D\uDC40","♡","←").contains(operator)) {
                str_expression += operator_button.getText();
                expression.add(str_expression);
            }

            System.out.println("현재 스택: " + expression);

            //초기화 먼저 처리
            if (operator.equals("AC")) {
                reset();
                expression.clear();
                str_expression = "";
                outputspace.setText("");
                last_expression = "";

                inputspace.setText(currentNumInput);
                //버튼으로 부터 입력받은 것 띄우기 -> 리셋해서 빈 화면 출력
            }
            else if(operator.equals("♡")){
                inputspace.setText("\uD83D\uDC8C  ");
                outputspace.setText("집중하면 할 수 있어♡");

            }
            //식 출력
            else if (operator.equals("\uD83D\uDC40")) { //받아온 택스트가 "식 불러오기"라면
                if(last_expression.isEmpty()){
                    outputspace.setText(("** 등호(=)를 먼저 눌러주세요 **"));
                }
                else{
                    str_expression = expression.peek();
                    outputspace.setText(str_expression);
                }

                System.out.println("식 : "+ str_expression);
            }
            //delete
            else if (operator.equals("←")) {
                if (operator_stack.isEmpty()) { // number도 empty임

                    if (expression.isEmpty()) {
                        return; // 모두 비어있음

                    } else { //숫자만 입력되어있는 경우
                        expression.pop();
                        str_expression = expression.peek();
                        if (expression.isEmpty())
                            return;
                        else{
                                expression.pop();
                                inputspace.setText("");
                            }
                        }

                        inputspace.setText(str_expression);

                }
                    else { //operator에 뭐가 있음
                    String check = expression.peek();
                    expression.pop();
                    char last_char = check.charAt(check.length() - 1); //마지막 식의 마지막이 연산자인지 숫자인지


                    if (last_char == '+' || last_char == '-' || last_char == '*' || last_char == '/') { //마지막 식이 연산자였다면
                        operator_stack.pop();
                    } else //마지막이 숫자였다면
                        number_stack.pop();
                    inputspace.setText(expression.peek());
                    }
            } //delete 기능 닫는 괄호

            //3) 그 외
            else {
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
            System.out.println("방정식: " + str_expression);


        } // {}action performe
    } // {}operator event listener

//#5.연산 메소드
    private void calculate() {
        while (!operator_stack.isEmpty()) {
            String operator = operator_stack.pop();
            double num2 = Double.parseDouble(number_stack.pop());// 스택 -> 뒤에서부터 꺼내기
            double num1 = Double.parseDouble(number_stack.pop());
            double result = 0;
            System.out.println();
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "x":
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

        public void reset(){
           currentNumInput = "";
           number_stack.clear(); //숫자스택비우기
           operator_stack.clear(); //연산자스택 비우기
       }



    public static void main(String[] args) {
        GUI cal = new GUI();
    }
}
