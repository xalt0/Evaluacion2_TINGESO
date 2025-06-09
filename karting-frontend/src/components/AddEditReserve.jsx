import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import reserveService from "../services/reserve.service";
import userService from "../services/user.service";
import kartService from "../services/kart.service";
import planService from "../services/plan.service";

import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { es } from "date-fns/locale";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import OutlinedInput from "@mui/material/OutlinedInput";
import Checkbox from "@mui/material/Checkbox";
import ListItemText from "@mui/material/ListItemText";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";

const AddEditReserve = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [users, setUsers] = useState([]);
  const [karts, setKarts] = useState([]);
  const [plans, setPlans] = useState([]);
  const [selectedPlan, setSelectedPlan] = useState("");

  const [formTitle, setFormTitle] = useState("");
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [selectedKarts, setSelectedKarts] = useState([]);
  const [loops, setLoops] = useState(0);
  const [trackTime, setTrackTime] = useState(0);
  const [totalTime, setTotalTime] = useState(0);
  const [fee, setFee] = useState(0);
  const [scheduleDate, setScheduleDate] = useState(null);
  const [startTime, setStartTime] = useState(null);
  const [endTime, setEndTime] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      if (id) {
        setFormTitle("Editar Reserva");

        const res = await reserveService.get(id);
        const data = res.data;

        setSelectedUsers(data.userIds || []);
        setSelectedKarts(data.kartIds || []);
        setLoops(data.loops);
        setTrackTime(data.trackTime);
        setTotalTime(data.totalTime);
        setFee(data.fee);
        setScheduleDate(new Date(data.scheduleDate));
        setStartTime(new Date(`1970-01-01T${data.startTime}`));
        setEndTime(new Date(`1970-01-01T${data.endTime}`));
        setSelectedPlan(data.plan?.name || "");

        const availableRes = await kartService.getAvailable();
        const availableKarts = availableRes.data;
        const assignedKarts = data.karts || [];

        const mergedKarts = [
          ...availableKarts,
          ...assignedKarts.filter(
            (ak) => !availableKarts.some((k) => k.id === ak.id)
          ),
        ];

        setKarts(mergedKarts);
      } else {
        setFormTitle("Nueva Reserva");
        const availableRes = await kartService.getAvailable();
        setKarts(availableRes.data);
      }

      const [userRes, planRes] = await Promise.all([
        userService.getAll(),
        planService.getAll(),
      ]);

      setUsers(userRes.data);
      setPlans(planRes.data);
    };

    fetchData();
  }, [id]);
  
  // Calculo de endTime. SOLO VISUAL, LÓGICA DE NEGOCIO EN BACKEND.
  useEffect(() => {
    if (startTime instanceof Date && totalTime > 0) {
      const end = new Date(startTime);
      end.setMinutes(end.getMinutes() + totalTime);
      setEndTime(end);
    }
  }, [startTime, totalTime]);

  // Manejo del Plan seleccionado.
  const handlePlanChange = (planName) => {
    const selected = plans.find((p) => p.name === planName);
    if (selected) {
      setSelectedPlan(planName);
      setLoops(selected.loops);
      setTrackTime(selected.trackTime);
      setTotalTime(selected.totalTime);
      setFee(selected.fee);
    }
  };

  const handleSubmit = async (e) => {
  e.preventDefault();

    const payload = {
      userIds: selectedUsers,
      kartIds: selectedKarts,
      planId: plans.find((p) => p.name === selectedPlan)?.id || null,
      loops,
      trackTime,
      totalTime,
      fee,
      scheduleDate: scheduleDate?.toISOString().split("T")[0], // "YYYY-MM-DD"
      startTime: startTime?.toTimeString().slice(0, 5), // "HH:mm"
      endTime: endTime?.toTimeString().slice(0, 5),     // "HH:mm"
    };

    try {
      console.log("Payload a enviar:", payload); // 👈 Agrega este log aquí

      if (id) {
        await reserveService.update(payload); // PUT /reserves/update
      } else {
        await reserveService.create(payload); // POST /reserves/save
      }

      alert("Reserva guardada con éxito");
      navigate("/reserves/list");
    } catch (error) {
      console.error("Error al guardar reserva:", error);
      alert("Error al guardar la reserva.");
    }
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{
        backgroundColor: "white",
        padding: 4,
        borderRadius: 2,
        boxShadow: 3,
        gap: 2,
        display: "flex",
        flexDirection: "column",
        width: "100%",
        maxWidth: 600,
        margin: "0 auto",
      }}
    >
      <h3>{formTitle}</h3>
      <hr />

      {/* Usuarios */}
      <FormControl fullWidth>
        <InputLabel>Usuarios</InputLabel>
        <Select
          multiple
          value={selectedUsers}
          onChange={(e) => setSelectedUsers(e.target.value)}
          input={<OutlinedInput label="Usuarios" />}
          renderValue={(selected) =>
            users.filter((u) => selected.includes(u.id)).map((u) => u.name).join(", ")
          }
        >
          {users.map((user) => (
            <MenuItem key={user.id} value={user.id}>
              <Checkbox checked={selectedUsers.indexOf(user.id) > -1} />
              <ListItemText primary={user.name} />
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      {/* Karts */}
      <FormControl fullWidth>
        <InputLabel>Karts</InputLabel>
        <Select
          multiple
          value={selectedKarts}
          onChange={(e) => setSelectedKarts(e.target.value)}
          input={<OutlinedInput label="Karts" />}
          renderValue={(selected) =>
            karts.filter((k) => selected.includes(k.id)).map((k) => k.code).join(", ")
          }
        >
          {karts.map((kart) => (
            <MenuItem key={kart.id} value={kart.id}>
              <Checkbox checked={selectedKarts.indexOf(kart.id) > -1} />
              <ListItemText primary={kart.code} />
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      {/* Selección de Plan */}
      <FormControl fullWidth>
        <InputLabel>Plan</InputLabel>
        <Select
          value={selectedPlan}
          onChange={(e) => handlePlanChange(e.target.value)}
          input={<OutlinedInput label="Plan" />}
        >
          {plans.map((plan) => (
            <MenuItem key={plan.name} value={plan.name}>
              {plan.name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      {/* Campos derivados del plan (solo lectura) */}
      <TextField label="Vueltas" type="number" value={loops} disabled fullWidth />
      <TextField label="Tiempo en pista (min)" type="number" value={trackTime} disabled fullWidth />
      <TextField label="Tiempo total (min)" type="number" value={totalTime} disabled fullWidth />
      <TextField label="Tarifa ($)" type="number" value={fee} disabled fullWidth />

      {/* Fecha de reserva */}
      <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={es}>
        {/* Fecha */}
        <DatePicker
          label="Fecha de reserva"
          value={scheduleDate}
          onChange={(newDate) => setScheduleDate(newDate)}
          renderInput={(params) => <TextField {...params} fullWidth />}
        />

        {/* Hora de inicio */}
        <TimePicker
          label="Hora de inicio"
          value={startTime}
          onChange={(newTime) => setStartTime(newTime)}
          renderInput={(params) => <TextField {...params} fullWidth />}
        />

        {/* Hora de fin */}
        <TimePicker
          label="Hora de fin"
          value={endTime}
          onChange={(newTime) => setEndTime(newTime)}
          renderInput={(params) => <TextField {...params} fullWidth />}
        />
      </LocalizationProvider>


      <Button
        variant="contained"
        color="info"
        type="submit"
        startIcon={<SaveIcon />}
        sx={{ marginTop: 2 }}
      >
        Guardar
      </Button>

      <Link to="/reserves/list">Volver al listado</Link>
    </Box>
  );
};

export default AddEditReserve;