import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import kartService from "../services/kart.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const KartList = () => {
  const [karts, setKarts] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    kartService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los Karts.", response.data);
        setKarts(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los Karts.",
          error
        );
      });
  };

  useEffect(() => {
    init();
  }, []);
  
  const handleDelete = (id) => {
    console.log("Printing id", id);
    const confirmDelete = window.confirm(
      "¿Esta seguro que desea borrar este Kart?"
    );
    if (confirmDelete) {
      kartService
        .remove(id)
        .then((response) => {
          console.log("Kart ha sido eliminado.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar el Kart.",
            error
          );
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/karts/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link
        to="/karts/add"
        style={{ textDecoration: "none", marginBottom: "1rem" }}
      >
        <Button
          variant="contained"
          color="primary"
          startIcon={<PersonAddIcon />}
        >
          Añadir Kart
        </Button>
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Código
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Modelo
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Mantención
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Disponibilidad
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Operaciones
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {karts.map((kart) => (
            <TableRow
              key={kart.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="center">{kart.code}</TableCell>
              <TableCell align="center">{kart.model}</TableCell>
              <TableCell align="center">
                {new Date(kart.maintenance).toLocaleDateString("es-CL")}
              </TableCell>
              <TableCell align="center">{kart.available ? "Sí" : "No"}</TableCell>
              <TableCell align="center">
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(kart.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>

                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(kart.id)}
                  style={{ marginLeft: "0.5rem" }}
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

export default KartList;