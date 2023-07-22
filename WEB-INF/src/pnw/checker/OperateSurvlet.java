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

@WebServlet("/OperateSurvlet")
public class OperateSurvlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OperateSurvlet() {
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

            HttpSession session = request.getSession();

            // int docked_number=0;
            // if (session.getAttribute("operate_num") == null) {
            // docked_number = 1;
            // } else {
            // docked_number = (int)session.getAttribute("operate_num");
            // }

            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．
            PnwDB db = new PnwDB("2023g03");
            PreparedStatement stmt = null;
            UsersBean focus_user = (UsersBean) session.getAttribute("focus_user");

            int docked_number = focus_user.getDockedNumber();
            String sql = "";
            String name = focus_user.getNickName();
            int completed = focus_user.getCompleted();
            String comment = "";

            switch (btn_val) {
                case "完了": // 完了処理の後、1つ後へ移動する処理を行う
                    switch (completed){
                        case 1:
                            // 現在のユーザのcompletedの値を2に変更する
                            sql = "UPDATE users SET completed=2 WHERE docked_number=?";
                            stmt = db.getStmt(sql);
                            stmt.setInt(1, docked_number);
                            int ret1 = stmt.executeUpdate();

                            // 次のユーザの情報をfocus_userに入れる
                            docked_number ++;
                            sql = "SELECT * FROM users WHERE docked_number=?";
                            stmt = db.getStmt(sql);
                            stmt.setInt(1, docked_number);
                            rs = stmt.executeQuery();
                            // 次の人がいるか確認する
                            int cnt = 0;
                            while (rs.next()){ // 次の人がいた場合
                                name = rs.getString("nickname");
                                completed = rs.getInt("completed");
                                // focus_userの情報を次の人に更新する
                                focus_user.setNickName(name);
                                focus_user.setCompleted(completed);
                                focus_user.setDockedNumber(docked_number);
                                // docked_number += 1;
                                cnt ++;
                            }
                            if (cnt == 0){ // 次の人がいなかった場合
                                comment = "次の人はいません";
                                // focus_userの情報はそのまま、completedの値だけ変更する
                                focus_user.setCompleted(2);
                            }
                            break;
                        
                        case 0:
                            comment = "まだ注文の確定がされていません";
                            break;
                        
                        case 2:
                            comment = "既に注文が完了しています";
                            break;

                        case 9:
                            comment = "注文がキャンセルされた方です";
                            break;
                    }
                    break;

                case "１つ後へ":
                    // sql = "select docked_number from users ORDER BY docked_number DESC LIMIT 0, 1";
                    // stmt = db.getStmt(sql);
                    // rs = stmt.executeQuery();
                    // int newest_num = rs.getInt("docked_number");
                    // if (docked_number + 1 <= newest_num) {
                    //     // 次のユーザの情報をfocus_userに入れる
                    //     docked_number++;
                    //     sql = "SELECT * FROM users WHERE docked_number=?";
                    //     stmt = db.getStmt(sql);
                    //     stmt.setInt(1, docked_number);
                    //     rs = stmt.executeQuery();
                    //     while (rs.next()) {
                    //         name = rs.getString("nickname");
                    //         completed = rs.getInt("completed");
                    //     }
                    //     focus_user.setNickName(name);
                    //     focus_user.setCompleted(completed);
                    //     focus_user.setDockedNumber(docked_number);
                    // } else {
                    //     comment = "次の人はいません";
                    // }

                    // 次のユーザの情報をfocus_userに入れる
                    docked_number ++;
                    sql = "SELECT * FROM users WHERE docked_number=?";
                    stmt = db.getStmt(sql);
                    stmt.setInt(1, docked_number);
                    rs = stmt.executeQuery();
                    // 次の人がいるか確認する
                    int cnt2 = 0;
                    while (rs.next()){ // 次の人がいた場合
                        name = rs.getString("nickname");
                        completed = rs.getInt("completed");
                        // focus_userの情報を次の人に更新する
                        focus_user.setNickName(name);
                        focus_user.setCompleted(completed);
                        focus_user.setDockedNumber(docked_number);
                        // docked_number += 1;
                        cnt2 ++;
                    }
                    if (cnt2 == 0){ // 次の人がいなかった場合
                        comment = "次の人はいません";
                    }
                    break;

                case "１つ前へ":
                    if (docked_number - 1 >= 1) {
                        // 前のユーザの情報をfocus_userに入れる
                        docked_number--;
                        sql = "SELECT * FROM users WHERE docked_number=?";
                        stmt = db.getStmt(sql);
                        stmt.setInt(1, docked_number);
                        rs = stmt.executeQuery();
                        while (rs.next()) {
                            name = rs.getString("nickname");
                            completed = rs.getInt("completed");
                            focus_user.setNickName(name);
                            focus_user.setCompleted(completed);
                            focus_user.setDockedNumber(docked_number);
                        }
                    } else {
                        comment = "前の人はいません";
                    }
                    break;
                
                case "指定した番号へジャンプ":
                    String jump_comment = "";
                    int jump_num = Integer.valueOf(request.getParameter("jump_num")).intValue();
                    // 入力された値が有効か判定
                    sql = "select docked_number from users ORDER BY docked_number DESC LIMIT 0, 1";
                    stmt = db.getStmt(sql);
                    rs = stmt.executeQuery();
                    int newest_num = 1;
                    while(rs.next()) {
                        newest_num = rs.getInt("docked_number");
                    }
                    // 入力された値が発券されている整理番号の範囲内か判定
                    if (0<=jump_num && jump_num <=newest_num){
                        // 有効だった場合、docked_numberを入力された値に変更し、情報を取得
                        docked_number = jump_num;
                        sql = "SELECT * FROM users WHERE docked_number=?";
                        stmt = db.getStmt(sql);
                        stmt.setInt(1, docked_number);
                        rs = stmt.executeQuery();
                        while (rs.next()) {
                            name = rs.getString("nickname");
                            completed = rs.getInt("completed");
                            focus_user.setNickName(name);
                            focus_user.setCompleted(completed);
                            focus_user.setDockedNumber(docked_number);
                        }
                        
                    } else{
                        // 有効範囲外の場合
                        jump_comment = "指定された整理番号はありません";
                    }
                    session.setAttribute("jumpcomm", jump_comment);
                    break;
                

            }

            session.setAttribute("comm", comment);

            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            // String sql = "SELECT tb2.donut_name, tb1.donuts_count, tb2.donut_price,
            // tb3.nickname FROM shopping_cart tb1 JOIN donuts tb2 ON tb1.donut_id =
            // tb2.donut_id JOIN users tb3 ON tb1.docked_number = tb3.docked_number WHERE
            // tb1.docked_number = ? AND tb3.completed = 1";
            sql = "SELECT tb2.donut_name, tb1.donuts_count, tb2.donut_price FROM shopping_cart tb1 JOIN donuts tb2 ON tb1.donut_id = tb2.donut_id JOIN users tb3 ON tb1.docked_number = tb3.docked_number WHERE tb1.docked_number = ?";
            stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/
            stmt.setInt(1, focus_user.getDockedNumber());
            // 実行結果取得
            rs = stmt.executeQuery();

            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            ArrayList<UserInfoBean_tyuumonn> order_list = new ArrayList<UserInfoBean_tyuumonn>();
            String nickname = "";

            while (rs.next()) {
                // カラムの値を取得する．
                // tb1.donuts_count, tb2.donut_name, tb2_donut_price

                int donuts_count = rs.getInt("tb1.donuts_count");
                String donut_name = rs.getString("tb2.donut_name");
                double donut_price = rs.getDouble("tb2.donut_price");
                // nickname = rs.getString("tb3.nickname");
                // beanを生成
                UserInfoBean_tyuumonn bean = new UserInfoBean_tyuumonn(donut_name, donut_price);
                bean.setCount(donuts_count);
                bean.setPrice(donut_price);

                // Listへbeanを追加する．
                order_list.add(bean);

                // 見つかった
                cnt++;
            }

            // requestへセットする．
            session.setAttribute("order_list", order_list);
            session.setAttribute("focus_user", focus_user);

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
