import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import reserveService from "../services/reserve.service"; // Asegúrate de tener este servicio
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const ReserveList = () => {
  const [reserves, setReserves] = useState([]);
  const navigate = useNavigate();

  const init = () => {
    reserveService
      .getAll()
      .then((response) => {
        console.log("Listado de reservas:", response.data);
        setReserves(response.data);
      })
      .catch((error) => {
        console.error("Error al cargar reservas", error);
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    if (window.confirm("¿Seguro que deseas eliminar esta reserva?")) {
      reserveService
        .remove(id)
        .then(() => {
          console.log("Reserva eliminada");
          init();
        })
        .catch((error) => {
          console.error("Error al eliminar reserva", error);
        });
    }
  };

  const handleEdit = (id) => {
    navigate(`/reserves/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link to="/reserves/add" style={{ textDecoration: "none" }}>
        <Button
          variant="contained"
          color="primary"
          startIcon={<PersonAddIcon />}
        >
          Añadir Reserva
        </Button>
      </Link>
      <br /><br />
      <Table sx={{ minWidth: 650 }} size="small">
        <TableHead>
          <TableRow>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Fecha</TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Inicio</TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Fin</TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Vueltas</TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Usuarios</TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Karts</TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {reserves.map((reserve) => (
            <TableRow key={reserve.id}>
              <TableCell align="center">
                {new Date(reserve.scheduleDate).toLocaleDateString("es-CL")}
              </TableCell>
              <TableCell align="center">
                {reserve.startTime?.slice(0, 5)}
              </TableCell>
              <TableCell align="center">
                {reserve.endTime?.slice(0, 5)}
              </TableCell>
              <TableCell align="center">{reserve.loops}</TableCell>
              <TableCell align="center">
                {reserve.users?.map((user) => (
                  <div key={user.id}>
                    {user.name} — <strong>${user.finalFee?.toFixed(0) ?? '-'}</strong>
                  </div>
                ))}
              </TableCell>
              <TableCell align="center">
                {reserve.karts?.map((kart) => kart.code || kart.id).join(", ")}
              </TableCell>
              <TableCell align="center">
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(reserve.id)}
                  startIcon={<EditIcon />}
                  style={{ marginRight: "0.5rem" }}
                >
                  Editar
                </Button>
                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(reserve.id)}
                  startIcon={<DeleteIcon />}
                >
                  Eliminar
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ReserveList;