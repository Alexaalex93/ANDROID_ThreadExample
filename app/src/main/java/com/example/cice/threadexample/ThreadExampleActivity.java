package com.example.cice.threadexample;

import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class ThreadExampleActivity extends AppCompatActivity {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) { //Este metodo es si quieres modificar la interfaz
            //super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");

            TextView myTextView = (TextView)findViewById(R.id.text_view);
            myTextView.setText(string);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_example);
    }

    /**
     *Creamos un objeto runnable para ponerlo en un hilo. Luego lo metemos en un hilo y lo lanzamos
     * Si intentas hacer un cambio en la interfaz a traves de un hilo va a dar error.
     * Hay que hacerlo en el hilo principal mediante un handler
     */
    public void buttonClick(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.ENGLISH);
                String dateString = dateFormat.format(new Date());
                bundle.putString("myKey", dateString);
                msg.setData(bundle);
                handler.sendMessage(msg);
     /*           long endTime = System.currentTimeMillis() + 5 * 1000;
                while(System.currentTimeMillis() < endTime){

                    synchronized (this){
                        try{
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e) {


                        }
                    }

                }
//                TextView textView = (TextView) findViewById(R.id.text_view);
//                textView.setText("Botón apretado");
                handler.sendEmptyMessage(0); //0 porque no quieres hacer nada con el mesaje*/
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void buttonClick2(View view){
        TextView textView = (TextView) findViewById(R.id.text_view2);
        textView.setText("Hecho");
    }
}
