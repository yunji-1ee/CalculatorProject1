import java.awt.*; // 스윙은 awt의 컨테이너를 상속받는 클래스니깐 불러옴
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
//RoundedButton
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Objects;
import java.util.Stack; // 연산을 위해 값 담을 곳
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class GUI extends JFrame { //JFrame 상속받아옴

    GridBagLayout grid = new GridBagLayout(); //레이아웃을 위함
    JPanel bt_panel = new JPanel(); // 복잡한 배치를 위한 JPanel 생성
    JPanel label_panel = new JPanel();
    JButton button = new JButton();
    JLabel label = new JLabel("입력");
    JTextField inputspace = new JTextField("입력받기"); // 텍스트를 입력받기 위함
    JFrame frame = new JFrame();


    public GUI(){

        setLayout(null);

    //#1. 화면 프레임에 대한 것
        setSize(400,550);
        setTitle("Calculator - 기본 계산기"); //제목설정
        setFont(new Font("Arial", Font.PLAIN, 40));
        //setBackground(Color.GRAY); // 화면 배경색 설정
        // (회색으로 떳다가 흰색으로 바뀌어버림,,)
        getContentPane().setBackground(Color.WHITE); // 화면 배경색 설정
        //바탕화면 색이 회색이었다가 흰 색으로 바뀌는 것을 방지하기 위해 getContentPane()을 붙여줬음
        setResizable(false); //화면크기 조절 불가하게 만들기
        setLocationRelativeTo(null); // 화면 가운데 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫으면 프로그램도 종료시키기

    //#2.버튼

        //버튼정렬
        bt_panel.setLayout(new GridLayout(5,5,2,2)); //행과 열, 버튼사이 간격 정하기
        bt_panel.setBounds(25,150, 350,350); //좌표로 직접 정렬,크기조정하기

        String bt_names[] ={ //버튼이름
                "CE","%","√","←",
                "1","2","3","+",
                "4","5","6","-",
                "7","8","9","X",
                ".","0","=","/"
        }; // 버튼이 너무 많아서 panel과 배열로 만들기

        JButton bt[] = new JButton[bt_names.length]; //버튼의 길이 -> 반복문을 통해 이름을 입력하기 위함

       for (int i = 0; i < bt.length; i++) {
           bt[i] = new JButton(bt_names[i]); //배열 길이만큼 버튼을 만들고 차례대로 이름 넣어주기

           bt[i].setFont(new Font("Arial", Font.PLAIN, 17));

           //고냥 이쁘게 만들려고 색상 정해주기
           if ((i>=4)&&(i<=6))
               bt[i].setBackground(new Color(246,234,200));
           else if((i>=8)&&(i<=10))
               bt[i].setBackground(new Color(246,234,200));
           else if((i>=12)&&(i<=14))
               bt[i].setBackground(new Color(246,234,200));
           else if(i==17)
               bt[i].setBackground(new Color(246,234,200));

           else bt[i].setBackground(new Color(236,213,227));

           bt[i].setBorderPainted(false); //버튼 테두리 없애기
           bt_panel.add(bt[i]); //다음 내용들을 bt_panel에 추가하기
           bt[i].setOpaque(true); //맥에서 색이 안먹는 것 해결

           if(((i>=4)&&(i<=6)) || ((i>=8)&&(i<=10)) || ((i>=12)&&(i<=14)) || (i==17) )
               bt[i].addActionListener(new Number()); //숫자버튼을 누르면
           else
               bt[i].addActionListener(new Operator());
       }
        add(bt_panel); //화면에 띄우기

    //#3. 입출력 레이블
        //레이아웃
        label_panel.setLayout(null);
        label_panel.setBounds(23,30,350,100);
        //레이블 표시하기
        label_panel.add(inputspace);
        frame.add(inputspace);

        add(label_panel);

        // 프레임이 보이도록 활성화
       setVisible(true);
        //맨 뒤에 와야한다고 함 (근데 맨 뒤가 아니어도 실행됐음)
          }

//#4. eventListener
        Stack<String> number_stack = new Stack<>(); // 숫자만 저장함
        Stack<String> operator_stack = new Stack<>(); //연산자만 저장함

        String operator = "";
        String num="";
        int[] numbers = {0};

        // 숫자 받아오기
        class Number implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton number_button = (JButton)e.getSource();

                if (e.getSource()==number_button){
                    number_stack.push(number_button.getText()); // 입력되는 문자를 받아서 스택에 넣기
                    num += number_button.getText(); //입력되는 문자를 받아서 String에 넣기
                }
                System.out.println("문자열 "+num);
                System.out.println("스택 "+number_stack);
            }
        }

        // 연산자 받아오기
        class Operator implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton operator_button = (JButton)e.getSource();

                if (e.getSource()==operator_button){
                    operator_stack.push(operator_button.getText()); // 입력되는 문자를 받아서 스택에 넣기
                    operator += operator_button.getText(); //입력되는 문자를 받아서 String에 넣기

                    numbers[0] = Integer.parseInt(num);
                    System.out.println(numbers[0]);


                   // System.out.println("스택 "+operator_stack);
                }
            }
        }


    public static void main(String[] args){
        GUI cal = new GUI();
    }
}
