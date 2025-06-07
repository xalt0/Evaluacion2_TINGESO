import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import userService from "../services/user.service";

import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { es } from 'date-fns/locale';

const AddEditUser = () => {
  const [rut, setRut] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [fidelity, setFidelity] = useState("");
  const [birthdate, setBirthdate] = useState("");
  const { id } = useParams();
  const [userForm, setUserForm] = useState("");
  const navigate = useNavigate();

  const saveUser = (e) => {
    e.preventDefault();

    const user = { rut, name, email, fidelity, birthdate, id };
    if (id) {
      //Actualizar Datos Empelado
      userService
        .update(user)
        .then((response) => {
          console.log("Usuario ha sido actualizado.", response.data);
          navigate("/users/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar actualizar datos del Usuario.",
            error
          );
        });
    } else {
      //Crear nuevo usuario
      userService
        .create(user)
        .then((response) => {
          console.log("Usuario ha sido añadido.", response.data);
          navigate("/users/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar crear nuevo Usuario.",
            error
          );
        });
    }
  };

  useEffect(() => {
    if (id) {
      setUserForm("Editar Usuario");
      userService
        .get(id)
        .then((user) => {
          setRut(user.data.rut);
          setName(user.data.name);
          setEmail(user.data.email);
          setFidelity(user.data.fidelity);
          setBirthdate(user.data.birthdate);
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setUserForm("Nuevo Usuario");
    }
  }, []);

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
    >
      <h3> {userForm} </h3>
      <hr />
      <form>
        <FormControl fullWidth>
          <TextField
            id="rut"
            label="Rut"
            value={rut}
            variant="standard"
            onChange={(e) => setRut(e.target.value)}
            helperText="Ej. 12.587.698-8"
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="name"
            label="Name"
            value={name}
            variant="standard"
            onChange={(e) => setName(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="email"
            label="Correo electrónico"
            type="email"
            value={email}
            variant="standard"
            onChange={(e) => setEmail(e.target.value)}
            helperText="Ej. nombre@correo.com"
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="fidelity"
            label="Fidelity"
            type="number"
            value={fidelity}
            variant="standard"
            onChange={(e) => setFidelity(e.target.value)}
            helperText="Cantidad de veces que ha venido a KartingRM."
          />
        </FormControl>

        <FormControl fullWidth>
            <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={es}>
              <DatePicker
                label="Birthdate."
                value={birthdate}
                onChange={(newValue) => setBirthdate(newValue)}
                renderInput={(params) => <TextField {...params} variant="standard" />}
              />
            </LocalizationProvider>
          </FormControl>

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(e) => saveUser(e)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Guardar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/users/list">Back to List</Link>
    </Box>
  );
};

export default AddEditUser;
