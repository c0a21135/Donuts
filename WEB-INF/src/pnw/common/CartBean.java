package pnw.common;

import java.util.LinkedList;

import pnw.common.DonutsCountBean;

public class CartBean {
     /**
     * ユーザのID
     */
    private int docked_number;

    /**
     * ドーナツの種類とカウント
     */    
    private LinkedList<DonutsCountBean> dcountBean;

    public CartBean() {
    }

    public CartBean(int docked_number, LinkedList<DonutsCountBean> dCountBeans){
        this();
        this.docked_number = docked_number;
        this.dcountBean = dCountBeans;
    }

    public int getDockedNumber() {
        return docked_number;
    }

    public void setDockedNumber(int docked_number) {
        this.docked_number = docked_number;
    }

    public LinkedList<DonutsCountBean> getDcountBean() {
        return dcountBean;
    }

    public void setDcountBean(LinkedList<DonutsCountBean> dcountBean) {
        this.dcountBean = dcountBean;
    }

    

    
}
