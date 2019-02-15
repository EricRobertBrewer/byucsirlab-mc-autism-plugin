package edu.byu.cs.autism;
import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

public class Router {



    AudioInputStream audioInputStream;
    static AudioInputStream ais;
    static AudioFormat format;
    static boolean status = true;
    static int port = 50005;
    static int sampleRate = 44100;

    static DataLine.Info dataLineInfo;
    static SourceDataLine sourceDataLine;

    public static Map<String, String> addressPair;

    public static void connectTo() throws Exception
    {

        //catch audio

        System.out.println("Server started at port:"+port);

        DatagramSocket serverSocket = new DatagramSocket(port);

        /**
         * Formula for lag = (byte_size/sample_rate)*2
         * Byte size 9728 will produce ~ 0.45 seconds of lag. Voice slightly broken.
         * Byte size 1400 will produce ~ 0.06 seconds of lag. Voice extremely broken.
         * Byte size 4000 will produce ~ 0.18 seconds of lag. Voice slightly more broken then 9728.
         */

        byte[] receiveData = new byte[4096];

        format = new AudioFormat(sampleRate , 16, 2, true, true);
        dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(format);
        sourceDataLine.start();

        //FloatControl volumeControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        //volumeControl.setValue(1.00f);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        ByteArrayInputStream baiss = new ByteArrayInputStream(receivePacket.getData());

        while (status == true)
        {
            serverSocket.receive(receivePacket);
            //forward it
            //ais = new AudioInputStream(baiss, format, receivePacket.getLength());
            DatagramSocket socket = new DatagramSocket();

            InetAddress addr;
            String fromaddress = receivePacket.getAddress().toString();

            if(addressPair.containsKey(fromaddress)) {
                String toaddress = addressPair.get(fromaddress);

                addr = InetAddress.getByName(toaddress);

                DatagramPacket dgp = new DatagramPacket(receivePacket.getData(), receivePacket.getData().length, addr, 50005);

                socket.send(dgp);
            }

            //toSpeaker(receivePacket.getData());
        }

        sourceDataLine.drain();
        sourceDataLine.close();
    }



}
