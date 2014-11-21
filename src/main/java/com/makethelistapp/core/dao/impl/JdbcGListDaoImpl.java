package com.makethelistapp.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.makethelistapp.core.dao.JdbcGListDao;
import com.makethelistapp.core.model.GList;

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
		String sql = "SELECT * FROM event WHERE idEvent = ?";
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
