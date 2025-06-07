import httpClient from "../http-common";

const getAll = () => {
  return httpClient.get("/reserves/plans");
};

export default { getAll };
