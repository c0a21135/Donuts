package pnw.common;

public class DonutsCountBean {
     /**
     * ドーナツのid
     */
    private int donut_id;
    
     /**
     * ドーナツの名前
     */
    private String donut_name;

     /**
     * ドーナツの値段
     */
    private Double donut_price;

    /**
     * ドーナツの選択数
     */
    private int donut_count;
    
    public DonutsCountBean(int donut_id, String donut_name, Double donut_price, int donut_count) {
        this.donut_id = donut_id;
        this.donut_name = donut_name;
        this.donut_price = donut_price;
        this.donut_count = donut_count;
    }

    public int getDonutId(){
        return donut_id;
    }

    public void setDonutId(int donut_id){
        this.donut_id = donut_id;
    }

    public int getDonutCount(){
        return donut_count;
    }

    public void setDonutCount(int donut_count){
        this.donut_count = donut_count;
    }

    public String getDonutName() {
        return donut_name;
    }

    public void setDonutName(String donut_name) {
        this.donut_name = donut_name;
    }

    public Double getDonutPrice() {
        return donut_price;
    }

    public void setDonutPrice(Double donut_price) {
        this.donut_price = donut_price;
    }

    
}
