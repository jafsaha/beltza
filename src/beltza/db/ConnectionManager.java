package beltza.db;

import java.sql.Connection;

public interface ConnectionManager {

	Connection getConnection();
	void release();
	
}
