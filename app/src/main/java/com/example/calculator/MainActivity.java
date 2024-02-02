package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultView, solutionView;
    MaterialButton buttonC, buttonAC, buttonDot, buttonOB, buttonCB;
    MaterialButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    MaterialButton buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.result_view);
        solutionView = findViewById(R.id.solution_view);

        assignID(R.id.button_c);
        assignID(R.id.button_ac);
        assignID(R.id.button_open_bracket);
        assignID(R.id.button_close_bracket);
        assignID(R.id.button_add);
        assignID(R.id.button_subtract);
        assignID(R.id.button_multiply);
        assignID(R.id.button_divide);
        assignID(R.id.button_equals);
        assignID(R.id.button_dot);
        assignID(R.id.button_one);
        assignID(R.id.button_two);
        assignID(R.id.button_three);
        assignID(R.id.button_four);
        assignID(R.id.button_five);
        assignID(R.id.button_six);
        assignID(R.id.button_seven);
        assignID(R.id.button_eight);
        assignID(R.id.button_nine);
        assignID(R.id.button_zero);
    }

    private void assignID(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String answer = solutionView.getText().toString();

        if (!solutionView.getText().toString().equals("") || !solutionView.getText().toString().equals("0")) {
            if (buttonText.equals("=")) {
                solutionView.setText(resultView.getText());
                return;
            }
            if (buttonText.equals("C") && answer.length() > 1) {
                answer = answer.substring(0, answer.length() - 1);
            }
            if (buttonText.equals("C") && !answer.equals("0")) {
                answer = "0";
            }
            if (buttonText.equals("AC")) {
                answer = "0";
                resultView.setText("0");
            } else if (!buttonText.equals("C")) {
                if (answer.equals("0") && !buttonText.equals(".")) {
                    answer = buttonText;
                } else {
                    answer += buttonText;
                }
            }
        }
        solutionView.setText(answer);

        String finalResult = getResult(answer);
        if (!finalResult.equals("err")) {
            resultView.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = (context.evaluateString(scriptable, data, "javascript", 1, null)).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", " ");
            }
            return finalResult;
        } catch (Exception e) {
            return "err";
        }
    }
}