package com.makethelistapp.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.makethelistapp.core.dao.JdbcGListDao;
import com.makethelistapp.core.model.GList;
import com.mysql.jdbc.Statement;

@Component
public class JdbcGListDaoImpl implements JdbcGListDao {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	@Override
	public GList getGListById(int id) {
		String sql = "SELECT * FROM glist WHERE idGList = ?";
		GList glist = jdbcTemplate.queryForObject(sql, new Object[] { id }, new GListRowMapper());
		return glist;
	}

	@Override
	public List<GList> getAllGListsByEventId(int eventId) {
		List<GList> glists = new ArrayList<GList>();
		String sql = "SELECT * FROM glist WHERE eventId=" + eventId;
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
			GList glist = new GList();
			glist.setId((int) row.get("idGlist"));
			glist.setName((String) row.get("glistName"));
			glist.setEventId((int) row.get("eventId"));
			glists.add(glist);
		}
		return glists;
	}
	
	@Override
	public int updateGList(final GList glist) {
		
		final String sql = "INSERT INTO glist(`idGlist`, `glistName`, `eventId`)"
				+ "VALUES(?,?,?)"
				+ "ON DUPLICATE KEY UPDATE"
				+ "`glistName` = VALUES(`glistName`),"
				+ "`eventId` = VALUES(`eventId`)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {           

            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, glist.getId());
                ps.setString(2, glist.getName());
                ps.setInt(3, glist.getEventId());

                return ps;
            }
        }, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	
	public class GListRowMapper implements RowMapper<GList> {

		@Override
		public GList mapRow(ResultSet rs, int rowNum) throws SQLException {
			GList glist = new GList();
			glist.setId(rs.getInt("idGlist"));
			glist.setName(rs.getString("glistName"));
			glist.setEventId(rs.getInt("eventId"));
			return glist;
		}
		
	}

}
