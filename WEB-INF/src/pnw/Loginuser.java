package pnw;

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

import javax.servlet.RequestDispatcher;

//ここ 
@WebServlet("/Loginuser")

public class Loginuser extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Loginuser() {
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
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            String sql = "SELECT * FROM users";
            PreparedStatement stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/

            // 実行結果取得
            rs = stmt.executeQuery();
            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            //ここ
            ArrayList<UsersBean> infoArray = new ArrayList<UsersBean>();
            while (rs.next()) {
                // カラムの値を取得する．
                int id = rs.getInt("docked_number");
                String name = rs.getString("nickname");
                int comp = rs.getInt("completed");
                // beanを生成
                UsersBean bean = new UsersBean(name, comp);
                bean.setDockedNumber(id);
                // Listへbeanを追加する．
                infoArray.add(bean);
                // 見つかった
                cnt++;
            }
            // requestへセットする．
            //
            request.setAttribute("userlist", infoArray);
            forwardURL = "/loginuser.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }

        // kono下は弄らない
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

