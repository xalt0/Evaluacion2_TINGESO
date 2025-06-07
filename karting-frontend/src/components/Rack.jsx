import React, { useState, useCallback } from 'react';
import FullCalendar from '@fullcalendar/react';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import esLocale from '@fullcalendar/core/locales/es';
import { Box, CircularProgress, Popover, Typography } from '@mui/material';
import reserveService from '../services/reserve.service';

export default function WeeklyReserveRack() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const [popoverData, setPopoverData] = useState(null);

  // Carga reservas del Backend usando el rango visible
  const loadReserves = useCallback(async (startStr, endStr) => {
    setLoading(true);
    try {
      const response = await reserveService.rack(startStr, endStr);
      const mapped = response.data.map((res) => ({
        id: res.id,
        title: `Reserva ID: ${res.id}`,
        start: `${res.scheduleDate}T${res.startTime}`,
        end: `${res.scheduleDate}T${res.endTime}`,
      }));
      setEvents(mapped);
    } catch (err) {
      console.error('Error al cargar reservas:', err);
      setEvents([]);
    } finally {
      setLoading(false);
    }
  }, []);

  // Se llama cada vez que cambia el rango visible del calendario
  const handleDatesSet = (arg) => {
    const start = arg.startStr.split('T')[0];
    const end = arg.endStr.split('T')[0];
    loadReserves(start, end);
  };

  // Maneja el click sobre un evento y abre el popover
  const handleEventClick = (info) => {
    setAnchorEl(info.el);
    setPopoverData({
      id: info.event.id,
      start: info.event.start,
      end: info.event.end,
    });
  };

  const handleClosePopover = () => {
    setAnchorEl(null);
    setPopoverData(null);
  };

  return (
    <Box p={2}>
      {loading && <CircularProgress />}
      <FullCalendar
        plugins={[timeGridPlugin, interactionPlugin]}
        initialView="timeGridWeek"
        allDaySlot={false}
        slotMinTime="10:00:00"
        slotMaxTime="22:00:00"
        locale={esLocale}
        firstDay={1}
        events={events}
        height="auto"
        eventClick={handleEventClick}
        datesSet={handleDatesSet}
        titleFormat={{
          year: 'numeric',
          month: 'long',
          day: 'numeric'
        }}
      />

      <Popover
        open={Boolean(anchorEl)}
        anchorEl={anchorEl}
        onClose={handleClosePopover}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
      >
        <Box p={2}>
          <Typography variant="subtitle1">Reserva ID: {popoverData?.id}</Typography>
          <Typography variant="body2">
            Inicio: {popoverData?.start?.toLocaleTimeString()}
          </Typography>
          <Typography variant="body2">
            Fin: {popoverData?.end?.toLocaleTimeString()}
          </Typography>
        </Box>
      </Popover>
    </Box>
  );
}
