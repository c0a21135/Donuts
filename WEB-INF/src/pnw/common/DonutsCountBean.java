package pnw.common;

public class DonutsCountBean {
     /**
     * ドーナツのID
     */
    private int donut_id;

    /**
     * ドーナツの選択数
     */
    private int donut_count;


    public DonutsCountBean(int donut_id, int donut_count) {
        this.donut_id = donut_id;
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

}
