package pnw.user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pnw.common.DonutsBean;
import pnw.common.PnwDB;
import pnw.common.UsersBean;

import javax.servlet.RequestDispatcher;

@WebServlet("/Menu")
public class Menu extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Menu() {
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

        try {
            PnwDB db = new PnwDB("2023g03");
            String sql = null;

            HttpSession session = request.getSession();

            PreparedStatement stmt = null;

            sql = "select s.donut_id, d.donut_name, d.donut_price, s.donut_stock from stock s join donuts d on (s.donut_id = d.donut_id)";
            stmt = db.getStmt(sql);

            // 実行結果取得
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

                // int id = rs.getInt("docked_number");
                // String name = rs.getString("nickname");
                // int comp = rs.getInt("completed");
                // // beanを生成
                DonutsBean bean = new DonutsBean(name, price, quantity);
                bean.setDonutId(id);
                // Listへbeanを追加する．
                infoArray.add(bean);
                // bean.setDockedNumber(id);
                // // Listへbeanを追加する．
                // infoArray.add(bean);
                // // 見つかった
                cnt++;
            }

            // セッションへセットする．
            session.setAttribute("stock", infoArray);
            forwardURL = "/user/cart.jsp";
            // forwardURL = "/loginuser.jsp";

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
