import axios from "axios";

const kartingBackendServer = import.meta.env.VITE_KARTING_BACKEND_SERVER;
const kartingBackendPort = import.meta.env.VITE_KARTING_BACKEND_PORT;

const httpClient = axios.create({
  baseURL: `http://${kartingBackendServer}:${kartingBackendPort}`,
  headers: {
    "Content-Type": "application/json"
  }
});

export default httpClient;