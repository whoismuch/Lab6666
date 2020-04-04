package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DataExchangeWithServer {
    private static SocketChannel outcomingchannel;
    private Charset charset;
    private byte[] outcoming;
    private ByteBuffer byteBuffer;

    public DataExchangeWithServer(SocketChannel outcomingchannel){
        this.outcomingchannel = outcomingchannel;
        charset = StandardCharsets.UTF_8;
    }

    public void sendToServer(String message) throws IOException {
        outcoming = new byte[(message.getBytes(charset).length)];
        outcoming = message.getBytes(charset);
        byteBuffer = ByteBuffer.wrap(outcoming);
        System.out.println("Затолкали в буффер массив из байтов");
        outcomingchannel.write(byteBuffer);
    }

    public String getFromServer() throws IOException {
        byteBuffer = ByteBuffer.allocate(5000);
        outcomingchannel.read(byteBuffer);
        String message = new String(byteBuffer.array(), charset);
        return message.trim();
    }
}
