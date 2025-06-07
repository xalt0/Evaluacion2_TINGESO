import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import SportsScoreIcon from '@mui/icons-material/SportsScore';
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import PaidIcon from "@mui/icons-material/Paid";
import CalculateIcon from "@mui/icons-material/Calculate";
import AnalyticsIcon from "@mui/icons-material/Analytics";
import DiscountIcon from "@mui/icons-material/Discount";
import HailIcon from "@mui/icons-material/Hail";
import MedicationLiquidIcon from "@mui/icons-material/MedicationLiquid";
import MoreTimeIcon from "@mui/icons-material/MoreTime";
import HomeIcon from "@mui/icons-material/Home";
import ScheduleIcon from '@mui/icons-material/Schedule';
import { useNavigate } from "react-router-dom";

export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const listOptions = () => (
    <Box
      role="presentation"
      onClick={toggleDrawer(false)}
    >
      <List>
        <ListItemButton onClick={() => navigate("/home")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Home" />
        </ListItemButton>

        <Divider />

        <ListItemButton onClick={() => navigate("/users/list")}>
          <ListItemIcon>
            <PeopleAltIcon />
          </ListItemIcon>
          <ListItemText primary="Usuarios" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/karts/list")}>
          <ListItemIcon>
            <SportsScoreIcon />
          </ListItemIcon>
          <ListItemText primary="Karts" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/reserves/list")}>
          <ListItemIcon>
            <ScheduleIcon />
          </ListItemIcon>
          <ListItemText primary="Reservas" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/rack")}>
          <ListItemIcon>
            <AnalyticsIcon />
          </ListItemIcon>
          <ListItemText primary="Rack" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/extraHours/list")}>
          <ListItemIcon>
            <MoreTimeIcon />
          </ListItemIcon>
          <ListItemText primary="Horas Extra" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/paycheck/list")}>
          <ListItemIcon>
            <PaidIcon />
          </ListItemIcon>
          <ListItemText primary="Planilla Sueldos" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/paycheck/calculate")}>
          <ListItemIcon>
            <CalculateIcon />
          </ListItemIcon>
          <ListItemText primary="Calcular Planilla" />
        </ListItemButton>

      </List>

      <Divider />

      <List>
        <ListItemButton onClick={() => navigate("/employee/discounts")}>
          <ListItemIcon>
            <DiscountIcon />
          </ListItemIcon>
          <ListItemText primary="Descuentos" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/paycheck/vacations")}>
          <ListItemIcon>
            <HailIcon />
          </ListItemIcon>
          <ListItemText primary="Vacaciones" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/paycheck/medicalleave")}>
          <ListItemIcon>
            <MedicationLiquidIcon />
          </ListItemIcon>
          <ListItemText primary="Licencias Medicas" />
        </ListItemButton>
      </List>
    </Box>
  );

  return (
    <div>
      <Drawer anchor={"left"} open={open} onClose={toggleDrawer(false)}>
        {listOptions()}
      </Drawer>
    </div>
  );
}
