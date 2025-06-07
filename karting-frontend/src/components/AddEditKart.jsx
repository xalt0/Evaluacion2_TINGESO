import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import kartService from "../services/kart.service";

import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { es } from 'date-fns/locale';

const AddEditKart = () => {
    // ESTADOS DEL COMPONENTE.
    // useState permite almacenar valores en formulario.
    // useParams permite acceder de manera dinámica a la ruta.
    // useNavigate permite redirigir.
    const {id} = useParams();
    const [code, setCode] = useState("");
    const [model, setModel] = useState("");
    const [maintenance, setMaintenance] = useState("");
    const [available, setAvailable] = useState("");
    const [kartForm, setKartForm] = useState("");
    const navigate = useNavigate();

    // FUNCIONES DEL COMPONENTE.
    // Función para guardar Kart.
    const saveKart = (e) => {
        e.preventDefault();
        const kart = {id, code, model, maintenance, available};
        if (id) {
            // Si id existe: Actualizar Kart.
            kartService
                .update(kart)
                .then((response) => {
                    console.log("El Kart ha sido actualizado con éxito.", response.data);
                    navigate("/karts/list");
                })
                .catch((error) => {
                    console.log("Ha ocurrido un error al intentar actualizar un Kart.", error);
                });
            } else{
            // De lo contrario: Crear Kart.
            kartService
                .create(kart)
                .then((response) => {
                    console.log("El Kart ha sido añadido con éxito.", response.data);
                    navigate("/karts/list");
                })
                .catch((error) => {
                    console.log("Ha ocurrido un error al intentar añadir un Kart.", error);
                });
            }
        };

    // Función para cargar los datos en caso de edición.
    useEffect(() => {
        if (id) {
          setKartForm("Editar Kart");
          kartService
            .get(id)
            .then((kart) => {
              setCode(kart.data.code);
              setModel(kart.data.model);
              setMaintenance(kart.data.maintenance);
              setAvailable(kart.data.available);
            })
            .catch((error) => {
              console.log("Se ha producido un error.", error);
            });
            } else {
            setKartForm("Nuevo Kart");
        }
        }, []);

      // Renderizado del formulario.
      return (
        <Box
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          component="form"
          onSubmit={saveKart}
          sx={{
            backgroundColor: "white",
            padding: 4,
            borderRadius: 2,
            boxShadow: 3,
            gap: 2,
            width: "100%",
            maxWidth: 600,
            margin: "0 auto",
          }}
        >
          <h3>{kartForm}</h3>
          <hr />
      
          <FormControl fullWidth>
            <TextField
              id="code"
              label="Code"
              value={code}
              variant="standard"
              onChange={(e) => setCode(e.target.value)}
              helperText="E.g.: K001"
            />
          </FormControl>
      
          <FormControl fullWidth>
            <TextField
              id="model"
              label="Model"
              value={model}
              variant="standard"
              onChange={(e) => setModel(e.target.value)}
              helperText="E.g.: Sodikart RT8"
            />
          </FormControl>
      
          <FormControl fullWidth>
            <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={es}>
              <DatePicker
                label="Upcoming maintenance date."
                value={maintenance}
                onChange={(newValue) => setMaintenance(newValue)}
                renderInput={(params) => <TextField {...params} variant="standard" />}
              />
            </LocalizationProvider>
          </FormControl>
      
          <FormControl fullWidth>
            <TextField
              id="available"
              label="Availability"
              select
              value={available}
              variant="standard"
              onChange={(e) => setAvailable(e.target.value === "true")}
            >
              <MenuItem value={"true"}>Disponible</MenuItem>
              <MenuItem value={"false"}>No disponible</MenuItem>
            </TextField>
          </FormControl>
      
          <FormControl>
            <Button
              variant="contained"
              color="info"
              type="submit" // Usamos submit en lugar de onClick
              style={{ marginTop: "1rem" }}
              startIcon={<SaveIcon />}
            >
              Guardar
            </Button>
          </FormControl>
      
          <hr />
          <Link to="/karts/list">Back to List</Link>
        </Box>
      );      
};

export default AddEditKart;