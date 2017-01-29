package ThreadPacek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import FormUI.ServerUI;
/*������Ƿ������˵ĵȴ��ͻ��˷�����Ϣ*/
public class ListenerClient extends Thread {
	
	//ÿ�������ͻ�����Ϣ�� �߳��࣬�����Լ��� reader�� wirter��client��Ҫ���� ServerUI
    BufferedReader reader;
    PrintWriter writer;
    ServerUI ui;
    Socket client;
    public ListenerClient(ServerUI ui, Socket client) {
        this.ui = ui;
        this.client=client;
        this.start();
    }
    //Ϊÿһ���ͻ��˴����̵߳ȴ�������Ϣ��Ȼ�����Ϣ�㲥��ȥ
    public void run() {
        String msg = "";
        while (true) {
            try {
                reader = new BufferedReader(new InputStreamReader(
                        client.getInputStream()));
                writer = new PrintWriter(client.getOutputStream(), true);
                msg = reader.readLine();
                
                /*
                 * �õ���Ϣmsg��֮�󣬵���Ⱥ������
                 */
                sendMsg(msg);
                
            } catch (IOException e) {
                println(e.toString());
                // e.printStackTrace();
                break;
            }
            if (msg != null && msg.trim() != "") {
                println(">>" + msg);
            }
        }
    }
    //����Ϣ�㲥�������û�
    
    /*
     * ���ã�����Ϣ�㲥�������û�
     * ���ߣ��չ�Զ
     * �뷨��������������д��������� ��Ϣ��ͷ����ͷ������Ҫ�����ĸ��û�������Ⱥ����������Ϣ��
     * ������ʵ���� ���� �� Ⱥ��
     */
    public synchronized void sendMsg(String msg) {
        try {
            for (int i = 0; i < ui.clients.size(); i++) {
                Socket client = ui.clients.get(i);
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.println(msg);
            }

        } catch (Exception e) {
            println(e.toString());
        }
    }

    

    public void println(String s) {
        if (s != null) {
            this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");
            System.out.println(s + "\n");
        }
    }
}