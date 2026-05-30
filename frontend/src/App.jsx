import { useState, useEffect } from "react";
import axios from "axios";

const API_URL = "http://localhost:8080/api/entradas-cine";
const ESTADOS = ["DISPONIBLE", "VENDIDA", "CANCELADA"];
const entradaVacia = {
  codigoEntrada: "", pelicula: "", sala: "",
  fechaFuncion: "", horaFuncion: "",
  precio: "", nombreCliente: "", estado: "DISPONIBLE",
};
const estadoBadge = {
  DISPONIBLE: { bg: "#0f2d1a", color: "#4ade80" },
  VENDIDA:    { bg: "#0f1e2d", color: "#60a5fa" },
  CANCELADA:  { bg: "#2d0f0f", color: "#f87171" },
};

export default function App() {
  const [entradas, setEntradas] = useState([]);
  const [modal, setModal]       = useState(false);
  const [form, setForm]         = useState(entradaVacia);
  const [editId, setEditId]     = useState(null);
  const [toast, setToast]       = useState(null);
  const [loading, setLoading]   = useState(false);
  const [deleting, setDeleting] = useState(null);

  useEffect(() => { cargar(); }, []);

  const cargar = async () => {
    setLoading(true);
    try { const r = await axios.get(API_URL); setEntradas(r.data); }
    catch { showToast("No se pudo conectar con el servidor.", "error"); }
    finally { setLoading(false); }
  };

  const showToast = (msg, type = "ok") => {
    setToast({ msg, type });
    setTimeout(() => setToast(null), 3500);
  };

  const abrirCrear  = () => { setForm(entradaVacia); setEditId(null); setModal(true); };
  const abrirEditar = (e) => {
    setForm({ codigoEntrada: e.codigoEntrada, pelicula: e.pelicula, sala: e.sala,
      fechaFuncion: e.fechaFuncion, horaFuncion: e.horaFuncion,
      precio: e.precio, nombreCliente: e.nombreCliente, estado: e.estado });
    setEditId(e.id); setModal(true);
  };
  const cerrarModal = () => { setModal(false); setEditId(null); setForm(entradaVacia); };
  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleGuardar = async () => {
    const body = { ...form, precio: parseFloat(form.precio) };
    try {
      if (editId) { await axios.put(`${API_URL}/${editId}`, body); showToast("Entrada actualizada."); }
      else        { await axios.post(API_URL, body); showToast("Entrada creada exitosamente."); }
      cerrarModal(); cargar();
    } catch (err) {
      showToast(err.response?.data?.message || "Error al guardar.", "error");
    }
  };

  const handleEliminar = async (id) => {
    if (!window.confirm("Confirmar eliminacion de esta entrada?")) return;
    setDeleting(id);
    try { await axios.delete(`${API_URL}/${id}`); showToast("Entrada eliminada."); cargar(); }
    catch { showToast("Error al eliminar.", "error"); }
    finally { setDeleting(null); }
  };

  const totalRecaudado = entradas.filter(e => e.estado === "VENDIDA")
      .reduce((s, e) => s + (e.precio || 0), 0);

  return (
      <>
        <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500;600;700&family=DM+Sans:wght@300;400;500&display=swap');
        *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
        body{background:#080810;color:#ddd6c8;font-family:'DM Sans',sans-serif;min-height:100vh}
        ::-webkit-scrollbar{width:5px}::-webkit-scrollbar-track{background:#111}::-webkit-scrollbar-thumb{background:#b8953a;border-radius:3px}

        .app{max-width:1240px;margin:0 auto;padding:36px 24px}

        .header{display:flex;align-items:center;justify-content:space-between;margin-bottom:40px;padding-bottom:24px;border-bottom:1px solid #1a1a28}
        .brand{display:flex;align-items:center;gap:14px}
        .brand-icon{width:44px;height:44px;background:linear-gradient(135deg,#c9a84c,#8a5c1e);border-radius:10px;display:grid;place-items:center;font-size:20px}
        .brand-name{font-family:'Cormorant Garamond',serif;font-size:26px;font-weight:700;color:#e8dfc8;letter-spacing:.02em}
        .brand-tag{font-size:11px;color:#5a5a6a;text-transform:uppercase;letter-spacing:.1em;margin-top:1px}
        .btn-add{background:linear-gradient(135deg,#c9a84c,#9a6e22);color:#080810;border:none;padding:11px 22px;border-radius:8px;font-weight:500;font-size:13px;cursor:pointer;font-family:'DM Sans',sans-serif;letter-spacing:.02em;transition:opacity .2s,transform .1s}
        .btn-add:hover{opacity:.88;transform:translateY(-1px)}

        .stats{display:grid;grid-template-columns:repeat(3,1fr);gap:14px;margin-bottom:28px}
        .stat{background:#0e0e18;border:1px solid #1a1a28;border-radius:12px;padding:18px 22px}
        .stat-n{font-family:'Cormorant Garamond',serif;font-size:34px;font-weight:700;color:#c9a84c;line-height:1}
        .stat-l{font-size:11px;color:#5a5a6a;text-transform:uppercase;letter-spacing:.08em;margin-top:5px}

        .panel{background:#0e0e18;border:1px solid #1a1a28;border-radius:14px;overflow:hidden}
        .panel-head{display:flex;align-items:center;justify-content:space-between;padding:18px 22px;border-bottom:1px solid #1a1a28}
        .panel-title{font-family:'Cormorant Garamond',serif;font-size:17px;font-weight:600;color:#c9a84c}
        .panel-count{font-size:12px;color:#4a4a5a;background:#14141e;padding:3px 10px;border-radius:20px}

        table{width:100%;border-collapse:collapse}
        th{padding:11px 16px;text-align:left;font-size:10px;font-weight:500;color:#4a4a5a;text-transform:uppercase;letter-spacing:.1em;border-bottom:1px solid #1a1a28;background:#09090f}
        td{padding:13px 16px;font-size:13px;color:#aaa;border-bottom:1px solid #141420;vertical-align:middle}
        tbody tr{transition:background .12s}
        tbody tr:hover{background:#111120}
        tbody tr:last-child td{border-bottom:none}

        .film-title{font-weight:500;color:#ddd6c8}
        .code{font-family:monospace;font-size:11px;color:#c9a84c;background:#1a1408;padding:2px 8px;border-radius:4px;border:1px solid #2a2008}
        .price{font-weight:500;color:#ddd6c8}

        .badge{display:inline-block;padding:3px 10px;border-radius:20px;font-size:11px;font-weight:500;letter-spacing:.04em}
        .actions{display:flex;gap:7px}
        .btn-e{background:transparent;border:1px solid #c9a84c33;color:#c9a84c;padding:5px 13px;border-radius:6px;font-size:12px;cursor:pointer;transition:all .15s;font-family:'DM Sans',sans-serif}
        .btn-e:hover{background:#c9a84c18;border-color:#c9a84c88}
        .btn-d{background:transparent;border:1px solid #f8717133;color:#f87171;padding:5px 13px;border-radius:6px;font-size:12px;cursor:pointer;transition:all .15s;font-family:'DM Sans',sans-serif}
        .btn-d:hover{background:#f8717118;border-color:#f8717188}
        .btn-d:disabled{opacity:.35;cursor:not-allowed}

        .empty{text-align:center;padding:56px;color:#3a3a4a;font-size:13px}

        .overlay{position:fixed;inset:0;background:rgba(0,0,0,.82);backdrop-filter:blur(6px);display:flex;align-items:center;justify-content:center;z-index:100;padding:20px}
        .modal{background:#0e0e18;border:1px solid #252535;border-radius:18px;width:100%;max-width:600px;max-height:90vh;overflow-y:auto}
        .modal-h{display:flex;align-items:center;justify-content:space-between;padding:22px 26px;border-bottom:1px solid #1a1a28}
        .modal-t{font-family:'Cormorant Garamond',serif;font-size:21px;font-weight:700;color:#ddd6c8}
        .modal-x{background:none;border:none;color:#555;font-size:24px;cursor:pointer;line-height:1;transition:color .15s;padding:0 4px}
        .modal-x:hover{color:#ddd6c8}
        .modal-b{padding:26px}
        .fgrid{display:grid;grid-template-columns:1fr 1fr;gap:16px}
        .fgroup{display:flex;flex-direction:column;gap:5px}
        .fgroup.span2{grid-column:1/-1}
        label{font-size:10px;font-weight:500;color:#6a6a7a;text-transform:uppercase;letter-spacing:.08em}
        input,select{background:#090910;border:1px solid #252535;color:#ddd6c8;padding:10px 13px;border-radius:8px;font-size:13px;font-family:'DM Sans',sans-serif;outline:none;transition:border-color .15s;width:100%}
        input:focus,select:focus{border-color:#c9a84c}
        input::placeholder{color:#3a3a4a}
        select option{background:#0e0e18}
        .modal-f{padding:18px 26px;border-top:1px solid #1a1a28;display:flex;gap:10px;justify-content:flex-end}
        .btn-cancel{background:transparent;border:1px solid #252535;color:#666;padding:10px 20px;border-radius:8px;cursor:pointer;font-family:'DM Sans',sans-serif;font-size:13px;transition:all .15s}
        .btn-cancel:hover{border-color:#444;color:#aaa}
        .btn-save{background:linear-gradient(135deg,#c9a84c,#9a6e22);color:#080810;border:none;padding:10px 26px;border-radius:8px;cursor:pointer;font-family:'DM Sans',sans-serif;font-weight:500;font-size:13px;transition:opacity .15s}
        .btn-save:hover{opacity:.88}

        .toast{position:fixed;bottom:24px;right:24px;padding:13px 18px;border-radius:10px;font-size:13px;font-weight:500;z-index:999;animation:slideUp .25s ease;max-width:300px;line-height:1.4}
        .toast.ok{background:#0f2d1a;border:1px solid #4ade8055;color:#4ade80}
        .toast.error{background:#2d0f0f;border:1px solid #f8717155;color:#f87171}
        @keyframes slideUp{from{opacity:0;transform:translateY(10px)}to{opacity:1;transform:translateY(0)}}

        @media(max-width:700px){.stats{grid-template-columns:1fr}.fgrid{grid-template-columns:1fr}.header{flex-direction:column;align-items:flex-start;gap:14px}th:nth-child(4),th:nth-child(5),th:nth-child(7),td:nth-child(4),td:nth-child(5),td:nth-child(7){display:none}}
      `}</style>

        <div className="app">
          <header className="header">
            <div className="brand">
              <div className="brand-icon">🎞</div>
              <div>
                <div className="brand-name">CineVault</div>
                <div className="brand-tag">Sistema de Gestión de Entradas</div>
              </div>
            </div>
            <button className="btn-add" onClick={abrirCrear}>+ Nueva Entrada</button>
          </header>

          <div className="stats">
            <div className="stat">
              <div className="stat-n">{entradas.length}</div>
              <div className="stat-l">Total Entradas</div>
            </div>
            <div className="stat">
              <div className="stat-n">{entradas.filter(e => e.estado === "DISPONIBLE").length}</div>
              <div className="stat-l">Disponibles</div>
            </div>
            <div className="stat">
              <div className="stat-n">${totalRecaudado.toLocaleString()}</div>
              <div className="stat-l">Recaudado</div>
            </div>
          </div>

          <div className="panel">
            <div className="panel-head">
              <span className="panel-title">Registro de Entradas</span>
              <span className="panel-count">{entradas.length} registros</span>
            </div>
            <table>
              <thead>
              <tr>
                <th>Código</th><th>Película</th><th>Sala</th>
                <th>Fecha</th><th>Hora</th><th>Precio</th>
                <th>Cliente</th><th>Estado</th><th>Acciones</th>
              </tr>
              </thead>
              <tbody>
              {loading ? (
                  <tr><td colSpan={9} className="empty">Cargando registros...</td></tr>
              ) : entradas.length === 0 ? (
                  <tr><td colSpan={9} className="empty">Sin registros. Crea la primera entrada.</td></tr>
              ) : entradas.map(e => {
                const b = estadoBadge[e.estado] || { bg:"#1a1a1a", color:"#aaa" };
                return (
                    <tr key={e.id}>
                      <td><span className="code">{e.codigoEntrada}</span></td>
                      <td><span className="film-title">{e.pelicula}</span></td>
                      <td>{e.sala}</td>
                      <td>{e.fechaFuncion}</td>
                      <td>{e.horaFuncion}</td>
                      <td><span className="price">${Number(e.precio).toLocaleString()}</span></td>
                      <td>{e.nombreCliente}</td>
                      <td><span className="badge" style={{background:b.bg,color:b.color}}>{e.estado}</span></td>
                      <td>
                        <div className="actions">
                          <button className="btn-e" onClick={() => abrirEditar(e)}>Editar</button>
                          <button className="btn-d" disabled={deleting===e.id} onClick={() => handleEliminar(e.id)}>
                            {deleting===e.id ? "..." : "Eliminar"}
                          </button>
                        </div>
                      </td>
                    </tr>
                );
              })}
              </tbody>
            </table>
          </div>
        </div>

        {modal && (
            <div className="overlay" onClick={ev => ev.target.classList.contains("overlay") && cerrarModal()}>
              <div className="modal">
                <div className="modal-h">
                  <span className="modal-t">{editId ? "Editar Entrada" : "Nueva Entrada"}</span>
                  <button className="modal-x" onClick={cerrarModal}>×</button>
                </div>
                <div className="modal-b">
                  <div className="fgrid">
                    <div className="fgroup">
                      <label>Código de Entrada</label>
                      <input name="codigoEntrada" value={form.codigoEntrada} onChange={handleChange} placeholder="ENT-001" />
                    </div>
                    <div className="fgroup">
                      <label>Estado</label>
                      <select name="estado" value={form.estado} onChange={handleChange}>
                        {ESTADOS.map(s => <option key={s} value={s}>{s}</option>)}
                      </select>
                    </div>
                    <div className="fgroup span2">
                      <label>Película</label>
                      <input name="pelicula" value={form.pelicula} onChange={handleChange} placeholder="Nombre de la película" />
                    </div>
                    <div className="fgroup">
                      <label>Sala</label>
                      <input name="sala" value={form.sala} onChange={handleChange} placeholder="Sala 1 - IMAX" />
                    </div>
                    <div className="fgroup">
                      <label>Precio</label>
                      <input type="number" name="precio" value={form.precio} onChange={handleChange} placeholder="15000" />
                    </div>
                    <div className="fgroup">
                      <label>Fecha Función</label>
                      <input type="date" name="fechaFuncion" value={form.fechaFuncion} onChange={handleChange} />
                    </div>
                    <div className="fgroup">
                      <label>Hora Función</label>
                      <input type="time" name="horaFuncion" value={form.horaFuncion} onChange={handleChange} />
                    </div>
                    <div className="fgroup span2">
                      <label>Nombre del Cliente</label>
                      <input name="nombreCliente" value={form.nombreCliente} onChange={handleChange} placeholder="Nombre completo" />
                    </div>
                  </div>
                </div>
                <div className="modal-f">
                  <button className="btn-cancel" onClick={cerrarModal}>Cancelar</button>
                  <button className="btn-save" onClick={handleGuardar}>{editId ? "Guardar cambios" : "Crear entrada"}</button>
                </div>
              </div>
            </div>
        )}

        {toast && <div className={`toast ${toast.type}`}>{toast.msg}</div>}
      </>
  );
}