package pnw.checker;

public class UserInfoBean_tyuumonn {

    /**
     * 主キー
     */
    private int COUNT;
    private double PRICE;

    private static double sum = 0;;

    /**
     * ユーザID
     */

    // privateは自クラスのみアクセスOK 外から変更が出来ない

    /**
     * パスワード
     */
    private String name;
    private String f_url;

    public UserInfoBean_tyuumonn(String name, double PRICE) {
        this.name = name;
        this.PRICE = PRICE;
        sum += PRICE;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the ID
     */
    public int getCount() {
        return COUNT;
    }

    /**
     * @param ID the ID to set
     */
    public void setCount(int COUNT) {
        this.COUNT = COUNT;
    }

    /**
     * @return String return the ID
     */
    public double getPrice() {
        return PRICE;
    }

    /**
     * @param ID the ID to set
     */
    public void setPrice(double PRICE) {
        this.PRICE = PRICE;
    }

    public static double getSum() {
        return sum;
    }

    public String getURL() {
        return f_url;
    }

    public void setURL(String f_url) {
        this.f_url = f_url;
    }
}
