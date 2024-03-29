package conn;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sender implements Runnable {
    private ConcurrentLinkedQueue<Message> queue;
    private ObjectOutputStream outputStream;

    public Sender(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
        queue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        while (true) {
            if (queue.isEmpty())
                continue;
            try {
                outputStream.reset();
                outputStream.writeObject(queue.poll());
            } catch (IOException e) {
                System.err.println("out stream closed by other end.");
                return;
            }
        }
    }

    public void send(Message message) {
        this.queue.offer(message);
    }
}
