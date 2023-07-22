package pnw.user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import java.time.LocalDateTime;

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

        try {
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成 donut_idが一致する行を持ってくる
            String sql = "SELECT * FROM donuts where donut_id=?";
            PreparedStatement stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/

            while (ite.hasNext()) {
                DonutsBean donut = ite.next();
                int i = donut.getDonutId();
                String predonutCount = request.getParameter(String.valueOf(i));
                int donutCount = Integer.parseInt(predonutCount);

                // ドーナツが選択されていたとき
                if (donutCount != 0) {
                    Integer i2 = Integer.valueOf(i);
                    String id = i2.toString();
                    stmt.setString(1, id);
                    // 実行結果取得
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        // カラムの値を取得する
                        String dname = rs.getString("donut_name");
                        double dprice = rs.getInt("donut_price");
                        DonutsCountBean dcount = new DonutsCountBean(i, dname, dprice, donutCount);
                        donuts.add(dcount);
                    }
                }
            }

            CartBean shoppingcart;

            // PreparedStatement stmt = null;
            switch (btn_val) {
                case "注文":
                    if (donuts.size() == 0) { // 注文が何もなかったら
                        String nonecart = "注文が何もありません";
                        session.setAttribute("nonecart", nonecart);
                        forwardURL = "/user/cart.jsp";

                        // ドーナツの情報を更新する処理
                        sql = "select s.donut_id, d.donut_name, d.donut_price, s.donut_stock from stock s join donuts d on (s.donut_id = d.donut_id)";
                        stmt = db.getStmt(sql);
                        // 実行結.果取得
                        rs = stmt.executeQuery();
                        // // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
                        int cnt = 0;
                        ArrayList<DonutsBean> infoArray = new ArrayList<DonutsBean>();
                        while (rs.next()) {
                            // // カラムの値を取得する．
                            int id = rs.getInt("donut_id");
                            String name = rs.getString("donut_name");
                            double price = rs.getInt("donut_price");
                            int quantity = rs.getInt("donut_stock");

                            // // beanを生成
                            DonutsBean bean = new DonutsBean(name, price, quantity);
                            bean.setDonutId(id);
                            // Listへbeanを追加する．
                            infoArray.add(bean);
                            cnt++;
                        }

                        // ドーナツの情報をセッションへセットする．
                        session.setAttribute("stock", infoArray);
                    } else { // 注文があったら
                        // 購入情報をBeanオブジェクトに格納して購入ページへ送信
                        int uid = user.getDockedNumber();
                        shoppingcart = new CartBean(uid, donuts);
                        session.setAttribute("shoppingcart", shoppingcart);
                        forwardURL = "/user/ordercheck.jsp";
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
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
