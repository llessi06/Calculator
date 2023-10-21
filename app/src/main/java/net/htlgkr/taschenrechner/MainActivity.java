package net.htlgkr.taschenrechner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String input;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText inputTextField = findViewById(R.id.input);
        input = inputTextField.getText().toString();
        input = input.replaceAll("\\s+", "");
        if (input.length() >= 1) {
            position = 0;
            int calculated = parseExpression();
            TextView ergebnis = findViewById(R.id.ergebnis);
            ergebnis.setText("=\n" + calculated);
        } else {
            TextView ergebnis = findViewById(R.id.ergebnis);
            ergebnis.setText("=\n" + "Ungültige Eingabe!");
        }

    }

    private int parseExpression() {
        int leftValue = parseTerm();
        while (position < input.length() && (input.charAt(position) == '+' || input.charAt(position) == '-')) {
            char operator = input.charAt(position++);
            int rightValue = parseTerm();
            if (operator == '+') {
                leftValue += rightValue;
            } else if (operator == '-') {
                leftValue -= rightValue;
            }
        }
        return leftValue;
    }

    private int parseTerm() {
        if (position < input.length() && input.charAt(position) == '(') {
            position++;
            int value = parseExpression();
            if (position < input.length() && input.charAt(position) == ')') {
                position++;
                return value;
            }
        } else if (position < input.length() && Character.isDigit(input.charAt(position))) {
            int start = position;
            while (position < input.length() && Character.isDigit(input.charAt(position))) {
                position++;
            }
            return Integer.parseInt(input.substring(start, position));
        } else {
            throw new RuntimeException("Ungültige Eingabe!");
        }
        return 0;
    }
}