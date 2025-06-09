import httpClient from "../http-common";

const getAll = () => {
  return httpClient.get("/plans/list");
};

export default { getAll };
