package pnw.common;

public class CreateMessage {
    private static String message = "";

    public static String generate(int num){
        switch(num){
            case 0:
                message = "選択中";
                break;
            case 1:
                message = "待機";
                break;
            case 2:
                message = "完了";
                break;
            case 9:
                message = "中止";
        }
        return message;
    }
}
