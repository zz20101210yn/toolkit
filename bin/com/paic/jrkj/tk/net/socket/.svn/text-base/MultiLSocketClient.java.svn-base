package com.paic.jrkj.tk.net.socket;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paic.jrkj.tk.ProcessAction;
import com.paic.jrkj.tk.net.Connection;
import com.paic.jrkj.tk.net.ConnectionListener;
import com.paic.jrkj.tk.net.SendAndReceiver;
import com.paic.jrkj.tk.tools.work.WorkManager;
import com.paic.jrkj.tk.util.HexUtil;
import com.paic.jrkj.tk.util.RandomUtil;

/**
 *
 */
public class MultiLSocketClient implements SendAndReceiver, Connection<LSocketClient> {

    private final Log logger = LogFactory.getLog(MultiLSocketClient.class);
    private Map<String, LSocketClient> clientMap = new HashMap<String, LSocketClient>();
    private ProcessAction vProcessAction;
    private boolean inCluster = true;
    private final String defaultWorkName = "default";
    private Map<String, LSocketClient> aliveClientMap = new HashMap<String, LSocketClient>();//alive socket map
    private Map<String, LSocketClient> blockedClientMap = new HashMap<String, LSocketClient>();//blocked socket map
    private Map<String, LSocketClient> unConnectedClientMap = new HashMap<String, LSocketClient>();//unConnected socket map

    public MultiLSocketClient() {
    }

    public MultiLSocketClient(SocketAddress[] socketAddrArray, ProcessAction vProcessAction) {
        this.vProcessAction = vProcessAction;
        LSocketClient client;
        for (SocketAddress addr : socketAddrArray) {
            client = new LSocketClient(addr);
            addLongSocketClient(client);
        }
    }

    public void addInCluster(boolean inCluster) {
        for (Map.Entry<String, LSocketClient> e : clientMap.entrySet()) {
            e.getValue().setInCluster(inCluster);
        }
    }

    public void addNeedHeartResp(boolean needHeartResp) {
        for (Map.Entry<String, LSocketClient> e : clientMap.entrySet()) {
            e.getValue().setNeedHeartResp(needHeartResp);
        }
    }

    public void addConnectionListener(ConnectionListener<LSocketClient> listener) {
        for (Map.Entry<String, LSocketClient> e : clientMap.entrySet()) {
            e.getValue().addConnectionListener(listener);
        }
    }

    public void setProcessAction(ProcessAction vProcessAction) {
        this.vProcessAction = vProcessAction;
    }

    public synchronized void addLongSocketClient(LSocketClient client) {
        client.setProcessAction(vProcessAction);
        client.setInCluster(inCluster);
        client.addConnectionListener(new ConnectionListener<LSocketClient>() {
            @Override
            public void callAfterConnected(LSocketClient connection) {
                moveBackServer(connection.getSocketAddress());
            }

            @Override
            public void callBeforeDisconnected(LSocketClient connection) {
                blockServer(connection.getSocketAddress());
            }
        });
        clientMap.put(client.getSocketAddress().toString(), client);
    }

    public synchronized void addAndActiveLSocketClient(final LSocketClient client){
        addLongSocketClient(client);
        WorkManager.getWorkManager(defaultWorkName).startWork(new Runnable() {
                @Override
                public void run() {
                    try {
                        client.connect();
                        addAliveClientMap(client.getSocketAddress().toString(), client);
                    } catch (IOException e) {
                        logger.fatal("client connect fail!", e);
                    }
                    
                }
            });
    }

    private LSocketClient getLongSocketClient() {
        int index = RandomUtil.getRandom(aliveClientMap.size());
        int i = 0;
        for (Map.Entry<String, LSocketClient> e : aliveClientMap.entrySet()) {
            if (index == i) {
                return e.getValue();
            }
            i++;
        }
        return null;
    }

    private LSocketClient getLongSocketClient(String address) {
        return clientMap.get(address);
    }

    private LSocketClient getLongSocketClient(SocketAddress[] serverList) {
        int length = serverList.length;
        if (length == 0) {
            logger.error("serverList is empty");
            return null;
        }
        int index = RandomUtil.getRandom(serverList.length);
        LSocketClient client = aliveClientMap.get(serverList[index].toString());
        if (client == null) {
            List<SocketAddress> list = new ArrayList<SocketAddress>();
            for (SocketAddress addr : serverList) {
                if (aliveClientMap.containsKey(addr.toString())) {
                    list.add(addr);
                }
            }
            if (list.size() == 0) {
                logger.error("alive serverList is empty");
                return null;
            }
            return getLongSocketClient(list.toArray(new SocketAddress[list.size()]));
        }
        return client;
    }

    public void connect() {
        for (final Map.Entry<String, LSocketClient> entry : clientMap.entrySet()) {
            WorkManager.getWorkManager(defaultWorkName).startWork(new Runnable() {
                @Override
                public void run() {
                    try {
                        entry.getValue().connect();
                        addAliveClientMap(entry.getKey(), entry.getValue());
                    } catch (IOException e) {
                        logger.fatal("client connect fail!", e);
                    }

                }
            });
        }
    }

    private synchronized void addAliveClientMap(String key, LSocketClient value) {
        aliveClientMap.put(key, value);
    }

    public void blockServer(SocketAddress socketAddr) {
        int count = 0;
        do {
            boolean succ = moveMap(socketAddr, aliveClientMap, blockedClientMap);
            if (succ) {
                break;
            } else {
                try {
                    Thread.sleep(10 * (++count));
                } catch (InterruptedException e) {
                    //
                }
            }
        } while (count <= 100);
    }

    public void resumeServer(SocketAddress socketAddr) {
        moveMap(socketAddr, blockedClientMap, aliveClientMap);
    }

    public void moveBackServer(SocketAddress socketAddr) {
        logger.info("unConnectedClientMap moveBackServer:" + socketAddr.toString());
        if (!aliveClientMap.containsKey(socketAddr.toString())) {
            moveMap(socketAddr, unConnectedClientMap, aliveClientMap);
        }
    }

    private synchronized boolean moveMap(SocketAddress socketAddr, Map<String, LSocketClient> fromMap, Map<String, LSocketClient> toMap) {
        LSocketClient client = fromMap.get(socketAddr.toString());
        if (client != null) {
            toMap.put(socketAddr.toString(), client);
            fromMap.remove(socketAddr.toString());
            return true;
        }
        return false;
    }

    public LSocketClient send(SocketAddress[] serverList, byte[] data)
            throws IOException {
        int count = 0;
        int maxCount = serverList.length;
        while (count < maxCount) {
            LSocketClient client = getLongSocketClient(serverList);
            if (client == null) {
                throw new IOException("LSocketClient not found!");
            }
            if(client.getSocket()==null || !client.getSocket().isConnected()){
            	moveMap(client.getSocketAddress(), aliveClientMap, unConnectedClientMap);
            	continue;
            }
            try {
                client.send(data);
                return client;
            } catch (IOException e) {
                count++;
                logger.warn("client send failed, move to unConnectedClientMap: " + client.getSocketAddress().toString());
                moveMap(client.getSocketAddress(), aliveClientMap, unConnectedClientMap);
                logger.warn(client + "send [" + HexUtil.encode(data) + "] fail! count [" + count + "]");
            }
        }
        for (SocketAddress addr : serverList) {
            LSocketClient client = getLongSocketClient(addr.toString());
            if (client.getSocket().isConnected()) {
                client.send(data);
                return client;
            }
        }
        throw new IOException("send fail! no alive LSocketClient!");
    }

    public void send(byte[] data)
            throws IOException {
        LSocketClient client = getLongSocketClient();
        if (client == null) {
            throw new IOException();
        }
        try {
            client.send(data);
        } catch (IOException e) {
            logger.warn(client + "send [" + HexUtil.encode(data) + "] fail!");
            try {
                Thread.sleep(10);
                client = getLongSocketClient();
                client.send(data);
            } catch (InterruptedException ex) {
                logger.fatal("", ex);
            }
        }
    }

    private void send(String address, byte[] data, int count) throws IOException {
        if (count <= 3) {
            LSocketClient client = getLongSocketClient(address);
            if (client == null) {
                throw new IOException();
            }
            try {
                client.send(data);
            } catch (IOException e) {
                logger.warn(client + "send [" + HexUtil.encode(data) + "] fail!");
                try {
                    Thread.sleep(10 * count);
                    count++;
                    send(address, data, count);
                } catch (InterruptedException ex) {
                    count++;
                    logger.fatal("", ex);
                }
            }
        }
    }

    public void send(String address, byte[] data) throws IOException {
        send(address, data, 1);
    }

    public byte[] received()
            throws IOException {
        return null;
    }

    public void disconnect() {
        for (Map.Entry<String, LSocketClient> e : aliveClientMap.entrySet()) {
            e.getValue().disconnect();
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("MultiLSocketClient[");
        for (Map.Entry<String, LSocketClient> e : clientMap.entrySet()) {
            buf.append(e.getValue()).append(" ");
        }
        buf.append("]");
        return buf.toString();
    }
}
