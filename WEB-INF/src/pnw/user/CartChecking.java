package pnw.user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import javax.servlet.ServletContext;

@WebServlet("/CartCheckingServlet")
public class CartChecking extends HttpServlet {
    private ScheduledExecutorService scheduler;
    // private Timer timer;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartChecking() {
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
        // フォワード先を待機画面に
        String forwardURL = null;
        HttpSession session = request.getSession();

        // ショッピングカート情報を取得
        CartBean cart = (CartBean) session.getAttribute("shoppingcart");
        int docked_number = cart.getDockedNumber();

        int count = 0;
        if (session.getAttribute("cnt") == null){
            count = 1;
        } else{
            count = (int)session.getAttribute("cnt");
        }

        try {
            // ここから
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．インスタンス化
            PnwDB db = new PnwDB("2023g03");

            // 自分の整理番号よりcountだけ小さい人のcompletedが9(注文キャンセル)だったら
            // さらに1つ小さい人のcompletedを見に行くようにする
            String sql = "SELECT completed FROM users where docked_number=?";
            PreparedStatement stmt = db.getStmt(sql);
            stmt.setInt(1, docked_number - count);
            rs = stmt.executeQuery();
            while(rs.next()){
                if (rs.getInt("completed")==9){
                    count ++;
                    session.setAttribute("cnt", count);
                }
            }


            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            sql = "SELECT * FROM users where docked_number=? and completed not in (0, 9)";
            // String sql = "SELECT COUNT(*) FROM users WHERE < docked_number=そいつのID"

            stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/

            // ?のところに値いれる
            stmt.setInt(1, docked_number - count);

            // 実行結果取得
            // ここまで
            rs = stmt.executeQuery();
            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;

            // completedを１にする
            // int completed = 1;

            while (rs.next()) {

                // 見つかった
                cnt++;
            }
            if (cnt != 0) {
                // 見つかった == 前の人が終わっている

                // 注文者が既に注文しているかcompletedの値から確認する
                sql = "SELECT * FROM users where docked_number=? and completed=1";
                stmt = db.getStmt(sql);
                stmt.setInt(1, docked_number);
                rs = stmt.executeQuery();

                int cnt2 = 0;
                while (rs.next()) {
                    cnt2++;
                }

                // completedが1じゃない場合注文処理を行う
                if (cnt2 == 0) {
                    LinkedList<DonutsCountBean> dcountBean = cart.getDcountBean();
                    Iterator<DonutsCountBean> ite = dcountBean.iterator();

                    // 在庫がきちんとあるかを確認する
                    boolean stockFlag = true;
                    LinkedList<Integer> stocklist = new LinkedList<>();
                    int stock = 0;
                    while (ite.hasNext()) {
                        DonutsCountBean bean = ite.next();
                        sql = "select donut_stock from stock where donut_id=?";
                        stmt = db.getStmt(sql);
                        stmt.setInt(1, bean.getDonutId());
                        rs = stmt.executeQuery();
                        while (rs.next()) {
                            stock = rs.getInt("donut_stock");
                        }
                        if (bean.getDonutCount() > stock) {
                            stockFlag = false;
                            forwardURL = "/Menu";
                            String nonecart = "申し訳ございません。在庫が足りませんでした。<br>" + "現在の"+bean.getDonutName() + "の在庫は" + stock
                                    + "個です。";
                            session.setAttribute("nonecart", nonecart);
                        } else {
                            stocklist.add(stock);
                        }

                    }

                    // 全ての商品で在庫があった場合
                    if (stockFlag == true) {
                        dcountBean = cart.getDcountBean();
                        ite = dcountBean.iterator();
                        int cnt1 = 0;
                        while (ite.hasNext()) {
                            DonutsCountBean bean = ite.next();
                            sql = "INSERT INTO shopping_cart (docked_number, donut_id, donuts_count) VALUES(?,?,?)";
                            stmt = db.getStmt(sql);
                            stmt.setInt(1, docked_number);
                            stmt.setInt(2, bean.getDonutId());
                            stmt.setInt(3, bean.getDonutCount());
                            int ret3 = stmt.executeUpdate();

                            sql = "update stock set donut_stock=? where donut_id=?";
                            stmt = db.getStmt(sql);
                            stmt.setInt(1, (stocklist.get(cnt1)- bean.getDonutCount()));
                            stmt.setInt(2, bean.getDonutId());
                            cnt1++;
                            int ret4 = stmt.executeUpdate();
                        }

                        // completedの値を1にする
                        sql = "UPDATE users SET completed=1 WHERE docked_number=?";
                        // sql = "UPDATE users SET completed=1 WHERE docked_number=そいつのID
                        stmt = db.getStmt(sql);
                        stmt.setInt(1, docked_number);
                        int ret5 = stmt.executeUpdate();
                        forwardURL = "/user/completed.jsp";
                    }
                }

            } else {
                // 見つからない＝＝前の人が終わっていないので戻る
                forwardURL = "/user/ordering.jsp";
            }
            // sql = "SELECT * FROM users";
            // stmt = db.getStmt(sql);

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

    // @Override
    // public void init() throws ServletException {
    // scheduler = Executors.newScheduledThreadPool(1);

    // ServletContext context = getServletContext();
    // HttpServletRequest request = (HttpServletRequest)
    // context.getAttribute("javax.servlet.forward.request");
    // HttpServletResponse response = (HttpServletResponse)
    // context.getAttribute("javax.servlet.forward.response");

    // CartCheckingTask cartCheckingTask = new CartCheckingTask(request, response);
    // scheduler.scheduleAtFixedRate(cartCheckingTask, 0, 4, TimeUnit.SECONDS);

    // // timer = new Timer();
    // // timer.schedule(new CartCheckingTask(request, response), 0, 3000);
    // }

    // @Override
    // public void destroy() {
    // if (scheduler != null) {
    // scheduler.shutdown();
    // }
    // }

    // private class CartCheckingTask extends TimerTask {

    // private HttpServletRequest request;
    // private HttpServletResponse response;

    // // コンストラクタでHttpServletRequestを受け取る
    // public CartCheckingTask(HttpServletRequest request, HttpServletResponse
    // response) {
    // this.request = request;
    // this.response = response;
    // }

    // @Override
    // public void run() {
    // try {
    // // // ここに定期実行したい処理を実装
    // // // セッション情報にアクセスする必要がある場合は、requestを使用して処理を記述する
    // HttpSession session = request.getSession();
    // int flag = (int) session.getAttribute("flag");
    // if (flag != 0){
    // scheduler.shutdown();
    // }
    // doGet(request, response); // doGetメソッドの呼び出し
    // // doPost(request, response);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // }

}
