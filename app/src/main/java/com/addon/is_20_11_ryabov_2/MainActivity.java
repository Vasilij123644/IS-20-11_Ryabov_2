package com.addon.is_20_11_ryabov_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String[] countries = { "Минимальная", "Легкая", "Средняя", "Высокая", "Экстремальная"};
    public double bmr;
    public Integer pol = 0;
    private final static String FILE_NAME = "document.txt";
    public Double koef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Button button = findViewById(R.id.button);
        Button button1 = findViewById(R.id.button2);
        Button button2 = findViewById(R.id.button3);
        Button button3 = findViewById(R.id.button4);
        EditText editText = findViewById(R.id.editTextNumber);
        EditText editText1 = findViewById(R.id.editTextNumber2);
        EditText editText2 = findViewById(R.id.editTextNumber3);
        ImageView imageView = findViewById(R.id.imageView3);
        imageView.setBackgroundResource(R.drawable.girlicon);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setBackgroundResource(R.drawable.girlicon);
                pol = 0;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setBackgroundResource(R.drawable.menicon);
                pol = 1;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vozrast = editText2.getText().toString();
                String rost = editText.getText().toString();
                String ves = editText1.getText().toString();
                String tren = spinner.getSelectedItem().toString();
                switch (tren)
                {
                    case "Минимальная":
                        koef = 1.2;
                        break;
                    case "Легкая":
                        koef = 1.35;
                        break;
                    case "Средняя":
                        koef = 1.55;
                        break;
                    case "Высокая":
                        koef = 1.75;
                        break;
                    case "Экстремальная":
                        koef = 1.95;
                        break;
                }
                if (vozrast.equals("") | rost.equals("") | ves.equals("") | tren.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите все данные!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    if (pol.equals(0))
                    {
                        bmr = (9.99 * Double.parseDouble(ves)) + (6.25 * Double.parseDouble(rost)) - (4.92 * Double.parseDouble(vozrast)) - 161;
                        bmr = bmr * koef;
                        saveText();
                        openText();
                    }
                    else
                    {
                        bmr = (9.99 * Double.parseDouble(ves)) + (6.25 * Double.parseDouble(rost)) - (4.92 * Double.parseDouble(vozrast)) + 5;
                        bmr = bmr * koef;
                        saveText();
                        openText();
                    }
                }
            }
        });
    }

    //метод получения пути
    private File getExternalPath() {
        return new File(getExternalFilesDir(null), FILE_NAME);
    }

    // сохранение файла
    public void saveText(){
        try(FileOutputStream fos = new FileOutputStream(getExternalPath())) {
            String str3 = String.valueOf(bmr);
            fos.write(str3.getBytes());
            Toast.makeText(this, "Калории:"+" "+ str3, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // открытие файла
    public void openText(){

        TextView textView = findViewById(R.id.textView6);
        File file = getExternalPath();
        // если файл не существует, выход из метода
        if(!file.exists()) return;
        try(FileInputStream fin =  new FileInputStream(file)) {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}