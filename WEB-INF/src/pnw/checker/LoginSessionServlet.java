package pnw.checker;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pnw.common.PnwDB;

import javax.servlet.RequestDispatcher;

@WebServlet("/checker/LoginSessionServlet")
public class LoginSessionServlet extends HttpServlet {

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginSessionServlet() {
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
		// ログイン画面で入力したユーザID
		String user_id = request.getParameter("checker_name");
		// ログイン画面で入力したパスワード
		String password = request.getParameter("checker_pw");
		// 転送先のURL
		String forwardURL = "";

		ResultSet rs;

		try {
			/** DB接続に関する共通部 START **/
			// DB接続してStatementを取得する．
			PnwDB db = new PnwDB("2023g03");
			// SQL文の作成(プレースホルダーを使うのがわかりやすい)
			String sql = "SELECT * FROM checker where checker_name=? and checker_pw=?";
			PreparedStatement stmt = db.getStmt(sql);
			/** DB接続に関する共通部 END **/

			stmt.setString(1, user_id);
			stmt.setString(2, password);
			// 実行結果取得
			rs = stmt.executeQuery();
			// データがなくなるまで(rs.next()がfalseになるまで)繰り返す
			int cnt = 0;
			ArrayList<UserInfoBean> infoArray = new ArrayList<UserInfoBean>();
			while (rs.next()) {
				// カラムの値を取得する．
				String id = rs.getString("checker_name");
				String pass = rs.getString("checker_pw");
				//beanを生成
				UserInfoBean bean = new UserInfoBean(id, pass);
				//Listへbeanを追加する．
				infoArray.add(bean);
				// 見つかった
				cnt++;
			}
			if (cnt == 0) {
				// 見つからない == 認証失敗
				forwardURL = "/checker/loginfailure.jsp";
			} else {
	//ここ
				HttpSession session = request.getSession();
				//Listの先頭を取得
				UserInfoBean info = infoArray.get(0);
				//Sessionへセットする．
				session.setAttribute("user", info);
				
				forwardURL = "/checker/checkerhome.jsp";

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
