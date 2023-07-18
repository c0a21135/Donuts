package pnw.checker;

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

import javax.servlet.RequestDispatcher;

@WebServlet("/checker/StockProcessServlet")
public class StockProcessServlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockProcessServlet() {
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

        request.setCharacterEncoding("utf-8");

        ResultSet rs;
        String forwardURL = null;
        // ボタンの値を取得する．
        String btn_val = request.getParameter("btn");

        try {
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．
            PnwDB db = new PnwDB("2023g03");
            String sql = null;
            // 入力値を取得しておく．
            int in_id = 0;
            if (!request.getParameter("id").equals("")) {
                in_id = Integer.valueOf(request.getParameter("id")).intValue();
            }
          
            String in_name = "dummy_name";
            if (!request.getParameter("name").equals("")) {
                in_name = request.getParameter("name");
            }

            String pre_price = "999";
            if (!request.getParameter("price").equals("")) {
                pre_price = request.getParameter("price");
            }
            double in_price = Double.parseDouble(pre_price);

            String pre_quantity = "999";
            if (!request.getParameter("quantity").equals("")) {
                pre_quantity = request.getParameter("quantity");
            }
            int in_quantity = Integer.parseInt(pre_quantity);

            PreparedStatement stmt = null;
            switch (btn_val) {
                case "追加":
                    sql = "INSERT INTO donuts (donut_id, donut_name, donut_price) VALUES(?,?,?)";
                    stmt = db.getStmt(sql);
                    stmt.setInt(1, in_id);
                    stmt.setString(2, in_name);
                    stmt.setDouble(3, in_price);
                    int ret1 = stmt.executeUpdate();

                    sql = "INSERT INTO stock (donut_id,donut_stock) VALUES(?,?)";
                    stmt = db.getStmt(sql);
                    stmt.setInt(1, in_id);
                    stmt.setInt(2, in_quantity);
                    int ret2 = stmt.executeUpdate();

                    break;

                case "更新":
                    sql = "UPDATE stock SET donut_id=?,donut_stock=? WHERE donut_id=?";
                    stmt = db.getStmt(sql);
                    stmt.setInt(1, in_id);
                    stmt.setInt(2, in_quantity);
                    stmt.setInt(3, in_id);
                    int ret3 = stmt.executeUpdate();
                    break;

                case "削除":
                    sql = "DELETE FROM stock WHERE donut_id=?";
                    stmt = db.getStmt(sql);
                    stmt.setInt(1, in_id);
                    int ret4 = stmt.executeUpdate();
                    
                    sql = "DELETE FROM donuts WHERE donut_id=?";
                    stmt = db.getStmt(sql);
                    stmt.setInt(1, in_id);
                    int ret5 = stmt.executeUpdate();
                    break;

            }
            // 個別の処理が終わったら，再度，DBから一覧を取得する．
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            sql = "select s.donut_id, d.donut_name, d.donut_price, s.donut_stock from stock s join donuts d on (s.donut_id = d.donut_id)";
            stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/

            // 実行結果取得
            rs = stmt.executeQuery();
            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            ArrayList<DonutsBean> infoArray = new ArrayList<DonutsBean>();
            while (rs.next()) {
                // カラムの値を取得する．
                int id = rs.getInt("donut_id");
                String name = rs.getString("donut_name");
                double price = rs.getInt("donut_price");
                int quantity = rs.getInt("donut_stock");
                // beanを生成
                DonutsBean bean = new DonutsBean(name, price, quantity);
                bean.setDonutId(id);
                // Listへbeanを追加する．
                infoArray.add(bean);
                // 見つかった
                cnt++;
            }
            // requestへセットする．
            request.setAttribute("stocklist", infoArray);
            forwardURL = "/checker/stockmanager.jsp";

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
