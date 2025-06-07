import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import userService from "../services/user.service";
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

const UserList = () => {
  const [users, setUsers] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    userService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los Usuarios.", response.data);
        setUsers(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los Usuarios.",
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
      "¿Esta seguro que desea borrar este Usuario?"
    );
    if (confirmDelete) {
      userService
        .remove(id)
        .then((response) => {
          console.log("Usuario ha sido eliminado.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar al Usuario",
            error
          );
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/users/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link
        to="/users/add"
        style={{ textDecoration: "none", marginBottom: "1rem" }}
      >
        <Button
          variant="contained"
          color="primary"
          startIcon={<PersonAddIcon />}
        >
          Añadir Usuario
        </Button>
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Rut
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Nombre
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Correo
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Fidelidad
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Cumpleaños
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              Operaciones
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {users.map((user) => (
            <TableRow
              key={user.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="center">{user.rut}</TableCell>
              <TableCell align="center">{user.name}</TableCell>
              <TableCell align="center">{user.email}</TableCell>
              <TableCell align="center">{user.fidelity}</TableCell>
              <TableCell align="center">
                {new Date(user.birthdate).toLocaleDateString("es-CL")}
              </TableCell>
              <TableCell align="center">
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(user.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>

                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(user.id)}
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

export default UserList;
