package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beltza.db.ConnectionManager;
import beltza.domain.Cliente;

public class JdbcClienteDAO extends JdbcDAO implements ClienteDAO {

	protected final String TABLE_CLIENTES = "CLIENTES";
	
	protected final String SEQ_CLIENTES = "SEQ_CLIENTES";

	protected final String ID = "ID";
	protected final String CODIGO= "CODIGO";
	
	
	public JdbcClienteDAO(ConnectionManager connectionManager) {
		super(connectionManager);
	}
	
	protected final String SQL_INSERT = "insert into " + TABLE_CLIENTES +
	" (" + ID + ", " + CODIGO + ") " +
	" values (?,?)";

	public Long addCliente(String codigo) {
		Connection con = getConnection();
		try {
			Long nextId = getNextSequenceValue(SEQ_CLIENTES);
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setObject(1, nextId);
			pstmt.setString(2, codigo);
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				return nextId;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Boolean existCliente(String codigo) {
		return (getClienteByCodigo(codigo) != null);
	}
	
	protected final String SQL_SELECT = "select * from " + TABLE_CLIENTES;

	public Collection<Cliente> searchClientes(Cliente cliente) {
		Connection con = getConnection();
		try {
			String sql = SQL_SELECT;
			if (cliente != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (cliente.getCodigo() != null && cliente.getCodigo().trim().length() > 0) 
					map.put(CODIGO, cliente.getCodigo());

				String where = DAOUtils.buildWhere(map, DAOUtils.AND);
				sql = sql + " " + where;
			}
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			Collection<Cliente> clientes = new ArrayList<Cliente>();
			while (rset.next()) {
				Cliente clienteResultado = buildCliente(rset);
				clientes.add(clienteResultado);
			}
			return clientes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Cliente getClienteById(Long id) {
		if (id == null) 
			return null;
		return getClienteBy(ID, id);
	}

	public Cliente getClienteByCodigo(String codigo) {
		if ((codigo == null) || ("".equals(codigo))) return null;
		return getClienteBy(CODIGO, codigo);
	}

	protected final String SQL_GETBY = "select * from " + TABLE_CLIENTES;

	protected Cliente getClienteBy(String field, Object value) {
		Connection con = getConnection();
		try {
			String sql = SQL_GETBY;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(field, value);
			String where = DAOUtils.buildWhere(map, DAOUtils.AND);
			sql = sql + " " + where;

			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			Cliente cliente = null;
			if (rset.next()) {
				cliente = buildCliente(rset);
			}
			return cliente;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected Cliente buildCliente(ResultSet rset) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rset.getLong(ID));
		cliente.setCodigo(rset.getString(CODIGO));
		return cliente;
	}
	
	
	
}
