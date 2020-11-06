package com.example.bluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private static  final  int REQUEST_ENABLE_BT=0;

    Button btnOn ,btnOff ,btnDis;
    ImageView Imageonoff;
    BluetoothAdapter bluetoothAdapter;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Imageonoff = findViewById(R.id.imageView2);
        btnOn = findViewById(R.id.button3);
        btnOff = findViewById(R.id.button4);
        btnDis = findViewById(R.id.button5);
        listView=findViewById(R.id.list_view);
        arrayList =new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            Imageonoff.setImageResource(R.drawable.ic_action_on1);
        } else {
            Imageonoff.setImageResource(R.drawable.ic_action_off1);
        }

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()) {
                    showToast("Turning ON...");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                    Imageonoff.setImageResource(R.drawable.ic_action_on1);
                } else {
                    showToast("Already ON...");
                }

            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    showToast("Turning OFF");
                    Imageonoff.setImageResource(R.drawable.ic_action_off1);
                } else {
                    showToast("Already OFF");
                }


            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter.isEnabled()) {
                    showToast("SCANING...");
                    bluetoothAdapter.startDiscovery();
                    Imageonoff.setImageResource(R.drawable.ic_action_scan1);
                }else{
                    showToast("Bluetooth is OFF");
                }
            }
        });
IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
registerReceiver(myReceiver,intentFilter);

arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
listView.setAdapter(arrayAdapter);
    }
 BroadcastReceiver myReceiver=new BroadcastReceiver() {
     @Override
     public void onReceive(Context context, Intent intent) {
     String action =intent.getAction();
     if(BluetoothDevice.ACTION_FOUND.equals(action))
     {
         BluetoothDevice device =intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
         arrayList.add(device.getName());
         arrayAdapter.notifyDataSetChanged();
     }
     }
 }  ;


    private  void showToast(String msg){
        Toast.makeText(this,msg, LENGTH_SHORT).show();
    }
}