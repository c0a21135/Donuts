package pnw.user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pnw.common.CartBean;
import pnw.common.DonutsBean;
import pnw.common.PnwDB;
import pnw.common.UsersBean;
import pnw.common.DonutsCountBean;

import javax.servlet.RequestDispatcher;

@WebServlet("/CartCreateServlet")
public class CartCreate extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // HTTP応答のエンコード設定
        response.setContentType("text/html; charset=UTF-8");

        ResultSet rs;
        String forwardURL = null;
        // ボタンの値を取得する．
        String btn_val = request.getParameter("btn");
        HttpSession session = request.getSession();

        // ログインしたユーザ情報を受け取る
        UsersBean user = (UsersBean) session.getAttribute("user");
        // ドーナツの一覧(名前・値段・在庫)を取得
        ArrayList<DonutsBean> list = (ArrayList<DonutsBean>) session.getAttribute("stock");
        Iterator<DonutsBean> ite = list.iterator();

        LinkedList<DonutsCountBean> donuts = new LinkedList<DonutsCountBean>();

        while (ite.hasNext()) {
            DonutsBean donut = ite.next();
            int i = donut.getDonutId();
            String predonutCount = request.getParameter(String.valueOf(i));
            int donutCount = Integer.parseInt(predonutCount);
            if (donutCount != 0) {
                DonutsCountBean dcount = new DonutsCountBean(i, donutCount);
                donuts.add(dcount);
            }
        }

        CartBean shoppingcart;

        // PreparedStatement stmt = null;
        switch (btn_val) {
            case "注文":
                if (donuts.size() == 0) {
                    String nonecart = "注文が何もありません";
                    request.setAttribute("nonecart", nonecart);
                    forwardURL = "/user/cart.jsp";
                } else {
                    // ログインしたユーザをUsersBeanオブジェクトに格納して購入ページへ送信
                    int uid = user.getDockedNumber();
                    shoppingcart = new CartBean(uid, donuts);
                    session.setAttribute("shoppingcart", shoppingcart);
                    forwardURL = "/user/ordercheck.jsp";
                }
                break;
        }
        
        // 外部ファイルに転送する準備
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
        // 外部ファイルに表示処理を任せる
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
