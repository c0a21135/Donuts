package pnw.common;

public class UsersBean {

    /**
     * 主キー
     */
    private int docked_number;

    /**
     * ニックネーム
     */
    private String nickname;

    /**
     * 取引が完了したか
     */
    private int completed;

    public UsersBean(String nickname, int completed) {
        this.nickname = nickname;
        this.completed = completed;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    /**
     * @return String return the ID
     */
    public int getDockedNumber() {
        return docked_number;
    }

    /**
     * @param docked_number the ID to set
     */
    public void setDockedNumber(int docked_number) {
        this.docked_number = docked_number;
    }

}