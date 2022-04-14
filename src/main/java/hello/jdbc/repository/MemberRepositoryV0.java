package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;

        PreparedStatement psmt = null;

        try {
            con = getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setString(1, member.getMemberId());
            psmt.setInt(2, member.getMoney());
            psmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {     // 항상 호출되는 게 보장할려고 finally에
            close(con, psmt, null);     // Exception 문제 생기면?
        }

    }

    private void close(Connection con, Statement stmt, ResultSet rs) {

        if (rs != null) {
            try{
                rs.close();   // SQLException 터져도 여기서 나올 수 있어
            } catch (SQLException e){
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try{
                stmt.close();   // SQLException 터져도 여기서 나올 수 있어
            } catch (SQLException e){
                log.info("error", e);
            }

        }
        if (con != null) {
            try{
                con.close();
            } catch (SQLException e){
                log.info("error", e);
            }
        }

    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}

