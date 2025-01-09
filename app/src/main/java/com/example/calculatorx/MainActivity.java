package com.example.calculatorx;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Input, Hasil;
    MaterialButton buttonAC, buttonKurBuk, buttonKurTup, buttonHapus;
    MaterialButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    MaterialButton buttonTambah, buttonKurang, buttonKali, buttonBagi, buttonHasil, buttonKoma;

    String kalkulasi = "";
    ArrayList<String> ekspresi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Input = findViewById(R.id.Input);
        Hasil = findViewById(R.id.Hasil);

        // Meng-assign ID button
        assignID(button0, R.id.button0);
        assignID(button1, R.id.button1);
        assignID(button2, R.id.button2);
        assignID(button3, R.id.button3);
        assignID(button4, R.id.button4);
        assignID(button5, R.id.button5);
        assignID(button6, R.id.button6);
        assignID(button7, R.id.button7);
        assignID(button8, R.id.button8);
        assignID(button9, R.id.button9);

        assignID(buttonAC, R.id.buttonAC);
        assignID(buttonKurBuk, R.id.buttonKurBuk);
        assignID(buttonKurTup, R.id.buttonKurTup);
        assignID(buttonHapus, R.id.buttonHapus);
        assignID(buttonTambah, R.id.buttonTambah);
        assignID(buttonKurang, R.id.buttonKurang);
        assignID(buttonBagi, R.id.buttonBagi);
        assignID(buttonKali, R.id.buttonKali);
        assignID(buttonHasil, R.id.buttonHasil);
        assignID(buttonKoma, R.id.buttonKoma);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();


        if (buttonText.equals("AC")) {
            kalkulasi = "";
            ekspresi.clear();
            Input.setText("");
            Hasil.setText("");
            return;
        }

        if (buttonText.equals("=")) {
            double result = hitungEkspresi(ekspresi);
            Hasil.setText(String.valueOf(result));
            return;
        }


        if (buttonText.equals("Del")) {
            if (!kalkulasi.isEmpty()) {
                kalkulasi = kalkulasi.substring(0, kalkulasi.length() - 1);
                ekspresi.remove(ekspresi.size() - 1);
            }
        } else {

            kalkulasi += buttonText;
            ekspresi.add(buttonText);
        }

        Input.setText(kalkulasi);
    }

    double hitungEkspresi(ArrayList<String> ekspresi) {
        ArrayList<Double> angka = new ArrayList<>();
        ArrayList<Character> operator = new ArrayList<>();

        StringBuilder tempAngka = new StringBuilder();
        for (String s : ekspresi) {
            if (isOperator(s)) {
                angka.add(Double.parseDouble(tempAngka.toString()));
                operator.add(s.charAt(0));
                tempAngka.setLength(0);
            } else {
                tempAngka.append(s);
            }
        }
        angka.add(Double.parseDouble(tempAngka.toString()));

        for (int i = 0; i < operator.size(); i++) {
            if (operator.get(i) == 'X' || operator.get(i) == '/') {
                double result = (operator.get(i) == 'X') ? angka.get(i) * angka.get(i + 1) : angka.get(i) / angka.get(i + 1);
                angka.set(i, result);
                angka.remove(i + 1);
                operator.remove(i);
                i--;
            }
        }

        double hasilAkhir = angka.get(0);
        for (int i = 0; i < operator.size(); i++) {
            if (operator.get(i) == '+') {
                hasilAkhir += angka.get(i + 1);
            } else if (operator.get(i) == '-') {
                hasilAkhir -= angka.get(i + 1);
            }
        }

        return hasilAkhir;
    }

    boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("X") || s.equals("/");
    }
}
