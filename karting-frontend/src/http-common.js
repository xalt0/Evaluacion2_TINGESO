import axios from "axios";

const kartingBackendServer = import.meta.env.VITE_KARTING_BACKEND_SERVER; // http://192.168.1.114
const kartingBackendPort = import.meta.env.VITE_KARTING_BACKEND_PORT;     // 80

const httpClient = axios.create({
  baseURL: `${kartingBackendServer}:${kartingBackendPort}`,
  headers: {
    "Content-Type": "application/json"
  }
});

export default httpClient;