package Model;

import java.io.Serializable;

//när jag ska skicka något över nätverket är det denna klassen jag använder (dvs inte en chattmeddelande)
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
