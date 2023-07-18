package pnw.checker;

public class UserInfoBean {

    /**
     * 主キー
     */
    private int ID;

    /**
     * ユーザID
     */
    private String userID;

    /**
     * パスワード
     */
    private String password;

    /*コンストラクタ（インスタンス化時に呼ばれる） */
    public UserInfoBean(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return String return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

}