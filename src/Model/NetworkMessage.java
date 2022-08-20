package Model;

import java.io.Serializable;

/**
 * Class that is used to send messages over the network.
 */
public class NetworkMessage  implements Serializable {
    private String typeOfMsg;
    private Object data;

    public NetworkMessage(String typeOfMsg, Object msg) {
        this.typeOfMsg = typeOfMsg;
        this.data = msg;
    }

    public String getTypeOfMsg() {
        return typeOfMsg;
    }

    public Object getData() {
        return data;
    }
}
