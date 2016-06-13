package beltza.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.commons.Operator;
import beltza.dao.CierreDAO;
import beltza.dao.ClienteDAO;
import beltza.dao.ConfiguracionDAO;
import beltza.dao.DiaDAO;
import beltza.dao.EspecieDAO;
import beltza.dao.MapDAO;
import beltza.dao.MovimientoDAO;
import beltza.dao.OperacionDAO;
import beltza.dao.UpdateDAO;
import beltza.db.Transactional;
import beltza.domain.Cierre;
import beltza.domain.CierreProp;
import beltza.domain.Cliente;
import beltza.domain.Dia;
import beltza.domain.DiaProp;
import beltza.domain.Especie;
import beltza.domain.EspecieProp;
import beltza.domain.Movimiento;
import beltza.domain.MovimientoProp;
import beltza.domain.MovimientoType;
import beltza.domain.Operacion;
import beltza.domain.OperacionProp;
import beltza.domain.OperacionSubType;
import beltza.domain.OperacionType;
import beltza.dto.EspecieDTO;
import beltza.dto.OperacionDTO;
import beltza.dto.SearchMovimientosDTO;
import beltza.dto.SearchOperacionesDTO;

public class BeltzaBusinessModelImpl implements BeltzaBusinessModel {

	private static Logger logger = Logger.getLogger(BeltzaBusinessModelImpl.class);

	private final String DEFAULT_PASSWORD = "admin";

	protected Map<ChangeType, List<BeltzaObserver>> observers;

	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	
	protected EspecieDAO especieDAO;
	protected ClienteDAO clienteDAO;
	protected OperacionDAO operacionDAO;
	protected MovimientoDAO movimientoDAO;
	protected ConfiguracionDAO configuracionDAO;
	protected DiaDAO diaDAO;
	protected MapDAO mapDAO;
	protected CierreDAO cierreDAO;
	protected UpdateDAO updateDAO;

	protected Integer appVersion = null;
	protected String codigoEspecieDeReferencia = "";
	protected String codigosEspeciesMonitoreo = "";

	protected Queue<ChangeType> notificationsQueue = new ConcurrentLinkedQueue<ChangeType>();
	
	public BeltzaBusinessModelImpl(ConfiguracionDAO configuracionDAO, EspecieDAO especieDAO, OperacionDAO operacionDAO, MovimientoDAO movimientoDAO,
			ClienteDAO clienteDAO, DiaDAO diaDAO, MapDAO mapDAO, CierreDAO cierreDAO, UpdateDAO updateDAO) {
		setEspecieDAO(especieDAO);
		setClienteDAO(clienteDAO);
		setConfiguracionDAO(configuracionDAO);
		setOperacionDAO(operacionDAO);
		setMovimientoDAO(movimientoDAO);
		setDiaDAO(diaDAO);
		setMapDAO(mapDAO);
		setCierreDAO(cierreDAO);
		setUpdateDAO(updateDAO);

		observers = new HashMap<ChangeType, List<BeltzaObserver>>();

		if (getConfiguracionDAO().getPassword() == null) {
			setPassword(DEFAULT_PASSWORD);
		}
	}

	public Especie getEspecieDeReferencia() {
		Especie especieReferencia = null;
		if (codigoEspecieDeReferencia != null) {
			especieReferencia = getEspecieDAO().getEspecieByCodigo(codigoEspecieDeReferencia);
		}
		return especieReferencia;
	}

	public Collection<Especie> getEspeciesMonitoreo() {
		Collection<Especie> especies = new ArrayList<Especie>();
		if (codigosEspeciesMonitoreo != null) {
			String[] codigos = codigosEspeciesMonitoreo.split(",");
			for (String codigo : codigos) {
				Especie especie = getEspecieDAO().getEspecieByCodigo(codigo.trim());
				if (especie != null) {
					especies.add(especie);
				}
			}
		}
		return especies;
	}

	public Integer getVersion() {
		if (appVersion == null) {
			appVersion  = configuracionDAO.getVersion();
		}
		return appVersion;
	}

	@Transactional
	public void setPassword(String password) {
		if (password == null) {
			throw new BeltzaBusinessException("Password inv\u00E1lida");
		}
		getConfiguracionDAO().setPassword(password);

		scheduleNotification(ChangeType.CONFIGURACION);
		notifyObservers();
	}

	public Boolean verifyPassword(String password) {
		if (password == null) {
			return false;
		}
		return getConfiguracionDAO().getPassword().equals(password);
	}

	@Transactional
	public Long addEspecie(EspecieDTO especieDTO) {
		if (!getEspecieDAO().existEspecie(especieDTO.getCodigo())) {
			Map<String, Object> especieMap = new HashMap<String, Object>();
			especieMap.put(EspecieProp.CODIGO, especieDTO.getCodigo());
			especieMap.put(EspecieProp.AFORO_INVERSO, especieDTO.getAforoInverso());
			if (especieDTO.getAforoInverso() && !especieDTO.getAforo().equals(0.0)) {
				especieMap.put(EspecieProp.AFORO, 1 / especieDTO.getAforo());
			} else {
				especieMap.put(EspecieProp.AFORO, especieDTO.getAforo());
			}
			especieMap.put(EspecieProp.STOCK, 0);
			especieMap.put(EspecieProp.POSICION, 0);
			Long id = getEspecieDAO().addEspecie(especieMap);
			if (!id.equals(-1L)) {

				scheduleNotification(ChangeType.ESPECIE);
				notifyObservers();
				
				return id;
			} else {
				throw new BeltzaBusinessException("Imposible agregar especie");
			}
		} else {
			throw new BeltzaBusinessException("Ya existe una especie con c\u00F3digo " + especieDTO.getCodigo());
		}
	}

	@Transactional
	public void saveEspecie(Especie especie) {
		getEspecieDAO().saveEspecie(especie);
		scheduleNotification(ChangeType.ESPECIE);
		notifyObservers();
	}

	public Collection<Especie> searchEspecies(Especie especie) {
		return getEspecieDAO().searchEspecies(especie);
	}

	public Collection<Especie> getAllEspecies() {
		return getEspecieDAO().searchEspecies(null);
	}

	public Especie getEspecieByCodigo(String codigo) {
		return getEspecieDAO().getEspecieByCodigo(codigo);
	}

	public Especie getEspecieById(Long id) {
		return getEspecieDAO().getEspecieById(id);
	}

	protected void addToPosicion(Long especieId, Double cantidad) {
		Especie especie = getEspecieDAO().getEspecieById(especieId);
		especie.setPosicion(especie.getPosicion() + cantidad);
		getEspecieDAO().saveEspecie(especie);

		scheduleNotification(ChangeType.ESPECIE);
		//notifyObservers();
	}

	protected void addToStock(Long especieId, Double cantidad) {
		Especie especie = getEspecieDAO().getEspecieById(especieId);
		especie.setStock(especie.getStock() + cantidad);
		getEspecieDAO().saveEspecie(especie);

		scheduleNotification(ChangeType.ESPECIE);
		//notifyObservers();
	}

	@Transactional
	public Long addCliente(String codigo) {
		if (!getClienteDAO().existCliente(codigo)) {
			Long id = getClienteDAO().addCliente(codigo);
			if (!id.equals(-1L)) {
				scheduleNotification(ChangeType.CLIENTE);
				notifyObservers();
				return id;
			} else {
				throw new BeltzaBusinessException("Imposible agregar cliente.");
			}
		} else {
			throw new BeltzaBusinessException("Ya existe un cliente con c\u00F3digo " + codigo);
		}
	}

	public Collection<Cliente> getAllClientes() {
		return getClienteDAO().searchClientes(null);
	}

	public Cliente getClienteByCodigo(String codigo) {
		return getClienteDAO().getClienteByCodigo(codigo);
	}

	public Cliente getClienteById(Long id) {
		return getClienteDAO().getClienteById(id);
	}

	public Collection<Cliente> searchClientes(Cliente cliente) {
		return getClienteDAO().searchClientes(cliente);
	}

	@Transactional(checkpoint = "false")
	public Long addOperacion(OperacionDTO operacionDTO) {
		Dia diaAbierto = null;
		if (operacionDTO.getDiaId() != null) {
			diaAbierto =  buildDiaFromMap(  getDiaDAO().getDiaById(operacionDTO.getDiaId()));
		} else {
			diaAbierto = getDiaAbierto();
		}
		if (diaAbierto == null) {
			throw new BeltzaBusinessException("Imposible agregar operacion. No hay dia abierto.");
		}
		Map<String, Object> operacionMap = new HashMap<String, Object>();
		operacionMap.put(OperacionProp.TIPO, operacionDTO.getTipo());
		operacionMap.put(OperacionProp.SUBTIPO, operacionDTO.getSubtipo());
		operacionMap.put(OperacionProp.CANTIDAD, operacionDTO.getCantidad());
		operacionMap.put(OperacionProp.CANTIDAD_SALE, operacionDTO.getCantidadSale());
		operacionMap.put(OperacionProp.FECHA_ALTA, new Date());
		operacionMap.put(OperacionProp.FECHA_LIQUIDACION, operacionDTO.getFechaLiquidacion());
		operacionMap.put(OperacionProp.NOTAS, operacionDTO.getNotas());
		operacionMap.put(OperacionProp.DIA, diaAbierto.getId());
		operacionMap.put(OperacionProp.VALORIZACION, operacionDTO.getValorizacion());
		if (operacionDTO.getClienteCod() != null) {
			// Si el cliente no existe se da de alta
			if (!getClienteDAO().existCliente(operacionDTO.getClienteCod())) {
				addCliente(operacionDTO.getClienteCod());
			}
			Cliente cliente = getClienteByCodigo(operacionDTO.getClienteCod());
			operacionMap.put(OperacionProp.CLIENTE, cliente.getId());
		}

		if (operacionDTO.getTipo().equals(OperacionType.INGRESO) || operacionDTO.getTipo().equals(OperacionType.ALTA)
				|| operacionDTO.getTipo().equals(OperacionType.CANJE) || operacionDTO.getTipo().equals(OperacionType.COMPRA)
				|| operacionDTO.getTipo().equals(OperacionType.VENTA)) {
			// Si la especie no existe se da de alta
			if (!getEspecieDAO().existEspecie(operacionDTO.getEspecieEntraCod())) {
				EspecieDTO especieDTO = new EspecieDTO();
				especieDTO.setCodigo(operacionDTO.getEspecieEntraCod());
				especieDTO.setAforo(1.0);
				especieDTO.setAforoInverso(false);
				addEspecie(especieDTO);
			}
			Especie especieEntra = getEspecieByCodigo(operacionDTO.getEspecieEntraCod());
			operacionMap.put(OperacionProp.ESPECIE_ENTRA, especieEntra.getId());
		}
		if (operacionDTO.getTipo().equals(OperacionType.EGRESO) || operacionDTO.getTipo().equals(OperacionType.BAJA)
				|| operacionDTO.getTipo().equals(OperacionType.CANJE) || operacionDTO.getTipo().equals(OperacionType.COMPRA)
				|| operacionDTO.getTipo().equals(OperacionType.VENTA)) {
			// Si la especie no existe se da de alta
			if (!getEspecieDAO().existEspecie(operacionDTO.getEspecieSaleCod())) {
				EspecieDTO especieDTO = new EspecieDTO();
				especieDTO.setCodigo(operacionDTO.getEspecieSaleCod());
				especieDTO.setAforo(1.0);
				especieDTO.setAforoInverso(false);
				addEspecie(especieDTO);
			}
			Especie especieSale = getEspecieByCodigo(operacionDTO.getEspecieSaleCod());
			operacionMap.put(OperacionProp.ESPECIE_SALE, especieSale.getId());
		}

		Long opId = getOperacionDAO().addOperacion(operacionMap);
		if (!opId.equals(-1L)) {

			Collection<Map<String, Object>> movimientos = new ArrayList<Map<String, Object>>();

			// Alta de Ingresos, Egresos
			if (OperacionType.INGRESO.equals(operacionMap.get(OperacionProp.TIPO)) || OperacionType.EGRESO.equals(operacionMap.get(OperacionProp.TIPO))) {

				Map<String, Object> movimientoMap = new HashMap<String, Object>();
				movimientoMap.put(MovimientoProp.OPERACION, opId);
				movimientoMap.put(MovimientoProp.DIA, diaAbierto.getId());
				movimientoMap.put(MovimientoProp.TIPO, MovimientoType.ORIGINAL);
				movimientoMap.put(MovimientoProp.CLIENTE, operacionMap.get(OperacionProp.CLIENTE));
				movimientoMap.put(MovimientoProp.LIQUIDADO, false);
				movimientoMap.put(MovimientoProp.FECHA_ALTA, operacionMap.get(OperacionProp.FECHA_ALTA));
				if (OperacionType.INGRESO.equals(operacionMap.get(OperacionProp.TIPO))) {
					movimientoMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_ENTRA));
					movimientoMap.put(MovimientoProp.CANTIDAD, operacionMap.get(OperacionProp.CANTIDAD));
					addToPosicion((Long) movimientoMap.get(MovimientoProp.ESPECIE), (Double) movimientoMap.get(MovimientoProp.CANTIDAD));
				} else if (OperacionType.EGRESO.equals(operacionMap.get(OperacionProp.TIPO))) {
					movimientoMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_SALE));
					movimientoMap.put(MovimientoProp.CANTIDAD, -1 * (Double) operacionMap.get(OperacionProp.CANTIDAD));
					addToPosicion((Long) movimientoMap.get(MovimientoProp.ESPECIE), (Double) movimientoMap.get(MovimientoProp.CANTIDAD));
				}
				movimientos.add(movimientoMap);

				// Alta de Altas y Bajas
			} else if (OperacionType.ALTA.equals(operacionMap.get(OperacionProp.TIPO)) || OperacionType.BAJA.equals(operacionMap.get(OperacionProp.TIPO))) {

				// Altas y bajas entran liquidadas y no tienen movimiento de
				// cancelacion
				operacionDTO.setLiquidado(true);

				Map<String, Object> movimientoMap = new HashMap<String, Object>();
				movimientoMap.put(MovimientoProp.OPERACION, opId);
				movimientoMap.put(MovimientoProp.DIA, diaAbierto.getId());
				movimientoMap.put(MovimientoProp.TIPO, MovimientoType.ORIGINAL);
				movimientoMap.put(MovimientoProp.CLIENTE, operacionMap.get(OperacionProp.CLIENTE));
				movimientoMap.put(MovimientoProp.LIQUIDADO, false);
				movimientoMap.put(MovimientoProp.FECHA_ALTA, operacionMap.get(OperacionProp.FECHA_ALTA));
				if (OperacionType.ALTA.equals(operacionMap.get(OperacionProp.TIPO))) {
					movimientoMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_ENTRA));
					movimientoMap.put(MovimientoProp.CANTIDAD, operacionMap.get(OperacionProp.CANTIDAD));
				} else if (OperacionType.BAJA.equals(operacionMap.get(OperacionProp.TIPO))) {
					movimientoMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_SALE));
					movimientoMap.put(MovimientoProp.CANTIDAD, -1 * (Double) operacionMap.get(OperacionProp.CANTIDAD));
				}
				movimientos.add(movimientoMap);

				// Alta de Canjes
			} else if (OperacionType.CANJE.equals(operacionMap.get(OperacionProp.TIPO))) {
				Map<String, Object> movEntraMap = new HashMap<String, Object>();
				movEntraMap.put(MovimientoProp.OPERACION, opId);
				movEntraMap.put(MovimientoProp.DIA, diaAbierto.getId());
				movEntraMap.put(MovimientoProp.TIPO, MovimientoType.ORIGINAL);
				movEntraMap.put(MovimientoProp.LIQUIDADO, false);
				movEntraMap.put(MovimientoProp.FECHA_ALTA, operacionMap.get(OperacionProp.FECHA_ALTA));
				movEntraMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_ENTRA));
				movEntraMap.put(MovimientoProp.CLIENTE, operacionMap.get(OperacionProp.CLIENTE));
				movEntraMap.put(MovimientoProp.CANTIDAD, operacionMap.get(OperacionProp.CANTIDAD));

				Map<String, Object> movSaleMap = new HashMap<String, Object>();
				movSaleMap.put(MovimientoProp.OPERACION, opId);
				movSaleMap.put(MovimientoProp.DIA, diaAbierto.getId());
				movSaleMap.put(MovimientoProp.TIPO, MovimientoType.ORIGINAL);
				movSaleMap.put(MovimientoProp.LIQUIDADO, false);
				movSaleMap.put(MovimientoProp.FECHA_ALTA, operacionMap.get(OperacionProp.FECHA_ALTA));
				movSaleMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_SALE));
				movSaleMap.put(MovimientoProp.CLIENTE, operacionMap.get(OperacionProp.CLIENTE));
				movSaleMap.put(MovimientoProp.CANTIDAD, -1 * ((Double) operacionMap.get(OperacionProp.CANTIDAD_SALE)));

				addToPosicion((Long) movEntraMap.get(MovimientoProp.ESPECIE), (Double) movEntraMap.get(MovimientoProp.CANTIDAD));
				addToPosicion((Long) movSaleMap.get(MovimientoProp.ESPECIE), (Double) movSaleMap.get(MovimientoProp.CANTIDAD));

				movimientos.add(movEntraMap);
				movimientos.add(movSaleMap);

				// Alta de Compras y Ventas
			} else if (OperacionType.COMPRA.equals(operacionMap.get(OperacionProp.TIPO)) || OperacionType.VENTA.equals(operacionMap.get(OperacionProp.TIPO))) {

				// Generacion de movimientos
				Map<String, Object> movEntraMap = new HashMap<String, Object>();
				movEntraMap.put(MovimientoProp.OPERACION, opId);
				movEntraMap.put(MovimientoProp.DIA, diaAbierto.getId());
				movEntraMap.put(MovimientoProp.TIPO, MovimientoType.ORIGINAL);
				movEntraMap.put(MovimientoProp.LIQUIDADO, false);
				movEntraMap.put(MovimientoProp.FECHA_ALTA, operacionMap.get(OperacionProp.FECHA_ALTA));
				movEntraMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_ENTRA));
				movEntraMap.put(MovimientoProp.CLIENTE, operacionMap.get(OperacionProp.CLIENTE));
				movEntraMap.put(MovimientoProp.VALORIZACION, operacionMap.get(OperacionProp.VALORIZACION));

				Map<String, Object> movSaleMap = new HashMap<String, Object>();
				movSaleMap.put(MovimientoProp.OPERACION, opId);
				movSaleMap.put(MovimientoProp.DIA, diaAbierto.getId());
				movSaleMap.put(MovimientoProp.TIPO, MovimientoType.ORIGINAL);
				movSaleMap.put(MovimientoProp.LIQUIDADO, false);
				movSaleMap.put(MovimientoProp.FECHA_ALTA, operacionMap.get(OperacionProp.FECHA_ALTA));
				movSaleMap.put(MovimientoProp.ESPECIE, operacionMap.get(OperacionProp.ESPECIE_SALE));
				movSaleMap.put(MovimientoProp.CLIENTE, operacionMap.get(OperacionProp.CLIENTE));
				movSaleMap.put(MovimientoProp.VALORIZACION, operacionMap.get(OperacionProp.VALORIZACION));

				Especie especieEntra = getEspecieDAO().getEspecieById((Long) movEntraMap.get(MovimientoProp.ESPECIE));
				logger.debug("Especie Entra: " + especieEntra.getCodigo());
				Especie especieSale = getEspecieDAO().getEspecieById((Long) movSaleMap.get(MovimientoProp.ESPECIE));
				logger.debug("Especie Sale: " + especieSale.getCodigo());

				// Obtencion de especie comercializada y de cuenta
				Especie especieComercializada = getEspecieComercializada((OperacionType) operacionMap.get(OperacionProp.TIPO), especieEntra, especieSale);
				logger.debug("Especie Comercializada: " + especieComercializada.getCodigo());
				Especie especieCuenta = getEspecieCuenta((OperacionType) operacionMap.get(OperacionProp.TIPO), especieEntra, especieSale);
				logger.debug("Especie Cuenta: " + especieCuenta.getCodigo());

				// Obtencion de especie valorizada. Es la especie a la que se
				// aplica la valorizacion ingresada
				Especie especieValorizada = getEspecieValorizada((OperacionSubType) operacionMap.get(OperacionProp.SUBTIPO), especieComercializada,
						especieCuenta);
				logger.debug("Especie Valorizada: " + especieValorizada.getCodigo());

				// Obtencion de valorizacion real (no inversa). Si la especie
				// valorizada tiene aforo inverso
				// se habra ingresado una valorizacion invertida.
				Double valorizacionReal = getValorizacionReal((Double) operacionMap.get(OperacionProp.VALORIZACION), especieValorizada);
				logger.debug("Valorizacion Ingresada: " + operacionMap.get(OperacionProp.VALORIZACION).toString());
				logger.debug("Valorizacion Real: " + valorizacionReal.toString());

				// Obtencion de la cantidad de la Especie de cuenta y de la
				// especie comercializada
				Double cantidadComercializada = getCantidadComercializada((OperacionType) operacionMap.get(OperacionProp.TIPO), (OperacionSubType) operacionMap
						.get(OperacionProp.SUBTIPO), (Double) operacionMap.get(OperacionProp.CANTIDAD));
				Double cantidadCuenta = getCantidadCuenta((OperacionType) operacionMap.get(OperacionProp.TIPO), (OperacionSubType) operacionMap
						.get(OperacionProp.SUBTIPO), cantidadComercializada, valorizacionReal, especieComercializada, especieCuenta);
				logger.debug("Cantidad comercializada: " + cantidadComercializada.toString());
				logger.debug("Cantidad cuenta: " + cantidadCuenta.toString());

				if (OperacionType.COMPRA.equals(operacionMap.get(OperacionProp.TIPO))) {
					movEntraMap.put(MovimientoProp.CANTIDAD, cantidadComercializada);
					movSaleMap.put(MovimientoProp.CANTIDAD, cantidadCuenta);
				} else if (OperacionType.VENTA.equals(operacionMap.get(OperacionProp.TIPO))) {
					movSaleMap.put(MovimientoProp.CANTIDAD, cantidadComercializada);
					movEntraMap.put(MovimientoProp.CANTIDAD, cantidadCuenta);
				}

				// Obtencion de la cantidad correspondiente a la especie
				// valorizada
				Double cantidadValorizada = getCantidadValorizada((OperacionSubType) operacionMap.get(OperacionProp.SUBTIPO), cantidadComercializada,
						cantidadCuenta);
				logger.debug("Cantidad valorizada: " + cantidadValorizada.toString());

				// Actualizacion de aforo
				if ((!especieValorizada.equals(getEspecieDeReferencia()))
						&& ((cantidadValorizada >= 0) && (especieValorizada.getPosicion() >= 0) || (cantidadValorizada < 0)
								&& (especieValorizada.getPosicion() < 0))) {
					this.updateAforo(especieValorizada.getId(), cantidadValorizada, valorizacionReal);
				}

				// Actualizacion de posicion
				addToPosicion((Long) movEntraMap.get(MovimientoProp.ESPECIE), (Double) movEntraMap.get(MovimientoProp.CANTIDAD));
				addToPosicion((Long) movSaleMap.get(MovimientoProp.ESPECIE), (Double) movSaleMap.get(MovimientoProp.CANTIDAD));

				movimientos.add(movEntraMap);
				movimientos.add(movSaleMap);
			}
			for (Map<String, Object> movimientoMap : movimientos) {
				Long movId = getMovimientoDAO().addMovimiento(movimientoMap);
				if (movId.equals(-1L)) {
					throw new BeltzaBusinessException("Error agregando movimiento de operacion");
				}
			}
			if (operacionDTO.getLiquidado()) {
				this.doLiquidarOperacion(getOperacionById(opId));
				scheduleNotification(ChangeType.MOVIMIENTO);
			}

/*
			// En modificaciones (delete + add) el dia ya esta cerrado
			if (!diaAbierto.isAbierto()) {
				// Actualiza los cierres cuando se usa este metodo en modificaciones de operaciones
				Operacion operacion = getOperacionById(opId);
				for (Movimiento movimiento : operacion.getMovimientos()) {
					if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
						actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), 0.0, movimiento.getCantidad());
					} else if (MovimientoType.CANCELACION.equals(movimiento.getTipo())) {
						actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), movimiento.getCantidad(), 0.0);
					}
				}
			}
*/			
			scheduleNotification(ChangeType.OPERACION);
			notifyObservers();
			return opId;
		} else {
			throw new BeltzaBusinessException("Error agregando operacion");
		}

	}

	protected void updateAforo(Long especieId, Double cantidad, Double aforo) {
		Especie especie = getEspecieById(especieId);
		especie.setAforo(((especie.getPosicion() * especie.getAforo()) + (cantidad * aforo)) / (especie.getPosicion() + cantidad));
		getEspecieDAO().saveEspecie(especie);
	}

	private Especie getEspecieComercializada(OperacionType operacionType, Especie especieEntra, Especie especieSale) {
		Especie especieComercializada = null;
		if (OperacionType.COMPRA.equals(operacionType)) {
			especieComercializada = especieEntra;
		} else if (OperacionType.VENTA.equals(operacionType)) {
			especieComercializada = especieSale;
		}
		return especieComercializada;
	}

	private Especie getEspecieCuenta(OperacionType operacionType, Especie especieEntra, Especie especieSale) {
		Especie especieCuenta = null;
		if (OperacionType.COMPRA.equals(operacionType)) {
			especieCuenta = especieSale;
		} else if (OperacionType.VENTA.equals(operacionType)) {
			especieCuenta = especieEntra;
		}
		return especieCuenta;
	}

	private Especie getEspecieValorizada(OperacionSubType operacionSubType, Especie especieComercializada, Especie especieCuenta) {
		// Obtencion de especie valorizada. Es la especie a la que se aplica la
		// valorizacion ingresada
		Especie especieValorizada = null; // Especie a la que corresponde la
		// valorizacion ingresada
		if ((OperacionSubType.BONO.equals(operacionSubType)) || (OperacionSubType.MONEDA.equals(operacionSubType))
				|| (OperacionSubType.ACCION.equals(operacionSubType))) {
			// La especie valorizada es siempre la especie comercializada
			especieValorizada = especieComercializada;
		} else if (OperacionSubType.BILLETE.equals(operacionSubType)) {
			// La especie valorizada es siempre la especie de cuenta
			especieValorizada = especieCuenta;
		}
		return especieValorizada;
	}

	private Double getValorizacionReal(Double valorizacionIngresada, Especie especieValorizada) {
		Double valorizacionReal = 0.0;
		if (especieValorizada.getAforoInverso()) {
			valorizacionReal = 1 / valorizacionIngresada;
		} else {
			valorizacionReal = valorizacionIngresada;
		}
		return valorizacionReal;
	}

	private Double getCantidadValorizada(OperacionSubType operacionSubType, Double cantidadComercializada, Double cantidadCuenta) {
		Double cantidadValorizada = 0.0;
		if ((OperacionSubType.BONO.equals(operacionSubType)) || (OperacionSubType.MONEDA.equals(operacionSubType))
				|| (OperacionSubType.ACCION.equals(operacionSubType))) {
			// La especie valorizada es siempre la especie comercializada
			cantidadValorizada = cantidadComercializada;
		} else if (OperacionSubType.BILLETE.equals(operacionSubType)) {
			// La especie valorizada es siempre la especie de cuenta
			cantidadValorizada = cantidadCuenta;
		}
		return cantidadValorizada;
	}

	private Double getCantidadComercializada(OperacionType operacionType, OperacionSubType operacionSubType, Double cantidad) {
		Double cantidadComercializada = 0.0;
		if (OperacionType.COMPRA.equals(operacionType)) {
			cantidadComercializada = cantidad;
		} else if (OperacionType.VENTA.equals(operacionType)) {
			cantidadComercializada = (-1) * cantidad;
		}
		return cantidadComercializada;
	}

	private Double getCantidadCuenta(OperacionType operacionType, OperacionSubType operacionSubType, Double cantidadComercializada, Double valorizacionReal,
			Especie especieComercializada, Especie especieCuenta) {
		Double cantidadCuenta = 0.0;
		if ((OperacionSubType.BONO.equals(operacionSubType)) || (OperacionSubType.MONEDA.equals(operacionSubType))
				|| (OperacionSubType.ACCION.equals(operacionSubType))) {

			cantidadCuenta = (-1) * (cantidadComercializada * valorizacionReal);

		} else if (OperacionSubType.BILLETE.equals(operacionSubType)) {

			if (OperacionType.COMPRA.equals(operacionType)) {
				cantidadCuenta = (-1) * (cantidadComercializada * especieComercializada.getAforo()) / valorizacionReal;
			} else if (OperacionType.VENTA.equals(operacionType)) {
				cantidadCuenta = (-1) * (cantidadComercializada * especieComercializada.getAforo()) / valorizacionReal;
			}
		}
		return cantidadCuenta;
	}

	@Transactional(checkpoint = "false")
	public void saveOperacion(Operacion operacion) {
		/*
		 * Solo permite la modificacion de cantidad, cantidadSale, valorizacion,
		 * fecha liquidacion y notas. Cualquier otra se ignora.
		 * TODO: Especie entra y sale, cliente
		 */
		Map<String, Object> operacionMap = getOperacionDAO().getOperacionById(operacion.getId());
		Collection<Map<String, Object>> movimientosMap = getMovimientoDAO().getMovimientosByOperacionId(operacion.getId());
		for (Map<String, Object> movimientoMap : movimientosMap) {
			movimientoMap.put(MovimientoProp.VALORIZACION, operacion.getValorizacion());
			Movimiento movimiento = buildMovimientoFromMap(movimientoMap, false);
			Double delta = 0.0; // Diferencia entre la cantidad original y la
			// nueva cantidad
			Double deltaSale = 0.0;

			if (OperacionType.INGRESO.equals(operacion.getTipo())) {

				if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
					movimientoMap.put(MovimientoProp.CANTIDAD, operacion.getCantidad());
					delta = operacion.getCantidad() - movimiento.getCantidad();
					this.addToPosicion(movimiento.getEspecie().getId(), delta);
				} else if (MovimientoType.CANCELACION.equals(movimiento.getTipo())) {
					movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * operacion.getCantidad());
					delta = operacion.getCantidad() - ((-1) * movimiento.getCantidad());
					this.addToStock(movimiento.getEspecie().getId(), delta);
				}

			} else if (OperacionType.EGRESO.equals(operacion.getTipo())) {

				if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
					movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * operacion.getCantidad());
					delta = ((-1) * operacion.getCantidad()) - movimiento.getCantidad();
					this.addToPosicion(movimiento.getEspecie().getId(), delta);
				} else if (MovimientoType.CANCELACION.equals(movimiento.getTipo())) {
					movimientoMap.put(MovimientoProp.CANTIDAD, operacion.getCantidad());
					delta = ((-1) * operacion.getCantidad()) - ((-1) * movimiento.getCantidad());
					this.addToStock(movimiento.getEspecie().getId(), delta);
				}

			} else if (OperacionType.ALTA.equals(operacion.getTipo())) {

				if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
					movimientoMap.put(MovimientoProp.CANTIDAD, operacion.getCantidad());
					delta = ((-1) * operacion.getCantidad()) + movimiento.getCantidad();
					this.addToStock(movimiento.getEspecie().getId(), delta);
				}

			} else if (OperacionType.BAJA.equals(operacion.getTipo())) {

				if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
					movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * operacion.getCantidad());
					delta = operacion.getCantidad() + movimiento.getCantidad();
					this.addToStock(movimiento.getEspecie().getId(), delta);
				}

			} else if (OperacionType.CANJE.equals(operacion.getTipo())) {

				if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
					if (movimiento.getCantidad() >= 0) {
						movimientoMap.put(MovimientoProp.CANTIDAD, operacion.getCantidad());
						delta = operacion.getCantidad() - movimiento.getCantidad();
						this.addToPosicion(movimiento.getEspecie().getId(), delta);
					} else {
						movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * operacion.getCantidadSale());
						delta = ((-1) * operacion.getCantidadSale()) - movimiento.getCantidad();
						this.addToPosicion(movimiento.getEspecie().getId(), delta);
					}
				} else if (MovimientoType.CANCELACION.equals(movimiento.getTipo())) {
					if (movimiento.getCantidad() < 0) {
						movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * operacion.getCantidad());
						delta = operacion.getCantidad() - ((-1) * movimiento.getCantidad());
						this.addToStock(movimiento.getEspecie().getId(), delta);
					} else {
						movimientoMap.put(MovimientoProp.CANTIDAD, operacion.getCantidadSale());
						delta = ((-1) * operacion.getCantidadSale()) - ((-1) * movimiento.getCantidad());
						this.addToStock(movimiento.getEspecie().getId(), delta);
					}
				}

			} else if (OperacionType.COMPRA.equals(operacion.getTipo()) || OperacionType.VENTA.equals(operacion.getTipo())) {

				logger.debug("Modificando mov. en operacion");
				Especie especieEntra = operacion.getEspecieEntra();
				logger.debug("Especie Entra: " + especieEntra.getCodigo());
				Especie especieSale = operacion.getEspecieSale();
				logger.debug("Especie Sale: " + especieSale.getCodigo());

				// Obtencion de especie comercializada y de cuenta
				Especie especieComercializada = getEspecieComercializada(operacion.getTipo(), especieEntra, especieSale);
				logger.debug("Especie Comercializada: " + especieComercializada.getCodigo());
				Especie especieCuenta = getEspecieCuenta(operacion.getTipo(), especieEntra, especieSale);
				logger.debug("Especie Cuenta: " + especieCuenta.getCodigo());

				// Obtencion de especie valorizada. Es la especie a la que se
				// aplica la valorizacion ingresada
				Especie especieValorizada = getEspecieValorizada(operacion.getSubtipo(), especieComercializada, especieCuenta);
				logger.debug("Especie Valorizada: " + especieValorizada.getCodigo());

				// Obtencion de valorizacion real (no inversa). Si la especie
				// valorizada tiene aforo inverso
				// se habra ingresado una valorizacion invertida.
				Double valorizacionReal = getValorizacionReal(operacion.getValorizacion(), especieValorizada);
				logger.debug("Valorizacion Ingresada: " + operacion.getValorizacion().toString());
				logger.debug("Valorizacion Real: " + valorizacionReal.toString());

				// Obtencion de la cantidad de la Especie de cuenta y de la
				// especie comercializada
				Double cantidadComercializada = getCantidadComercializada(operacion.getTipo(), operacion.getSubtipo(), operacion.getCantidad());
				Double cantidadCuenta = getCantidadCuenta(operacion.getTipo(), operacion.getSubtipo(), cantidadComercializada, valorizacionReal,
						especieComercializada, especieCuenta);
				logger.debug("Cantidad comercializada: " + cantidadComercializada.toString());
				logger.debug("Cantidad cuenta: " + cantidadCuenta.toString());

				if (OperacionType.COMPRA.equals(operacion.getTipo())) {
					if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
						if (movimiento.getCantidad() >= 0) {
							movimientoMap.put(MovimientoProp.CANTIDAD, cantidadComercializada);
							delta = cantidadComercializada - movimiento.getCantidad();
							logger.debug("Delta Posicion comercializado: " + delta.toString());
							this.addToPosicion(movimiento.getEspecie().getId(), delta);
						} else {
							movimientoMap.put(MovimientoProp.CANTIDAD, cantidadCuenta);
							delta = cantidadCuenta - movimiento.getCantidad();
							logger.debug("Delta Posicion cuenta: " + delta.toString());
							this.addToPosicion(movimiento.getEspecie().getId(), delta);
						}
					} else if (MovimientoType.CANCELACION.equals(movimiento.getTipo())) {
						if (movimiento.getCantidad() < 0) {
							movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * cantidadComercializada);
							delta = cantidadComercializada - ((-1) * movimiento.getCantidad());
							logger.debug("Delta Stock comercializado: " + delta.toString());
							this.addToStock(movimiento.getEspecie().getId(), delta);
						} else {
							movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * cantidadCuenta);
							delta = cantidadCuenta - ((-1) * movimiento.getCantidad());
							logger.debug("Delta Stock cuenta: " + delta.toString());
							this.addToStock(movimiento.getEspecie().getId(), delta);
						}
					}
				} else if (OperacionType.VENTA.equals(operacion.getTipo())) {
					if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
						if (movimiento.getCantidad() < 0) {
							movimientoMap.put(MovimientoProp.CANTIDAD, cantidadComercializada);
							delta = cantidadComercializada - movimiento.getCantidad();
							logger.debug("Delta Posicion comercializado: " + delta.toString());
							this.addToPosicion(movimiento.getEspecie().getId(), delta);
						} else {
							movimientoMap.put(MovimientoProp.CANTIDAD, cantidadCuenta);
							delta = cantidadCuenta - movimiento.getCantidad();
							logger.debug("Delta Posicion cuenta: " + delta.toString());
							this.addToPosicion(movimiento.getEspecie().getId(), delta);
						}
					} else if (MovimientoType.CANCELACION.equals(movimiento.getTipo())) {
						if (movimiento.getCantidad() >= 0) {
							movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * cantidadComercializada);
							delta = cantidadComercializada - ((-1) * movimiento.getCantidad());
							logger.debug("Delta Stock comercializado: " + delta.toString());
							this.addToStock(movimiento.getEspecie().getId(), delta);
						} else {
							movimientoMap.put(MovimientoProp.CANTIDAD, (-1) * cantidadCuenta);
							delta = cantidadCuenta - ((-1) * movimiento.getCantidad());
							logger.debug("Delta Stock cuenta: " + delta.toString());
							this.addToStock(movimiento.getEspecie().getId(), delta);
						}
					}
				}
			}

			getMovimientoDAO().saveMovimiento(movimientoMap);

			if (movimiento.getTipo().equals(MovimientoType.ORIGINAL)) {
				if (operacion.getTipo().equals(OperacionType.INGRESO) || operacion.getTipo().equals(OperacionType.EGRESO)
						|| operacion.getTipo().equals(OperacionType.CANJE) || operacion.getTipo().equals(OperacionType.COMPRA)
						|| operacion.getTipo().equals(OperacionType.VENTA)) {
					actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), 0.0, delta);
				} else if (operacion.getTipo().equals(OperacionType.ALTA) || operacion.getTipo().equals(OperacionType.BAJA)) {
					actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), delta, 0.0);
				}
			} else if (movimiento.getTipo().equals(MovimientoType.CANCELACION)) {
				actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), delta, 0.0);
			}

		}
		operacionMap.put(OperacionProp.FECHA_LIQUIDACION, operacion.getFechaLiquidacion());
		operacionMap.put(OperacionProp.VALORIZACION, operacion.getValorizacion());
		operacionMap.put(OperacionProp.CANTIDAD, operacion.getCantidad());
		operacionMap.put(OperacionProp.CANTIDAD_SALE, operacion.getCantidadSale());
		operacionMap.put(OperacionProp.NOTAS, operacion.getNotas());
		getOperacionDAO().saveOperacion(operacionMap);

		scheduleNotification(ChangeType.OPERACION);
		scheduleNotification(ChangeType.MOVIMIENTO);
		scheduleNotification(ChangeType.CIERRE);
		scheduleNotification(ChangeType.ESPECIE);
		notifyObservers();
	}

	/**
	 * Actualiza stock y posicion de la especie indicada, en cierres
	 * porsteriores al dia indicado.
	 * 
	 * @param dia
	 * @param especie
	 * @param deltaStock
	 * @param deltaPosicion
	 */
	private void actualizarCierresPosteriores(Dia dia, Especie especie, Double deltaStock, Double deltaPosicion) {
		Collection<Map<String, Object>> diasMap = getDiaDAO().searchDiasDesde(dia.getId());
		for (Map<String, Object> diaMap : diasMap) {
			Map<String, Object> cierreFilterMap = new HashMap<String, Object>();
			cierreFilterMap.put(CierreProp.DIA, diaMap.get(DiaProp.ID));
			cierreFilterMap.put(CierreProp.ESPECIE, especie.getId());
			// Obtiene el cierre del dia y la especie del movimiento
			Collection<Map<String, Object>> cierresMap = getCierreDAO().searchCierres(cierreFilterMap);
			// Deberia haber solo un cierre por dia por especie

			if (cierresMap.isEmpty()) {
				Map<String, Object> cierreMap = new HashMap<String, Object>();
				cierreMap.put(CierreProp.DIA, diaMap.get(DiaProp.ID));
				cierreMap.put(CierreProp.ESPECIE, especie.getId());
				cierreMap.put(CierreProp.AFORO, especie.getAforo());
				cierreMap.put(CierreProp.POSICION, deltaPosicion);
				cierreMap.put(CierreProp.STOCK, deltaStock);
				getCierreDAO().addCierre(cierreMap);
			} else {
				for (Map<String, Object> cierreMap : cierresMap) {
					Double posicion = (Double) cierreMap.get(CierreProp.POSICION);
					cierreMap.put(CierreProp.POSICION, posicion + deltaPosicion);
					Double stock = (Double) cierreMap.get(CierreProp.STOCK);
					cierreMap.put(CierreProp.STOCK, stock + deltaStock);
					getCierreDAO().saveCierre(cierreMap);
				}
			}
		}
	}

	@Transactional(checkpoint = "false")
	public void deleteOperacion(Operacion operacion, Boolean actualizaCierres) {
		long ini = new Date().getTime();
		Collection<Map<String, Object>> movimientosMap = getMovimientoDAO().getMovimientosByOperacionId(operacion.getId());
		// Para todos los movimientos de la operacion se actualiza el stock y
		// posicion segun corresponde
		for (Map<String, Object> movimientoMap : movimientosMap) {
			Movimiento movimiento = buildMovimientoFromMap(movimientoMap, false);
			Double delta = 0.0;
			if (movimiento.getTipo().equals(MovimientoType.ORIGINAL)) {
				if (operacion.getTipo().equals(OperacionType.INGRESO) || operacion.getTipo().equals(OperacionType.EGRESO)
						|| operacion.getTipo().equals(OperacionType.CANJE) || operacion.getTipo().equals(OperacionType.COMPRA)
						|| operacion.getTipo().equals(OperacionType.VENTA)) {
					delta = (-1) * movimiento.getCantidad();

					this.addToPosicion(movimiento.getEspecie().getId(), delta);
					if (actualizaCierres) {
						actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), 0.0, delta);
					}
				} else if (operacion.getTipo().equals(OperacionType.ALTA) || operacion.getTipo().equals(OperacionType.BAJA)) {
					delta = movimiento.getCantidad();
					this.addToStock(movimiento.getEspecie().getId(), delta);
					if (actualizaCierres) {
						actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), delta, 0.0);
					}
				}
			} else if (movimiento.getTipo().equals(MovimientoType.CANCELACION)) {
				delta = movimiento.getCantidad();
				this.addToStock(movimiento.getEspecie().getId(), delta);
				if (actualizaCierres) {
					actualizarCierresPosteriores(movimiento.getDia(), movimiento.getEspecie(), delta, 0.0);
				}
			}

		}
		// Los movimientos de la operacion se borran por cascada
		if (!getOperacionDAO().deleteOperacion(operacion.getId())) {
			throw new BeltzaBusinessException("Imposible eliminar operacion");
		} else {
			scheduleNotification(ChangeType.OPERACION);
			scheduleNotification(ChangeType.MOVIMIENTO);
			scheduleNotification(ChangeType.CIERRE);
			scheduleNotification(ChangeType.ESPECIE);
			notifyObservers();
		}
		
		logger.debug("deleteOperacion en: "  +(new Date().getTime() - ini));
	}

	public Operacion getOperacionByNumero(Long numero) {
		return buildOperacionFromMap(getOperacionDAO().getOperacionByNumero(numero));
	}

	public Operacion getOperacionById(Long id) {
		return buildOperacionFromMap(getOperacionDAO().getOperacionById(id));
	}

	public Collection<Operacion> getOperacionesByDia(Dia dia) {
		Collection<Map<String, Object>> operacionesMap = getOperacionDAO().getOperacionByDia(dia.getId());
		Collection<Operacion> operaciones = new ArrayList<Operacion>();
		for (Map<String, Object> operacionMap : operacionesMap) {
			operaciones.add(buildOperacionFromMap(operacionMap));
		}
		return operaciones;
	}

	public Collection<Operacion> searchOperaciones(Operacion operacion) {
		Collection<Map<String, Object>> maps = getOperacionDAO().searchOperaciones(buildMapFromOperacion(operacion));
		logger.debug("Se encontraron " + maps.size() + " operaciones.");
		Collection<Operacion> operaciones = new ArrayList<Operacion>();
		for (Map<String, Object> map : maps) {
			operaciones.add(buildOperacionFromMap(map));
		}
		return operaciones;
	}

	public Collection<Operacion> searchOperaciones(Operacion operacion, Date fechaAlta) {
		if (fechaAlta != null) {
			Dia diaAlta = buildDiaFromMap(getDiaDAO().getDiaByFechaAsociada(fechaAlta));
			// Si el dia no existe, no hay operaciones para ese dia
			if (diaAlta == null) {
				return new ArrayList<Operacion>();
			}
			operacion.setDia(diaAlta);
		}
		return searchOperaciones(operacion);
	}

	public Collection<Operacion> searchOperaciones(SearchOperacionesDTO searchDTO) {
		long ini = new Date().getTime();
		Collection<Map<String, Object>> maps = getOperacionDAO().search(searchDTO);
		logger.debug("Se encontraron " + maps.size() + " operaciones.");
		
		Collection<Operacion> operaciones = new ArrayList<Operacion>();
		//TODO: Este loop es lento para muchas operaciones
		for (Map<String, Object> map : maps) {
			operaciones.add(buildOperacionFromMap(map));
		}
		
		logger.debug("searchOperaciones en: "  +(new Date().getTime() - ini));
		return operaciones;
	}
	
	public Collection<Map<String, Object>> getOperacionSubTypeListFromDiaAbierto() {
		return getOperacionDAO().getOperacionSubTypeListFromDiaAbierto();
	}

	public Collection<Map<String, Object>> getOperacionSubTypeListByExample(SearchOperacionesDTO searchDTO) {
		return getOperacionDAO().getOperacionSubTypeListByExample(searchDTO);
	}

	@Transactional
	public void liquidarOperacion(Operacion operacion) {
		doLiquidarOperacion(operacion);
		
		scheduleNotification(ChangeType.OPERACION);
		scheduleNotification(ChangeType.MOVIMIENTO);
		notifyObservers();
	}

	private void doLiquidarOperacion(Operacion operacion) {
		Dia diaAbierto = getDiaAbierto();
		if (diaAbierto == null) {
			throw new BeltzaBusinessException("Imposible liquidar operacion. No hay dia abierto.");
		}
		Collection<Map<String, Object>> movimientos = getMovimientoDAO().getMovimientosByOperacionId(operacion.getId());
		for (Map<String, Object> movimientoMap : movimientos) {
			Movimiento mov = buildMovimientoFromMap(movimientoMap, true);
			if (!mov.getLiquidado()) {
				liquidarMovimiento(mov.getId(), diaAbierto);
			}
		}
	}
	
	
	@Transactional
	public void desliquidarOperacion(Operacion operacion) {
		Collection<Map<String, Object>> movimientos = getMovimientoDAO().getMovimientosByOperacionId(operacion.getId());
		for (Map<String, Object> movimientoMap : movimientos) {
			Movimiento mov = buildMovimientoFromMap(movimientoMap, true);
			if ((MovimientoType.ORIGINAL.equals(mov.getTipo())) && mov.getLiquidado()) {
				doDesliquidarMovimiento(mov.getId());
			}
		}

		scheduleNotification(ChangeType.OPERACION);
		scheduleNotification(ChangeType.MOVIMIENTO);
		notifyObservers();
	}

	public Movimiento getMovimientoById(Long id) {
		return buildMovimientoFromMap(getMovimientoDAO().getMovimientoById(id), true);
	}

	public Collection<Movimiento> searchMovimientos(Movimiento movimiento) {
		Collection<Map<String, Object>> maps = getMovimientoDAO().searchMovimientos(buildMapFromMovimiento(movimiento));
		Collection<Movimiento> movimientos = new ArrayList<Movimiento>();
		for (Map<String, Object> map : maps) {
			movimientos.add(buildMovimientoFromMap(map, true));
		}
		return movimientos;
	}

	public Collection<Movimiento> searchMovimientosPositivos(Movimiento movimiento, Operator operator) {
		Collection<Map<String, Object>> maps = getMovimientoDAO().searchPositiveMovimientos(buildMapFromMovimiento(movimiento), operator);
		Collection<Movimiento> movimientos = new ArrayList<Movimiento>();
		for (Map<String, Object> map : maps) {
			movimientos.add(buildMovimientoFromMap(map, true));
		}
		return movimientos;
	}

	public Collection<Movimiento> searchMovimientosPositivos(Movimiento movimiento, Date fechaAlta, Operator operator) {
		if (fechaAlta != null) {
			Dia diaAlta = null;
			if (operator != null) {
				if (operator.equals(Operator.MAYOR_IGUAL)) {
					diaAlta = buildDiaFromMap(getDiaDAO().getFirstDiaGreaterThanFechaAsociada(fechaAlta));
				} else if (operator.equals(Operator.MENOR_IGUAL)) {
					diaAlta = buildDiaFromMap(getDiaDAO().getFirstDiaLessThanFechaAsociada(fechaAlta));
				} else {
					diaAlta = buildDiaFromMap(getDiaDAO().getDiaByFechaAsociada(fechaAlta));
				}
			}
			// Si el dia no existe, no hay movimientos para ese dia
			if (diaAlta == null) {
				return new ArrayList<Movimiento>();
			}
			movimiento.setDia(diaAlta);
		}
		return searchMovimientosPositivos(movimiento, operator);
	}

	public Collection<Movimiento> searchMovimientosNegativos(Movimiento movimiento, Operator operator) {
		Collection<Map<String, Object>> maps = getMovimientoDAO().searchNegativeMovimientos(buildMapFromMovimiento(movimiento), operator);
		Collection<Movimiento> movimientos = new ArrayList<Movimiento>();
		for (Map<String, Object> map : maps) {
			movimientos.add(buildMovimientoFromMap(map, true));
		}
		return movimientos;
	}

	public Collection<Movimiento> searchMovimientosNegativos(Movimiento movimiento, Date fechaAlta, Operator operator) {
		if (fechaAlta != null) {
			Dia diaAlta = null;
			if (operator != null) {
				if (operator.equals(Operator.MAYOR_IGUAL)) {
					diaAlta = buildDiaFromMap(getDiaDAO().getFirstDiaGreaterThanFechaAsociada(fechaAlta));
				} else if (operator.equals(Operator.MENOR_IGUAL)) {
					diaAlta = buildDiaFromMap(getDiaDAO().getFirstDiaLessThanFechaAsociada(fechaAlta));
				} else {
					diaAlta = buildDiaFromMap(getDiaDAO().getDiaByFechaAsociada(fechaAlta));
				}
			}
			// Si el dia no existe, no hay movimientos para ese dia
			if (diaAlta == null) {
				return new ArrayList<Movimiento>();
			}
			movimiento.setDia(diaAlta);
		}
		return searchMovimientosNegativos(movimiento, operator);
	}

	public Collection<Movimiento> searchMovimientos(SearchMovimientosDTO searchDTO) {
		Collection<Map<String, Object>> maps = getMovimientoDAO().search(searchDTO);
		
		Collection<Movimiento> movimientos = new ArrayList<Movimiento>();
		for (Map<String, Object> map : maps) {
			movimientos.add(buildMovimientoFromMap(map, true));
		}
		return movimientos;
	}

	public Collection<Map<String, Object>> searchMovimientosConsolidados(Especie especie, Cliente cliente, int signo, boolean filtrarCeros) {
		Long especieId = (especie != null ? especie.getId() : null);
		Long clienteId = (cliente != null ? cliente.getId() : null);
		return getMapDAO().searchMovimientosConsolidados(especieId, clienteId, signo, filtrarCeros);
	}

	public Collection<Map<String, Object>> searchMovimientosConsolidadosByFechaLiq(Especie especie, Cliente cliente, int signo, Date fechaLiquidacion,
			boolean filtrarCeros) {
		Long especieId = (especie != null ? especie.getId() : null);
		Long clienteId = (cliente != null ? cliente.getId() : null);
		return getMapDAO().searchMovimientosConsolidadosByFechaLiq(especieId, clienteId, signo, fechaLiquidacion, filtrarCeros);
	}

	public Collection<Movimiento> searchMovimientosNoLiqByCliente(String clienteCod, String especieCod) {
		Collection<Movimiento> movimientos = new ArrayList<Movimiento>();
		Cliente cliente = getClienteDAO().getClienteByCodigo(clienteCod);
		Especie especie = getEspecieDAO().getEspecieByCodigo(especieCod);
		if (cliente != null && especie != null) {
			Collection<Map<String, Object>> movimientosMap = getMovimientoDAO().searchMovimientosCtaCteByCliente(cliente.getId(), especie.getId());
			for (Map<String, Object> movimientoMap : movimientosMap) {
				Movimiento movimiento = buildMovimientoFromMap(movimientoMap, true);
				movimientos.add(movimiento);
			}
		}
		return movimientos;
	}

	@Transactional
	public void liquidarMovimiento(Movimiento movimiento) {
		doLiquidarMovimiento(movimiento);
		
		scheduleNotification(ChangeType.OPERACION); //Puede modificar el estado de liquidacion de la operacion
		scheduleNotification(ChangeType.MOVIMIENTO);
		notifyObservers();
	}
	
	@Transactional
	public void liquidarDesliquidarMovimientos(Collection<Movimiento> movimientos) {
		for(Movimiento movimiento : movimientos){
			if(movimiento.getLiquidado()){
				doDesliquidarMovimiento(movimiento.getId());
			}else{
				doLiquidarMovimiento(movimiento);
			}
		}
		scheduleNotification(ChangeType.OPERACION); //Puede modificar el estado de liquidacion de la operacion
		scheduleNotification(ChangeType.MOVIMIENTO);
		notifyObservers();
	}

	private void doLiquidarMovimiento(Movimiento movimiento) {
		Dia diaAbierto = getDiaAbierto();
		if (diaAbierto == null) {
			String msg = "Imposible liquidar movimiento. No hay dia abierto.";
			logger.error(msg);
			throw new BeltzaBusinessException(msg);
		}
		this.liquidarMovimiento(movimiento.getId(), diaAbierto);
	}
	

	protected void liquidarMovimiento(Long movimientoId, Dia diaLiquidacion) {
		Date today = new Date();
		// Se crea movimiento de cancelacion en la misma operacion
		Movimiento movOriginal = getMovimientoById(movimientoId);
		if (movOriginal.getLiquidado()) {
			throw new BeltzaBusinessException("Imposible liquidar movimiento. El movimiento ya esta liquidado");
		}
		movOriginal.setLiquidado(true);
		movOriginal.setFechaConcrecion(today);
		movOriginal.setDiaConcrecion(diaLiquidacion);
		getMovimientoDAO().saveMovimiento(buildMapFromMovimiento(movOriginal));

		if ((!OperacionType.ALTA.equals(movOriginal.getOperacion().getTipo())) && (!OperacionType.BAJA.equals(movOriginal.getOperacion().getTipo()))) {

			Map<String, Object> movimientoMap = new HashMap<String, Object>();
			movimientoMap.put(MovimientoProp.OPERACION, movOriginal.getOperacion().getId());
			movimientoMap.put(MovimientoProp.DIA, diaLiquidacion.getId());
			movimientoMap.put(MovimientoProp.CANTIDAD, -1 * movOriginal.getCantidad());
			movimientoMap.put(MovimientoProp.TIPO, MovimientoType.CANCELACION);
			movimientoMap.put(MovimientoProp.LIQUIDADO, true);
			movimientoMap.put(MovimientoProp.FECHA_ALTA, today);
			movimientoMap.put(MovimientoProp.FECHA_CONCRECION, today);
			movimientoMap.put(MovimientoProp.DIA_CONCRECION, diaLiquidacion.getId());
			movimientoMap.put(MovimientoProp.ESPECIE, movOriginal.getEspecie().getId());
			if (movOriginal.getCliente() != null) {
				movimientoMap.put(MovimientoProp.CLIENTE, movOriginal.getCliente().getId());
			}
			movimientoMap.put(MovimientoProp.VALORIZACION, movOriginal.getValorizacion());
			getMovimientoDAO().addMovimiento(movimientoMap);
			addToStock(movOriginal.getEspecie().getId(), movOriginal.getCantidad());
		} else {
			addToStock(movOriginal.getEspecie().getId(), (-1) * movOriginal.getCantidad());
		}
	}

	@Transactional
	public void desliquidarMovimiento(Long movimientoId) {
		doDesliquidarMovimiento(movimientoId);
		
		scheduleNotification(ChangeType.OPERACION); //Puede modificar el estado de liquidacion de la operacion
		scheduleNotification(ChangeType.MOVIMIENTO);
		notifyObservers();
	}

	private void doDesliquidarMovimiento(Long movimientoId) {
		Movimiento movimiento = getMovimientoById(movimientoId);
		if (!movimiento.getLiquidado()) {
			String msg = "Imposible desliquidar el movimiento. No esta liquidado.";
			logger.error(msg);
			throw new BeltzaBusinessException(msg);
		}
		Movimiento movOriginal = null;
		Movimiento movCancelacion = null;

		// Si recibo el movimiento original
		if (MovimientoType.ORIGINAL.equals(movimiento.getTipo())) {
			movOriginal = movimiento;
			for (Movimiento mov : movimiento.getOperacion().getMovimientos()) {
				if (MovimientoType.CANCELACION.equals(mov.getTipo()) && mov.getEspecie().getCodigo().equals(movimiento.getEspecie().getCodigo())) {
					movCancelacion = mov;
					break;
				}
			}
		} else {
			// Si recibo el movimiento cancelacion
			movCancelacion = movimiento;
			for (Movimiento mov : movimiento.getOperacion().getMovimientos()) {
				if (MovimientoType.ORIGINAL.equals(mov.getTipo()) && mov.getEspecie().getCodigo().equals(movimiento.getEspecie().getCodigo())) {
					movOriginal = mov;
					break;
				}
			}
		}

		movOriginal.setLiquidado(false);
		movOriginal.setFechaConcrecion(null);
		movOriginal.setDiaConcrecion(null);
		getMovimientoDAO().saveMovimiento(buildMapFromMovimiento(movOriginal));

		if ((!OperacionType.ALTA.equals(movOriginal.getOperacion().getTipo())) && (!OperacionType.BAJA.equals(movOriginal.getOperacion().getTipo()))) {

			getMovimientoDAO().deleteMovimiento(movCancelacion.getId());
			addToStock(movOriginal.getEspecie().getId(), (-1) * movOriginal.getCantidad());
		} else {
			addToStock(movOriginal.getEspecie().getId(), movOriginal.getCantidad());
		}
	}

	protected Map<String, Object> buildMapFromOperacion(Operacion operacion) {
		Map<String, Object> operationMap = new HashMap<String, Object>();
		operationMap.put(OperacionProp.ID, operacion.getId());
		operationMap.put(OperacionProp.CANTIDAD, operacion.getCantidad());
		operationMap.put(OperacionProp.CANTIDAD_SALE, operacion.getCantidadSale());
		if (operacion.getCliente() != null) {
			operationMap.put(OperacionProp.CLIENTE, operacion.getCliente().getId());
		}
		if (operacion.getEspecieEntra() != null) {
			operationMap.put(OperacionProp.ESPECIE_ENTRA, operacion.getEspecieEntra().getId());
		}
		if (operacion.getEspecieSale() != null) {
			operationMap.put(OperacionProp.ESPECIE_SALE, operacion.getEspecieSale().getId());
		}
		if (operacion.getDia() != null) {
			operationMap.put(OperacionProp.DIA, operacion.getDia().getId());
		}
		operationMap.put(OperacionProp.FECHA_ALTA, operacion.getFechaAlta());
		operationMap.put(OperacionProp.FECHA_LIQUIDACION, operacion.getFechaLiquidacion());
		operationMap.put(OperacionProp.NOTAS, operacion.getNotas());
		operationMap.put(OperacionProp.NUMERO, operacion.getNumero());
		operationMap.put(OperacionProp.SUBTIPO, operacion.getSubtipo());
		operationMap.put(OperacionProp.TIPO, operacion.getTipo());
		operationMap.put(OperacionProp.VALORIZACION, operacion.getValorizacion());
		return operationMap;
	}

	protected Operacion buildOperacionFromMap(Map<String, Object> map) {
		Operacion operacion = new Operacion();
		operacion.setId((Long) map.get(OperacionProp.ID));
		operacion.setCantidad((Double) map.get(OperacionProp.CANTIDAD));
		operacion.setCantidadSale((Double) map.get(OperacionProp.CANTIDAD_SALE));
		operacion.setFechaAlta((Date) map.get(OperacionProp.FECHA_ALTA));
		operacion.setFechaLiquidacion((Date) map.get(OperacionProp.FECHA_LIQUIDACION));
		operacion.setNotas((String) map.get(OperacionProp.NOTAS));
		operacion.setNumero((Long) map.get(OperacionProp.NUMERO));
		operacion.setSubtipo(OperacionSubType.valueOf(map.get(OperacionProp.SUBTIPO).toString()));
		operacion.setTipo(OperacionType.valueOf(map.get(OperacionProp.TIPO).toString()));
		operacion.setDia(buildDiaFromMap(getDiaDAO().getDiaById((Long) map.get(OperacionProp.DIA))));
		operacion.setValorizacion((Double) map.get(OperacionProp.VALORIZACION));
		operacion.setCliente(getClienteDAO().getClienteById((Long) map.get(OperacionProp.CLIENTE)));
		operacion.setEspecieEntra(getEspecieDAO().getEspecieById((Long) map.get(OperacionProp.ESPECIE_ENTRA)));
		operacion.setEspecieSale(getEspecieDAO().getEspecieById((Long) map.get(OperacionProp.ESPECIE_SALE)));

		Collection<Map<String, Object>> movimientosMap = getMovimientoDAO().getMovimientosByOperacionId(operacion.getId());
		Collection<Movimiento> movimientos = new ArrayList<Movimiento>();
		for (Map<String, Object> movimientoMap : movimientosMap) {
			Movimiento movimiento = buildMovimientoFromMap(movimientoMap, false);
			movimiento.setOperacion(operacion);
			movimientos.add(movimiento);
		}
		operacion.setMovimientos(movimientos);

		return operacion;
	}

	protected Map<String, Object> buildMapFromMovimiento(Movimiento movimiento) {
		Map<String, Object> movimientoMap = new HashMap<String, Object>();
		movimientoMap.put(MovimientoProp.ID, movimiento.getId());
		movimientoMap.put(MovimientoProp.CANTIDAD, movimiento.getCantidad());
		if (movimiento.getEspecie() != null) {
			movimientoMap.put(MovimientoProp.ESPECIE, movimiento.getEspecie().getId());
		}
		if (movimiento.getCliente() != null) {
			movimientoMap.put(MovimientoProp.CLIENTE, movimiento.getCliente().getId());
		}
		movimientoMap.put(MovimientoProp.FECHA_ALTA, movimiento.getFechaAlta());
		movimientoMap.put(MovimientoProp.FECHA_CONCRECION, movimiento.getFechaConcrecion());
		movimientoMap.put(MovimientoProp.LIQUIDADO, movimiento.getLiquidado());
		if (movimiento.getOperacion() != null) {
			movimientoMap.put(MovimientoProp.OPERACION, movimiento.getOperacion().getId());
		}
		if (movimiento.getDia() != null) {
			movimientoMap.put(MovimientoProp.DIA, movimiento.getDia().getId());
		}
		if (movimiento.getDiaConcrecion() != null) {
			movimientoMap.put(MovimientoProp.DIA_CONCRECION, movimiento.getDiaConcrecion().getId());
		}
		movimientoMap.put(MovimientoProp.TIPO, movimiento.getTipo());
		movimientoMap.put(MovimientoProp.VALORIZACION, movimiento.getValorizacion());
		return movimientoMap;
	}

	protected Movimiento buildMovimientoFromMap(Map<String, Object> map, Boolean linkOperacion) {
		Movimiento movimiento = new Movimiento();
		movimiento.setId((Long) map.get(MovimientoProp.ID));
		movimiento.setDia(buildDiaFromMap(getDiaDAO().getDiaById((Long) map.get(MovimientoProp.DIA))));
		movimiento.setCantidad((Double) map.get(MovimientoProp.CANTIDAD));
		movimiento.setFechaAlta((Date) map.get(MovimientoProp.FECHA_ALTA));
		movimiento.setValorizacion((Double) map.get(MovimientoProp.VALORIZACION));
		movimiento.setEspecie(getEspecieDAO().getEspecieById((Long) map.get(MovimientoProp.ESPECIE)));
		movimiento.setCliente(getClienteDAO().getClienteById((Long) map.get(MovimientoProp.CLIENTE)));
		movimiento.setFechaConcrecion((Date) map.get(MovimientoProp.FECHA_CONCRECION));
		movimiento.setDiaConcrecion(buildDiaFromMap(getDiaDAO().getDiaById((Long) map.get(MovimientoProp.DIA_CONCRECION))));
		movimiento.setLiquidado((Boolean) map.get(MovimientoProp.LIQUIDADO));
		movimiento.setTipo(MovimientoType.valueOf(map.get(MovimientoProp.TIPO).toString()));
		if (linkOperacion) {
			movimiento.setOperacion(buildOperacionFromMap(getOperacionDAO().getOperacionById((Long) map.get(MovimientoProp.OPERACION))));
		}
		return movimiento;
	}

	@Transactional
	public void abrirDia() {
		this.abrirDia(new Date());
	}

	@Transactional
	public void abrirDia(Date fechaAsociada) {
		Dia diaAbierto = getDiaAbierto();
		if (diaAbierto != null) {
			throw new BeltzaBusinessException("Imposible abrir dia. Ya existe un dia abierto.");
		}
		Dia ultimoDiaCerrado = buildDiaFromMap(getDiaDAO().getUltimoDiaCerrado());
		if (ultimoDiaCerrado != null) {
			if (ultimoDiaCerrado.getFechaAsociada().getTime() >= (fechaAsociada.getTime())) {
				throw new BeltzaBusinessException("Imposible abrir dia. Ya existe un dia cerrado con fecha igual o superior.");
			}
		}
		Map<String, Object> diaMap = new HashMap<String, Object>();
		diaMap.put(DiaProp.FECHA_ASOCIADA, fechaAsociada);
		diaMap.put(DiaProp.DESCRIPCION, null);
		diaMap.put(DiaProp.ABIERTO, true);
		getDiaDAO().addDia(diaMap);
		
		scheduleNotification(ChangeType.DIA);
		notifyObservers();
	}

	@Transactional
	public void reabrirUltimoDia() {
		Dia diaAbierto = getDiaAbierto();
		if (diaAbierto != null) {
			throw new BeltzaBusinessException("Imposible reabrir dia. Existe un dia abierto.");
		}
		Dia ultimoDiaCerrado = buildDiaFromMap(getDiaDAO().getUltimoDiaCerrado());
		if (ultimoDiaCerrado == null) {
			throw new BeltzaBusinessException("Imposible reabrir dia. No hay dias cerrados.");
		}
		Map<String, Object> cierreFilterMap = new HashMap<String, Object>();
		cierreFilterMap.put(CierreProp.DIA, ultimoDiaCerrado.getId());
		Collection<Map<String, Object>> cierresMap = getCierreDAO().searchCierres(cierreFilterMap);
		for (Map<String, Object> cierreMap : cierresMap) {
			getCierreDAO().deleteCierre((Long) cierreMap.get(CierreProp.ID));
		}
		ultimoDiaCerrado.setAbierto(true);
		getDiaDAO().saveDia(buildMapFromDia(ultimoDiaCerrado));

		scheduleNotification(ChangeType.DIA);
		scheduleNotification(ChangeType.CIERRE);
		notifyObservers();
	}

	@Transactional
	public boolean borrarUltimoDiaCerrado() {
		Dia diaAbierto = getDiaAbierto();
		if (diaAbierto != null) {
			throw new BeltzaBusinessException("Imposible borrar dia. Existe un dia abierto.");
		}
		Dia ultimoDiaCerrado = buildDiaFromMap(getDiaDAO().getUltimoDiaCerrado());
		if (ultimoDiaCerrado == null) {
			throw new BeltzaBusinessException("Imposible borrar dia. No hay dias cerrados.");
		}
		Movimiento movimiento = new Movimiento();
		movimiento.setDia(ultimoDiaCerrado);
		Collection<Movimiento> res = searchMovimientos(movimiento);
		if (res != null && res.size() > 0){
			throw new BeltzaBusinessException("Imposible borrar dia. El d\u00EDa ya tiene movimientos.");
		}
		getCierreDAO().deleteCierreByDiaId(ultimoDiaCerrado.getId());
		getDiaDAO().deleteDiaById(ultimoDiaCerrado.getId());
		return true;
	}

	@Transactional
	public void cerrarDia() {
		Dia diaAbierto = getDiaAbierto();
		if (diaAbierto != null) {
			diaAbierto.setAbierto(false);
			getDiaDAO().saveDia(buildMapFromDia(diaAbierto));
			Collection<Especie> especies = getAllEspecies();
			for (Especie especie : especies) {
				Map<String, Object> cierreMap = new HashMap<String, Object>();
				cierreMap.put(CierreProp.DIA, diaAbierto.getId());
				cierreMap.put(CierreProp.ESPECIE, especie.getId());
				cierreMap.put(CierreProp.AFORO, especie.getAforo());
				cierreMap.put(CierreProp.POSICION, especie.getPosicion());
				cierreMap.put(CierreProp.STOCK, especie.getStock());
				getCierreDAO().addCierre(cierreMap);
			}

			scheduleNotification(ChangeType.DIA);
			scheduleNotification(ChangeType.CIERRE);
			notifyObservers();
			
		} else {
			throw new BeltzaBusinessException("Imposible cerrar dia. No hay dia abierto.");
		}
	}

	public Dia getDiaAbierto() {
		Collection<Map<String, Object>> dias = getDiaDAO().getDiaByAbierto(true);
		if (dias.isEmpty()) {
			return null;
		} else {
			return buildDiaFromMap(dias.iterator().next());
		}
	}

	public Dia getDiaByFechaAsociada(Date fechaAsociada) {
		return buildDiaFromMap(getDiaDAO().getDiaByFechaAsociada(fechaAsociada));
	}

	protected Map<String, Object> buildMapFromDia(Dia dia) {
		Map<String, Object> diaMap = new HashMap<String, Object>();
		diaMap.put(DiaProp.ID, dia.getId());
		diaMap.put(DiaProp.FECHA_ASOCIADA, dia.getFechaAsociada());
		diaMap.put(DiaProp.ABIERTO, dia.isAbierto());
		diaMap.put(DiaProp.DESCRIPCION, dia.getDescripcion());
		return diaMap;
	}

	protected Dia buildDiaFromMap(Map<String, Object> map) {
		if (map == null)
			return null;
		Dia dia = new Dia();
		dia.setId((Long) map.get(DiaProp.ID));
		dia.setFechaAsociada((Date) map.get(DiaProp.FECHA_ASOCIADA));
		dia.setAbierto((Boolean) map.get(DiaProp.ABIERTO));
		dia.setDescripcion((String) map.get(DiaProp.DESCRIPCION));
		return dia;
	}

	@Transactional
	public void saveCierre(Cierre cierre) {
		Map<String, Object> cierreMap = buildMapFromCierre(cierre);
		getCierreDAO().saveCierre(cierreMap);
		scheduleNotification(ChangeType.CIERRE);
		notifyObservers();
	}

	public Collection<Cierre> getCierresByFechaEspecie(Date fecha, Especie especie) {
		Map<String, Object> diaFilterMap = new HashMap<String, Object>();
		diaFilterMap.put(DiaProp.FECHA_ASOCIADA, fecha);
		Collection<Map<String, Object>> diasMap = getDiaDAO().searchDias(diaFilterMap);
		Collection<Cierre> cierres = new ArrayList<Cierre>();
		for (Map<String, Object> diaMap : diasMap) {
			Map<String, Object> cierreFilterMap = new HashMap<String, Object>();
			if (especie != null) {
				cierreFilterMap.put(CierreProp.ESPECIE, especie.getId());
			}
			cierreFilterMap.put(CierreProp.DIA, diaMap.get(DiaProp.ID));
			Collection<Map<String, Object>> cierresMap = getCierreDAO().searchCierres(cierreFilterMap);
			for (Map<String, Object> cierreMap : cierresMap) {
				if (((Double) cierreMap.get(CierreProp.POSICION) != 0) || ((Double) cierreMap.get(CierreProp.STOCK) != 0)) {
					cierres.add(buildCierreFromMap(cierreMap));
				}
			}
		}
		return cierres;
	}

	protected Map<String, Object> buildMapFromCierre(Cierre cierre) {
		Map<String, Object> cierreMap = new HashMap<String, Object>();
		cierreMap.put(CierreProp.ID, cierre.getId());
		if (cierre.getDia() != null) {
			cierreMap.put(CierreProp.DIA, cierre.getDia().getId());
		}
		if (cierre.getEspecie() != null) {
			cierreMap.put(CierreProp.ESPECIE, cierre.getEspecie().getId());
		}
		cierreMap.put(CierreProp.AFORO, cierre.getAforo());
		cierreMap.put(CierreProp.POSICION, cierre.getPosicion());
		cierreMap.put(CierreProp.STOCK, cierre.getStock());
		return cierreMap;
	}

	protected Cierre buildCierreFromMap(Map<String, Object> map) {
		Cierre cierre = new Cierre();
		cierre.setId((Long) map.get(CierreProp.ID));
		cierre.setDia(buildDiaFromMap(getDiaDAO().getDiaById((Long) map.get(CierreProp.DIA))));
		cierre.setEspecie(getEspecieDAO().getEspecieById((Long) map.get(CierreProp.ESPECIE)));
		cierre.setAforo((Double) map.get(CierreProp.AFORO));
		cierre.setPosicion((Double) map.get(CierreProp.POSICION));
		cierre.setStock((Double) map.get(CierreProp.STOCK));
		return cierre;
	}

	public void compact(Date fechaLimite) {
		/*
		 * Dia dia =
		 * buildDiaFromMap(getDiaDAO().getFirstDiaLessThanFechaAsociada(fechaLimite));
		 * if (dia != null) { }
		 * 
		 */
	}

	public void addObserver(BeltzaObserver observer, ChangeType changeType) {
		List<BeltzaObserver> obsList = observers.get(changeType);
		if (obsList == null) {
			obsList = new ArrayList<BeltzaObserver>();
			observers.put(changeType, obsList);
		}
		if (!obsList.contains(observer)) {
			obsList.add(observer);
		}
	}

	public void removeObserver(BeltzaObserver observer, ChangeType changeType) {
		List<BeltzaObserver> obsList = observers.get(changeType);
		if (obsList != null) {
			obsList = new ArrayList<BeltzaObserver>();
			obsList.remove(observer);
		}
	}

	@Deprecated
	protected void notifyObservers(ChangeType changeType) {
		List<BeltzaObserver> obsList = observers.get(changeType);
		if (obsList != null) {
			for (BeltzaObserver observer : obsList) {
				logger.debug("Notificando " + observer.getClass().getName() + ":" + changeType);
				observer.modelChanged(new BeltzaEvent(changeType));
			}
		}
	}

	protected void scheduleNotification(ChangeType changeType) {
		if (!notificationsQueue.contains(changeType)) {
			notificationsQueue.add(changeType);
		}
	}
	
	protected void notifyObservers() {
		logger.debug("Notificando...");
		while (!notificationsQueue.isEmpty()) {
			ChangeType changeType = notificationsQueue.poll();
			List<BeltzaObserver> obsList = observers.get(changeType);
			if (obsList != null) {
				for (BeltzaObserver observer : obsList) {
					logger.debug("Notificando " + observer.getClass().getName() + ":" + changeType);
					ObserverNotificator notificator = new ObserverNotificator(observer, changeType);
					//notificator.run(); //Ejecuta en el mismo thread
					threadPool.execute(notificator); //Ejecuta en otro thread
				}
			}
		}
	}

	private class ObserverNotificator implements Runnable {
		private BeltzaObserver observer;
		private ChangeType changeType;
		
		public ObserverNotificator(BeltzaObserver observer, ChangeType changeType) {
			this.observer = observer;
			this.changeType = changeType;
		}
		
		public void run() {
			// Para que no de exceptions al repintar la vista
			/*
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					observer.modelChanged(new BeltzaEvent(changeType));
				}
			});
			*/
			observer.modelChanged(new BeltzaEvent(changeType));
		}
	}

	public EspecieDAO getEspecieDAO() {
		return especieDAO;
	}

	public void setEspecieDAO(EspecieDAO especieDAO) {
		this.especieDAO = especieDAO;
	}

	public ConfiguracionDAO getConfiguracionDAO() {
		return configuracionDAO;
	}

	public void setConfiguracionDAO(ConfiguracionDAO configuracionDAO) {
		this.configuracionDAO = configuracionDAO;
	}

	public OperacionDAO getOperacionDAO() {
		return operacionDAO;
	}

	public void setOperacionDAO(OperacionDAO operacionDAO) {
		this.operacionDAO = operacionDAO;
	}

	public MovimientoDAO getMovimientoDAO() {
		return movimientoDAO;
	}

	public void setMovimientoDAO(MovimientoDAO movimientoDAO) {
		this.movimientoDAO = movimientoDAO;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public DiaDAO getDiaDAO() {
		return diaDAO;
	}

	public void setDiaDAO(DiaDAO diaDAO) {
		this.diaDAO = diaDAO;
	}

	public MapDAO getMapDAO() {
		return mapDAO;
	}

	public void setMapDAO(MapDAO mapDAO) {
		this.mapDAO = mapDAO;
	}

	public void release() {
	}

	public void setCodigoEspecieDeReferencia(String codigoEspecieDeReferencia) {
		this.codigoEspecieDeReferencia = codigoEspecieDeReferencia;
	}

	public void setCodigosEspeciesMonitoreo(String codigosEspeciesMonitoreo) {
		this.codigosEspeciesMonitoreo = codigosEspeciesMonitoreo;
	}

	public CierreDAO getCierreDAO() {
		return cierreDAO;
	}

	public void setCierreDAO(CierreDAO cierreDAO) {
		this.cierreDAO = cierreDAO;
	}

	public void setUpdateDAO(UpdateDAO updateDAO) {
		this.updateDAO = updateDAO;
	}

	@Transactional
	public void runUpdates(Integer targetVersion) {
		Integer currentVersion = getVersion(); 
		if (currentVersion < targetVersion) {
			this.updateDAO.update(currentVersion, targetVersion);
			this.configuracionDAO.setVersion(targetVersion);
			this.appVersion = null;
		}
	}
}
