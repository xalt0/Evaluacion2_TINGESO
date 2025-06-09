import httpClient from "../http-common";

// listReserve
const getAll = () => {
    return httpClient.get("/reserves/list");
}

// getReserve
const get = id => {
    return httpClient.get(`/reserves/get/${id}`);
}

// saveReserve
const create = data => {
    return httpClient.post("/reserves/save", data);
}

// updateReserve
const update = data => {
    return httpClient.put('/reserves/update', data);
}

// deleteReserve
const remove = id => {
    return httpClient.delete(`/reserves/delete/${id}`);
}

// getRack
const rack = (startDate, endDate) => {
    return httpClient.get(`/reserves/rack`, {
        params: {
            startDate,
            endDate
        }
    });
};

// edit
const edit = id => {
    return httpClient.get(`/reserves/detailed/${id}`);
}

export default {getAll, get, create, update, remove, rack, edit};