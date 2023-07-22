package pnw.common;

public class DonutsBean {
     /**
     * 主キー
     */
    private int donut_id;

    /**
     * ドーナツの名前
     */
    private String donut_name;

    /**
     * ドーナツの値段
     */
    private double donut_price;

    /**
     * ドーナツの在庫数
     */
    private int quantity;


    public DonutsBean(String donut_name, double donut_price, int quantity) {
        this.donut_name = donut_name;
        this.donut_price = donut_price;
        this.quantity = quantity;
    }

    public String getDonutName() {
        return donut_name;
    }

    public void setDonutName(String donut_name) {
        this.donut_name = donut_name;
    }

    public double getDonutPrice() {
        return donut_price;
    }

    public void setDonutPrice(double donut_price) {
        this.donut_price = donut_price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * @return String return the ID
     */
    public int getDonutId() {
        return donut_id;
    }

    /**
     * @param donut_id the ID to set
     */
    public void setDonutId(int donut_id) {
        this.donut_id = donut_id;
    }

}
