package LuseDma.ussd.pojos.luse;

public class SocketResponse {

    private String code;
    private String message;
    private Object payload;

    public SocketResponse() {
    }

    public SocketResponse(String code, String message, Object payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }


}
