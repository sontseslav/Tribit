package tribit;

/**
 * Created by user on 09.10.15.
 */
public class Pyramid {
    public boolean isComplete; //false by default
    public boolean isUpsideDown;
    public byte head;
    public byte baseElement1;
    public byte baseElement2;
    public byte baseElement3;
    public int result;

    public Pyramid(boolean isUpsideDown, byte head, byte bodyElement1,byte bodyElement2,byte bodyElement3){
        this.isUpsideDown = isUpsideDown;
        this.head = head;
        this.baseElement1 = bodyElement1;
        this.baseElement2 = bodyElement2;
        this.baseElement3 = bodyElement3;
        setResult();
        isComplete = setIsComplete();
    }

    @Override
    public String toString(){
        String s = String.format(Integer.toString(result,2));
        while (s.length() < 4){//Bad code style, but loop is small
            s = "0" + s;
        }
        return s;
    }

    private void setResult(){
        result = head * 1 + baseElement3 * 2 + baseElement2 * 4 + baseElement1 * 8;
    }

    public boolean setIsComplete(){
        isComplete = (result == 0 || result == 0b1111)?true:false;
        return isComplete;
    }

    public void modifyResult(){
        if (!isComplete) {
            switch (result) {
                case 1:
                    result = 0b1000;
                    break;
                case 2:
                    result = 0b0001;
                    break;
                case 3:
                case 5:
                    result = 0b0010;
                    break;
                case 4:
                    result = 0b0000;
                    break;
                case 6:
                case 7:
                    result = 0b1011;
                    break;
                case 8:
                    result = 0b0100;
                    break;
                case 9:
                    result = 0b0101;
                    break;
                case 10:
                case 14:
                    result = 0b0111;
                    break;
                case 11:
                    result = 0b1111;
                    break;
                case 12:
                    result = 0b1101;
                    break;
                case 13:
                    result = 0b1110;
                    break;
            }
            isComplete = setIsComplete();
        }
    }
}
