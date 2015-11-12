package com.example.brittany.hcd;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Random;

import zephyr.android.BioHarnessBT.*;

public class NewConnectedListener extends ConnectListenerImpl
{
    public static int temp_rate;
    private Handler _OldHandler;
    private Handler _aNewHandler;
    final int GP_MSG_ID = 0x20;
    private final int HEART_RATE = 0x100;
    private final int RESPIRATION_RATE = 0x101;

    /*Creating the different Objects for different types of Packets*/
    private GeneralPacketInfo GPInfo = new GeneralPacketInfo();


    private PacketTypeRequest RqPacketType = new PacketTypeRequest();
    public NewConnectedListener(Handler handler, Handler _NewHandler) {
        super(handler, null);
        _OldHandler= handler;
        _aNewHandler = _NewHandler;
        // TODO Auto-generated constructor stub

    }
    public void Connected(ConnectedEvent<BTClient> eventArgs) {
        System.out.println(String.format("Connected to BioHarness %s.", eventArgs.getSource().getDevice().getName()));
		/*Use this object to enable or disable the different Packet types*/
        RqPacketType.GP_ENABLE = true;
        RqPacketType.BREATHING_ENABLE = true;
        RqPacketType.LOGGING_ENABLE = true;


        //Creates a new ZephyrProtocol object and passes it the BTComms object
        ZephyrProtocol _protocol = new ZephyrProtocol(eventArgs.getSource().getComms(), RqPacketType);
        //ZephyrProtocol _protocol = new ZephyrProtocol(eventArgs.getSource().getComms(), );
        _protocol.addZephyrPacketEventListener(new ZephyrPacketListener() {
            public void ReceivedPacket(ZephyrPacketEvent eventArgs) {
                ZephyrPacketArgs msg = eventArgs.getPacket();
                byte CRCFailStatus;
                byte RcvdBytes;

                CRCFailStatus = msg.getCRCStatus();
                RcvdBytes = msg.getNumRvcdBytes() ;
                int MsgID = msg.getMsgID();
                byte [] heartArray = msg.getBytes();
                switch (MsgID)
                {
                    case GP_MSG_ID:
                        //***************Displaying the Heart Rate********************************
                        int HRate =  GPInfo.GetHeartRate(heartArray);
                        Message text1 = _aNewHandler.obtainMessage(HEART_RATE);
                        Bundle b1 = new Bundle();
                        b1.putString("HeartRate", String.valueOf(HRate));
                        text1.setData(b1);
                        _aNewHandler.sendMessage(text1);
                        temp_rate=HRate;

                        //***************Displaying the Respiration Rate********************************
                        double RespRate = GPInfo.GetRespirationRate(heartArray);

                        text1 = _aNewHandler.obtainMessage(RESPIRATION_RATE);
                        b1.putString("RespirationRate", String.valueOf(RespRate));
                        text1.setData(b1);
                        _aNewHandler.sendMessage(text1);
                }
            }
        });
    }
public static int temp()
{
    return temp_rate;
}

}
class MockData
{
    public static Point getDataFromReceiver(int x)
    {
        return new Point(x,NewConnectedListener.temp());
    }
}