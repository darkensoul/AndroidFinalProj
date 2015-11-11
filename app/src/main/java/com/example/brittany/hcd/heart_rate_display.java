package com.example.brittany.hcd;

import android.app.ProgressDialog;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;

import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Set;

import android.R.*;
import android.app.Activity;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import org.achartengine.GraphicalView;

import zephyr.android.BioHarnessBT.*;


class Point {
    private int x;
    private int y;

    public Point(int x, int y)
    {
        this.x =x;
        this.y=y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
}



public class heart_rate_display extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    BluetoothAdapter adapter = null;
    BTClient _bt;
    ZephyrProtocol _protocol;
    NewConnectedListener _NConnListener;
    private final int HEART_RATE = 0x100;
    private final int RESPIRATION_RATE = 0x101;
    private final int SKIN_TEMPERATURE = 0x102;
    private final int POSTURE = 0x103;
    private final int PEAK_ACCLERATION = 0x104;


    private static GraphicalView view;
    private LineGraph line = new LineGraph();
    private static Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_display);

    /*Sending a message to android that we are going to initiate a pairing request*/
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        /*Registering a new BTBroadcast receiver from the Main Activity context with pairing request event*/
        this.getApplicationContext().registerReceiver(new BTBroadcastReceiver(), filter);
        // Registering the BTBondReceiver in the application that the status of the receiver has changed to Paired
        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
        this.getApplicationContext().registerReceiver(new BTBondReceiver(), filter2);

        //Obtaining the handle to act on the CONNECT button
        TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
        String ErrorText = "Not Connected to BioHarness !";
        tv.setText(ErrorText);

        Button btnConnect = (Button) findViewById(R.id.ButtonConnect);
        if (btnConnect != null) {
            btnConnect.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                //Line chart
                /*    thread = new Thread()
                    {
                        public void run()
                        {
                            for(int i =0 ; i<10000;i++)
                            {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Point p = MockData.getDataFromReceiver(i);
                                line.addNewPoints(p);
                                view.repaint();
                            }
                        }        };
                    thread.start();

                    view = line.getView(heart_rate_display.this);
                    //setContentView(view);
                    LinearLayout layout =(LinearLayout) findViewById(R.id.linechart);
                    layout.addView(view);*/


          //Bioharness
                    String BhMacID = "00:07:80:9D:8A:E8";
                    //String BhMacID = "00:07:80:88:F6:BF";
                    adapter = BluetoothAdapter.getDefaultAdapter();

                    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            if (device.getName().startsWith("BH")) {
                                BluetoothDevice btDevice = device;
                                BhMacID = btDevice.getAddress();
                                break;

                            }
                        }
                    }

                    //BhMacID = btDevice.getAddress();
                    BluetoothDevice Device = adapter.getRemoteDevice(BhMacID);
                    String DeviceName = Device.getName();
                    _bt = new BTClient(adapter, BhMacID);
                    _NConnListener = new NewConnectedListener(Newhandler, Newhandler);
                    _bt.addConnectedEventListener(_NConnListener);

                    TextView tv1 = (TextView) findViewById(R.id.labelHeartRate);
                    tv1.setText("000");

                    tv1 = (TextView) findViewById(R.id.labelRespRate);
                    tv1.setText("0.0");


                    if (_bt.IsConnected()) {
                        _bt.start();
                        TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
                        String ErrorText = "Connected to BioHarness " + DeviceName;
                        tv.setText(ErrorText);

                        thread = new Thread()
                        {
                            public void run()
                            {
                                for(int i =0 ; i<10000;i++)
                                {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Point p = MockData.getDataFromReceiver(i);
                                    line.addNewPoints(p);
                                    view.repaint();
                                }
                            }        };
                        thread.start();

                        view = line.getView(heart_rate_display.this);
                        //setContentView(view);
                        LinearLayout layout =(LinearLayout) findViewById(R.id.linechart);
                        layout.addView(view);

                        //Reset all the values to 0s

                    } else {
                        TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
                        String ErrorText = "Unable to Connect !";
                        tv.setText(ErrorText);

                    }
                }
            });
        }
        /*Obtaining the handle to act on the DISCONNECT button*/
        Button btnDisconnect = (Button) findViewById(R.id.ButtonDisconnect);
        if (btnDisconnect != null) {
            btnDisconnect.setOnClickListener(new OnClickListener() {
                @Override
				/*Functionality to act if the button DISCONNECT is touched*/
                public void onClick(View v) {
                    // TODO Auto-generated method stub
					/*Reset the global variables*/
                    TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
                    String ErrorText = "Disconnected from BioHarness!";
                    tv.setText(ErrorText);

					/*This disconnects listener from acting on received messages*/
                   // _bt.removeConnectedEventListener(_NConnListener);
					/*Close the communication with the device & throw an exception if failure*/
                    _bt.Close();

                    final ProgressDialog dig = new ProgressDialog(heart_rate_display.this);
                    dig.setTitle("Disconnect BioHarness.");
                    dig.setMessage("Disconnected from BioHarness!");
                    dig.show();
                    Toast.makeText(heart_rate_display.this,"Disconnected from BioHarness!",Toast.LENGTH_SHORT).show();
                    Intent intent_Bioharness = new Intent(heart_rate_display.this, User_add_exercise.class);
                    startActivity(intent_Bioharness);

                }
            });
        }
    }
    //@Override
    //protected void onStart()
    //{
        //super.onStart();
        //view = line.getView(this);
        //setContentView(view);
    //}


    private class BTBondReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
            Log.d("Bond state", "BOND_STATED = " + device.getBondState());
        }
    }

    private class BTBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BTIntent", intent.getAction());
            Bundle b = intent.getExtras();
            Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE").toString());
            Log.d("BTIntent", b.get("android.bluetooth.device.extra.PAIRING_VARIANT").toString());
            try {
                BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
                Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[]{String.class});
                byte[] pin = (byte[]) m.invoke(device, "1234");
                m = device.getClass().getMethod("setPin", new Class[]{pin.getClass()});
                Object result = m.invoke(device, pin);
                Log.d("BTTest", result.toString());
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    final Handler Newhandler = new Handler() {
        public void handleMessage(Message msg) {
            TextView tv;
            switch (msg.what) {
                case HEART_RATE:
                    String HeartRatetext = msg.getData().getString("HeartRate");
                    tv = (TextView) findViewById(R.id.labelHeartRate);
                    System.out.println("Heart Rate Info is " + HeartRatetext);
                    if (tv != null) tv.setText(HeartRatetext);
                    break;

                case RESPIRATION_RATE:
                    String RespirationRatetext = msg.getData().getString("RespirationRate");
                    tv = (TextView) findViewById(R.id.labelRespRate);
                    if (tv != null) tv.setText(RespirationRatetext);

                    break;
            }
        }

    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_heart_rate_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
