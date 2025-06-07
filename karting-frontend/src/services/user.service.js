import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/users/list');
}

const create = data => {
    return httpClient.post("/users/save/", data);
}

const get = id => {
    return httpClient.get(`/users/get/${id}`);
}

const update = data => {
    return httpClient.put('/users/update', data);
}

const remove = id => {
    return httpClient.delete(`/users/delete/${id}`);
}
export default { getAll, create, get, update, remove };