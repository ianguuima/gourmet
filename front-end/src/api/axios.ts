import axios from "axios";

const api = axios.create({
  baseURL:
    process.env.REACT_APP_API?.length === 0
      ? "http://localhost:8080"
      : process.env.REACT_APP_API,
});

export default api;
