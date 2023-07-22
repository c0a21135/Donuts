
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

import pnw.common.PnwDB;
import pnw.common.UsersBean;
import pnw.common.DonutsBean;

import javax.servlet.RequestDispatcher;

@WebServlet("/OperateHomeSurvlet")
public class OperateHomeSurvlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OperateHomeSurvlet() {
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
        try {

            HttpSession session = request.getSession();
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            String sql = "";
            PreparedStatement stmt = null;
            /** DB接続に関する共通部 END **/

            // -------
            // int docked_number=0;
            // if (session.getAttribute("operate_num") == null) {
            //     docked_number = 1;
            // } else {
            //     docked_number = (int)session.getAttribute("operate_num");
            // }
            // -------

            // ckeckerが見るユーザ情報を取る
            int start_docked_num = 0;
            UsersBean focus_user = new UsersBean("初期値", 999);
            focus_user.setDockedNumber(start_docked_num);
            if (session.getAttribute("focus_user") == null){ // sessionに今まで見ていたユーザの情報が無い場合、新たに作る
                // sqlでdocked_number=1の人の情報をもってくる
                sql = "SELECT * FROM users WHERE docked_number=1";
                stmt = db.getStmt(sql);
                rs = stmt.executeQuery();
                while(rs.next()){ //ユーザがいた場合、そのユーザの情報に置き換える(いなかった場合は初期値のまま)
                    focus_user.setDockedNumber(1);
                    focus_user.setNickName(rs.getString("nickname"));
                    focus_user.setCompleted(rs.getInt("completed"));
                }

            } else { // 見ていた情報があった場合、そのデータを持ってくる
                focus_user = (UsersBean)session.getAttribute("focus_user");
            }


            sql = "SELECT tb2.donut_name, tb1.donuts_count, tb2.donut_price FROM shopping_cart tb1 JOIN donuts tb2 ON tb1.donut_id = tb2.donut_id JOIN users tb3 ON tb1.docked_number = tb3.docked_number WHERE tb1.docked_number = ?";
            stmt = db.getStmt(sql);
            // -----
            // stmt.setInt(1, docked_number);
            // -----
            stmt.setInt(1, focus_user.getDockedNumber());
            // 実行結果取得
            rs = stmt.executeQuery();


            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            ArrayList<UserInfoBean_tyuumonn> order_list = new ArrayList<UserInfoBean_tyuumonn>();

            while (rs.next()) {
                // カラムの値を取得する．
                // tb1.donuts_count, tb2.donut_name, tb2_donut_price

                int donuts_count = rs.getInt("tb1.donuts_count");
                String donut_name = rs.getString("tb2.donut_name");
                double donut_price = rs.getDouble("tb2.donut_price");
                // beanを生成
                UserInfoBean_tyuumonn bean = new UserInfoBean_tyuumonn(donut_name, donut_price);
                bean.setCount(donuts_count);
                bean.setPrice(donut_price);

                // Listへbeanを追加する．
                order_list.add(bean);

                // 見つかった
                cnt++;
            }

            // request, sessionへセットする．
            session.setAttribute("order_list", order_list);
            session.setAttribute("focus_user", focus_user);

            String comment = "";
            session.setAttribute("comm", comment);
            session.setAttribute("jumpcomm", "");
            
            forwardURL = "/UserListServlet";
            
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
