import httpClient from "../http-common";

// listKarts
const getAll = () => {
    return httpClient.get("/karts/list");
}

// getKart
const get = id => {
    return httpClient.get(`/karts/get/${id}`);
}

// saveKart
const create = data => {
    return httpClient.post("/karts/save", data);
}

// updateKart
const update = data => {
    return httpClient.put('/karts/update', data);
}

// deleteKart
const remove = id => {
    return httpClient.delete(`/karts/delete/${id}`);
}

// availableReserve
const getAvailable = () => {
    return httpClient.get('/karts/available');
}

export default {getAll, get, create, update, remove, getAvailable};